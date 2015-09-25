package org.cl.main.crawler;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.cl.configuration.Config;
import org.cl.run.ClearComment;
import org.cl.service.GetInfo;
import org.cl.service.MyRejectHandler;
import org.cl.service.RWUid;

public class Main_ClearComment {
	private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(Config.corePoolSize,Config.maximumPoolSize,Config.keepAliveTime,
			Config.unit,new LinkedBlockingQueue<Runnable>(),new MyRejectHandler());

	public static void main(String args[]) throws IOException, InterruptedException
	{	
		File dir = new File(Config.SAVE_PATH+"Weibos_Cleared\\");
		if(!dir.exists()){dir.mkdirs();}

		//读取用户ID放入ids[hashSet]
		RWUid y_ids = GetInfo.getUIDinDir("Weibos");
		GetInfo.idfilter_dirId(y_ids, "Weibos_Cleared");
		//抓取

		String uid = null;
		while (null!=(uid = y_ids.getUid())) {
			ClearComment clearComment = new ClearComment(uid);
			threadPool.execute(clearComment);
		}

		threadPool.shutdown();
		while (threadPool.isTerminated()) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		//改变文件名
		dir = new File(Config.SAVE_PATH+"Weibos");
		dir.renameTo(new File(Config.SAVE_PATH+"Weibos_BeforeCleared\\"));
		dir = new File(Config.SAVE_PATH+"Weibos_Cleared\\");
		dir.renameTo(new File(Config.SAVE_PATH+"Weibos\\"));
	}
}
