package org.cl.model;

import java.util.ArrayList;

public class CommentInfo
{
	/** 微博回复的json信息集合*/
	private ArrayList<String> jsons=null;
	/** 下一页游标*/
	private int next_cursor=0;
	/** 上一页游标*/
	private int previous_cursor=0;
	/** ID总数*/
	private int total_number=0;
	
	public CommentInfo()
	{
		jsons=new ArrayList<String>();
	}

	@Override
	public String toString()
	{
		return "CommentInfo [jsons=" + jsons + ", next_cursor=" + next_cursor
				+ ", previous_cursor=" + previous_cursor + ", total_number="
				+ total_number + "]";
	}

	public ArrayList<String> getJsons()
	{
		return jsons;
	}

	public void setJsons(ArrayList<String> jsons)
	{
		this.jsons = jsons;
	}

	public int getNext_cursor()
	{
		return next_cursor;
	}

	public void setNext_cursor(int nextCursor)
	{
		next_cursor = nextCursor;
	}

	public int getPrevious_cursor()
	{
		return previous_cursor;
	}

	public void setPrevious_cursor(int previousCursor)
	{
		previous_cursor = previousCursor;
	}

	public int getTotal_number()
	{
		return total_number;
	}

	public void setTotal_number(int totalNumber)
	{
		total_number = totalNumber;
	}
}
