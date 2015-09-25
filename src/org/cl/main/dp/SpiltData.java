package org.cl.main.dp;

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
import org.cl.service.GetInfo;
import org.cl.service.SaveInfo;

public class SpiltData {

	/**
	 * 根据ID，从主文件中分离出小文件，便于后续处理
	 * @param args
	 * @throws IOException 
	 */
	static Set<String> id_set = new HashSet<String>();
	static String new_root = "D:\\Project_DataMinning\\Data\\Sina_res\\Sina_AgePre_JSON_1000\\mute\\";
	public static void main(String args[]) throws IOException{
		GetInfo.getSet("ExpandID0_mute.txt", id_set);
		/*SpiltJsonData("UserInfo0.txt");
		SpiltJsonData("UserInfoOfEnterprise0.txt");
		SpiltJsonData("UidInfo_follows0.txt");
		SpiltJsonData("UidInfo_friends0.txt");*/
		SpiltWeiboData("Weibos");
		SpiltWeiboData("Weibos_Participle");
		SpiltWeiboData("WeibosCon");
		/*GetInfo.getSet("ExpandID1_mute.txt", id_set);
		SpiltJsonData("UserInfo1.txt");
		SpiltJsonData("UserInfoOfEnterprise1.txt");
		SpiltJsonData("UidInfo_follows1.txt");
		SpiltJsonData("UidInfo_friends1.txt");*/
	}

	private static void SpiltWeiboData(String srcDir) {
		SaveInfo.mkdir(new_root+srcDir);
		File src = new File(Config.SAVE_PATH+srcDir);
		File[] srcfiles = src.listFiles();
		for(File f : srcfiles){
			String id = f.getName().replace(".txt", "");
			if(id_set.contains(id)){
				SaveInfo.fileCopy(f, new_root+srcDir+"\\"+f.getName());
			}
		}
 	}

	private static void SpiltJsonData(String srcfile) throws IOException {
		File r=new File(Config.SAVE_PATH+srcfile);
		BufferedReader br=new BufferedReader(new FileReader(r));
		File f = new File(new_root+srcfile);
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));

		String line="";
		while((line=br.readLine())!=null)
		{
			if(line.equals("")){continue;}
			JSONObject json = JSONObject.fromObject(line);
			String id = json.getString("id");
			if(id_set.contains(id)){bw.write(line+"\r\n");}
		}
		br.close();
		bw.flush();bw.close();

	}
}
