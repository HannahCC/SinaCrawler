package org.cl.parser;

public class Timeparser
{
	private static final String[] MONTH={"Jan", "Feb", "Mar", "Apr", "May",
		"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	
	//Fri Aug 28 00:00:00 +0800 2009
	//2009-Aug-28 00:00:00
	/** 解析时间得到2009-08-28 00:00:00格式*/
	public static String getTime(String t)
	{
//		System.out.println(t);
		String[] array=t.split("\\s+");
		if(array.length!=6)
		{
			return null;
		}
		StringBuffer time=new StringBuffer();
		time.append(array[5]+"-");
		boolean flag=false;
		for(int i=0;i<12;i++)
		{
			if(array[1].equals(MONTH[i]))
			{
				if(i>=9)
				{
					time.append((i+1)+"-");
				}
				else
				{
					time.append("0"+(i+1)+"-");
				}
				flag=true;
				break;
			}
		}
		if(!flag)
		{
			return null;
		}
		time.append(array[2]+" ");
		time.append(array[3]);
		
		return time.toString();
	}
}
