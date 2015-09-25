package org.cl.function;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.cl.configuration.Config;
import org.cl.run.GetFolId;
import org.cl.service.GetInfo;
import org.cl.service.MyRejectHandler;
import org.cl.service.RWUid;
import org.cl.service.SaveInfo;

public class Func_GetFolId{
	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(Config.corePoolSize, Config.maximumPoolSize, Config.keepAliveTime, 
			Config.unit, new LinkedBlockingQueue<Runnable>(),new MyRejectHandler());
		
	public void getUidInfo(String path,int deep) throws IOException, InterruptedException
	{	
		SaveInfo.initFileEnvironment_GetFolId(deep);
		//读取用户ID放入ids[hashSet]
		RWUid y_ids = GetInfo.getUID(path);
		idFilter(y_ids,deep);
        String uid = null;
        while (null!=(uid=y_ids.getUid())) {
        	GetFolId getUidInfo = new GetFolId(uid);
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
        
		SaveInfo.close_GetFolId();
	}
	private static void idFilter(RWUid y_ids,int deep) throws IOException{
		GetInfo.idfilter_userId(y_ids,"//Config//UserNotExist.txt");
		for(int i=0;i<=deep;i++){
			GetInfo.idfilter_userJson(y_ids,"//UidInfo_follows"+i+".txt","//UserInfoOfEnterprise"+i+".txt");
		}
	}
}
