package org.cl.run;

import java.io.IOException;

import org.cl.configuration.Config;
import org.cl.http.SpiderSina;
import org.cl.model.UidInfo;
import org.cl.parser.UIDParser;
import org.cl.service.SaveInfo;

public class GetFriId implements Runnable
{
	/**用户ID*/
	private String uid=null;
	private SpiderSina spider=null;

	public GetFriId(String uid,SpiderSina spider)
	{
		this.uid=uid;
		this.spider = spider;
	}

	public void run()
	{
		System.out.println("Getting friends ID of "+uid);
		UidInfo ids_friends = null;
		String json_friends=spider.getFriends(uid, 500, 0);
		ids_friends=UIDParser.getUID(uid,json_friends);
		if(ids_friends==null){return;}//发生意外，或用户不存在
		//存储关注关系
		try {
			SaveInfo.saveFriends(uid,ids_friends);
			SaveInfo.saveExpandID(ids_friends);
		} catch (IOException e) {e.printStackTrace();}
		//每爬一个ID休眠5s
		try {
			Thread.sleep(Config.getUnitSleepTime());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
