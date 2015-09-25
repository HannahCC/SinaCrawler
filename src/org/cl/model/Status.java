package org.cl.model;

import net.sf.json.JSONObject;


public class Status implements java.io.Serializable 
{
	private static final long serialVersionUID = -8795691786466526420L;

	private String id = null;                                   //status id字符串
	private String userId = null;                                  //发布状态的用户id 
	private String createdAt = null;                              //status创建时间   
	private String source = null;                              //微博来源  
	private String geo = null;                              //微博地点 
	private String text = null;                                 //微博内容
	private String originalPic = null;                          //微博内容中的图片的原始图片
	private Status retweetedStatus = null;               //转发的原博文
	private int repostsCount = 0;                            //转发数
	private int commentsCount = 0;                           //评论数     
	
	public Status() {}
	
	public Status(JSONObject json) {
		this.id = json.getString("id");
		this.userId = json.getString("userId");
		this.createdAt = json.getString("createdAt");
		this.source = json.getString("source");
		this.geo = json.getString("geo");
		this.text = json.getString("text");
		this.originalPic = json.getString("originalPic");
		this.repostsCount = json.getInt("repostsCount");
		this.commentsCount = json.getInt("commentsCount");
		Object retweetedStatus = json.get("retweetedStatus");
		if(!retweetedStatus.equals("null")){this.retweetedStatus = new Status(json.getJSONObject("retweetedStatus"));}
		else{this.retweetedStatus = null;}
		
	}


	/**
	 * 微博ID,微博创建时间,微博来源,微博地点,微博内容,微博内容中的图片的地址,是否是转发的博文,转发数,评论数
	 * 3726228043686743	2014-06-28 00:10:58	河南商报改版6周年生日送礼！劲爆抽奖就劲爆支持相信每一天都足够特别，所以要努力加油！！ 地址：http://t.cn/Rv86Dhh	null	0	1	<a href="http://app.weibo.com/t/feed/59hmLP" rel="nofollow">微活动</a>	null	|***|3726061000768834|**|2014-06-27 13:07:11|**|【河南商报改版6周年生日送礼！】河南商报改版6年，三根金条等你来拿，即日起到6月30日，成为@河南商报 粉丝，转发置顶微博@3名好友，带话题#和你在一起# 商报君生日祝福！3根金条、20箱牛奶、可乐歌词瓶等你来抢，本次抽奖将完全通过新浪官方抽奖平台进行。 http://t.cn/Rv86Dhh|**|http://ww3.sinaimg.cn/large/657fef28tw1ehsl8wywacj20c812bjup.jpg|**|497009|**|232987|**|<a href="http://app.weibo.com/t/feed/59hmLP" rel="nofollow">微活动</a>|**|null|**|null|**|1702883112|***|	{3726229318036566 | false | 远方的我祝你幸福，不认识，偶尔看到，祝福一下[礼物] | 3498137427 | 2014-06-28 00:16:00}
	 * */
	@Override
	public String toString() 
	{
		String str = id + "\t"
				+ createdAt + "\t" 
				+ text + "\t"   
				+ originalPic + "\t" ;
		if(retweetedStatus!=null){//如果该微博转发自别的微博，存储原微博，格式如下
			String retweet_str = retweetedStatus.toString();
			retweet_str = retweet_str.replace("\t", "|**|");
			str += "|***|"+retweet_str+"|**|"+retweetedStatus.getUserId()+"|***|\t";
		}else{
			str += "null\t";
		}		
		str += repostsCount + "\t" 
				+ commentsCount + "\t"
				+ source +"\t"
				+ geo;
		return str;
	}
	

	public Status getRetweetedStatus() {
		return retweetedStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCreatedAt()
	{
		return createdAt;
	}
	public void setCreatedAt(String createdAt)
	{
		this.createdAt = createdAt;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		if(text!=null)
			text=text.replaceAll("\\s+", " ");
		this.text = text;
	}
	public String getOriginalPic()
	{
		return originalPic;
	}
	public void setOriginalPic(String originalPic)
	{
		this.originalPic = originalPic;
	}

	public Status isRetweetedStatus()
	{
		return retweetedStatus;
	}

	public void setRetweetedStatus(Status status)
	{
		this.retweetedStatus = status;
	}
	public int getRepostsCount()
	{
		return repostsCount;
	}
	public void setRepostsCount(int repostsCount)
	{
		this.repostsCount = repostsCount;
	}
	public int getCommentsCount()
	{
		return commentsCount;
	}
	public void setCommentsCount(int commentsCount)
	{
		this.commentsCount = commentsCount;
	}
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	
}
