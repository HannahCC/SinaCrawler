package org.cl.test;

import java.io.IOException;

import org.cl.run.GetWeiboSrc;

public class testWeiboSrc {
	public static void main(String args[]) throws IOException{
		String uid = "1040943140";
		String result_filename = "WeibosSrc\\Src_map.txt";
		GetWeiboSrc getWeiboSrc=new GetWeiboSrc(uid,result_filename);
		getWeiboSrc.run();
	}
}
