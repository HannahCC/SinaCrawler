package org.cl.parser;

import org.cl.model.UidInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UIDParser
{
	/**
	 * 根据json数据解析用户ID
	 * */
	public static UidInfo getUID(String uid,String json)
	{
		if(json==null) return null;
		int start=json.indexOf('{');
		int end=json.lastIndexOf('}')+1;
		if(start==-1 || end==-1 || end<=start){return null;}
		json=json.substring(start, end);
		JSONObject jb = null;
		try{
			jb = JSONObject.fromObject(json);
		}catch(Exception e){
			String message = e.getMessage();
			System.out.println("StatusParser-error_changeJson:"+message);
			return null;
		}
		//判断获取是否失败
		if(jb.containsKey("error"))
		{
			if(jb.getString("error").equals("User does not exists!")){
				System.out.println("UIDParser-error1:"+jb.getString("error"));
			}else{
				System.out.println("UIDParser_error2:"+jb.getString("error"));
			}
			return null;
		}
		UidInfo uid_info=new UidInfo();
		uid_info.setId(uid);
		if(jb.containsKey("ids"))
		{
			JSONArray jsons = jb.getJSONArray("ids");
			int num=jsons.size();
			for(int i=0;i<num;i++)
			{
				uid_info.getUids().add(jsons.getString(i));
			}
		}


		if(jb.containsKey("total_number"))
		{
			uid_info.setTotal_number(jb.getInt("total_number"));
		}

		return uid_info;
	}

}
