package org.cl.parser;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.cl.model.Tags;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TagsParser
{
	public static Tags getTags(String json)
	{
		//此用户没有标签
		if(json==null)return null;
		if(json.contains("error"))
		{
			return null;
		}

		int start = json.indexOf('[');
		int end = json.lastIndexOf(']') + 1;
		if (start == -1 || end == -1 || end <= start)
		{
			return null;
		}
		json = json.substring(start, end);
		JSONArray tag_json = JSONArray.fromObject(json);
		Set<String> tag_set = new HashSet<String>();
		for (int i = 0; i < tag_json.size(); i++)
		{
			JSONObject JsonObj = tag_json.getJSONObject(i);
			Iterator<?> iter = JsonObj.keys(); 
			iter.hasNext();
			String key = (String) iter.next();
			// 获取key的value
			String temp =(String)JsonObj.get(key);
			tag_set.add(temp);
		}
		Tags tags = new Tags();
		tags.setTags(tag_set);
		tags.setTotal_number(tag_json.size());
		return tags;
	}
}
