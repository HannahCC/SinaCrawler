package org.cl.main.dp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.cl.configuration.Config;

import net.sf.json.JSONObject;

public class Deduplicate {

	public static void main(String args[]) throws IOException{
		deduplicateJSONFile("UserInfo0.txt","UserInfo0.txt.c","id");
		deduplicateJSONFile("UserInfo1.txt","UserInfo1.txt.c","id");
		//deduplicateJSONFile("UserInfoOfEnterprise0.txt","UserInfoOfEnterprise0.txt.c","id");
		deduplicateJSONFile("UserInfoOfEnterprise1.txt","UserInfoOfEnterprise1.txt.c","id");
		deduplicateJSONFile("UidInfo_follows0.txt","UidInfo_follows0.txt.c","id");
		deduplicateJSONFile("UidInfo_friends0.txt","UidInfo_friends0.txt.c","id");
		deduplicateJSONFile("UidInfo_follows1.txt","UidInfo_follows1.txt.c","id");
		deduplicateJSONFile("UidInfo_friends1.txt","UidInfo_friends1.txt.c","id");
		//deduplicateCOMFile("ExpandID0.txt","ExpandID0.txt.c","\\s",0);
		deduplicateCOMFile("ExpandID1.txt","ExpandID1.txt.c","\\s",0);
		//deduplicateCOMFile("Config\\UserNotExist.txt","Config\\UserNotExist.txt.c","\\s",0);
	}

	private static void deduplicateCOMFile(String srcFile, String resFile,String regex,int i) throws IOException {
		Set<String> key_Set = new HashSet<String>();
		File  f1 = new File(Config.SAVE_PATH+srcFile);
		BufferedReader br = new BufferedReader(new FileReader(f1));
		File  f2 = new File(Config.SAVE_PATH+resFile);
		BufferedWriter bw = new BufferedWriter(new FileWriter(f2));
		String line = null;
		while((line = br.readLine())!=null){
			String id = line.split(regex)[i];
			if(!key_Set.contains(id)){
				key_Set.add(id);
				bw.write(line+"\r\n");
			}
		}
		br.close();
		bw.flush();
		bw.close();
	}

	private static void deduplicateJSONFile(String srcFile, String resFile,String key) throws IOException {
		Set<String> key_Set = new HashSet<String>();
		File  f1 = new File(Config.SAVE_PATH+srcFile);
		BufferedReader br = new BufferedReader(new FileReader(f1));
		File  f2 = new File(Config.SAVE_PATH+resFile);
		BufferedWriter bw = new BufferedWriter(new FileWriter(f2));
		String line = null;JSONObject json = null;
		while((line = br.readLine())!=null){
			json = JSONObject.fromObject(line);
			String id = json.getString(key).intern();
			if(!key_Set.contains(id)){
				key_Set.add(id);
				bw.write(line+"\r\n");
			}
		}
		br.close();
		bw.flush();
		bw.close();
		
	}
}
