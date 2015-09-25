package org.cl.function;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.cl.configuration.Config;
import org.cl.run.GetUserType;
import org.cl.service.GetInfo;
import org.cl.service.MyRejectHandler;
import org.cl.service.RWUid;
import org.cl.service.SaveInfo;

public class Func_GetUserType
{

	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(Config.corePoolSize,Config.maximumPoolSize,Config.keepAliveTime,
			Config.unit,new LinkedBlockingQueue<Runnable>(),new MyRejectHandler());
	/**
	 * 
	 * @throws IOException 
	 * @throws InterruptedException 
	 * */
	public void getUserType(String path,int deep) throws IOException, InterruptedException
	{	
		SaveInfo.initFileEnvironment_GetUserType(deep);
		//读取用户ID放入ids[hashSet]
		RWUid y_ids = GetInfo.getUID(path);
		//过滤已获取的id
		idFilter(y_ids,deep);
		
		String uid = null;
		while (null!=(uid=y_ids.getUid())) {
			GetUserType getUserType = new GetUserType(uid);
			threadPool.execute(getUserType);
		}
		
		threadPool.shutdown();
		while (threadPool.isTerminated()) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		SaveInfo.close_GetUserType();
	}
	/**
	 * 将已经获取到信息的id剔除
	 * @param y_ids
	 * @throws IOException 
	 */
	public void idFilter(RWUid y_ids,int deep) throws IOException{
		for(int i=0;i<=deep;i++){
			GetInfo.idfilter_userJson(y_ids,"//UserType"+i+".txt");
		}
		GetInfo.idfilter_userId(y_ids,"//Config//UserNotExist.txt");
	}
}
