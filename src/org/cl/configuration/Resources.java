package org.cl.configuration;

/** 资源类*/
public class Resources
{
	/** 当前请求次数*/
	private static long REQUEST_NUM=0;
	
	/**增加请求次数*/
	public synchronized static void addREQUEST()
	{
		REQUEST_NUM++;
	}

	/** 返回请求次数*/
	public synchronized static long getREQUEST_NUM()
	{
		return REQUEST_NUM;
	}

	public static void setREQUEST_NUM(long rEQUESTNUM)
	{
		REQUEST_NUM = rEQUESTNUM;
	}
}
