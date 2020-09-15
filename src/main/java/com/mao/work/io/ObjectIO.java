package com.mao.work.io;

import java.util.*;
import java.io.*;
import android.os.*;
import com.mao.work.*;

public class ObjectIO <T>
{

	private File root = new File(Environment.getExternalStorageDirectory(), "/work/");

	//序列化
	public void outObject(T t, String fname)
	{

		try
		{
			mkRootDir();

			if (fname.contains("/"))
			{
				File d  = new File(root, fname.split("/")[0]);
				File f = new File(root, File.separator + fname);
				if (null == t)
				{
					if (f.exists())
					{
						f.delete();
					}

					if (d.exists() && d.isDirectory() && d.listFiles().length == 0)
					{
						d.delete();
					}
				}
				else
				{
					if (!d.exists())
					{   
						d.mkdir();
					}
					out(t,fname);
				}
			}
			else
			{
				out(t,fname);
			}
		}
		catch (Exception e)
		{
			MyLog.d("Out IOException");
		}

	}

	//反序列化
	public T inObject(String fname)
	{

		T t = null;

		try
		{
			FileInputStream fis = new FileInputStream(root + File.separator + fname);
			ObjectInputStream ois = new ObjectInputStream(fis);
			t = (T) ois.readObject();
		}
		catch (ClassNotFoundException e)
		{
			//MyLog.d("Class Not Found");
		}
		catch (IOException e)
		{
			//MyLog.d(" In IOException");
		}

		return t;
	}

	//创建文件
	public void mkRootDir()
	{
		try 
		{
			if (!root.exists())
			{   
				root.mkdir();
			}
		}
		catch (Exception e)
		{}
	}
	
	//写入文件
	public void out(T t, String fname)
	
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try
		{
			fos = new FileOutputStream(root + File.separator + fname);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(t);
		}
		catch (IOException e)
		{}
		finally{
			try
			{
				oos.flush();
				oos.close();
			}
			catch (IOException e)
			{}
		}
	}
}
