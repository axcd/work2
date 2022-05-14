package com.mao.work2.settings;

import java.util.*;
import com.mao.work2.io.*;
import com.mao.work2.config.*;
import com.mao.work2.*;
import com.mao.work2.bean.*;
import java.io.*;
public class Settings
{
	private String path;
	private String fpath;
	
	private ObjectIO<Map<String,Float>> objectIO = new ObjectIO<Map<String,Float>>();
	private Map<String,Float> settings = new HashMap<String,Float>();
	
	//这儿可以修改设置项
	private String[] list = {
		"周期开始(日期)",
		"基本工资(元)",
		"本月绩效(元)",
		"岗位补贴(元)", 
//		"中班补贴(元/天)",
		"夜班补贴(元/天)" ,
		"高温补贴(元)","交通补贴(元)", 
		"养老保险(元)","医疗保险(元)","失业保险(元)", "公积金(元)",
		"工会费(元)",
		"其他补贴(元)", "其他扣款(元)"
	};

	public Settings(String path)
	{
		this.path = path;
		this.fpath = path + "/.s";
		init();
	}

	public void setSettings(Map<String, Float> settings)
	{
		for(String key : settings.keySet())
		{
			set(key, settings.get(key));
		}
	}

	private boolean isExits(String path)
	{
		File file = new File(objectIO.getRoot(), path);
		return file.exists();
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
//		set("基本工资(元)",2600f);
//		set("本月绩效(元)",500f);
//		set("岗位补贴(元)",100f);
	}
	
	public void loadSettings(String fname)
	{

		Map<String,Float> load_settings = (Map<String,Float>) objectIO.readFromFile(fname);
		
		if(load_settings!=null)
			setSettings(load_settings);
	}
	
	public void init()
	{
		setMapDefaultValue();
		loadSettings(".setting");
		loadSettings(fpath);
	}
	
	public void save()
	{
		if(!isExits(fpath)) writerS();
	}
	
	public void update()
	{
		writerSettings();
//		if(isExits(path))  
		writerS();
	}
	
	public void writerSettings()
	{
		Map<String,Float> load_settings = copys(settings);
//		load_settings.put("本月绩效(元)", 500f);
		load_settings.put("高温补贴(元)", 0f);
		load_settings.put("交通补贴(元)", 0f);
		load_settings.put("其他补贴(元)", 0f);
		load_settings.put("其他扣款(元)", 0f);
		objectIO.writerToFile(load_settings,".setting");
	}
	
	public void writerS()
	{
		Map<String,Float> load_settings = copys(settings);
		load_settings.remove("周期开始(日期)");
		objectIO.writerToFile(load_settings, fpath);
	}
	
	public Map<String,Float> copys(Map<String,Float> settings)
	{
		Map<String,Float> load_settings = new HashMap<String,Float>();
		for(String key : settings.keySet())
		{
			load_settings.put(key, settings.get(key));
		}
		return load_settings;
	}
	
}
