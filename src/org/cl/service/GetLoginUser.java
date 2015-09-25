package org.cl.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cl.configuration.Config;
import org.cl.http.LoginSina;


public class GetLoginUser
{
	/** 账号密码*/
	private static LoginSina LOGIN_USER=null;
	private static List<String> USERNAME;
	private static List<String> PASSWORD;
	private static int COUNT=0;

	static
	{
		File f=new File(Config.SAVE_PATH+"Config/SinaAccount.txt");
		try {
			BufferedReader r = new BufferedReader(new FileReader(f));
			String usr = "";
			USERNAME = new ArrayList<String>();
			PASSWORD = new ArrayList<String>();
			while((usr = r.readLine())!= null){
				if (usr.equals("")) {
					continue;
				}
				USERNAME.add(usr.split("\\s")[0]);
				PASSWORD.add(usr.split("\\s")[1]);
			}
			r.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public synchronized static LoginSina getLoginUser()
	{
		if(COUNT>=USERNAME.size()){
			//COUNT=0;//循环使用账号
			return null;//不循环使用账号
		}
		LoginSina user=new LoginSina(USERNAME.get(COUNT), PASSWORD.get(COUNT));
		user.dologinSina();
		user.redirect();
		COUNT++;
		return user;
	}

	public  synchronized static void setCount(int count){
		COUNT = count;
	}

	public synchronized static LoginSina getLOGIN_USER()
	{
		return LOGIN_USER;
	}


	public synchronized static void setLOGIN_USER(LoginSina lOGINUSER)
	{
		LOGIN_USER = lOGINUSER;
	}
}
