package org.cl.model;

import java.util.Set;

public class Tags
{
	private Set<String> tags=null;

	private int total_number = 0;
	
	@Override
	public String toString()
	{
		if(tags.size()<=0)
		{
			return null;
		}
		StringBuffer tag=new StringBuffer();
		for(String s : tags)
		{
			tag.append(s+"^");
		}
		String temp=tag.toString();
		temp=temp.substring(0, temp.length()-1);
		return temp;
	}

	public Set<String> getTags()
	{
		return tags;
	}

	public void setTags(Set<String> tag_set)
	{
		this.tags = tag_set;
	}

	public int getTotal_number() {
		return total_number;
	}

	public void setTotal_number(int total_number) {
		this.total_number = total_number;
	}
	
	
}
