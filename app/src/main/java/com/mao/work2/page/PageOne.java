package com.mao.work2.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mao.work2.*;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Context;
import com.mao.work2.config.*;
import com.mao.work2.bean.*;
import com.mao.work2.enum.*;
import java.math.*;
import android.app.*;
import android.support.v4.view.*;
import com.mao.work2.util.*;
import java.util.*;

/**
 * Created by Jay on 2020/9/28 0028.
 */
public class PageOne
{
	private static View view;
	private static Context context;
	private static Report report;

    public PageOne()
	{
		//空构造函数
    }

	public static void setReport(Report report)
	{
		PageOne.report = report;
	}

	public static Report getReport()
	{
		return report;
	}

    public View onCreateView(LayoutInflater inflater)
	{
		view = inflater.inflate(R.layout.page_one, null);
		context = view.getContext();
		updateView();	
        return view;
    }


	public static void updateView()
	{
		getData();
		
		//这儿可以修改报告项
		String[] list = new String[] {

			"平时加班(时)", "周末加班(时)", "节假日加班(时)", 
//		    "中班天数(天)",
			"夜班天数(天)" ,
			"调休(时)", "事假(时)", "病假(时)","年假(时)",
//		    "本月绩效(元)", 
//		    "岗位补贴(元)",
			"夜班补贴(元)",
			"加班费基数(元)",
			"平时加班(元/时)",
			"平时加班费(元)",
			"周末加班(元/时)",
			"周末加班费(元)",
			"节假日加班(元/时)",
			"节假日加班费(元)",
//		    "交通补贴(元)", "高温补贴(元)", 
//		    "养老保险(元)", "医疗保险(元)","失业保险(元)","公积金(元)", 
//		    "工会费(元)",
//		    "其他补贴(元)", "其他扣款(元)", 
//		    "调休费(元)","事假费(元)","病假费(元)",
			"本月应发(元)", "本月实发(元)" ,
			"本月5号应发(元)", "本月5号实发(元)",
			};
		
		//添加适配器
		ListAdapter adapter = new MyAdapter(context, list);
		ListView listView = (ListView) view.findViewById(R.id.pageoneListView);
		listView.setAdapter(adapter);
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//报告算法在这儿
	public static void getData()
	{
		Calendar cal = (Calendar)Config.getCalendar().clone();

		PageOne.report = new Report(cal);
		
		cal.add(Calendar.MONTH, -1);
		Report report1 = new Report(cal);
		
		cal.add(Calendar.MONTH, -1);
		Report report2 = new Report(cal);
		
		//应发工资
		report.set("本月5号应发(元)", MathUtil.F(report1.get("基本工资(元)") + report1.get("本月绩效(元)") + report1.get("岗位补贴(元)")
										 + report1.get("夜班天数(天)") * report1.get("夜班补贴(元/天)") + report1.get("中班天数(天)") * report1.get("中班补贴(元/天)")
										 + report1.get("交通补贴(元)") + report1.get("高温补贴(元)") + report1.get("其他补贴(元)")
										 + report2.get("平时加班费(元)") + report2.get("周末加班费(元)") + report2.get("节假日加班费(元)") , 2));

		//实发工资
		report.set("本月5号实发(元)", MathUtil.F(report.get("本月5号应发(元)")
		                                 - report2.get("事假费(元)") - report2.get("病假费(元)")
										 - report1.get("养老保险(元)") - report1.get("医疗保险(元)") - report1.get("失业保险(元)") 
										 - report1.get("公积金(元)")
										 - report1.get("工会费(元)") - report1.get("其他扣款(元)") , 2));
//										 
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}
}

class MyAdapter extends ArrayAdapter<String>
{
	public MyAdapter(Context context, String[] values) 
	{
		super(context, R.layout.page_one_entry, values);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.page_one_entry, parent, false);

		String text = getItem(position);

		TextView textView1 = (TextView) view.findViewById(R.id.entryTextView1);
		textView1.setText(text);

		TextView textView2 = (TextView) view.findViewById(R.id.entryTextView2);
		textView2.setText(PageOne.getReport().get(text) + "");

		return view;
	}
}

