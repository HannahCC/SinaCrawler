package org.cl.run;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cl.service.GetInfo;
import org.cl.service.SaveInfo;

import sina.utils.Utils;

public class GetWeiboSrc implements Runnable{
	/**用户ID*/
	private String uid;
	private String result_file;

	public GetWeiboSrc(String uid, String result_file)
	{
		this.uid=uid;
		this.result_file = result_file;
	}

	public void run()
	{
		Map<String,Integer> src_map = new HashMap<String,Integer>();
		System.out.println("Getting weiboSrc of "+uid);
		try {
			List<String> src_list = new ArrayList<String>();
			GetInfo.getList("\\Weibos\\"+uid+".txt",src_list,false,"weibo","source");
			List<String> spilted_src_list = new ArrayList<String>();
			for(String src : src_list){
				String src_clean = Utils.clearSource(src);
				String src_href = Utils.getSource(src);
				Utils.putInMap(src_map, src_clean, 1);
				spilted_src_list.add(src_clean+"\t"+src_href);
			}
			SaveInfo.saveList("\\WeibosSrc\\"+uid+".txt", spilted_src_list,false);
			SaveInfo.saveMap(result_file,uid,src_map,true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}