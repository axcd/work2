package com.mao.work2.config;

import java.util.*;
import com.mao.work2.bean.*;
import com.mao.work2.view.*;
import com.mao.work2.enum.*;
import com.mao.work2.io.*;
import java.text.*;
import com.mao.work2.*;
import com.mao.work2.settings.*;
import android.view.*;
import android.app.*;
import com.mao.work2.page.*;

public class Config
{
	private static int startDay;
	private static Date today;
	private static Date selectedDate;
	private static Date startDate;
	private static Date endDate;
	private static Month preMonth;
	private static Month nextMonth;
	private static DayView selectedView;
	private static boolean weekend;
	private static Calendar calendar;
	private static Settings settings;

	public static void setStartDay(int startDay)
	{
		Config.startDay = startDay;
	}

	public static void init()
	{
		Calendar calendar = Calendar.getInstance();
		Config.setToday(calendar.getTime());
		Config.setCalendar(calendar);
		Config.setConfig();
	}
	
	public static void save()
	{
		//保存修改
		settings.save();
		PageOne.updateView();
		PageTwo.updateView();
		PageThree.updateView();
	}
	
	//设置Config
	public static void setConfig()
	{
		setPreMonth();
		setNextMonth();
		setSettings(new Settings(nextMonth.getIndex()));
		setStartDate();
		setEndDate();
	}
	
	//设置开始日期
	public static void setStartDate()
	{
		Calendar start_cal = (Calendar)calendar.clone();
		start_cal.add(Calendar.MONTH, -1);
		int maxDay = start_cal.getActualMaximum(Calendar.DATE);
		
		if(startDay>1 && startDay<maxDay+1){
			start_cal.set(Calendar.DATE, startDay-1);
		}else{
			start_cal.set(Calendar.DATE, maxDay);
		}
		Config.startDate = start_cal.getTime();
	}

	public static Date getStartDate()
	{
		return startDate;
	}

	//设置结束日期
	public static void setEndDate()
	{
		Calendar end_cal = (Calendar)calendar.clone();
		int maxDay = end_cal.getActualMaximum(Calendar.DATE);
		
		if(startDay>1 && startDay<maxDay+1){
			end_cal.set(Calendar.DATE, startDay);
		}else{
			end_cal.set(Calendar.DATE, maxDay+1);
		}
		Config.endDate = end_cal.getTime();
	}

	public static void setStartDay()
	{    
		Config.startDay = (int)new Settings("setting").get("周期开始(日期)");
	}

	public static int getStartDay()
	{
		return startDay;
	}
	
	public static Date getEndDate()
	{
		return endDate;
	}

	public static void setSelectedDate(Date selectedDate)
	{
		Config.selectedDate = selectedDate;
	}

	public static Date getSelectedDate()
	{
		return selectedDate;
	}

	public static void setSelectedView(DayView selectedView)
	{
		Config.selectedView = selectedView;
	}

	public static DayView getSelectedView()
	{
		return selectedView;
	}

	public static void setToday(Date today)
	{
		Config.today = today;
	}

	public static Date getToday()
	{
		return today;
	}

	public static void setPreMonth()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");

		Calendar cal = (Calendar)getCalendar().clone();
		cal.add(Calendar.MONTH,-1);
		String pmonth = sdf.format(cal.getTime());
		Config.preMonth = new Month(pmonth);	
	}

	public static Month getPreMonth()
	{
		return preMonth;
	}

	public static void setNextMonth()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");

		Calendar cal = (Calendar)getCalendar().clone();
		String nmonth = sdf.format(cal.getTime());
		Config.nextMonth = new Month(nmonth);
	}

	public static Month getNextMonth()
	{
		return nextMonth;
	}

	public static void setWeekend(boolean weekend)
	{
		Config.weekend = weekend;
	}

	public static boolean isWeekend()
	{
		return weekend;
	}
	
	public static void setCalendar(Calendar calendar)
	{
		setStartDay();
		
		if(Config.startDay!=1)
		{
			if (calendar.get(Calendar.DATE) >= startDay)
			{
				calendar.add(Calendar.MONTH, 1);
			}
		}
		Config.calendar = calendar;
	}

	public static Calendar getCalendar()
	{
		return calendar;
	}
	
	public static void setSettings(Settings settings)
	{
		Config.settings = settings;
	}

	public static Settings getSettings()
	{
		return settings;
	}

}
