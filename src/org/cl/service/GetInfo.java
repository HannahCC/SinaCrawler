package org.cl.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.cl.configuration.Config;

import sina.utils.Utils;

public class GetInfo {

	public static void getUid1_2(ArrayList<String> uid1_list, ArrayList<String> uid2_list) throws IOException{
		File f = new File(Config.SAVE_PATH+"ExpandID0.txt");
		BufferedReader r = new BufferedReader(new FileReader(f));
		String id = "";
		ArrayList<String> tmp_list = new ArrayList<String>();
		while((id = r.readLine())!=null){
			if(!id.equals("")){
				tmp_list.add(id);
			}else{
				int size = tmp_list.size();
				for(int i=0;i<size-1;i++){
					for(int j=i+1;j<size;j++){
						uid1_list.add(tmp_list.get(i));
						uid2_list.add(tmp_list.get(j));
					}
				}
				tmp_list = new ArrayList<String>();
			}
		}
		//处理最后一组ID
		int size = tmp_list.size();
		for(int i=0;i<size-1;i++){
			for(int j=i+1;j<size;j++){
				uid1_list.add(tmp_list.get(i));
				uid2_list.add(tmp_list.get(j));
			}
		}
		tmp_list = new ArrayList<String>();
		r.close();
	}


	public static RWUid getUID(String filename) throws IOException{
		//读取用户ID放入ids[hashSet]
		RWUid y_ids=new RWUid();
		File r=new File(Config.SAVE_PATH+filename);
		BufferedReader br=new BufferedReader(new FileReader(r));
		String uid="";
		while((uid=br.readLine())!=null)
		{
			if(!(uid.equals("")))y_ids.setUid(uid.split("\\s")[0]);
		}
		br.close();
		return y_ids;
	}
	public static RWUid getUIDinDir(String dir) {
		//读取用户ID放入ids[hashSet]
		File flist = new File(Config.SAVE_PATH+dir);
		String[] weibo_list = flist.list();
		RWUid y_ids=new RWUid();
		for(String id :weibo_list){
			id = id.replace(".txt", "");
			y_ids.setUid(id);
		}
		return y_ids;
	}

