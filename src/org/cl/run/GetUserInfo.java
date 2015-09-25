package org.cl.run;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.cl.configuration.Config;
import org.cl.http.SpiderSina;
import org.cl.model.Tags;
import org.cl.model.User;
import org.cl.parser.TagsParser;
import org.cl.parser.UserParser;
import org.cl.service.SaveInfo;

public class GetUserInfo implements Runnable
{
	/**用户ID*/
	private String uid=null;

	public GetUserInfo(String uid)
	{
		this.uid=uid;
	}

	public void run()
	{
		SpiderSina spider=new SpiderSina();
		System.out.println("Getting userInfo of "+uid);
		User user = null;
		String json=spider.getUserInfo(uid);
		user=UserParser.getUser(json);
		if(user==null){return;}//发生意外，未得到用户信息
		if(user.getId().equals("-1")){//用户不存在
			try{SaveInfo.saveUserNotExist(uid+"\r\n");} 
			catch (IOException e){e.printStackTrace();}
			return;
		}
		json=spider.getTags(uid);
		Tags tags=TagsParser.getTags(json);
		if(tags==null){//如果获取标签失败,则认为没有标签
			tags = new Tags();
			tags.setTags(new HashSet<String>());
			tags.setTotal_number(0);
		}
		user.setTags(tags);//设置标签
		if(isEnterprise(user)){
			try{SaveInfo.saveEnterpriseUser(user);} 
			catch (IOException e){e.printStackTrace();}
		}else{
			try{SaveInfo.saveUser(user);} 
			catch (IOException e){e.printStackTrace();}
		}
		
		//每爬一个ID休眠5s
		try {
			Thread.sleep(Config.getUnitSleepTime());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * @param user
	 * @return
	 */
	public boolean isEnterprise(User user) {
		int type = user.getVerifiedType();//标记企业用户
		if(type>0&&type<=8){return true;}//蓝V
		if(type==0)return false;//黄v
		Set<String> keyword = new HashSet<String>();
		keyword.add("官方微博");keyword.add("官方账号");keyword.add("官方帐号");keyword.add("官方围脖");
		keyword.add("事务所");keyword.add("工作室");keyword.add("俱乐部");
		keyword.add("公司");keyword.add("中心");keyword.add("协会");keyword.add("杂志");
		keyword.add("代购");keyword.add("联盟");keyword.add("社区");
		keyword.add("平台");keyword.add("学院");keyword.add("代理");keyword.add("频道");
		String screen_name = user.getScreenName();
		String description = user.getDescription();
		String verifyreason = user.getVerifiedReason();
		if(screen_name!=null&&contains(keyword,screen_name)){return true;}
		else if(description!=null&&contains(keyword,description)){return true;}
		else if(verifyreason!=null&&contains(keyword,verifyreason)){return true;}//此前没有使用这个字段筛选
		return false;
	}

	private boolean contains(Set<String> keyword, String str) {
		for(String key : keyword){
			if(str.contains(key))return true;
		}
		return false;
	}
}
