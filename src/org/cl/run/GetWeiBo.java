package org.cl.run;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cl.configuration.Config;
import org.cl.http.SpiderSina;
import org.cl.model.Comment;
import org.cl.model.CommentInfo;
import org.cl.model.Status;
import org.cl.model.StatusAndComment;
import org.cl.model.WeiBoInfo;
import org.cl.parser.CommentParser;
import org.cl.parser.StatusParser;
import org.cl.service.SaveInfo;

public class GetWeiBo implements Runnable
{
	/**用户ID*/
	private String uid=null;

	public GetWeiBo(String uid)
	{
		this.uid=uid;
	}

	public void run()
	{
		System.out.println("Getting weibo of "+uid);
		SpiderSina spider=new SpiderSina();
		ArrayList<StatusAndComment> status_and_comments=new ArrayList<StatusAndComment>();
		Status wb=null;
		for(int i=1;i<=5;i++)
		{
			String json=spider.getWeiBo(uid,i,100);
			WeiBoInfo wbinfo=StatusParser.getWeiBoInfo(json);
			if(wbinfo==null){continue;}//crawler fail
			if(wbinfo.getTotal_number()==-1){break;}//user not exist.
			for(String wbJson : wbinfo.getJsons())//100条微博
			{
				wb=StatusParser.getStatus(wbJson);
				List<Comment> comments=new ArrayList<Comment>();
				if(wb.getCommentsCount()>0)
				{
					//获取前100条评论
					System.out.println("Getting weibo comment of "+wb.getId());
					json = spider.getComment(wb.getId());
					CommentInfo comm=CommentParser.getCommentInfo(json);
					if(comm!=null){//没爬到评论//，则认为评论为0
						for(String info : comm.getJsons()){	
							comments.add(CommentParser.getComment(info));
						}
					}//else{wb.setCommentsCount(0);}
					long time = Config.getUnitSleepTime();	
					if((time & 2) > 0){ //随机休眠时间能够整除2则休眠，即50%的可能休眠。替代原来的//每爬一条微博的评论休眠一次   太慢了！！！
						try {
							Thread.sleep(time);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				StatusAndComment temp=new StatusAndComment();
				temp.setWeibo(wb);
				temp.setComment(comments);
				status_and_comments.add(temp);
			}
			if((i*50)>wbinfo.getTotal_number())//如果已经获取所有评论，则结束爬取
			{
				break;
			}

			//每爬一轮微博休眠5s
			try {
				Thread.sleep(Config.getUnitSleepTime());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if(status_and_comments.size()>0)
		{
			//保存
			try
			{
				SaveInfo.saveWeiBo("Weibos\\",uid, status_and_comments);		
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		//每爬一个ID休眠5s
		try {
			Thread.sleep(Config.getUnitSleepTime());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
