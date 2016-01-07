package org.cl.main.crawler;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.cl.configuration.Config;
import org.cl.http.SpiderSina;
import org.cl.run.GetAtRec;
import org.cl.service.GetInfo;
import org.cl.service.Login;
import org.cl.service.MyRejectHandler;
import org.cl.service.RWUid;
import org.cl.service.SaveInfo;
/**
 * 获取每个用户微博中@到的用户id、@的次数，被@人回复的次数、用户名
 */
public class Main_GetAtRec {
	private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Config.corePoolSize,Config.maximumPoolSize,Config.keepAliveTime,
			Config.unit,new LinkedBlockingQueue<Runnable>(),new MyRejectHandler());
	
	private static void idFilter(RWUid y_ids){
		GetInfo.idfilter_dirId(y_ids, "Weibos_At");
	}
	public static void main(String args[]) throws IOException, InterruptedException
	{	
		Config.initial("D:\\Project_DataMinning\\Data\\Sina_res\\Sina_AgePre\\");
		//登录
		Login.login();
		SaveInfo.mkdir("Weibos_At");
		RWUid y_ids = GetInfo.getUIDinDir("Weibos");//读取用户ID放入ids[hashSet]
		idFilter(y_ids);
		//抓取
		String uid = null;
		while (null!=(uid=y_ids.getUid())) {
			SpiderSina spider = new SpiderSina();
			GetAtRec getAtRec = new GetAtRec(uid, spider);
			threadPoolExecutor.execute(getAtRec);
		}
		threadPoolExecutor.shutdown();
		while(threadPoolExecutor.isTerminated()){
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
}