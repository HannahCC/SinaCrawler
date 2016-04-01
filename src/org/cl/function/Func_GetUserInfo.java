package org.cl.function;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.cl.configuration.Config;
import org.cl.http.SpiderSina;
import org.cl.run.GetUserInfo;
import org.cl.service.GetInfo;
import org.cl.service.MyRejectHandler;
import org.cl.service.RWUid;
import org.cl.service.SaveInfo;

public class Func_GetUserInfo
{
	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(Config.corePoolSize,Config.maximumPoolSize, Config.keepAliveTime, 
			Config.unit, new LinkedBlockingQueue<Runnable>(), new MyRejectHandler());//限制3个线程，防止服务器性能不够用
	private SpiderSina spider;

	public Func_GetUserInfo(SpiderSina spider) {
		spider = this.spider;
	}
	public void getUserInfo(String path,int deep) throws IOException, InterruptedException
	{	
		SaveInfo.initFileEnvironment_GetUserInfo(deep);
		//读取用户ID放入ids[hashSet]
		RWUid y_ids = GetInfo.getUID(path);
		//过滤已获取的id
		idFilter(y_ids,deep);
		System.out.println(y_ids.getNum());
		String uid = null;
		while(null!=(uid=y_ids.getUid())){
			GetUserInfo getUserInfo = new GetUserInfo(uid,spider);
			threadPool.execute(getUserInfo);
		}

		// 等待所有单子下完，为了提高性能，在每次检查单子是否下完时，如果没完成，则当前线程休眠100ms
		threadPool.shutdown();
		while (!threadPool.isTerminated()) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// log.info("error during sleep");
			}
		}
		SaveInfo.close_GetUserInfo();
	}
	
	private static void idFilter(RWUid y_ids,int deep) throws IOException{
		GetInfo.idfilter_userId(y_ids,"//Config//UserNotExist.txt");
		for(int i=0;i<=deep;i++){
			GetInfo.idfilter_userJson(y_ids,"//UserInfo"+i+".txt","//UserInfoOfEnterprise"+i+".txt");
			System.out.println(y_ids.ids.size());
		}
	}
}
