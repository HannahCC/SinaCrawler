package org.cl.run;

import java.io.IOException;

import org.cl.configuration.Config;
import org.cl.http.SpiderSina;
import org.cl.model.User;
import org.cl.parser.UserParser;
import org.cl.service.SaveInfo;

public class GetUserType implements Runnable
{
	/**用户ID*/
	private String uid=null;
	private SpiderSina spider=null;

	public GetUserType(String uid,SpiderSina spider)
	{
		this.uid=uid;
		this.spider = spider;
	}
	public void run()
	{
		System.out.println("Getting uidType of "+uid);
		String json=spider.getUserInfo(uid);
		User user=UserParser.getUser(json);
		if(user==null){return;}//发生意外，未得到用户信息
		if(user.getId().equals("-1")){//用户不存在
			try{SaveInfo.saveUserNotExist(uid+"\r\n");} 
			catch (IOException e){e.printStackTrace();}
			return;
		}
		try{SaveInfo.saveUserType(uid+"\t"+user.getVerifiedType()+"\r\n");} 
		catch (IOException e){e.printStackTrace();}
		//每爬一个ID休眠1-10s
		try {
			Thread.sleep(Config.getUnitSleepTime());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
