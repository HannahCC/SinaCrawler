package org.cl.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.cl.configuration.Config;
import org.cl.model.StatusAndComment;
import org.cl.model.UidInfo;
import org.cl.model.User;

/** 保存信息到文件*/
public class SaveInfo 
{
	//用户信息
	private static FileOutputStream user_info=null;
	//获取的企业用户信息
	private static FileOutputStream user_enterprise=null;
	//关系过多的用户信息
	//private static FileOutputStream user_rel_tooMany=null;
	//不存在的用户
	private static FileOutputStream user_notExist=null;
	//用户id信息
	private static FileOutputStream uid_fri=null;
	//用户id信息
	private static FileOutputStream uid_fol=null;
	//拓展用户ID
	private static FileOutputStream expand_id=null;
	//用户的类别
	private static FileOutputStream user_type=null;

	public static void initFileEnvironment_GetUserInfo(int deep) {
		File temp0=new File(Config.SAVE_PATH+"UserInfo"+deep+".txt");
		File temp2=new File(Config.SAVE_PATH+"UserInfoOfEnterprise"+deep+".txt");
		File temp3=new File(Config.SAVE_PATH+"Config/UserNotExist.txt");
		try 
		{
			user_info=new FileOutputStream(temp0,true);
			user_enterprise=new  FileOutputStream(temp2,true);
			user_notExist=new FileOutputStream(temp3,true);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void initFileEnvironment_GetFriId(int deep){//path是原用户ID的文件名
		File temp0=new File(Config.SAVE_PATH+"UidInfo_friends"+deep+".txt");
		File temp2=new File(Config.SAVE_PATH+"ExpandID"+(deep+1)+".txt");
		try 
		{
			uid_fri=new FileOutputStream(temp0,true);
			expand_id=new FileOutputStream(temp2,true);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void initFileEnvironment_GetFolId(int deep){//path是原用户ID的文件名
		File temp1=new File(Config.SAVE_PATH+"UidInfo_follows"+deep+".txt");
		File temp2=new File(Config.SAVE_PATH+"ExpandID"+(deep+1)+".txt");
		try 
		{
			uid_fol=new FileOutputStream(temp1,true);
			expand_id=new FileOutputStream(temp2,true);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void initFileEnvironment_GetWeibo(){
		mkdir("Weibos");
	}
	public static void initFileEnvironment_GetUserType(int deep){
		File temp0=new File(Config.SAVE_PATH+"UserType"+deep+".txt");
		File temp3=new File(Config.SAVE_PATH+"Config/UserNotExist.txt");
		try{
			user_type = new FileOutputStream(temp0,true);
			user_notExist=new FileOutputStream(temp3,true);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void close_GetUserInfo() throws IOException {
		user_info.flush();
		user_enterprise.flush();
		user_notExist.flush();
		user_info.close();
		user_enterprise.close();
		user_notExist.close();
	}
	public static void close_GetFriId() throws IOException {
		uid_fri.flush();
		expand_id.flush();
		uid_fri.close();
		expand_id.close();
	}
	public static void close_GetFolId() throws IOException {
		uid_fol.flush();
		expand_id.flush();
		uid_fol.close();
		expand_id.close();
	}
	public static void close_GetUserType() throws IOException {
		user_type.flush();
		user_type.close();
	}


	/** 不存在的用户ID
	 * @throws IOException */
	public synchronized static void saveUserNotExist(String info) throws IOException
	{
		user_notExist.write(info.getBytes());
	}
	/** 保存普通用户信息
	 * @param original 
	 * @throws IOException */
	public synchronized static void saveUser(User user) throws IOException
	{	
		JSONObject jsonobj=JSONObject.fromObject(user);
		String info = jsonobj.toString()+"\r\n";
		user_info.write(info.getBytes());//原始用户
	}
	/** 保存企业用户信息
	 * @throws IOException */
	public synchronized static void saveEnterpriseUser(User user) throws IOException
	{
		JSONObject jsonobj=JSONObject.fromObject(user);
		String info = jsonobj.toString()+"\r\n";
		user_enterprise.write(info.getBytes());
	}
	/** 保存关系
	 * @throws IOException */
	public static synchronized void saveFriends(String uid,UidInfo ids_friends) throws IOException {
		JSONObject jsonobj=JSONObject.fromObject(ids_friends);
		String info = jsonobj.toString()+"\r\n";
		uid_fri.write(info.getBytes());
	}
	/** 保存关系
	 * @throws IOException */
	public static synchronized void saveFollows(String uid,UidInfo ids_follower) throws IOException {
		JSONObject jsonobj=JSONObject.fromObject(ids_follower);
		String info = jsonobj.toString()+"\r\n";
		uid_fol.write(info.getBytes());
	}
	/**
	 * 保存拓展用户的ID
	 * @param fri_id
	 * @throws IOException
	 */
	public synchronized static void saveExpandID(UidInfo ids) throws IOException
	{
		Set<String> ids_set = new HashSet<String>();
		for(String id:ids.getUids()){ids_set.add(id);};
		for(String id:ids_set){
			String res = id+"\r\n";
			expand_id.write(res.getBytes());
		}
	}
	/** 保存用户类别信息
	 * @param original 
	 * @throws IOException */
	public synchronized static void saveUserType(String info) throws IOException
	{
		user_type.write(info.getBytes());
	}
	
	public static void saveWeiBo(String filename,String uid,ArrayList<StatusAndComment> weibos) throws IOException {
		JSONArray weibos_json=JSONArray.fromObject(weibos);
		String path=Config.SAVE_PATH+filename+uid+".txt";
		File file=new File(path);
		FileOutputStream fout=new FileOutputStream(file);
		for(int i=0;i<weibos_json.size();i++){
			JSONObject  wb = weibos_json.getJSONObject(i);
			String info = wb.toString()+"\r\n";
			fout.write(info.getBytes());
		}
		fout.close();
	}


	/************UTILS**************************************/
	/**
	 * 获取dirname+count目录下文件数目
	 * @param dirname
	 * @param count
	 * @return
	 */
	public static int getDirSize(String dirname,int count) {
		File dir = new File(Config.SAVE_PATH+dirname+count);
		if(!dir.exists())return 0;
		int size = dir.listFiles().length;
		return size;
	}
	/**
	 * 将s文件中的内容复制到filename中
	 * @param s
	 * @param filename
	 */
	public static void fileCopy(File s,String filename){
		try {
			FileInputStream fi = new FileInputStream(s);
			FileChannel in = fi.getChannel();
			File t = new File(filename);
			FileOutputStream fo = new FileOutputStream(t);
			FileChannel out = fo.getChannel();
			in.transferTo(0, in.size(), out);
			fo.flush();
			fi.close();
			in.close();
			fo.close();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 创建目录
	 * @param filename
	 */
	public static void mkdir(String filename){
		File dir= new File(Config.SAVE_PATH+filename);
		if(!dir.exists()){dir.mkdirs();}
	}
	/**
	 * 删除文件
	 * @param filename
	 */
	public static void clearFile(String filename) {
		File file = new File(Config.SAVE_PATH+filename);
		if(file.exists()){file.delete();}
	}
	/**
	 * 判断目录是否存在
	 * @param filename
	 * @return
	 */
	public static boolean isFileExist(String filename){
		File dir= new File(filename);
		if(dir.exists()){return true;}
		else{return false;}
	}

	public static synchronized int saveDict(String filename,Set<String> set,boolean isAppend,int s) throws IOException {
		if(set==null||set.size()==0)return -1;
		File f = new File(Config.SAVE_PATH+filename);
		BufferedWriter w = new BufferedWriter(new FileWriter(f,isAppend));
		int i = s;
		for(String emotion:set){
			w.write(emotion+"\t"+i+"\r\n");
			i++;
		}
		w.flush();
		w.close();
		return i;
	}
	public static synchronized int saveDict(String filename,Set<String> set,boolean isAppend) throws IOException {
		if(set==null||set.size()==0)return -1;
		File f = new File(Config.SAVE_PATH+filename);
		BufferedWriter w = new BufferedWriter(new FileWriter(f,isAppend));
		int i = 0;
		for(String emotion:set){
			w.write(i+" "+emotion+"\r\n");
			i++;
		}
		w.flush();
		w.close();
		return i;
	}
	//存储原始词项，及词项标签
	public static synchronized void saveDict(String filename,Set<String> set,String POS) throws IOException {
		if(set==null||set.size()==0)return;
		File f = new File(Config.SAVE_PATH+filename);
		BufferedWriter w = new BufferedWriter(new FileWriter(f,true));
		for(String emotion:set){
			w.write(emotion+POS+"\r\n");
		}
		w.flush();
		w.close();
	}
	public static synchronized void saveEntryList(String filename,List<Entry<String, Integer>> list,String separator,boolean isAppend) throws IOException {
		if(list==null||list.size()==0)return;
		File f = new File(Config.SAVE_PATH+filename);
		BufferedWriter w = new BufferedWriter(new FileWriter(f,isAppend));
		for(Entry<String, Integer> l : list){
			w.write(l.getKey()+separator+l.getValue()+"\r\n");
		}
		w.flush();
		w.close();
	}
	public static synchronized void saveMapList(String filename,List<Map<Integer, Integer>> list,String separator,boolean isAppend) throws IOException {
		if(list==null||list.size()==0)return;
		File f = new File(Config.SAVE_PATH+filename);
		BufferedWriter w = new BufferedWriter(new FileWriter(f,isAppend));
		for(Map<Integer, Integer>  map : list){
			for(Entry<Integer, Integer> entry : map.entrySet()){
				w.write(entry.getKey()+separator+entry.getValue()+"\t");
			}
			w.write("\r\n");
		}
		w.flush();
		w.close();
	}
	public static synchronized void saveMap(String filename, Map<Integer,Integer> map,boolean isAppend) throws IOException {
		if(map==null||map.size()==0)return;
		File f = new File(Config.SAVE_PATH+filename);
		BufferedWriter w = new BufferedWriter(new FileWriter(f,isAppend));
		Iterator<Entry<Integer,Integer>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Entry<Integer,Integer> entry = it.next();
			w.write(entry.getKey()+":"+entry.getValue()+"\r\n");
		}
		w.flush();
		w.close();
	}
	public static synchronized void saveMap(String filename, String uid, Map<?,?> map,boolean isAppend) {
		try {
			if(map==null||map.size()==0)return;
			File f = new File(Config.SAVE_PATH+filename);
			BufferedWriter w = new BufferedWriter(new FileWriter(f,isAppend));
			Iterator<?> it = map.entrySet().iterator();
			w.write(uid+"\t");
			while(it.hasNext()){
				Entry entry = (Entry) it.next();
				w.write(entry.getKey()+":"+entry.getValue()+"\t");
			}
			w.write("\r\n");
			w.flush();
			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static synchronized void saveDict(String filename,List<String> list,boolean isAppend,int s) throws IOException {
		if(list==null||list.size()==0)return;
		File f = new File(Config.SAVE_PATH+filename);
		BufferedWriter w = new BufferedWriter(new FileWriter(f,isAppend));
		int i = s;
		for(String emotion:list){
			w.write(emotion+"\t"+i+"\r\n");
			i++;
		}
		w.flush();
		w.close();
	}
	public static synchronized void saveList(String filename, List<String> list,boolean isAppend) throws IOException {
		if(list==null||list.size()==0)return;
		File f = new File(Config.SAVE_PATH+filename);
		BufferedWriter w = new BufferedWriter(new FileWriter(f,isAppend));
		for(String item : list){
			w.write(item+"\r\n");
		}
		w.flush();
		w.close();
	}
	public static synchronized void saveSet(String filename, Set<String> set,boolean isAppend) throws IOException {
		if(set==null||set.size()==0)return;
		File f = new File(Config.SAVE_PATH+filename);
		BufferedWriter w = new BufferedWriter(new FileWriter(f,isAppend));
		for(String item : set){
			w.write(item+"\r\n");
		}
		w.flush();
		w.close();
	}
	public static synchronized void saveString(String filename, String info,boolean isAppend) throws IOException {
		if(null==info||"".equals(info))return;
		File f = new File(Config.SAVE_PATH+filename);
		BufferedWriter w = new BufferedWriter(new FileWriter(f,isAppend));
		w.write(info+"\r\n");
		w.flush();
		w.close();
	}
	public static void writeFileFromStream(String filename,InputStream in){
		if(filename==null || filename.trim().length()==0)
			return;
		File file=new File(filename);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream fou=null;
		try {
			fou = new FileOutputStream(file);
			byte []buffer=new byte[1024*4];
			int len=-1;
			while((len=in.read(buffer))!=-1){
				fou.write(buffer,0,len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(fou!=null)
				try {
					fou.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}	

}
