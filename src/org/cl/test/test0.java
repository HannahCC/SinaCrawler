package org.cl.test;

import java.io.IOException;

import org.cl.configuration.Config;
import org.cl.http.SpiderSina;
import org.cl.service.Login;
/**
 * 测试各个功能
 * @author Administrator
 *
 */
public class test0
{
	public static void main(String[] args) throws IOException
	{
		Config.initial("D:\\Project_DataMinning\\Data\\Sina_res\\Sina_AgePre_JSON\\");
		//登录
		Login.login();
		SpiderSina spider=new SpiderSina();
		String uid = "1321621261";
		spider.tempGet();
		/*GetUserInfo user_info=new GetUserInfo(uid, spider);
		user_info.run();*/

		/*SaveInfo.initFileEnvironment_GetFriId(0);
		GetFriId fri_info = new GetFriId(uid, spider);
		fri_info.run();
		SaveInfo.close_GetFriId();*/
		
		/*GetFolId fol_info = new GetFolId(uid, spider);
		fol_info.run();*/
		/*GetWeiBo wb=new GetWeiBo(uid, spider);
		wb.run();
		GetAtRec at = new GetAtRec(uid,spider);
		at.run();*/
		
	}
}
