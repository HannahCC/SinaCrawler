package org.cl.configuration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import sina.utils.Utils;

public class Config
{

	
	/** 所有信息保存地址*/
	public static String SAVE_PATH="D:\\Project_DataMinning\\Data\\Sina_res\\Sina_NLPIR\\";//ext1000_Mute_GenderPre//"D:\\Project_DataMinning\\Data\\Sina_res\\Sina_AgePre_JSON_1000\\";// 
	//public static String SAVE_PATH="D:\\Project_DataMinning\\Data\\Sina_res\\Sina_GenderPre\\"; 
	
	/** 程序最大连续请求次数（建议10000-20000）*/
	public static int REQUEST_MAX=20000;
	
	/** 账号全部使用过后休眠一段时间 **/
	public static int SLEEP_TIME=36000000;

	/** 请求重试次数*/
	public static int RETRY = 3;
	
	/** 多线程设置 */
	public static int corePoolSize = 4;
	public static int maximumPoolSize = 4;
	public static int keepAliveTime = 10;
	public static TimeUnit unit = TimeUnit.MINUTES;
	
	/** 账号设置 **/
	public static List<String> USERNAME;
	public static List<String> PASSWORD;
	public static int COUNT = -1;
	public static int COUNT_MAX = 0;
	
	/** 每次登陆获取一个账号，所有账号使用一轮后休眠一次 **/
	public synchronized static void changeCount() {
		if(COUNT<COUNT_MAX-1){
			Utils.sleep(getUnitSleepTime());
			COUNT++;
		}else {
			Utils.sleep(getSleepTime());
			Config.COUNT = 0;//重新循环使用账号
			Resources.setREQUEST_NUM(0);	//请求数归零
		}
	}
	
	/** 每爬取一个ID的信息后，线程休眠随机时间，以此降低爬取速度*/
	public static long getUnitSleepTime(){
		Random r = new Random();
		int sleep_time = r.nextInt(10000);
		return sleep_time;
	}
	
	/** 达到最大的请求次数或请求被拒绝时程序的休眠时间,单位为毫秒（建议3600000-7200000）*/
	public static long getSleepTime(){
		Random r = new Random();
		int sleep_time = r.nextInt(3600000);
		return sleep_time;
	}

	public static void initial(String Path)
	{
		SAVE_PATH = Path;
		//获取配置
		File f = new File(SAVE_PATH+"Config\\Config.txt");
		Map<String,String> confmap = new HashMap<String,String>();
		try {
			BufferedReader r = new BufferedReader(new FileReader(f));
			String conf = "";
			while((conf = r.readLine())!= null){
				if("".equals(conf))continue;
				String conf_name = conf.split("\\s")[0];
				String conf_value =  conf.split("\\s")[1];
				confmap.put(conf_name, conf_value);
			}
			if(confmap.containsKey("REQUEST_MAX")){REQUEST_MAX = Integer.parseInt(confmap.get("REQUEST_MAX"));}
			if(confmap.containsKey("SLEEP_TIME")){SLEEP_TIME = Integer.parseInt(confmap.get("SLEEP_TIME"));}
			if(confmap.containsKey("RETRY")){RETRY = Integer.parseInt(confmap.get("RETRY"));}
			if(confmap.containsKey("corePoolSize")){corePoolSize = Integer.parseInt(confmap.get("corePoolSize"));}
			if(confmap.containsKey("maximumPoolSize")){maximumPoolSize = Integer.parseInt(confmap.get("maximumPoolSize"));}
			if(confmap.containsKey("keepAliveTime")){keepAliveTime = Integer.parseInt(confmap.get("keepAliveTime"));}
			r.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		f=new File(Config.SAVE_PATH+"Config\\SinaAccount.txt");
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
				COUNT_MAX++;
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
}
