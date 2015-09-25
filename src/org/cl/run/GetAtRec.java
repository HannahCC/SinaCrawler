package org.cl.run;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.cl.configuration.Config;
import org.cl.configuration.RegexString;
import org.cl.http.SpiderSina;
import org.cl.model.AtUser;
import org.cl.model.User;
import org.cl.parser.UserParser;
import sina.utils.Utils;

public class GetAtRec implements Runnable
{
	/**用户ID*/
	private String uid=null;
	/** 抓取类*/
	private SpiderSina spider=null;

	public GetAtRec(String uid,SpiderSina spider)
	{
		this.uid=uid;
		this.spider=spider;
	}

	public void run()
	{
		System.out.println("Getting AtData of "+uid);
		File fw = new File(Config.SAVE_PATH+"Weibos_At\\"+uid+".txt");
		/*if(fw.exists()){return;}*/
		ArrayList<AtUser> atuser_list = new ArrayList<AtUser>();
		ArrayList<String> uname_list = new ArrayList<String>();
		ArrayList<String> uid_list = new ArrayList<String>();
		ArrayList<Integer> atNum_list = new ArrayList<Integer>();

		//首先从微薄内容中取出每个@用户名，并记录次数
		String regex_atUname = RegexString.Regex_at;//"@(\\w|[\\x{4e00}-\\x{9fa5}]|[-]|[_])*(\\s|[:]|$)";//用@用户名  （由字母、汉字，-,_组成）作为正则表达式
		Pattern p_atUname = Pattern.compile(regex_atUname);
		File f=new File(Config.SAVE_PATH+"Weibos\\"+uid+".txt");
		try {
			BufferedReader br=new BufferedReader(new FileReader(f));
			String line = "";
			while((line=br.readLine())!=null)
			{
				if(line.equals(""))continue;
				JSONObject statusAndComment = JSONObject.fromObject(line);
				String weibo = statusAndComment.getJSONObject("weibo").getString("text");
				String weibo_text = Utils.clearWeibo(weibo);
				Matcher m = p_atUname.matcher(weibo_text);
				while(m.find())
				{
					String uname = m.group();//得到之后需要去掉@和空格或制表符
					int s = uname.indexOf("@");
					int e = uname.lastIndexOf(" ");
					if(e<s)e = uname.lastIndexOf("	");
					if(e<s)e = uname.lastIndexOf(":");
					if(e<s){e = uname.length();}
					uname = uname.substring(s+1,e);
					if(uname.equals("")){continue;}
					int index = uname_list.indexOf(uname);
					if(-1 == index){uname_list.add(uname);atNum_list.add(1);}//如果不存在则加入列表
					else{atNum_list.set(index, atNum_list.get(index)+1);}//存在则将对应的次数+1
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//如果没有@用户名则结束该进程
		if(uname_list.size()==0)return;

		//根据得到的@用户名得到@用户ID
		for(String uname:uname_list){
			String json=spider.getUserInfo_byname(uname);
			User user=UserParser.getUser(json);
			if(user==null){System.out.println(uid+":Fail to get "+uname+"'s ID");uid_list.add("-1");}//未得到用户信息
			else{uid_list.add(user.getId());}
		}
		//将数据整合
		int size =uname_list.size();
		for(int i=0;i<size;i++){
			AtUser user_item = new AtUser();
			user_item.setUname(uname_list.get(i));
			user_item.setUid(uid_list.get(i));
			user_item.setAtNum(atNum_list.get(i));
			atuser_list.add(user_item);
		}
		//根据用户ID得到该用户被@后回复次数
		f = new File(Config.SAVE_PATH+"Weibos\\"+uid+".txt");
		//String regex_comment_item = "\\s\\{\\d*\\s\\|";//用" {1234567887654321 |"隔开每条回复
		//String regex_comment_cont = "\\s\\|\\s";//用" | "隔开每条回复中的各个字段，第3个字段则为回复者ID
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = "";
			while((line=br.readLine())!=null)
			{
				JSONObject statusAndComment = JSONObject.fromObject(line);
				JSONArray comments = statusAndComment.getJSONArray("comment");
				for(int i=1;i<comments.size();i++){
					String user_id = comments.getJSONObject(i).getString("user_id");
					int index = uid_list.indexOf(user_id);
					if(index!=-1){
						AtUser user = atuser_list.get(index);
						user.setReNum(user.getReNum()+1);
						atuser_list.set(index, user);
					}
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//将atuser_list排序，按user.getAtNum()从大到小排列
		Collections.sort(atuser_list,new Comparator<AtUser>(){
			public int compare(AtUser arg0, AtUser arg1) {
				//if(arg0.getAtNum()>arg1.getAtNum())
				return arg1.getAtNum()-arg0.getAtNum();
			}
		});
		//将结果存入文档
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter(fw));
			for(AtUser user:atuser_list){
				w.write(user.getUid()+"\t"+user.getAtNum()+"\t"+user.getReNum()+"\t"+user.getUname()+"\r\n");
			}
			w.flush();
			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//每爬一个ID休眠5s
		try {
			Thread.sleep(Config.getUnitSleepTime()+10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
