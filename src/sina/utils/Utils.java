package sina.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.cl.configuration.RegexString;
//I/O操作类
public class Utils {

	public static Date getDateFromString(String dtext,Date fileCreateDate) {
		Date date=null;
		int y,mm,se;  
		Calendar c = Calendar.getInstance();  
		c.setTime(fileCreateDate);
		y = c.get(Calendar.YEAR); //年  
		//d = c.get(Calendar.DAY_OF_MONTH); //日  
		mm = c.get(Calendar.MINUTE); //分
		se = c.get(Calendar.SECOND);//秒
		if(dtext.contains("秒前")){
			int end=0;
			for(int i=0;i<dtext.length();i++){
				if(dtext.charAt(i)>='0' && dtext.charAt(i)<='9'){
					end++;
				}else{
					break;
				}
			}
			dtext=dtext.substring(0,end);
			int second=Integer.parseInt(dtext);
			c.set(Calendar.SECOND, se-second);
			date=c.getTime();
		}
		else if(dtext.contains("分钟前")){
			int end=0;
			for(int i=0;i<dtext.length();i++){
				if(dtext.charAt(i)>='0' && dtext.charAt(i)<='9'){
					end++;
				}else{
					break;
				}
			}
			dtext=dtext.substring(0,end);
			int minute=Integer.parseInt(dtext);
			c.set(Calendar.MINUTE, mm-minute);
			date=c.getTime();
		}else if(dtext.contains("今天")){
			dtext=dtext.replace("今天 ", "").trim();
			String ss[]=dtext.split(":");
			if(ss!=null && ss.length==2){
				c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(ss[0]));
				c.set(Calendar.MINUTE, Integer.parseInt(ss[1]));
				date=c.getTime();
			}
		}else if(dtext.contains("月")){
			dtext=y+"年".concat(dtext);
			SimpleDateFormat sf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			try {
				date=sf.parse(dtext);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if(dtext.contains("-")){
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				date=sf.parse(dtext);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
	

	
	
	/**
	 * 清除原始微博中包含的转发前内容、以及链接
	 * @param weibo_item
	 * @return
	 */
	public static String clearWeibo(String weibo_item) {
		String[] originalTexts = weibo_item.split("//@");
		if(originalTexts.length==0){return "";}
		String originalText = originalTexts[0].replaceAll("转发微博", "").trim();//得到原创内容
		if(originalText.equals("")){return "";}
		//去掉链接的微博内容
		String regex_url = RegexString.Regex_url;
		Pattern p_link = Pattern.compile(regex_url);
		Matcher m_link = p_link.matcher(originalText);
		String originalText_nolink = m_link.replaceAll("");
		return originalText_nolink;
	}


	public static void putInMap( Map<Integer, Integer> map,int index,int value) {
		if(map.containsKey(index)){
			map.put(index,map.get(index)+value);
		}else{
			map.put(index,value);
		}
	}
	public static void putInMap( Map<String, Integer> map,String index,int value) {
		if(map.containsKey(index)){
			map.put(index,map.get(index)+value);
		}else{
			map.put(index,value);
		}
	}
	public static Set<String> MapToSet(Map<String, Integer> map,int threshold) {
		Set<String> item_set = new HashSet<String>();
		Iterator<Entry<String, Integer>> map_it = map.entrySet().iterator();
		while(map_it.hasNext()){
			Entry<String, Integer> entry = map_it.next();
			if(entry.getValue()>threshold){//超过threshold数目，才将该记录添加到Set中
				item_set.add(entry.getKey());
			}
		}
		return item_set;
	}
	public static void SetToMap(Map<String, Integer> map,Set<String> set) {
		for(String item:set){//将f文件中出现的词项集合加入到map中
			if(map.containsKey(item)){
				map.put(item, map.get(item)+1);
			}else{
				map.put(item, 1);
			}
		}
	}
	public static void mapSortByValueInteger(List<String> list,Map<String, Integer> map) {
		List<Entry<String,Integer>> list_tmp = new ArrayList<Entry<String,Integer>>(map.entrySet());
		Collections.sort(list_tmp,new Comparator<Entry<String,Integer>>(){
			public int compare(Entry<String,Integer> arg0,Entry<String,Integer> arg1) {
				double r = arg1.getValue()-arg0.getValue();
				if(r>0)return 1;
				else if(r<0)return -1;
				else return 0;
			}
		});
		for(Entry<String,Integer> entry : list_tmp){
			String item = entry.getKey()+":"+entry.getValue();
			list.add(item);
		}
	}

	public static void sleep(long sleepTime){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println(Thread.currentThread().getName()+"Start to sleep!!!NowTime:"+df.format(new Date())+"I will sleep for "+sleepTime);
		try
		{	//休眠指定时间
			Thread.sleep(sleepTime);
		}catch (InterruptedException ee)
		{
			ee.printStackTrace();
			System.out.println(Thread.currentThread().getName()+"Fail to sleep!!!");
		}
		System.out.println(Thread.currentThread().getName()+"Succeed to sleep!!!NowTime:"+df.format(new Date()));
	}

}
