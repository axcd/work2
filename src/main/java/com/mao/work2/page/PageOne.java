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

/**
 * Created by Jay on 2015/8/28 0028.
 */
public class PageOne 
{

	public static View view;
	private static Context context;
	public static float[] data = new float[25];

    public PageOne()
	{
		//空构造函数
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container)
	{

		view = inflater.inflate(R.layout.page_two, container, false);
		context = view.getContext();
		setView();
        return view;
    }


	public static void setView()
	{
		//数组置零,获取数据
		String[] companies = new String[] {
			"平时加班(小时)", "周末加班(小时)", "节假日加班(小时)", "中班天数(天)", "夜班天数(天)" ,
			"调休(小时)", "事假(小时)", "病假(小时)","年假(小时)",
			"本月绩效(元)", "岗位补贴(元)", "交通补贴(元)", "高温补贴(元)", "社会保险(元)", "公积金(元)", 
			"其他补贴(元)", "其他扣款(元)", "平时加班(元/小时)","平时加班费", "周末加班(元/小时)",
			"周末加班费(元)", "节假日加班(元/小时)","节假日加班费(元)",
			"本月应发(元)", "本月实发(元)"};
		ListAdapter adapter = new MyAdapter(context, companies);
		getData();

		//添加适配器
		ListView listView = (ListView) view.findViewById(R.id.pagetwoListView);
		listView.setAdapter(adapter);
	}

	public static void getData()
	{
		//数组置零
		for (int i=0;i < data.length;i++)
		{
			data[i] = 0;
		}

		//把两月组合成一个月
		Month month  = new Month("");
		int n = Config.getSettings().getStartDay();
		if(n==1)n=32;
		for (int i=n ;i <=31 ;i++)
		{
			month.setDay(i, Config.getPreMonth().getDay(i));
		}

		for (int i=1; i < n; i++)
		{
			month.setDay(i, Config.getNextMonth().getDay(i));
		}

		//遍历该周期
		for (int i=1; i <= 31; i++)
		{

			if (null != month.getDay(i))
			{
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

							data[0] += hour;
						}
						//周末加班
						else if (Rate.TWO.equals(month.getDay(i).getRate()))
						{
							data[1] += hour;
						}
						//节假日加班
						else if (Rate.THREE.equals(month.getDay(i).getRate()))
						{

							data[2] += hour;
						}
					}
					else
					{
						if (hour >= 4)
						{

							//中班天数
							if (Shift.MIDDLE.equals(month.getDay(i).getShift()))
							{
								data[3] -= 1;
							}
							//夜班天数
							if (Shift.NIGHT.equals(month.getDay(i).getShift()))
							{
								data[4] -= 1;
							}
						}
					}

					//中班天数
					if (Shift.MIDDLE.equals(month.getDay(i).getShift()))
					{
						data[3] += 1;
					}
					//夜班天数
					if (Shift.NIGHT.equals(month.getDay(i).getShift()))
					{
						data[4] += 1;
					}
					//调休
					if (Fake.TAKEOFF.equals(month.getDay(i).getFake()))
					{
						data[5] += hour;
					}
					//事假
					if (Fake.LEAVE.equals(month.getDay(i).getFake()))
					{
						data[6] += hour;
					}
					//病假
					if (Fake.SICK.equals(month.getDay(i).getFake()))
					{
						data[7] += hour;
					}
					//年假
					if (Fake.SICK.equals(month.getDay(i).getFake()))
					{
						data[8] += hour;
					}
				}
			}
			//绩效
			data[9] = Config.getSettings().getPerformance();
			//岗位补贴
			data[10] = Config.getSettings().getPostSubsidy();
			//交通补贴
			data[11] = Config.getSettings().getTransportationSubsidy();
			//高温补贴
			data[12] = Config.getSettings().getTemperatureSubsidy();
			//社会保险
			data[13] = Config.getSettings().getSocialInsurance();
			//公积金
			data[14] = Config.getSettings().getHousingFund();
			//其他补贴
			data[15] = Config.getSettings().getOtherSubsidy();
			//其他扣款
			data[16] = Config.getSettings().getOtherDeductions();
			//基本工资
			float base = Config.getSettings().getBasePay();
			//平时加班(H)
			data[17] = F(base / 21.75 / 8 * 1.5 , 1);
			//平时加班费
			data[18] = F((data[17] * data[0]), 1);
			//周末加班(H)
			data[19] = F(base / 21.75 / 8 * 2 , 1);
			//周末加班费
			data[20] = F(data[19] * data[1], 1);
			//节假日加班(H)
			data[21] = F(base / 21.75 / 8 * 3 , 1);
			//节假日加班费
			data[22] = F(data[21] * data[2], 1);

			float middle = Config.getSettings().getMiddleShiftSubsidy();
			float night = Config.getSettings().getNightShiftSubsidy();
			//应发工资
			data[23] = F((base + data[3] * middle + data[4] * night + data[18] + data[20] + data[22] + data[9] + data[10] + data[12] + data[15]), 1);
			//实发工资
			data[24] = F((data[23] - (float)(base / 21.75 / 8 * 1.5 * data[5]) - (float)(base / 21.75 / 8 * data[6]) - (float)(base / 21.75 / 8 * 0.3 * data[7]) - data[13] - data[14] - data[16]), 1);
		}
	}

	//设置保留位数
	public static float F(double num, int n)
	{
		BigDecimal bg = new BigDecimal(num);
		double num1 = bg.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
		return((float)num1);
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
		textView2.setText(PageOne. data[position] + "");

		return view;
	}
}

