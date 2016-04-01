package org.cl.main.crawler;

import java.io.IOException;

import org.cl.configuration.Config;
import org.cl.function.Func_GetFolId;
import org.cl.function.Func_GetFriId;
import org.cl.function.Func_GetUserInfo;
import org.cl.function.Func_GetUserType;
import org.cl.function.Func_GetWeibo;
import org.cl.http.SpiderSina;
import org.cl.service.Login;
/**
 * 用于获取用户信息及用户关系
 * 用于获取用户微博及评论
 * @author Administrator
 * params:
 * 
 * 0.文件的目录（String)
 * 1.重新获取的深度deep(int)
 * 3.type
 */
public class Main_Crawl{
	public static void main(String[] args) throws IOException, InterruptedException
	{

		/*int deep = Integer.parseInt(args[0]);
		int type = Integer.parseInt(args[1]);
		String Root_Path = args[2];
		String ID_Path = args[3];*/

		int deep = 1;
		int type = 1;
		String Root_Path = "D:\\Project_DataMinning\\Data\\Sina_res\\Sina_NLPIR23335_AgeEduJob\\";//"D:\\Project_DataMinning\\Data\\Sina_res\\Sina_GenderPre\\";//Sina_SexPre\\";//
		String ID_Path = "ExpandID1.txt";//"32w_user_id_all.txt";//


		/*int deep = 0;
		int type = 22;
		String Root_Path = "D:\\Project_DataMinning\\Data\\Sina_res\\Sina_SexPre\\";//Sina_SexPre\\";//
		String ID_Path = "ExpandID0.txt";//"32w_user_id_all.txt";//*/
		
		//登录
		Config.initial(Root_Path);
		Login.login(false);
		SpiderSina spider = new SpiderSina(Login.getLOGIN_LOGIN_WC().getCookieManager().getCookies());
		//若deep>1时，将之前得到的拓展ID当做此次的原Id,此时的ID仍要获取拓展ID，但在存储用户信息时还是要注明为拓展用户
		if(type==1||type==4){
			Func_GetUserInfo getUserInfo = new Func_GetUserInfo(spider);
			getUserInfo.getUserInfo(ID_Path,deep);
		}
		if(type==21||type==4){
			Func_GetFriId getUid = new Func_GetFriId(spider);
			getUid.getUidInfo(ID_Path,deep);
		}
		if(type==22||type==4){
			Func_GetFolId getUid = new Func_GetFolId(spider);
			getUid.getUidInfo(ID_Path,deep);
		}
		if(type==3||type==4){
			Func_GetWeibo getWeibo = new Func_GetWeibo(spider);
			getWeibo.getWeibos(ID_Path,deep);
		}
		if(type==5){
			Func_GetUserType getUserType = new Func_GetUserType(spider);
			getUserType.getUserType(ID_Path,deep);
		}

	}
}