	public static void getList(File f,List<String> lines,boolean isCleared) throws IOException{
		BufferedReader br=new BufferedReader(new FileReader(f));
		String line="";
		while((line=br.readLine())!=null)
		{
			if(!(line.equals(""))){
				if(isCleared&&lines.contains(line))continue;
				lines.add(line);
			}
		}
		br.close();
	}
	public static void getList(String filename, List<String> list, boolean isCleared) throws IOException {
		File r=new File(Config.SAVE_PATH+filename);
		BufferedReader br=new BufferedReader(new FileReader(r));
		String line="";
		while((line=br.readLine())!=null)
		{
			if(!(line.equals(""))){
				if(isCleared&&list.contains(line))continue;
				list.add(line);
			}
		}
		br.close();
	}
	public static List<String> getList(String filename,List<String> list,boolean isCleared,String...names) throws IOException {
		File f=new File(Config.SAVE_PATH+filename);
		if(!f.exists())return null;
		BufferedReader br=new BufferedReader(new FileReader(f));
		String line="";
		while((line=br.readLine())!=null)
		{
			if(!(line.equals(""))){
				JSONObject json = JSONObject.fromObject(line);
				for(int i=0;i<names.length-1;i++){
					json = json.getJSONObject(names[i]);
				}
				String item = json.getString(names[names.length-1]);
				if(isCleared&&line.contains(item)){continue;}
				list.add(item);
			}
		}
		br.close();
		return list;
	}
	public static List<List<String>> getListList(String filename,List<List<String>> list_list,String...names) throws IOException {
		File f=new File(Config.SAVE_PATH+filename);
		BufferedReader br=new BufferedReader(new FileReader(f));
		String line="";
		while((line=br.readLine())!=null)
		{
			if(!(line.equals(""))){
				JSONObject json = JSONObject.fromObject(line);
				for(int i=0;i<names.length-1;i++){
					json = json.getJSONObject(names[i]);
				}
				@SuppressWarnings("unchecked")
				List<String> item = (List<String>) json.get(names[names.length-1]);
				list_list.add(item);
			}
		}
		br.close();
		return list_list;
	}
	public static List<String> getList(File f,List<String> list,boolean isCleared,String...names) throws IOException {
		BufferedReader br=new BufferedReader(new FileReader(f));
		String line="";
		while((line=br.readLine())!=null)
		{
			if(!(line.equals(""))){
				JSONObject json = JSONObject.fromObject(line);
				for(int i=0;i<names.length-1;i++){
					json = json.getJSONObject(names[i]);
				}
				String item = json.getString(names[names.length-1]);
				if(isCleared&&line.contains(item)){continue;}
				list.add(item);
			}
		}
		br.close();
		return list;
	}
	public static List<String> getList(File f,List<String> lines,String regex, int i,boolean isCleared) throws IOException {
		BufferedReader br=new BufferedReader(new FileReader(f));
		String line="";
		while((line=br.readLine())!=null)
		{
			if(!(line.equals(""))){
				String item = line.split(regex)[i];
				if(isCleared&&lines.contains(item)){continue;}
				lines.add(item);
			}
		}
		br.close();
		return lines;
	}
	public static List<String> getList(String filename,List<String> lines,String regex, int i,boolean isCleared) throws IOException {
		File f=new File(Config.SAVE_PATH+filename);
		BufferedReader br=new BufferedReader(new FileReader(f));
		String line="";
		while((line=br.readLine())!=null)
		{
			if(!(line.equals(""))){
				String item = line.split(regex)[i];
				if(isCleared&&lines.contains(item)){continue;}
				lines.add(item);
			}
		}
		br.close();
		return lines;
	}
	@SuppressWarnings("unchecked")
	public static void getRelUidSet(String uid,Set<String> uidset,String filename) throws IOException {
		File f = new File(Config.SAVE_PATH+filename);
		BufferedReader b = new BufferedReader(new FileReader(f));
		String line = "";
		while((line = b.readLine())!=null){
			JSONObject uidinfo = JSONObject.fromObject(line);
			String id = uidinfo.getString("id");
			if(id.equals(uid)){
				uidset.addAll((List<String>) uidinfo.get("uids"));
				break;
			}
		}
		b.close();
	}
	public static void getSet(String filename, Set<String> set) throws IOException {
		File r=new File(Config.SAVE_PATH+filename);
		BufferedReader br=new BufferedReader(new FileReader(r));
		String line="";
		while((line=br.readLine())!=null)
		{
			if(!(line.equals(""))){
				set.add(line);
			}
		}
		br.close();
	}

	public static void getSet(String filename, Set<String> set,String regex, int i) throws IOException {
		File r=new File(Config.SAVE_PATH+filename);
		BufferedReader br=new BufferedReader(new FileReader(r));
		String line="";
		while((line=br.readLine())!=null)
		{
			if(!(line.equals(""))){
				String item = line.split(regex)[i];
				set.add(item);
			}
		}
		br.close();
	}

	public static void getSet(String filename, Set<String> set,String regex, int i, int j,int threshold) throws IOException {
		File r=new File(Config.SAVE_PATH+filename);
		BufferedReader br=null;
		br=new BufferedReader(new FileReader(r));
		String line="";
		while((line=br.readLine())!=null)
		{
			if(!(line.equals(""))){
				String[] items = line.split(regex);
				if(Integer.parseInt(items[j])>threshold){
					set.add(items[i]);
				}
			}
		}
		br.close();
	}


	@SuppressWarnings("unchecked")
	public static void getSet(String filename, Set<String> set, String keys) throws IOException {
		File f = new File(Config.SAVE_PATH+filename);
		BufferedReader b = new BufferedReader(new FileReader(f));
		String line = "";
		while((line = b.readLine())!=null){
			JSONObject uidinfo = JSONObject.fromObject(line);
			set.addAll((List<String>) uidinfo.get(keys));
		}
		b.close();
	}
	
