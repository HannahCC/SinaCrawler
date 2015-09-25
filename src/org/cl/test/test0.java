package org.cl.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cl.configuration.Config;
import org.cl.http.SpiderSina;
import org.cl.run.GetAtRec;
import org.cl.run.GetFolId;
import org.cl.run.GetFriId;
import org.cl.run.GetUserInfo;
import org.cl.run.GetWeiBo;
import org.cl.service.GetInfo;
import org.cl.service.GetLoginUser;
import org.cl.service.Login;
import org.cl.service.SaveInfo;

import com.gargoylesoftware.htmlunit.WebClient;
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
