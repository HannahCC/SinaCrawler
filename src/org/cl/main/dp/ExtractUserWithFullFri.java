package org.cl.main.dp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONObject;

import org.cl.configuration.Config;
import org.cl.service.GetInfo;
import org.cl.service.SaveInfo;

import sina.utils.Utils;

/**
 * 将种子Id根据 未获取个人信息的 朋友数从小到大排序
 * @author Hannah
 */
public class ExtractUserWithFullFri {
	static Map<String, Set<String>> follow_set_map = new HashMap<String, Set<String>>();
	static Map<String, Set<String>> friend_set_map = new HashMap<String, Set<String>>();
	static List<String> idList = new ArrayList<String>();

	public static void main(String args[]) throws IOException{
		Set<String> idSet = new HashSet<String>();//存放不存在 或已经获取 的ID
		GetInfo.getSetMap("UidInfo_follows0.txt", follow_set_map,"id","uids");//获取所有的 id:fri_id_set对
		GetInfo.getSetMap("UidInfo_friends0.txt", friend_set_map,"id","uids");//获取所有的 id:fol_id_set对
		GetInfo.getSet("Config\\UserNotExist.txt", idSet);
		System.out.println("获取到所有未存在用户ID"+idSet.size());
		getSet("UserInfo0.txt", idSet, "id");
		System.out.println("获取到所有已爬的第一层用户ID"+idSet.size());
		getSet("UserInfoOfEnterprise0.txt", idSet, "id");
		System.out.println("获取到所有已爬的第一层企业用户ID"+idSet.size());
		getSet("UserInfoOfEnterprise1.txt", idSet, "id");
		System.out.println("获取到所有已爬的第二层企业用户ID"+idSet.size());
		getSet("UserInfo1.txt", idSet, "id");
		System.out.println("获取到所有已爬的第二层用户ID"+idSet.size());
		filterId(idSet);//将follow_set_map、friend_set_map中不存在 或已经获取 的ID剔除，并按照未爬取朋友数将originalId从小到大排列
		idSet.clear();
		SaveInfo.saveList("ExpandID0_FriInfoMeiPaShu.txt", idList, false);
	}

	private static void filterId(Set<String> idSet) throws IOException {
		Map<String, Integer> size_map = new HashMap<String, Integer>();//存储id：未爬取朋友数
		for(Entry<String, Set<String>> entry : follow_set_map.entrySet()){
			String original_id = entry.getKey();
			Set<String> follow_id_set = entry.getValue();
			Set<String> friend_id_set = friend_set_map.get(original_id);
			for(String id : idSet){
				if(follow_id_set.contains(id)){follow_id_set.remove(id);}
				if(friend_id_set.contains(id)){friend_id_set.remove(id);}
			}
			size_map.put(original_id, follow_id_set.size()+friend_id_set.size());
		}
		Utils.mapSortByValueInteger(idList, size_map);
	}
	
	private static void getSet(String filename, Set<String> set, String key) throws IOException {
		File f = new File(Config.SAVE_PATH+filename);
		BufferedReader b = new BufferedReader(new FileReader(f));
		String line = "";
		while((line = b.readLine())!=null){
			JSONObject uidinfo = JSONObject.fromObject(line);
			set.add(uidinfo.getString(key));
		}
		b.close();
	}
}
