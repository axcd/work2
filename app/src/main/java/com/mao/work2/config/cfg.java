package com.mao.work2.config;
import java.util.*;
import com.mao.work2.bean.*;
import java.text.*;
import com.mao.work2.*;

public class cfg
{
	private Calendar calendar;
	private Month pMonth;
	private Month nMonth;
	
	public cfg(Calendar calendar)
	{
		this.calendar = calendar;
		
		setPreMonth();
		setNextMonth();
	}

	public void setPMonth(Month pMonth)
	{
		this.pMonth = pMonth;
	}

	public Month getPMonth()
	{
		return pMonth;
	}

	public void setNMonth(Month nMonth)
	{
		this.nMonth = nMonth;
	}

	public Month getNMonth()
	{
		return nMonth;
	}

	public void setPreMonth()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");

		Calendar cal = (Calendar)this.calendar.clone();
		cal.add(Calendar.MONTH,-1);
		String pmonth = sdf.format(cal.getTime());
		this.pMonth = new Month(pmonth);	
	}

	public void setNextMonth()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");

		Calendar cal = (Calendar)this.calendar.clone();
		String nmonth = sdf.format(cal.getTime());
		this.nMonth = new Month(nmonth);
	}
	
}