	public static void getSet(Set<String> set, String filename, String keys) throws IOException {
		File f = new File(Config.SAVE_PATH+filename);
		BufferedReader b = new BufferedReader(new FileReader(f));
		String line = "";
		while((line = b.readLine())!=null){
			JSONObject uidinfo = JSONObject.fromObject(line);
			set.add(uidinfo.getString(keys));
		}
		b.close();
	}
	public static void getDict(String filename,Map<String,Integer> lines) throws IOException{
		File r=new File(Config.SAVE_PATH+filename);
		BufferedReader br=null;
		br=new BufferedReader(new FileReader(r));
		String line="";
		while((line=br.readLine())!=null)
		{
			if(!(line.equals(""))){
				String[] item = line.split("\t");
				lines.put(item[0],Integer.parseInt(item[1]));//(我们做朋友吧\t2)	
			}
		}
		br.close();
	}
	
	public static void getMap(String filename,Map<String, Integer> lines,String regex,int key,int value,boolean isReplace) throws IOException{
		File r=new File(Config.SAVE_PATH+filename);
		BufferedReader br=null;
		br=new BufferedReader(new FileReader(r));
		String line="";
		while((line=br.readLine())!=null)
		{
			if(!(line.equals(""))){
				String[] item = line.split(regex);
				if(isReplace){lines.put(item[key], Integer.parseInt(item[value]));}
				else{Utils.putInMap(lines, item[key], Integer.parseInt(item[value]));}
			}
		}
		br.close();
	}
	public static void getMap(String filename, Map<String, List<String>> map,String[] keys, String[] values) throws IOException {
		File r=new File(Config.SAVE_PATH+filename);
		BufferedReader br=null;
		br=new BufferedReader(new FileReader(r));
		String line="";
		while((line=br.readLine())!=null)
		{
			if(!(line.equals(""))){
				JSONObject json = JSONObject.fromObject(line);
				for(int i=0;i<keys.length-1;i++){
					json = json.getJSONObject(keys[i]);
				}
				String key = json.getString(keys[keys.length-1]);
				for(int i=0;i<values.length-1;i++){
					json = json.getJSONObject(values[i]);
				}
				@SuppressWarnings("unchecked")
				List<String> value = (List<String>) json.get(values[values.length-1]);
				map.put(key,value);
			}
		}
		br.close();
	}

	public static void getMap(String filename, Map<String,Integer> map,String keys, String values) throws IOException {
		File r=new File(Config.SAVE_PATH+filename);
		BufferedReader br=null;
		br=new BufferedReader(new FileReader(r));
		String line="";
		while((line=br.readLine())!=null)
		{
			if(!(line.equals(""))){
				JSONObject json = JSONObject.fromObject(line);
				String key = json.getString(keys);
				int value = json.getInt(values);
				map.put(key,value);
			}
		}
		br.close();
	}

	public static void getMap(String filename,String keys, String values, Map<String,String> map) throws IOException {
		File r=new File(Config.SAVE_PATH+filename);
		BufferedReader br=null;
		br=new BufferedReader(new FileReader(r));
		String line="";
		while((line=br.readLine())!=null)
		{
			if(!(line.equals(""))){
				JSONObject json = JSONObject.fromObject(line);
				String key = json.getString(keys);
				String value = json.getString(values);
				map.put(key,value);
			}
		}
		br.close();
	}

	public static void getMap(String filename, String regex,int key,int value,Map<String, Integer> map) throws IOException {
		File r=new File(Config.SAVE_PATH+filename);
		BufferedReader br=new BufferedReader(new FileReader(r));
		String line="";
		while((line=br.readLine())!=null)
		{
			if(!(line.equals(""))){
				String[] item = line.split(regex);
				if(map.containsKey(item[key])){
					System.out.println();
				}
				map.put(item[key],Integer.parseInt(item[value]));
			}
		}
		br.close();
	}

