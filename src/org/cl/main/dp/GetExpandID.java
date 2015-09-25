package org.cl.main.dp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

import org.cl.configuration.Config;
import org.cl.service.GetInfo;

public class GetExpandID {

	public static void main(String args[]) throws IOException{
		//从朋友ID中得到ExpandID
		getUID("UidInfo_friends0.txt","ExpandID1.txt");
		getUID("UidInfo_follows0.txt","ExpandID1.txt");
		
		
		/*//将ExpandID1中不存在的ID及其朋友ID从UidInfo_follows1、UidInfo_friends1中去除
		Set<String> set = new HashSet<String>();
		GetInfo.getSet("ExpandID1.txt", set);
		delUser(set,"UidInfo_follows1.txt");
		delUser(set,"UidInfo_friends1.txt");

		//将ExpandID1中不存在的ID信息从UserInfo.txt中去除
		set.clear();
		GetInfo.getSet("ExpandID1.txt", set,"\\s",0);
		delUser(set,"UserInfo0.txt");
		delUser(set,"UserInfoOfEnterprise0.txt");*/


	}
	private static void delUser(Set<String> set, String filename) throws IOException {
		File f = new File(Config.SAVE_PATH+filename);
		BufferedReader b = new BufferedReader(new FileReader(f));
		File f2 = new File(Config.SAVE_PATH+filename+".del");
		BufferedWriter w = new BufferedWriter(new FileWriter(f2,true));
		String line = "";
		while((line = b.readLine())!=null){
			if(line.equals(""))continue;
			JSONObject uidinfo = JSONObject.fromObject(line);
			String uid = uidinfo.getString("id");
			if(set.contains(uid)){
				w.write(line+"\r\n");
			}
		}
		w.flush();
		w.close();
		b.close();
	}

	private static void getUID(String srcfile,String resfile) throws IOException {
		File f = new File(Config.SAVE_PATH+srcfile);
		BufferedReader b = new BufferedReader(new FileReader(f));
		File f2 = new File(Config.SAVE_PATH+resfile);
		BufferedWriter w = new BufferedWriter(new FileWriter(f2));
		String line = "";
		while((line = b.readLine())!=null){
			JSONObject uidinfo = JSONObject.fromObject(line);
			@SuppressWarnings("unchecked")
			List<String> uids = (List<String>) uidinfo.get("uids");
			for(String uid : uids){
				w.write(uid+"\r\n");
			}
		}
		w.flush();
		w.close();
		b.close();
	}
}
