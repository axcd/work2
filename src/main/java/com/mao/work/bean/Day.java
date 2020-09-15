package com.mao.work.bean;

import com.mao.work.enum.*;
import java.io.*;
import java.util.*;

public class Day implements Serializable
{
	//日期 2020/09/09
	private String index;
	//班别
	private Shift shift;
	//假别
	private Fake fake;
	//倍数
	private Rate rate;
	//时数
	private Hour hour;

	public Day(String index, Shift shift, Fake fake, Rate rate, Hour hour)
	{
		this.index = index;
		this.shift = shift;
		this.fake = fake;
		this.rate = rate;
		this.hour = hour;
	}

	public void setIndex(String index)
	{
		this.index = index;
	}

	public String getIndex()
	{
		return index;
	}

	public void setShift(Shift shift)
	{
		this.shift = shift;
	}

	public Shift getShift()
	{
		return shift;
	}

	public void setFake(Fake fake)
	{
		this.fake = fake;
	}

	public Fake getFake()
	{
		return fake;
	}

	public void setRate(Rate rate)
	{
		this.rate = rate;
	}

	public Rate getRate()
	{
		return rate;
	}

	public void setHour(Hour hour)
	{
		this.hour = hour;
	}

	public Hour getHour()
	{
		return hour;
	}

}
