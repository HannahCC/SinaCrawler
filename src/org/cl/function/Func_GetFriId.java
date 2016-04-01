package org.cl.function;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.cl.configuration.Config;
import org.cl.http.SpiderSina;
import org.cl.run.GetFriId;
import org.cl.service.GetInfo;
import org.cl.service.MyRejectHandler;
import org.cl.service.RWUid;
import org.cl.service.SaveInfo;

/**
 * 获取用户的关注用户ID
 * @author Hannah
 *
 */
public class Func_GetFriId{
	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(Config.corePoolSize, Config.maximumPoolSize, Config.keepAliveTime, 
			Config.unit, new LinkedBlockingQueue<Runnable>(),new MyRejectHandler());
	private SpiderSina spider;

	public Func_GetFriId(SpiderSina spider) {
		spider = this.spider;
	}
	public void getUidInfo(String path,int deep) throws IOException, InterruptedException
	{	
		SaveInfo.initFileEnvironment_GetFriId(deep);
		//读取用户ID放入ids[hashSet]
		RWUid y_ids = GetInfo.getUID(path);
		idFilter(y_ids,deep);
        String uid = null;
        while (null!=(uid=y_ids.getUid())) {
        	GetFriId getUidInfo = new GetFriId(uid,spider);
        	threadPool.execute(getUidInfo);
		}
        
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {
        	 try {
                 Thread.sleep(100);
             } catch (Exception e) {
                 // log.info("error during sleep");
             }
		}
        
		SaveInfo.close_GetFriId();
	}
	
	private static void idFilter(RWUid y_ids,int deep) throws IOException{
		GetInfo.idfilter_userId(y_ids,"//Config//UserNotExist.txt");
		for(int i=0;i<=deep;i++){
			GetInfo.idfilter_userJson(y_ids,"//UidInfo_friends"+i+".txt"/*,"//UserInfoOfEnterprise"+i+".txt"*/);
		}
	}
}
