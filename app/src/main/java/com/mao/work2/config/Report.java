package com.mao.work2.config;

import java.util.*;
import com.mao.work2.settings.*;
import com.mao.work2.io.*;
import android.os.*;

public class Report
{
	public String reportFile;
	public Map<String,Float> report = new HashMap<String,Float>();
	
	//这儿可以修改报告项
	public String[] list = new String[] {
		
		"平时加班(时)", "周末加班(时)", "节假日加班(时)", 
//		"中班天数(天)",
		"夜班天数(天)" ,
		"调休(时)", "事假(时)", "病假(时)","年假(时)",
//		"本月绩效(元)", 
//		"岗位补贴(元)",
		"夜班补贴(元)",
		"加班费基数(元)",
		"平时加班(元/时)",
		"平时加班费(元)",
		"周末加班(元/时)",
		"周末加班费(元)",
		"节假日加班(元/时)",
		"节假日加班费(元)",
//		"交通补贴(元)", "高温补贴(元)", 
//		"养老保险(元)", "医疗保险(元)","失业保险(元)","公积金(元)", 
//		"工会费(元)",
//		"其他补贴(元)", "其他扣款(元)", 
//		"调休费(元)","事假费(元)","病假费(元)",
//		"本月应发(元)", "本月实发(元)" ,
		"下月5号应发(元)", "下月5号实发(元)"};

	public Report(String reportFile)
	{
		this.reportFile = reportFile + "/.r";
	}

	public void setReportFile(String reportFile)
	{
		this.reportFile = reportFile;
	}

	public String getReportFile()
	{
		return reportFile;
	}

	public  void setList(String[] list)
	{
		this.list = list;
	}

	public  String[] getList()
	{
		return list;
	}
		
	public void set(String key, Float value)
	{
		report.put(key,value);
	}

	public float get(String key)
	{
		Float value = report.get(key);
		if(null == value)
		{
			value = 0f;
		}
		return value;
	}
	
	public void setMapDefaultValue(Settings settings)
	{
		for(String key : list)
		{
			set(key, 0f);
		}
		
		for(String key : settings.getList())
		{
			set(key, settings.get(key));
		}
	}
	
	public void readFromFile()
	{
		Map<String,Float> report0 = (Map<String,Float>) new ObjectIO().readFromFile(reportFile);
		
		if(null != report0)
		{
			report = report0;
		}
		
	}
	
	public void writerToFile()
	{
		new ObjectIO().writerToFile(report,reportFile);
	}
}
