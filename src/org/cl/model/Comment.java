package org.cl.model;

import net.sf.json.JSONObject;

public class Comment
{
	private String created_at=null;	//评论创建时间
	private String id=null;			//评论的ID
	private String text=null;		//评论的内容
	private String user_id=null;	//评论作者的用户ID
	private boolean reply_comment=false;	//评论是否来源评论，当本评论属于对另一评论的回复时返回此字段
	public Comment() {}
	public Comment(JSONObject json) {
		this.created_at = json.getString("created_at");
		this.id = json.getString("id");
		this.text = json.getString("text");
		this.user_id = json.getString("user_id");
		this.reply_comment = json.getBoolean("reply_comment");
	}

	/*
	 * {评论的ID,字符串型的评论ID,评论的MID,评论是否来源评论(false/true),评论的来源,评论的内容,评论作者的用户ID,评论创建时间}
	 * */
	@Override
	public String toString()
	{
		return "{"+ id +
				" | " + reply_comment + 
				" | " + text +
				" | " + user_id + 
				" | " + created_at +
				"}";
	}
	
	public String getCreated_at()
	{
		return created_at;
	}
	public void setCreated_at(String createdAt)
	{
		created_at = createdAt;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		text=text.replaceAll("\\s+", " ");
		this.text = text;
	}
	public String getUser_id()
	{
		return user_id;
	}
	public void setUser_id(String userId)
	{
		user_id = userId;
	}
	public boolean isReply_comment()
	{
		return reply_comment;
	}
	public void setReply_comment(boolean replyComment)
	{
		reply_comment = replyComment;
	}
}
