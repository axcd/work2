package com.mao.work2.settings;

import java.util.*;
import com.mao.work2.io.*;

public class Settings
{
	private ObjectIO objectIO = new ObjectIO();
	private Map<String,Float> settings = new HashMap<String,Float>();
	//这儿可以修改设置项
	private String[] list = {
		"周期开始(日期)", "基本工资(元)", "本月绩效(元)",
		"中班补贴(元/天)", "夜班补贴(元/天)" ,
		"岗位补贴(元)", "高温补贴(元)","交通补贴(元)", 
		"社会保险(元)", "公积金(元)",
		"其他补贴(元)", "其他扣款(元)", "专项扣除(元)"
		};

	public Settings(String[] list)
	{
		this.list = list;
		init();
	}

	public Settings()
	{
		init();
	}

	public void setSettings(Map<String, Float> settings)
	{
		this.settings = settings;
	}

	public Map<String, Float> getSettings()
	{
		return settings;
	}

	public void setList(String[] list)
	{
		this.list = list;   
	}

	public String[] getList()
	{
		return list;
	}
	
	public void set(String key, Float value)
	{
		settings.put(key,value);
	}
	
	public float get(String key)
	{
		Float value = settings.get(key);
		if(null == value)
		{
			value = 0f;
		}
		return value;
	}
	
	//这儿可以设置默认值
	public void setMapDefaultValue()
	{
		set("周期开始(日期)",1f);
		set("基本工资(元)",2200f);
		set("本月绩效(元)",1100f);
	}
	
	private void init()
	{
		setMapDefaultValue();
		
		Map<String,Float> load_settings = (Map) objectIO.inObject(".setting");
		//防止修改过后，清空.settings文件的以前配置
		if(null != load_settings)
		{
			for(String key : list)
			{
				set(key,load_settings.get(key));
			}
		}
	}
	
	public void save()
	{
		objectIO.outObject(settings,".setting");
	}
	
}