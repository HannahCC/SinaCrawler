package org.cl.http;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;

import com.gargoylesoftware.htmlunit.util.Cookie;

public class SpiderSina
{
	/** 请求授权参数*/
	
	private static final String SOURCE="140226478";
	private static final  Map<String, String> headers = new HashMap<String, String>();
	
	private SpiderSina(){}
	
	private static class SpiderFactory{
		private static SpiderSina spiderSina = new SpiderSina();
	}
	public static SpiderSina getInstance(){
		return SpiderFactory.spiderSina;
	}

	public void setHeader(Set<Cookie> cookie)
	{
		headers.put("Accept", "text/html, application/xhtml+xml, */*");
		headers.put("Accept-Language", "zh-cn");
		headers.put("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; BOIE9;ZHCN");
		headers.put("Connection", "Keep-Alive");
		headers.put("Cache-Control", "no-cache");
		String cookieValue = HttpUtils.setCookieString(cookie);
		headers.put("Cookie", cookieValue);
	}
	
	/**
	 * 返回用户信息
	 * */
	public String getUserInfo(String uid)
	{
		String url="http://api.weibo.com/2/users/show.json?uid="+uid+"&source="+SOURCE ;
		//		this.headers.put("Host", "weibo.com");
		//		this.headers.put("Referer", "http://weibo.com/u/" + uid);
		HttpResponse response = HttpUtils.doGet(url, headers);
		String responseText = HttpUtils.getStringFromResponse(response);
		return responseText;
	}
	/**
	 * 返回用户信息
	 * */
	public String getUserInfo_byname(String screen_name)
	{
		String url="http://api.weibo.com/2/users/show.json?screen_name="+screen_name+"&source="+SOURCE ;
		//		this.headers.put("Host", "weibo.com");
		//		this.headers.put("Referer", "http://weibo.com/u/" + uid);
		HttpResponse response = HttpUtils.doGet(url, headers);
		String responseText = HttpUtils.getStringFromResponse(response);
		return responseText;
	}
	/**
	 * 获取用户的粉丝的ID列表
	 * count	单页返回的记录条数，默认为500，最大不超过5000。
	 * cursor	返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
	 * */
	public String getFollowers(String uid, int count ,int next_cursor)
	{
		String url ="http://api.weibo.com/2/friendships/followers/ids.json?uid="+uid+
				"&count="+count+
				"&cursor="+next_cursor+
				"&source="+SOURCE;
		//		this.headers.put("Host", "weibo.com");
		//		this.headers.put("Referer", "http://weibo.com/" + uid + "/fans");
		HttpResponse response = HttpUtils.doGet(url, headers);
		String responseText = HttpUtils.getStringFromResponse(response);
		return responseText;
	}
	/**
	 * 获取用户互相关注的ID列表
	 * count	单页返回的记录条数，默认为50，最大不超过2000。
	 * cursor	返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
	 * */
	public String getInterFriends(String uid, int count)
	{
		String url ="https://api.weibo.com/2/friendships/friends/bilateral/ids.json?uid="+uid+
				"&count="+count+
				"&source="+SOURCE;
		//		this.headers.put("Host", "weibo.com");
		//		this.headers.put("Referer", "http://weibo.com/" + uid + "/fans");
		HttpResponse response = HttpUtils.doGet(url, headers);
		String responseText = HttpUtils.getStringFromResponse(response);
		return responseText;
	}
	/**
	 * 获取用户关注的用户ID列表
	 * count	单页返回的记录条数，默认为500，最大不超过5000。
	 * cursor	返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0。
	 * */
	public String getFriends(String uid, int count ,int cursor)
	{
		String url ="http://api.weibo.com/2/friendships/friends/ids.json?uid="+uid+
				"&count="+count+
				"&cursor="+cursor+
				"&source="+SOURCE;
		//		this.headers.put("Host", "weibo.com");
		//		this.headers.put("Referer", "http://weibo.com/" + uid + "/follow");
		HttpResponse response = HttpUtils.doGet(url, headers);
		String responseText = HttpUtils.getStringFromResponse(response);
		return responseText;
	}

	/**
	 * 获取用户微博
uid		false	int64		需要查询的用户ID。
since_id	false	int64		若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
max_id		false	int64		若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
count		false	int		单页返回的记录条数，最大不超过100，超过100以100处理，默认为20。
page		false	int		返回结果的页码，默认为1。
trim_user	false	int		返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0。
	 */
	public String getWeiBo(String uid,int page,int count)
	{
		String url ="http://api.weibo.com/2/statuses/user_timeline.json?uid="+uid+
				//"&since_id="+start_id+
				//"&max_id="+end_id+
				"&page="+page+
				"&count="+count+
				"&trim_user=1"+
				"&feature=1"+
				"&source="+SOURCE;
		HttpResponse response = HttpUtils.doGet(url, headers);
		String responseText = HttpUtils.getStringFromResponse(response);
		return responseText;
	}

	/**
	 * 获取用户的标签列表
	 * */
	public String getTags(String uid)
	{
		String url ="http://api.weibo.com/2/tags.json?uid="+uid+
				"&source="+SOURCE;
		HttpResponse response = HttpUtils.doGet(url, headers);
		String responseText = HttpUtils.getStringFromResponse(response);
		return responseText;
	}

	/** 
	 * 根据微博ID返回某条微博的评论列表
					必选		类型及范围		说明
source				false	string	采用OAuth授权方式不需要此参数，其他授权方式为必填参数，数值为应用的AppKey。
access_token		false	string	采用OAuth授权方式为必填参数，其他授权方式不需要此参数，OAuth授权后获得。
id					true	int64	需要查询的微博ID。
since_id			false	int64	若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
max_id				false	int64	若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
count				false	int		单页返回的记录条数，默认为50。
page				false	int		返回结果的页码，默认为1。
filter_by_author	false	int		作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。

	 * */
	public String getComment(String id)
	{
		String url ="http://api.weibo.com/2/comments/show.json?id="+id+
				"&filter_by_author=0" +
				"&count=100"+
				"&source="+SOURCE;
		HttpResponse response = HttpUtils.doGet(url, headers);
		String responseText = HttpUtils.getStringFromResponse(response);
		return responseText;
	}

	public String getAddress(String geo) {
		int i = geo.indexOf("[");
		int e = geo.indexOf("]");
		geo = geo.substring(i+1,e);
		String url ="http://api.weibo.com/2/location/geo/geo_to_address.json?coordinate="+geo+
				"&source="+SOURCE;
		HttpResponse response = HttpUtils.doGet(url, headers);
		String responseText = HttpUtils.getStringFromResponse(response);
		return responseText;
	}
	//用于临时测试接口是否正常工作
	public String tempGet() throws UnsupportedEncodingException{
		String q = "范冰冰";
		int page = 1;
		int count = 10;
		
		String encode_q = java.net.URLEncoder.encode(q,"utf-8");
		String url ="http://api.weibo.com/2/search/topics.json?coordinate="+
				"&q="+encode_q+
				"&page="+page+
				"&count="+count+
				"&source="+SOURCE;
		HttpResponse response = HttpUtils.doGet(url, headers);
		String responseText = HttpUtils.getStringFromResponse(response);
		System.out.println(responseText);
		return responseText;
	}
	
}
