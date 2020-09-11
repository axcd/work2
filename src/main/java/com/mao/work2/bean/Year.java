package com.mao.work2.bean;

import java.io.*;
import java.util.*;

public class Year implements Serializable
{
	private int year;
	private List<Month> months;

	public Year(int year, List<Month> months)
	{
		this.year = year;
		this.months = months;
	}
	
	public void setYear(int year)
	{
		this.year = year;
	}

	public int getYear()
	{
		return year;
	}

	public void setMonths(List<Month> months)
	{
		this.months = months;
	}

	public List<Month> getMonths()
	{
		return months;
	}
}
