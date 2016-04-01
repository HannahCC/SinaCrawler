package org.cl.function;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.cl.configuration.Config;
import org.cl.http.SpiderSina;
import org.cl.run.GetWeiBo;
import org.cl.service.GetInfo;
import org.cl.service.MyRejectHandler;
import org.cl.service.RWUid;
import org.cl.service.SaveInfo;

public class Func_GetWeibo
{
	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1,1,Config.keepAliveTime,
			Config.unit,new LinkedBlockingQueue<Runnable>(),new MyRejectHandler());
	private SpiderSina spider;

	public Func_GetWeibo(SpiderSina spider) {
		spider = this.spider;
	}
	/**
	 * 根据用户ID获取用户的微博
	 * */
	public void getWeibos(String path,int deep) throws IOException, InterruptedException
	{
		SaveInfo.initFileEnvironment_GetWeibo();
		//读取用户ID放入ids[hashSet]
		RWUid y_ids=GetInfo.getUID(path);
		//过滤已经爬到的id
		idFilter(y_ids,deep);

		String uid = null;
		while (null!=(uid=y_ids.getUid())) {
			GetWeiBo getWeiBo = new GetWeiBo(uid,spider);
			threadPool.execute(getWeiBo);
		}
		
		threadPool.shutdown();
		while (threadPool.isTerminated()) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	/**
	 * 将已经获取到信息的id剔除
	 * 剔除id包括
	 * Weibos文件夹下已经存在的文件名中的id
	 * UserNotExist.txt中的id
	 * @param y_ids
	 * @throws IOException 
	 */
  	public void idFilter(RWUid y_ids,int deep) throws IOException{
		//UserInfo.txt
		GetInfo.idfilter_userId(y_ids,"//Config//UserNotExist.txt");
		GetInfo.idfilter_dirId(y_ids, "//Weibos");
		/*for(int i=0;i<=deep;i++){
			GetInfo.idfilter_userJson(y_ids,"//UserInfoOfEnterprise"+deep+".txt");
		}*/
	}
}
