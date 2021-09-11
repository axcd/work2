package com.mao.work2.config;

import java.util.*;
import com.mao.work2.settings.*;

public class Report
{
	public static Map<String,Float> report = new HashMap<String,Float>();
	//这儿可以修改报告项
	public static String[] list = new String[] {
		"平时加班(时)", "周末加班(时)", "节假日加班(时)", 
		"中班天数(天)", "夜班天数(天)" ,
		"调休(时)", "事假(时)", "病假(时)","年假(时)",
		"本月绩效(元)", 
		"岗位补贴(元)",
		"交通补贴(元)", "高温补贴(元)", 
		"社会保险(元)", "公积金(元)", 
		"其他补贴(元)", "其他扣款(元)", "加班费基数(元)",
		"平时加班(元/时)",
		"平时加班费(元)",
		"周末加班(元/时)",
		"周末加班费(元)",
		"节假日加班(元/时)",
		"节假日加班费(元)",
		"本月应发(元)", "本月实发(元)"};

	public static void setList(String[] list)
	{
		Report.list = list;
	}

	public static String[] getList()
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
			set(key, settings.get(key));
		}
	}
}