	public static void getSetMap(Map<String, Set<String>> set_map,String filename,String regex1,String regex2,int i,int j) throws IOException {
		File f1 = new File(filename);
		BufferedReader br = new BufferedReader(new FileReader(f1));
		String line;
		while((line = br.readLine())!=null){
			Set<String> word_set = new HashSet<String>();
			String[] items = line.split(regex1);
			String[] item = items[j].split(regex2);
			for(String it : item){word_set.add(it);}
			set_map.put(items[i], word_set);
		}
		br.close();
	}
	public static void getMapList(String filename,List<Map<Integer, Integer>> emotion_map_list) throws IOException {
		File r=new File(Config.SAVE_PATH+filename);
		BufferedReader br=new BufferedReader(new FileReader(r));
		String line="";
		while((line=br.readLine())!=null)
		{

			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			if(!(line.equals(""))){
				String[] item_list = line.split("\t");
				for(String item : item_list){
					String[] type = item.split(":");
					map.put(Integer.parseInt(type[0]), Integer.parseInt(type[1]));
				}
			}
			emotion_map_list.add(map);
		}
		br.close();
	}
	/**
	 * 从filename.txt文件中获取用户的信息，存入Map<String,Set<String>> v_user_info,其中key是id,value为用户所属的类别（一个用户可以属于多个类别）
	 * @param filenames
	 * @return
	 * @throws IOException 
	 */
	public static void getFriInfo(Map<String,Set<String>> v_user_info,String... filenames) throws IOException{
		for(String filename : filenames){
			File r=new File(Config.SAVE_PATH+filename);
			BufferedReader br=new BufferedReader(new FileReader(r));
			String line=null;
			while((line=br.readLine())!=null)
			{
				if(!(line.equals(""))){
					String[] item = line.split("\t");
					String id = item[0];
					Set<String> v_user_type = new HashSet<String>();
					for(int i=2;i<item.length;i++){
						String type = item[i].split("##")[0];
						v_user_type.add(type);
					}
					v_user_info.put(id, v_user_type);
				}
			}
			br.close();
		}
	}


	public static void idfilter_userJson(RWUid y_ids, String ... filenames) throws IOException {
		for(String filename : filenames){
			File f=new File(Config.SAVE_PATH+filename);
			if(!f.exists()){continue;}
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = "";
			while((line = br.readLine())!=null){
				JSONObject user_json = JSONObject.fromObject(line);
				String id = user_json.getString("id");
				y_ids.delete(id);
			}
			br.close();
		}
	}

	public static void idfilter_userId(RWUid y_ids, String filename) throws IOException {
		File f=new File(Config.SAVE_PATH+filename);
		if(!f.exists()){return;}
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = "";
		while((line = br.readLine())!=null){
			String id = line;
			y_ids.delete(id);
		}
		br.close();
	}


	public static void idfilter_userId(RWUid y_ids, String filename,String regex, int i) throws IOException {
		//UserNotExist.txt
		File f=new File(Config.SAVE_PATH+filename);
		if(!f.exists()){return;}
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = "";
		while((line = br.readLine())!=null){
			String id = line.split(regex)[i];
			y_ids.delete(id);
		}
		br.close();
	}

	public static void idfilter_dirId(RWUid y_ids, String dirname) {
		File f1=new File(Config.SAVE_PATH+dirname);
		File file[] = f1.listFiles(); 
		for(File f : file){
			String id = f.getName();
			id = id.replaceAll(".txt", "");
			y_ids.delete(id);
		}
	}


	@SuppressWarnings("unchecked")
	public static void getSetMap(String filename, Map<String, Set<String>> list_map,String keyname,String valuename) throws IOException {
		File f = new File(Config.SAVE_PATH+filename);
		BufferedReader b = new BufferedReader(new FileReader(f));
		String line = "",key="";
		JSONObject json = null;
		List<String> lists = null;
		while((line = b.readLine())!=null){
			json = JSONObject.fromObject(line);
			key = json.getString(keyname);
			lists = (List<String>) json.get(valuename);
			Set<String> values = new HashSet<String>();
			values.addAll(lists);
			list_map.put(key, values);
		}
		b.close();
	}

	public static String getStringFromStream(InputStream in) {
		BufferedReader reader=null;
		reader = new BufferedReader(new InputStreamReader(in));
		StringBuffer buffer=new StringBuffer();
		String str=null;
		try{
			while((str=reader.readLine())!=null){
				buffer.append(str+"\n");
			}	
			reader.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		try {
			return new String(buffer.toString().getBytes(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "error:"+e.getMessage();
		}
	}







}
