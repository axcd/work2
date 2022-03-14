package com.mao.work2.io;

import java.util.*;
import java.io.*;
import android.os.*;
import com.mao.work2.*;
import android.app.usage.*;
import com.mao.work2.bean.*;

public class ObjectIO <T>
{
	private File root;
	
	private final File STORAGE = Environment.getExternalStorageDirectory();
	private final File DATA = Environment.getDataDirectory();
	private final String mao = "com.mao.work2";

	public ObjectIO()
	{
		root = new File(DATA, "data/"+mao);
		root = new File(STORAGE, mao);
	}

	//读取文件
	public T readFromFile(String fname)
	{

		T t = null;

		try
		{
			FileInputStream fis = new FileInputStream(root + File.separator + fname);
			ObjectInputStream ois = new ObjectInputStream(fis);
			t = (T) ois.readObject();
		}
		catch (ClassNotFoundException e)
		{}
		catch (IOException e)
		{}

		return t;
	}

	//删除文件
	public void deleteFile(File f)
	{
		if (f.exists() && !f.equals(root))
		{
			if(f.isFile() || ( f.isDirectory() && f.listFiles().length == 0))
			{
				f.delete();

				f = f.getParentFile();
				deleteFile(f);
			}
		}
	}	
	
	//删除文件夹的文件
	public void deleteDir(String dname)
	{
		File d = new File(root, dname);
		
		if(d.exists() &&d.isDirectory())
		{
			for(File file : d.listFiles())
			{
				deleteFile(file);
			}
		}
	}
	
	//写入文件
	public void writerToFile(T t, String fname)
	{
		File dir = new File(root,fname).getParentFile();
		
		if(!dir.exists()) dir.mkdirs();
		
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
