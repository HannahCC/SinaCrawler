package org.cl.run;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.cl.service.GetInfo;
import org.cl.service.SaveInfo;

import sina.utils.Utils;

public class GetWeiboCon implements Runnable{
	/**用户ID*/
	private String uid=null;

	public GetWeiboCon(String uid)
	{
		this.uid=uid;
	}

	public void run()
	{
		System.out.println("Getting weiboCon of "+uid);
		try {
			List<String> weibo_list = new ArrayList<String>();
			GetInfo.getList("/Weibos/"+uid+".txt",weibo_list,false,"weibo","text");
			List<String> cleared_weibo_list = new ArrayList<String>();
			for(String weibo : weibo_list){
				String cleared_weibo = Utils.clearWeibo(weibo);//weibo.split("\\t")[2]);
				if(null!=cleared_weibo&&!"".equals(cleared_weibo)){cleared_weibo_list.add(cleared_weibo);}
			}
			SaveInfo.saveList("/WeibosCon/"+uid+".txt", cleared_weibo_list,false);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}