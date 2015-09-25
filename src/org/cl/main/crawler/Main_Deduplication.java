package org.cl.main.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import net.sf.json.JSONObject;

import org.cl.configuration.Config;
/**
 * 对文件去重
 * 去重后需要手动修改文件名，保证其他程序正常使用
 * @author Hannah
 */
public class Main_Deduplication {

	public static void main(String args[]) throws IOException {
		userInfo_deduplicate("UserInfo0.txt","UserInfo0_deduplicated.txt");
		userInfo_deduplicate("UserInfo1.txt","UserInfo1_deduplicated.txt");
		userInfo_deduplicate("UserInfoOfEnterprise0.txt","UserInfoOfEnterprise0_deduplicated.txt");
		userInfo_deduplicate("UserInfoOfEnterprise1.txt","UserInfoOfEnterprise1_deduplicated.txt");
		userNotExist_deduplicate("Config\\UserNotExist.txt","Config\\UserNotExist_deduplicated.txt");
		
	}

	private static void userNotExist_deduplicate(String src, String res) throws IOException {
		Set<String> id_set = new HashSet<String>();
		File f = new File(Config.SAVE_PATH+src);
		BufferedReader r = new BufferedReader(new FileReader(f));
		File f2 = new File(Config.SAVE_PATH+res);
		BufferedWriter w = new BufferedWriter(new FileWriter(f2));
		String id = null;
		while(null!=(id=r.readLine())){
			if(id.equals(""))continue;
			if(!id_set.contains(id)){
				id_set.add(id);
				w.write(id+"\r\n");
			}
		}
		r.close();
		w.flush();w.close();
	}

	private static void userInfo_deduplicate(String src, String res) throws IOException {
		Set<String> id_set = new HashSet<String>();
		File f = new File(Config.SAVE_PATH+src);
		BufferedReader r = new BufferedReader(new FileReader(f));
		File f2 = new File(Config.SAVE_PATH+res);
		BufferedWriter w = new BufferedWriter(new FileWriter(f2));
		String line = null;
		while(null!=(line=r.readLine())){
			if(line.equals(""))continue;
			JSONObject jsonObject = JSONObject.fromObject(line);
			String id = jsonObject.getString("id");
			if(!id_set.contains(id)){
				id_set.add(id);
				w.write(line+"\r\n");
			}
		}
		r.close();
		w.flush();w.close();
	}
}
