package org.cl.parser;


import org.cl.model.Status;
import org.cl.model.WeiBoInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StatusParser
{
	/**
	 * 根据json数据解析微博信息
	 * */
	public static Status getStatus(String json)
	{
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
			System.out.println("StatusParser-error_changeJson1:"+message);
			return null;
		}
		Status status=new Status();

		if(jb.containsKey("idstr"))
		{
			status.setId(jb.getString("idstr"));
		}
		if(jb.containsKey("uid")){
			/*JSONObject user = JSONObject.fromObject(jb.getString("user"));
			System.out.println(user.getString("idstr"));
			 */status.setUserId(jb.getString("uid"));
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
			status.setCreatedAt(d);
		}
		else
		{
			status.setCreatedAt(date);
		}

		if(jb.containsKey("source"))
		{
			status.setSource(jb.getString("source"));
		}

		if(jb.containsKey("geo"))
		{
			JSONObject jsons = jb.getJSONObject("geo");
			if(jsons!=null&&jsons.containsKey("coordinates")){
				status.setGeo(jsons.getString("coordinates").replaceAll("\\[|\\]", ""));
			}
		}
		if(jb.containsKey("text"))
		{
			String text = jb.getString("text");
			if(text.equals("")){status.setText("null");}
			else{status.setText(jb.getString("text"));}
		}


		if(jb.containsKey("original_pic"))
		{
			status.setOriginalPic(jb.getString("original_pic"));
		}

		if(jb.containsKey("retweeted_status"))//原微博信息
		{
			//retweeted_status = getStatus(jb.getString("retweeted_status"));
			status.setRetweetedStatus(getStatus(jb.getString("retweeted_status")));
		}

		if(jb.containsKey("reposts_count"))
		{
			status.setRepostsCount(jb.getInt("reposts_count"));
		}

		if(jb.containsKey("comments_count"))
		{
			status.setCommentsCount(jb.getInt("comments_count"));
		}

		return status;
	}

	public static WeiBoInfo getWeiBoInfo(String json)
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
			System.out.println("StatusParser-error_changeJson2:"+message);
			return null;
		}

		WeiBoInfo wbinfo=new WeiBoInfo();
		if(jb.containsKey("error"))
		{
			if(jb.getString("error").equals("User does not exists!")){
				wbinfo.setTotal_number(-1);
				return wbinfo;
			}else{
				System.out.println("StatusParser-error2:"+jb.getString("error"));
				return null;
			}
		}
		if(jb.containsKey("statuses"))
		{
			JSONArray jsons = jb.getJSONArray("statuses");
			int num=jsons.size();
			for(int i=0;i<num;i++)
			{
				wbinfo.getJsons().add(jsons.getString(i));
			}
		}

		if(jb.containsKey("next_cursor"))
		{
			wbinfo.setNext_cursor(jb.getInt("next_cursor"));
		}

		if(jb.containsKey("previous_cursor"))
		{
			wbinfo.setPrevious_cursor(jb.getInt("previous_cursor"));
		}

		if(jb.containsKey("total_number"))
		{
			wbinfo.setTotal_number(jb.getInt("total_number"));
		}

		return wbinfo;
	}




}
