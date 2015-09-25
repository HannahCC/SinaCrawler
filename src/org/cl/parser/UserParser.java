package org.cl.parser;


import org.cl.model.User;
import net.sf.json.JSONObject;

public class UserParser
{
	/**
	 * 根据json数据解析用户信息
	 * */
	public static User getUser(String json)
	{
		if(json==null)return null;
		int start=json.indexOf('{');
		int end=json.lastIndexOf('}')+1;
		if(start==-1 || end==-1 || end<=start)
		{
			return null;
		}
		json=json.substring(start, end);
		JSONObject jb = null;
		try{
			jb = JSONObject.fromObject(json);
		}catch(Exception e){
			String message = e.getMessage();
			System.out.println("StatusParser-error_Json:"+message);
			return null;
		}

		User user=new User();
		//判断用户是否存在
		if(jb.containsKey("error"))
		{
			if(jb.getString("error").equals("User does not exists!")){
				System.out.println("UserParser-error1:UserNotExist!");
				user.setId("-1");
				return user;
			}else{
				System.out.println("UserParser-error2:"+jb.getString("error"));
				return null;
			}
		}
		
		if(jb.containsKey("idstr"))
		{
			user.setId(jb.getString("idstr"));
		}
		if(jb.containsKey("verified"))
		{
			user.setVerified(jb.getBoolean("verified"));
			/*if(!user.isVerified()){
				System.out.println("UserParser:"+user.getId()+"is not V!");
				try {
					SaveInfo.saveUserIsNotV(user.getId()+"\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				userNotExist = true;//用于排除大V
				return null;
			}*/
		}
		if(jb.containsKey("screen_name"))
		{
			String screen_name = jb.getString("screen_name");
			user.setScreenName(screen_name);
		}
		
		if(jb.containsKey("location"))
		{
			user.setLocation(jb.getString("location"));
		}
		
		if(jb.containsKey("description"))
		{
			user.setDescription(jb.getString("description"));
		}
		
		if(jb.containsKey("domain"))
		{
			user.setUserDomain(jb.getString("domain"));
		}
		
		if(jb.containsKey("gender"))
		{
			user.setGender(jb.getString("gender"));
		}
		
		if(jb.containsKey("followers_count"))
		{
			user.setFollowersCount(jb.getInt("followers_count"));
		}
		
		if(jb.containsKey("friends_count"))
		{
			user.setFriendsCount(jb.getInt("friends_count"));
		}
		
		if(jb.containsKey("bi_followers_count")){
			user.setBi_followers_count(jb.getInt("bi_followers_count"));
		}
		
		if(jb.containsKey("statuses_count"))
		{
			user.setStatusesCount(jb.getInt("statuses_count"));
		}
		
		if(jb.containsKey("favourites_count"))
		{
			user.setFavouritesCount(jb.getInt("favourites_count"));
		}
		String d=null;
		String date=null;
		if(jb.containsKey("created_at"))
		{
			date=jb.getString("created_at");
			//时间转换
			d=Timeparser.getTime(date);
		}
		if(d!=null)
		{
			user.setCreatedAt(d);
		}
		else
		{
			user.setCreatedAt(date);
		}
		
		if(jb.containsKey("verified_reason"))
		{
			user.setVerifiedReason(jb.getString("verified_reason"));
		}
		
		if(jb.containsKey("verified_type"))
		{
			int type = jb.getInt("verified_type");
			user.setVerifiedType(type);
		}
		
		if(jb.containsKey("lang"))
		{
			user.setLang(jb.getString("lang"));
		}
		
		return user;
	}
}