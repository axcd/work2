package com.mao.work.bean;

import java.io.*;
import com.mao.work.*;
import java.util.*;

public class Month implements Serializable
{
	private String index;
	private Day[] days;

	public Month(String index)
	{
		this.index = index;
		this.days = new Day[31];
	}

	public void setDay(int i, Day day)
	{
		if(i>0 && i<=days.length+1)
		{
			this.days[i-1] = day;
		}
	}

	public Day getDay(int i)
	{
		Day day = null;
		if(i>0 && i<=days.length+1)
		{
			day = this.days[i-1];
		}
		return day;
	}

	public void setIndex(String index)
	{
		this.index = index;
	}

	public String getIndex()
	{
		return index;
	}

	public void setDays(Day[] days)
	{
		this.days = days;
	}

	public Day[] getDays()
	{
		return days;
	}
	
}
