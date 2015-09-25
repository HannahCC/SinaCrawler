package org.cl.parser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.cl.model.Comment;
import org.cl.model.CommentInfo;
import org.cl.model.User;

public class CommentParser
{
	public static Comment getComment(String json)
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
		Comment comment=new Comment();

		if(jb.containsKey("id"))
		{
			comment.setId(jb.getString("id"));
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
			comment.setCreated_at(d);
		}
		else
		{
			comment.setCreated_at(date);
		}

		if(jb.containsKey("text"))
		{
			comment.setText(jb.getString("text"));
		}

		if(jb.containsKey("reply_comment"))
		{
			comment.setReply_comment(true);
		}

		if(jb.containsKey("user"))
		{
			String user=jb.getString("user");
			User u=UserParser.getUser(user);
			comment.setUser_id(u.getId());
		}

		return comment;
	}

	public static CommentInfo getCommentInfo(String json)
	{	
		if(json==null){return null;}
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
		if(jb.containsKey("error"))
		{
			System.out.println("CommentParser-error1:"+jb.getString("error"));
			return null;
		}
		CommentInfo commentinfo=new CommentInfo();

		if(jb.containsKey("comments"))
		{
			JSONArray jsons = jb.getJSONArray("comments");
			int num=jsons.size();
			for(int i=0;i<num;i++)
			{
				commentinfo.getJsons().add(jsons.getString(i));
			}
		}

		if(jb.containsKey("next_cursor"))
		{
			commentinfo.setNext_cursor(jb.getInt("next_cursor"));
		}

		if(jb.containsKey("previous_cursor"))
		{
			commentinfo.setPrevious_cursor(jb.getInt("previous_cursor"));
		}

		if(jb.containsKey("total_number"))
		{
			commentinfo.setTotal_number(jb.getInt("total_number"));
		}

		return commentinfo;
	}
}
