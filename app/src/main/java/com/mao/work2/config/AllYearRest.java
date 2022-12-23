package com.mao.work2.config;
import com.mao.work2.enum.*;
import com.mao.work2.io.*;
import java.io.*;
import java.util.*;

public class AllYearRest
{
	private String index;
	private ObjectIO io = new ObjectIO<Map<String,Float>>();
	private Map<String,Float> total = new TreeMap<String,Float>();

	public AllYearRest(String index)
	{
		this.index = index;
		getTotal();
	}

	public void add(Hour hour)
	{
		String hstr = hour.getHourName();
		String s = hstr.substring(0,hstr.length()-1);
		total.put(this.index, Float.valueOf(s));
	}
	
	public void del()
	{
		total.remove(this.index);
	}
	
	public void getTotal()
	{
		Map tm = (Map) io.readFromFile(this.index.substring(0,4)+"/all");
		if(tm!=null)
		{
			total = tm;
		}
	}
		
	public float getDays(){
		Float f = 0f;
		for(String key : this.total.keySet())
		{
//System.out.println(key.compareTo(index));
			if(key.compareTo(index)<1)
			{
				f += total.get(key); 
//System.out.println(key+" "+total.get(key));
			}
		}
		
		return f/8.0f;
	}
	
	public void save()
	{
//System.out.println(total.size());
		if(this.total.size()==0)
		{
			io.deleteFile(new File(io.getRoot(), this.index.substring(0,4)+"/all"));
			//new File(this.index.substring(0,4)+"/all").delete();
			//System.out.println(new File(this.index.substring(0,4)+"/all").delete());
		}else{
			io.writerToFile(this.total, this.index.substring(0,4)+"/all");
		}
	}
	
}
