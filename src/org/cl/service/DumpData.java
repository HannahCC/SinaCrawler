package org.cl.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Iterator;

import org.cl.configuration.Config;


public class DumpData {
	public static void dumpData(String srcFile,String desFile) throws IOException{
		File f=new File(Config.SAVE_PATH+srcFile);
		RandomAccessFile ra = new RandomAccessFile(f,"r");
		HashSet<String> fakeOrID = new HashSet<String>();
		String exid = "";
		while((exid = ra.readLine())!=null){
			fakeOrID.add(exid);
		}
		ra.close();
		f.delete();
		
		File ff=new File(Config.SAVE_PATH+desFile);
		if(ff.exists()){
			ff.delete();
			ff=new File(Config.SAVE_PATH+desFile);
		}
		BufferedWriter w = new BufferedWriter( new FileWriter(ff));
		Iterator<String> it = fakeOrID.iterator();
		while(it.hasNext()){
			String id = it.next();
			w.write(id);
			w.newLine();
		}
		w.close();
	}
}
