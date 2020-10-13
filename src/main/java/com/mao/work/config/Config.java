package com.mao.work.config;

import java.util.*;
import com.mao.work.bean.*;
import com.mao.work.view.*;
import com.mao.work.enum.*;
import com.mao.work.io.*;
import java.text.*;
import com.mao.work.*;
import com.mao.work.settings.*;
import android.view.*;
import android.app.*;
import com.mao.work.page.*;

public class Config
{
	private static Settings settings;
	private static Date today;
	private static Date selectedDate;
	private static Date startDate;
	private static Date endDate;
	private static Month preMonth;
	private static Month nextMonth;
	private static DayView selectedView;
	private static boolean weekend;
	private static Calendar calendar;
	private static Shift shift = Shift.DAY;
	private static Rate rate = Rate.ONE_AND_HALF;
	private static Hour hour = Hour.THREE;

	public static void init()
	{

		settings = new ObjectIO<Settings>().inObject("settings");
		if(null==settings)  settings = new Settings();

		Config.calendar = Calendar.getInstance();
		Config.setToday(Config.calendar.getTime());

		//如果大于开始日期显示在下一月
		if (calendar.get(Calendar.DATE) > Config.getSettings().getStartDay() && Config.getSettings().getStartDay() != 1)
		{
			Config.calendar.add(Calendar.MONTH, 1);
		}

		Config.setConfig();
	}
	
	public static void save()
	{
		new ObjectIO<Settings>().outObject(settings,"settings");
		//setConfig();
		PageTwo.updateView();
	}
	
	//设置Config
	public static void setConfig()
	{
		setStartDate(getCalendar());
		setEndDate(getCalendar());

		Calendar cal = (Calendar)getCalendar().clone();
		ObjectIO<Month> io = new ObjectIO<Month>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");

		String nMonth = sdf.format(cal.getTime());
		setNextMonth(io.inObject(nMonth));
		if(null == getNextMonth())
		{
			setNextMonth(new Month(nMonth));
		}

		cal.add(Calendar.MONTH,-1);
		String pMonth = sdf.format(cal.getTime());    
		setPreMonth(io.inObject(pMonth));
		if(null == getPreMonth())
		{
			setPreMonth(new Month(pMonth));
		}

	}
	
	//设置开始日期
	public static void setStartDate(Calendar calendar)
	{
		Calendar start_cal = (Calendar)calendar.clone();
	    if(Config.getSettings().getStartDay()!=1)start_cal.add(Calendar.MONTH, -1);
		int maxDay = start_cal.getActualMaximum(Calendar.DATE);
		
		if(maxDay<Config.getSettings().getStartDay()){
			start_cal.set(Calendar.DATE, maxDay);
		}else{
			start_cal.set(Calendar.DATE, Config.getSettings().getStartDay()-1);
		}
		Config.startDate = start_cal.getTime();
	}

	public static Date getStartDate()
	{
		return startDate;
	}

	//设置结束日期
	public static void setEndDate(Calendar calendar)
	{
		Calendar end_cal = (Calendar)calendar.clone();
	    if(Config.getSettings().getStartDay()==1)end_cal.add(Calendar.MONTH,1);
		int maxDay = end_cal.getActualMaximum(Calendar.DATE);
		
		if(maxDay<Config.getSettings().getStartDay()){
			end_cal.set(Calendar.DATE,maxDay+1);
		}else{
			end_cal.set(Calendar.DATE, Config.getSettings().getStartDay());
		}
		Config.endDate = end_cal.getTime();
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

	public static void setPreMonth(Month preMonth)
	{
		Config.preMonth = preMonth;
	}

	public static Month getPreMonth()
	{
		return preMonth;
	}

	public static void setNextMonth(Month nextMonth)
	{
		Config.nextMonth = nextMonth;
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

	public static void setShift(Shift shift)
	{
		Config.shift = shift;
	}

	public static Shift getShift()
	{
		return shift;
	}
	
	public static void setHour(Hour hour)
	{
		Config.hour = hour;
	}

	public static Hour getHour()
	{
		return hour;
	}

	public static void setRate(Rate rate)
	{
		Config.rate = rate;
	}

	public static Rate getRate()
	{
		return rate;
	}

}
