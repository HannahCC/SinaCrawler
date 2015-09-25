package org.cl.model;

import java.util.List;

public class StatusAndComment
{
	/** 微博*/
	private Status weibo=null;
	/** 评论*/
	private List<Comment> comment=null;
	
	public Status getWeibo()
	{
		return weibo;
	}

	public void setWeibo(Status weibo)
	{
		this.weibo = weibo;
	}

	public List<Comment> getComment()
	{
		return comment;
	}

	public void setComment(List<Comment> comment)
	{
		this.comment = comment;
	}
}
