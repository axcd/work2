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
	private static Report report = new Report();

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
		
		ListAdapter adapter = new MyAdapter(context, report.getList());
		getData();

		//添加适配器
		ListView listView = (ListView) view.findViewById(R.id.pageoneListView);
		listView.setAdapter(adapter);
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//报告算法在这儿
	public static void getData()
	{
		report.setMapDefaultValue(Config.getSettings());

		//把两月组合成一个月
		Month month  = new Month("");
		int n = Config.getStartDay();
		if(n==1)n=32;
		for (int i=n ;i <=31 ;i++)
		{
			month.setDay(i, Config.getPreMonth().getDay(i));
		}

		for (int i=1; i < n; i++)
		{
			month.setDay(i, Config.getNextMonth().getDay(i));
		}

		//调休倍率*小时，可以选择调休倍率
		float rateXhour = 0;
		//遍历该周期
		for (int i=1; i <= 31; i++)
		{

			if (null != month.getDay(i))
			{
				//获取小时
				String rate_str = month.getDay(i).getRate().getRateName();
				int rate_length = rate_str.length();
				float rate = Float.parseFloat(rate_str.substring(0, rate_length - 1));
				//获取小时
				String shour = month.getDay(i).getHour().getHourName();
				int length = shour.length();
				float hour = Float.parseFloat(shour.substring(0, length - 1));
				//不是休班
				if (!Shift.REST.equals(month.getDay(i).getShift()))
				{
					//加班
					if (Fake.NORMAL.equals(month.getDay(i).getFake()))
					{
						//平时加班
						if (Rate.ONE_AND_HALF.equals(month.getDay(i).getRate()))
						{
							String key = "平时加班(时)";
							report.set(key,report.get(key)+hour);
						}
						//周末加班
						else if (Rate.TWO.equals(month.getDay(i).getRate()))
						{
							String key = "周末加班(时)";
							report.set(key,report.get(key)+hour);
						}
						//节假日加班
						else if (Rate.THREE.equals(month.getDay(i).getRate()))
						{
							String key = "节假日加班(时)";
							report.set(key,report.get(key)+hour);
						}
					}
					else
					{
						if (hour >= 4)
						{

							//中班天数
							if (Shift.MIDDLE.equals(month.getDay(i).getShift()))
							{
								String key = "中班天数(天)";
								report.set(key,report.get(key)-1);
							}
							//夜班天数
							if (Shift.NIGHT.equals(month.getDay(i).getShift()))
							{
								String key = "夜班天数(天)";
								report.set(key,report.get(key)-1);
							}
						}
					}

					//中班天数
					if (Shift.MIDDLE.equals(month.getDay(i).getShift()))
					{
						String key = "中班天数(天)";
						report.set(key,report.get(key)+1);
					}
					//夜班天数
					if (Shift.NIGHT.equals(month.getDay(i).getShift()))
					{
						String key = "夜班天数(天)";
						report.set(key,report.get(key)+1);
					}
					//调休
					if (Fake.TAKEOFF.equals(month.getDay(i).getFake()))
					{
						String key = "调休(时)";
						report.set(key,report.get(key)+hour);
					rateXhour += rate*hour;
					}
					//事假
					if (Fake.LEAVE.equals(month.getDay(i).getFake()))
					{
						String key = "事假(时)";
						report.set(key,report.get(key)+hour);
					}
					//病假
					if (Fake.SICK.equals(month.getDay(i).getFake()))
					{
						String key = "病假(时)";
						report.set(key,report.get(key)+hour);
					}
					//年假
					if (Fake.PAID.equals(month.getDay(i).getFake()))
					{
						String key = "年假(时)";
						report.set(key,report.get(key)+hour);
					}
				}
			}
			
			//基本工资
			float base = Config.getSettings().get("基本工资(元)");
			//平时加班(H)
			report.set("平时加班(元/时)", MathUtil.F(base / 21.75 / 8 * 1.5 , 1));
			//平时加班费
			report.set("平时加班费(元)",  MathUtil.F((report.get("平时加班(元/时)") * report.get("平时加班(时)")), 1));
			//周末加班(H)
			report.set("周末加班(元/时)", MathUtil.F(base / 21.75 / 8 * 2, 1));
			//周末加班费
			report.set("周末加班费(元)",  MathUtil.F((report.get("周末加班(元/时)") * report.get("周末加班(时)")), 1));
			//节假日加班(H)
			report.set("节假日加班(元/时)", MathUtil.F(base / 21.75 / 8 * 3 , 1));
			//节假日加班费
			report.set("节假日加班费(元)",  MathUtil.F((report.get("节假日加班(元/时)") * report.get("节假日加班(时)")), 1));
			
			//应发工资
			report.set("本月应发(元)", MathUtil.F( base+report.get("本月绩效(元)")+report.get("岗位补贴(元)")
						 +report.get("夜班天数(天)")*Config.getSettings().get("夜班补贴(元/天)")+report.get("中班天数(天)")*Config.getSettings().get("中班补贴(元/天)")
			            +report.get("交通补贴(元)")+report.get("高温补贴(元)")+report.get("其他补贴(元)")
						+report.get("平时加班费(元)")+report.get("周末加班费(元)")+report.get("节假日加班费(元)") ,1));
			//实发工资
			report.set("本月实发(元)", MathUtil.F( report.get("本月应发(元)")-report.get("社会保险(元)")-report.get("公积金(元)")-report.get("其他扣款(元)"), 1));
		}
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

