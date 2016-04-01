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
	private String result_file2;

	public GetWeiboSrc(String uid, String result_file, String result_file2)
	{
		this.uid=uid;
		this.result_file = result_file;
		this.result_file2 = result_file2;
	}

	public void run()
	{
		Map<String,Integer> src_map = new HashMap<String,Integer>();

		StringBuffer src_str = new StringBuffer();
		src_str.append(uid+"\t");
		System.out.println("Getting weiboSrc of "+uid);
		try {
			List<String> src_list = new ArrayList<String>();
			GetInfo.getList("\\Weibos\\"+uid+".txt",src_list,false,"weibo","source");
			List<String> spilted_src_list = new ArrayList<String>();
			for(String src : src_list){
				String src_clean = Utils.clearSource(src);
				String src_href = Utils.getSource(src);
				if(src_map.containsKey(src_clean)){
					src_map.put(src_clean, src_map.get(src_clean)+1);
				}else{
					src_map.put(src_clean, 1);
					src_str.append(src_clean.replaceAll("\\s+", "")+"\t");
				}
				spilted_src_list.add(src_clean+"\t"+src_href);
			}
			SaveInfo.saveList("\\WeibosSrc\\"+uid+".txt", spilted_src_list,false);
			SaveInfo.saveMap(result_file,uid,src_map,true);
			SaveInfo.saveString(result_file2,src_str.toString(),true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}