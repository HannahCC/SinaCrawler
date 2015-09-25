package org.cl.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.cl.configuration.Config;
import org.cl.model.Comment;
import org.cl.model.Status;
import org.cl.model.StatusAndComment;
import org.cl.service.SaveInfo;

public class ClearComment implements Runnable
{
	/**用户ID*/
	private String uid=null;

	public ClearComment(String uid)
	{
		this.uid=uid;
	}

	public void run()
	{
		System.out.println("Clearing Comment of "+uid);
		File file_or = new File(Config.SAVE_PATH+"/Weibos/"+uid+".txt");
		String line = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file_or));
			ArrayList<StatusAndComment> status_and_comments=new ArrayList<StatusAndComment>();
			while((line=br.readLine())!=null)
			{
				if(line.equals(""))continue;
				JSONObject weiboJSON = JSONObject.fromObject(line);
				JSONArray comments = weiboJSON.getJSONArray("comment");
				Status weibo = new Status(weiboJSON.getJSONObject("weibo"));
				int comment_num = weibo.getCommentsCount();
				int spam_comment_num = 0;
				List<Comment> comments_list=new ArrayList<Comment>();
				for(int i=0;i<comments.size();i++){//使用制表符隔开一条微博记录后，第10项,即weibo_item[9]开始为评论
					Comment comment = new Comment(comments.getJSONObject(i));
					String comment_text = comment.getText();//comment_text = 评论内容
					if(comment_text.contains("互粉")){spam_comment_num++;break;}//weibo_item_con[2]为评论内容，如果为垃圾评论，则将该评论置空
					if(comment_text.contains("互相粉")){spam_comment_num++;break;}
					if(comment_text.contains("进来看看")){spam_comment_num++;break;}
					if(comment_text.contains("进来逛逛")){spam_comment_num++;break;}
					if(comment_text.contains("关注我")){spam_comment_num++;break;}
					if(comment_text.contains("本店")){spam_comment_num++;break;}
					if(comment_text.contains("小店")){spam_comment_num++;break;}
					if(comment_text.contains("明星代言")){spam_comment_num++;break;}
					if(comment_text.contains("明星同款")){spam_comment_num++;break;}
					if(comment_text.contains("直销")){spam_comment_num++;break;}
					if(comment_text.contains("抽奖")){spam_comment_num++;break;}
					if(comment_text.contains("开奖")){spam_comment_num++;break;}
					if(comment_text.contains("包邮")){spam_comment_num++;break;}
					if(comment_text.contains("猛戳")){spam_comment_num++;break;}
					if(comment_text.contains("请戳")){spam_comment_num++;break;}
					comments_list.add(comment);
				}
				comment_num -= spam_comment_num;
				weibo.setCommentsCount(comment_num);
				StatusAndComment temp=new StatusAndComment();
				temp.setWeibo(weibo);
				temp.setComment(comments_list);
				status_and_comments.add(temp);
			}
			br.close();
			SaveInfo.saveWeiBo("Weibos_Cleared\\",uid, status_and_comments);
		} catch (Exception e) {
			System.out.println(uid);
			System.out.println(line);
			e.printStackTrace();
		}
	}
}

