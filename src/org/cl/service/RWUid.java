package org.cl.service;

import java.util.HashSet;
import java.util.Iterator;

/** 管理UID*/
public class RWUid
{
	/** 用户ID集合*/
	public HashSet<String> ids=null;
	
	public RWUid()
	{
		ids=new HashSet<String>();
	}
	
	/** 加入UID*/
	public void setUid(String uid)
	{
		ids.add(uid);
	}
	
	/** 返回下一个，没有返回null*/
	public String getUid()
	{
		if(ids.size()<=0)
		{
			return null;
		}
		Iterator<String> iter=ids.iterator();
		String id=iter.next();
		ids.remove(id);
		return id;
	}
	
	/**返回剩下的ID数*/
	public int getNum()
	{
		return ids.size();
	}
	/**删除列表中的id**/
	public boolean delete(String id){
		return ids.remove(id);
	}
	
	/**判断id是否存在于set中**/
	public boolean isExist(String id){
		return ids.contains(id);
	}
}
