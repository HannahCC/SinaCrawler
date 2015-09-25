package org.cl.run;

import org.cl.service.GetLoginUser;

/** 实现定时更换登录用户*/
public class LoginRun implements Runnable
{

	/**
	 * 定时登陆
	 * */
	public void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(3600000);//一个小时登录一次
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			//登录
			GetLoginUser.setLOGIN_USER(GetLoginUser.getLoginUser());
		}
	}
}
