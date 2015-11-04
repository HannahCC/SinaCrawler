package org.cl.main.dp;

import java.io.IOException;
import org.cl.service.GetInfo;
import org.cl.service.RWUid;
import org.cl.service.SaveInfo;

public class IdFilter {
	/**
	 * 过滤还未爬取的用户ID
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException
	{
		RWUid y_ids = GetInfo.getUID("ExpandID1.txt");
		idFilterUserInfo(y_ids,1);
		SaveInfo.saveSet("ExpandID1_usersy.txt", y_ids.ids, false);
	}
	private static void idFilterUserInfo(RWUid y_ids,int deep) throws IOException{
		GetInfo.idfilter_userId(y_ids,"//Config//UserNotExist.txt");
		for(int i=0;i<=deep;i++){
			GetInfo.idfilter_userJson(y_ids,"//UserInfo"+i+".txt","//UserInfoOfEnterprise"+i+".txt");
		}
	}
	
	private static void idFilterFri(RWUid y_ids,int deep) throws IOException{
		GetInfo.idfilter_userId(y_ids,"//Config//UserNotExist.txt");
		for(int i=0;i<=deep;i++){
			GetInfo.idfilter_userJson(y_ids,"//UidInfo_friends"+i+".txt","//UserInfoOfEnterprise"+i+".txt");
		}
	}
	
	private static void idFilterFol(RWUid y_ids,int deep) throws IOException{
		GetInfo.idfilter_userId(y_ids,"//Config//UserNotExist.txt");
		for(int i=0;i<=deep;i++){
			GetInfo.idfilter_userJson(y_ids,"//UidInfo_follows"+i+".txt","//UserInfoOfEnterprise"+i+".txt");
		}
	}
}
