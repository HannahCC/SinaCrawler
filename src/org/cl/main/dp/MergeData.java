package org.cl.main.dp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.cl.configuration.Config;
import org.cl.model.UidInfo;
import org.cl.service.GetInfo;
import org.cl.service.SaveInfo;

public class MergeData {
	/**
	 * 将分离的数据整合到同一个文档中（通常是分开爬的数据，去重合并到主文档中）
	 * @throws IOException 
	 */
	public static void main(String args[]) throws IOException{
		MergeJSON("UserInfo1.txt","ExpandID0_sy\\UserInfo1.txt");
		MergeJSON("UserInfoOfEnterprise1.txt","ExpandID0_sy\\UserInfoOfEnterprise1.txt");
		MergeText("Config\\UserNotExist.txt","ExpandID0_sy\\UserNotExist.txt");
		MergeJSON("UidInfo_follows1.txt","ExpandID0_sy\\UidInfo_follows1.txt","ExpandID0_sy\\UidInfo_follows1 (2).txt");
		MergeJSON("UidInfo_friends1.txt","ExpandID0_sy\\UidInfo_friends1.txt","ExpandID0_sy\\UidInfo_friends1 (2).txt");
	}

	private static void MergeText(String srcfile, String  ... newfiles) throws IOException {
		Set<String> id_rel_set = new HashSet<String>();
		for(String newfile : newfiles){
			GetInfo.getSet(newfile, id_rel_set);
			SaveInfo.saveSet(srcfile, id_rel_set, true);
			id_rel_set.clear();
		}
	}

	private static void MergeRelWithFilter(String srcfile,String newfile) throws IOException {
		//读取新文件的用户ID及关系数据
		Map<String, Set<String>> new_id_map = new HashMap<String, Set<String>>();
		Map<String, Integer> new_id_size_map = new HashMap<String, Integer>();
		GetInfo.getSetMap(newfile, new_id_map, "id", "uids");
		GetInfo.getMap(newfile, new_id_size_map, "id", "total_number");
		//遍历主文件，若原ID存在于新new_id_map综合两者并写入文件
		File f = new File(Config.SAVE_PATH+srcfile);
		BufferedReader b = new BufferedReader(new FileReader(f));
		File f1 = new File(Config.SAVE_PATH+srcfile+".new");
		BufferedWriter w = new BufferedWriter(new FileWriter(f1,true));
		String line = "";int number = 0;
		while((line = b.readLine())!=null){
			JSONObject json = JSONObject.fromObject(line);
			String id = json.getString("id");
			if(new_id_map.containsKey(id)){//原ID存在于新new_id_map,合并两UIDS,重新写入文件,并将ID从new_id_map中删除
				number++;
				@SuppressWarnings("unchecked")
				List<String> lists = (List<String>) json.get("uids");
				Set<String> uids = new_id_map.get(id);
				uids.addAll(lists);
				int total_number = new_id_size_map.get(id);
				total_number = total_number>uids.size()?total_number:uids.size();
				UidInfo ids_rel = new UidInfo();
				ids_rel.setId(id);
				ids_rel.setUids(uids);
				ids_rel.setTotal_number(total_number);
				JSONObject jsonobj=JSONObject.fromObject(ids_rel);
				line = jsonobj.toString();
				new_id_map.remove(id);
				new_id_size_map.remove(id);
			}
			//原ID不存在于新new_id_map,直接重新写入文件
			w.write(line+"\r\n");
		}
		System.out.println("重复的爬取的ID关系数据有："+number);
		b.close();
		//将剩余的新的ID关系数据写入文件
		for(String id:new_id_map.keySet()){
			UidInfo ids_rel = new UidInfo();
			ids_rel.setId(id);
			ids_rel.setUids(new_id_map.get(id));
			ids_rel.setTotal_number(new_id_size_map.get(id));
			JSONObject jsonobj=JSONObject.fromObject(ids_rel);
			line = jsonobj.toString();
			w.write(line+"\r\n");
		}
		w.flush();
		w.close();
	}

	private static void MergeJSON(String srcfile, String ... newfiles) throws IOException {
		//读取主文件ID
		Set<String> id_set = new HashSet<String>();
		GetInfo.getSet(id_set, srcfile, "id");
		//遍历新文件，若ID不存在于id_set中，则追加写入主文件
		File f1 = new File(Config.SAVE_PATH+srcfile);
		BufferedWriter w = new BufferedWriter(new FileWriter(f1,true));
		for(String newfile:newfiles){
			File f = new File(Config.SAVE_PATH+newfile);
			BufferedReader b = new BufferedReader(new FileReader(f));
			String line = "";int number = 0;
			while((line = b.readLine())!=null){
				JSONObject json = JSONObject.fromObject(line);
				String id = json.getString("id");
				if(id_set.contains(id)){
					number++;
				}else{
					w.write(line+"\r\n");
				}
			}
			System.out.println("重复的爬取的ID用户信息数据有："+number);
			b.close();
		}
		w.flush();
		w.close();
	}

	public static void getSet(String filename, Set<String> set, String keys) throws IOException {
		File f = new File(Config.SAVE_PATH+filename);
		BufferedReader b = new BufferedReader(new FileReader(f));
		String line = "";
		while((line = b.readLine())!=null){
			JSONObject uidinfo = JSONObject.fromObject(line);
			set.add(uidinfo.getString(keys));
		}
		b.close();
	}
}
