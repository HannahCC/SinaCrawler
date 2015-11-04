package org.cl.main.dp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.cl.configuration.Config;
import org.cl.service.GetInfo;
import org.cl.service.SaveInfo;

public class SpiltUserID {

	/**
	 * 将用户ID分成N份供爬取
	 * @param args
	 * @throws IOException 
	 */
	static String dir = "ExpandID0_sy\\";
	static String id_source = "ExpandID1_usersy";
	static int groups = 5;//将id_source分为groups份
	static int max = 100000;
	public static void main(String args[]) throws IOException{
		SaveInfo.mkdir(dir);
		Set<String> id_set = new TreeSet<String>();
		GetInfo.getSet(id_source+".txt", id_set);
		Iterator<String> it = id_set.iterator();
		int size = id_set.size()/groups;
		//int size = 15;
		for(int i=1;i<groups;i++){
			writeId(i,size,it);
		}
		writeId(groups,max,it);
	}

	private static void writeId(int i, int size, Iterator<String> it) throws IOException {
		File f = new File(Config.SAVE_PATH+dir+id_source+i+".txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		int k = 0;
		while (k++<size&&it.hasNext()) {
			String id = it.next();
			bw.write(id+"\r\n");
		}
		bw.flush();bw.close();
	}
}
