package org.cl.model;

public class User implements java.io.Serializable 
{
	private static final long serialVersionUID = -332738032648843482L;
	private String id;                    //用户UID
	private boolean verified = false;     		//加V标示，是否微博认证用户
	private String verifiedReason = "";		  //认证原因
	private int verifiedType;		  	  		  //认证类别
	private String screenName;            //微博昵称
	private String location;              //地址
	private String gender;                //性别,m--男，f--女,n--未知
	private String description = "";           //个人描述
	private int followersCount;           //粉丝数
	private int friendsCount;             //关注数
	private int bi_followers_count;		  //互粉数
	private int statusesCount;            //微博数
	private int favouritesCount;          //收藏数
	private String createdAt;             //创建时间
	private String userDomain = "";            //用户个性化URL
	private String lang = "";                  //用户语言版本
	private Tags tags = null;				  	  //标签
/*
用户UID,是否是原用户,是否只获取互粉用户,是否微博认证用户,认证原因,用户昵称,地址,用户个人描述,性别(m：男、f：女、n：未知),
粉丝数,关注数,互粉数,微博数,收藏数,
用户创建（注册）时间,用户的个性化域名,备注信息,用户语言版本,标签
 */
	@Override
	public String toString() 
	{
		return 
				id +
		"\t" + verified + 
		"\t" + verifiedReason +	
		"\t" + screenName + 
		"\t" + location + 
		"\t" + description + 
		"\t" + gender + 
		"\t" + followersCount + 
		"\t" + friendsCount + 
		"\t" + bi_followers_count +
		"\t" + statusesCount + 
		"\t" + favouritesCount + 
		"\t" + createdAt + 
		"\t" + userDomain + 
		"\t" + lang +
		"\t" + tags +
		"\t" + verifiedType;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getScreenName()
	{
		return screenName;
	}

	public void setScreenName(String screenName)
	{
		this.screenName = screenName;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		description=description.replaceAll("\\s+", " ");
		if(description.trim().equals(""))
		{
			this.description = "";
			return ;
		}
		this.description = description;
	}

	public String getUserDomain()
	{
		return userDomain;
	}

	public void setUserDomain(String userDomain)
	{
		userDomain = userDomain.replaceAll("\\s+", " ");
		if(userDomain.trim().equals("")){
			this.userDomain = "";
			return;
		}
		this.userDomain = userDomain;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public int getFollowersCount()
	{
		return followersCount;
	}

	public void setFollowersCount(int followersCount)
	{
		this.followersCount = followersCount;
	}

	public int getFriendsCount()
	{
		return friendsCount;
	}

	public void setFriendsCount(int friendsCount)
	{
		this.friendsCount = friendsCount;
	}

	public int getStatusesCount()
	{
		return statusesCount;
	}

	public void setStatusesCount(int statusesCount)
	{
		this.statusesCount = statusesCount;
	}

	public int getFavouritesCount()
	{
		return favouritesCount;
	}

	public void setFavouritesCount(int favouritesCount)
	{
		this.favouritesCount = favouritesCount;
	}


	public String getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(String createdAt)
	{
		this.createdAt = createdAt;
	}

	public boolean isVerified()
	{
		return verified;
	}

	public void setVerified(boolean verified)
	{
		this.verified = verified;
	}

	public String getLang()
	{
		return lang;
	}

	public void setLang(String lang)
	{
		this.lang = lang;
	}

	public String getVerifiedReason()
	{
		return verifiedReason;
	}

	public void setVerifiedReason(String verifiedReason)
	{
		verifiedReason=verifiedReason.replaceAll("\\s+", " ");
		if(verifiedReason.trim().equals(""))
		{
			this.verifiedReason = "";
			return ;
		}
		this.verifiedReason = verifiedReason;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public Tags getTags()
	{
		return tags;
	}

	public void setTags(Tags tags)
	{
		this.tags = tags;
	}

	public int getBi_followers_count() {
		return bi_followers_count;
	}

	public void setBi_followers_count(int bi_followers_count) {
		this.bi_followers_count = bi_followers_count;
	}

	public int getVerifiedType() {
		return verifiedType;
	}

	public void setVerifiedType(int verifiedType) {
		this.verifiedType = verifiedType;
	}
	
}
