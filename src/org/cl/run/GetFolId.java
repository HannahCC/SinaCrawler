package org.cl.run;

import java.io.IOException;

import org.cl.configuration.Config;
import org.cl.http.SpiderSina;
import org.cl.model.UidInfo;
import org.cl.parser.UIDParser;
import org.cl.service.SaveInfo;

public class GetFolId implements Runnable
{
	/**用户ID*/
	private String uid=null;
	private SpiderSina spider=null;

	public GetFolId(String uid,SpiderSina spider)
	{
		this.uid=uid;
		this.spider = spider;
	}
	public void run()
	{
		System.out.println("Getting uidInfo of "+uid);
		UidInfo ids_follower = null;
		String json_follower=spider.getFollowers(uid, 500, 0);
		ids_follower=UIDParser.getUID(uid,json_follower);
		if(ids_follower==null){return;}//发生意外，或用户不存在
		//存储关系
		try {
			SaveInfo.saveFollows(uid,ids_follower);
			SaveInfo.saveExpandID(ids_follower);
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
