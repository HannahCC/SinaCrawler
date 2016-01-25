package org.cl.service;

import java.util.Date;

import org.cl.configuration.Config;
import org.cl.http.HttpUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class Login_old {
	private static WebClient LOGIN_WC = null;
	private static Date lastTime = null;
	public synchronized static WebClient getLOGIN_LOGIN_WC() {
		return LOGIN_WC;
	}
	public synchronized static void login(boolean flag){//flag 为true表示换IP
		if(lastTime!=null){//多个线程同时遇到403，即speed limit,都会选择重新登录，导致一次性会登录（线程数）次，导致不必要的休眠
			Date now = new Date();
			long diff = now.getTime() - lastTime.getTime();
			long mins = diff / (1000 * 60);
			if(mins<1){//1分钟内不再次登录
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		}
		if(!flag){LOGIN_WC = new WebClient(BrowserVersion.CHROME);}
		else{/** 每次获取一个IP，所有IP使用一轮后休眠一次 **/
			//SaveRecord.saveIP(Config.PROXY.get(Config.PROXY_COUNT)+"\t"+Config.PROT.get(Config.PROXY_COUNT)+"\t"+request_counts+"\t"+getCurrentTime());
			if(Config.PROXY_COUNT<Config.PROXY_COUNT_MAX){
				try {
					Thread.sleep(Config.getUnitSleepTime());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Config.PROXY_COUNT++;
			}else {
				try {
					Thread.sleep(Config.getSleepTime());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Config.PROXY_COUNT = 0;//重新循环使用账号
			}
			String proxy = Config.PROXY.get(Config.PROXY_COUNT);
			if(proxy.equals("")) LOGIN_WC = new WebClient(BrowserVersion.CHROME); //用本机IP
			else LOGIN_WC = new WebClient(BrowserVersion.CHROME,Config.PROXY.get(Config.PROXY_COUNT),Config.PROT.get(Config.PROXY_COUNT));
		}

		/*HttpClient httpclient = new DefaultHttpClient();
		HttpClientParams.setCookiePolicy(httpclient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);*/
		//WebClient LOGIN_WC = new WebClient(BrowserVersion.CHROME);
		LOGIN_WC.getOptions().setCssEnabled(false);
		LOGIN_WC.getOptions().setActiveXNative(false);
		LOGIN_WC.getOptions().setJavaScriptEnabled(false);

		HtmlPage page = HttpUtils.getPage(LOGIN_WC, "http://login.weibo.cn/login/");
		//如果得到的页面标题不对，则登录失败，否则执行提交登录请求
		/*if(!"Welcome to Facebook - Log In, Sign Up or Learn More".endsWith(page.getTitleText())){
		System.out.println("get the wrong page:"+page.getTitleText());return null;
		}*/
		//获取登录表单，提交登录请求
		HtmlForm login_form = (HtmlForm) page.getElementsByTagName("form").get(0);
		HtmlTextInput username = login_form.getInputByName("mobile");

		if(Config.USERNAME.size()==0||Config.PASSWORD.size()==0){System.out.println("Please write your Account into Account.txt file.");System.exit(-1);}
		Config.changeCount();
		username.setValueAttribute(Config.USERNAME.get(Config.COUNT));
		HtmlPasswordInput password = (HtmlPasswordInput) page.getByXPath("//Input[@type='password']").get(0);
		password.setValueAttribute(Config.PASSWORD.get(Config.COUNT));
		HtmlSubmitInput login = login_form.getInputByName("submit");
		HtmlPage index = HttpUtils.click(login);

		//判断是否登录成功
		//System.out.println(index.asText());
		if(!"我的首页".endsWith(index.getTitleText())){
			System.out.println(Config.USERNAME.get(Config.COUNT)+":"+Config.PASSWORD.get(Config.COUNT)+"--login fail!:"+index.getTitleText());return;
		}else{
			System.out.println(Config.USERNAME.get(Config.COUNT)+":"+Config.PASSWORD.get(Config.COUNT)+"--login successfully!");	
		}
		lastTime = new Date();
	}
}
