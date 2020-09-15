package com.mao.work;

import java.io.*;
import android.os.Environment;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyLog
{
	private static boolean isAppend = false;
	private static boolean isLogging = true;

	public static void setIsAppend(boolean isAppend)
	{
		MyLog.isAppend = isAppend;
	}

	public static boolean isAppend()
	{
		return isAppend;
	}

	public static void setIsLogging(boolean isLogging)
	{
		MyLog.isLogging = isLogging;
	}

	public static boolean isLogging()
	{
		return isLogging;
	}

	public static void d(String info){

		if(!isLogging){
			return;
		}

		try {
			File dir = new File(Environment.getExternalStorageDirectory(), "AppProjects/");

			if(!dir.exists()){   
				dir.mkdir();  
			}

			File file = new File(Environment.getExternalStorageDirectory(),"AppProjects/log.txt");

			if(!file.exists()){
				file.createNewFile();
			}

			BufferedWriter bw = new BufferedWriter(new FileWriter(file, isAppend));
			SimpleDateFormat sdf = new SimpleDateFormat("[ yyyy-MM-dd HH:mm:ss ]  ");
			String date = sdf.format(Calendar.getInstance().getTime());
			bw.write(date+info+"\r\n");
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		isAppend = true;
	}

}
