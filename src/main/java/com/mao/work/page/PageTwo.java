package com.mao.work.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mao.work.*;

import android.app.*;
import android.os.*;
import android.widget.*;
import java.util.*;
import android.view.*;
import android.content.Context;
import com.mao.work.view.*;
import java.text.*;
import android.graphics.*;
import com.mao.work.config.*;
import android.content.*;
import com.mao.work.bean.*;
import android.content.pm.*;
import android.support.v4.app.*;
import com.mao.work.activity.*;
import com.mao.work.util.*;

public class PageTwo
{

	private static GridView gv;
	private static View view;
	private static TextView mtv;

    public PageTwo()
	{
    }

    public View onCreateView(LayoutInflater inflater)
	{
        view = inflater.inflate(R.layout.page_two, null);

		TextView ptv = (TextView)view.findViewById(R.id.premonth);
		TextView ntv = (TextView)view.findViewById(R.id.nextmonth);
		gv = (GridView)view.findViewById(R.id.mainGridView);

		//获取View
		getCalendarView();

		ptv.setOnClickListener(new View.OnClickListener(){
				public void onClick(View view)
				{
					Config.getCalendar().add(Calendar.MONTH, -1);
					updateView();
				}
			});

		ntv.setOnClickListener(new View.OnClickListener(){
				public void onClick(View view)
				{
					Config.getCalendar().add(Calendar.MONTH, 1);
					updateView();
				}
			});

		return view;
    }

	//更新日历和统计
	public static void updateView()
	{
		//添加canlendar配置
		Config.setConfig();
		getCalendarView();
		PageOne.updateView();
	}

	public static void getCalendarView()
	{

		//设置显示年月		
		Calendar calendar = (Calendar)Config.getCalendar().clone();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		TextView tv = (TextView)view.findViewById(R.id.yyyyMM);
		tv.setText(sdf.format(calendar.getTime()));

		//设置开始日期
		if (Config.getSettings().getStartDay() != 1)
		{
			calendar.add(Calendar.MONTH, -1);
		}
		calendar.set(Calendar.DATE, Config.getSettings().getStartDay());

		//获取List<Date>
		List<Date> dates = new ArrayList<Date>();
		int w = calendar.get(Calendar.DAY_OF_WEEK);
		int n = calendar.getActualMaximum(Calendar.DATE);
		int m = (int)Math.ceil(1.0 * (n + w - 1) / 7) * 7;
		calendar.add(Calendar.DATE, 1 - w);	
		while (dates.size() < m)
		{ 
			dates.add(calendar.getTime());
			calendar.add(Calendar.DATE, 1);
		}

		//设置适配器
        ListAdapter adapter = new DayAdapter(view.getContext(), dates);
		gv.setAdapter(adapter);
	}

}

class DayAdapter extends ArrayAdapter<Date>
{
	public DayAdapter(Context context, List<Date> values) 
	{
		super(context, R.layout.page_two_day_view, values);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.page_two_day_view, parent, false);

		final Date date = getItem(position);

		//设置View的文本
		final DayView textView = (DayView) view.findViewById(R.id.entryTextView1);
		textView.setText(date.getDate() + "");

		//在范围之内日期设置
		if (date.after(Config.getStartDate()) && date.before(Config.getEndDate()))
		{
			textView.setBackgroundColor(Color.parseColor("#FFFDDFEE"));

			//是否绘制
			textView.setDraw(true);

			//标记今天
			if (date.equals(Config.getToday()))
			{
				textView.setToday(true);
			}

			//标记选中
			if (date.equals(Config.getSelectedDate()))
			{
				textView.setSelected(true);
				Config.setSelectedView(textView);
			}

			//绑定数组数据
			SimpleDateFormat sdfm = new SimpleDateFormat("yyyy/MM");
			SimpleDateFormat sdfd = new SimpleDateFormat("dd");
			int i = Integer.parseInt(sdfd.format(date));

			if (Config.getPreMonth().getIndex().equals(sdfm.format(date)))
			{
				if (null != Config.getPreMonth().getDay(i))
				{
					textView.setDay((Config.getPreMonth().getDay(i)));
				}
			}

			if (Config.getNextMonth().getIndex().equals(sdfm.format(date)))
			{
				if (null != Config.getNextMonth().getDay(i))
				{
					textView.setDay((Config.getNextMonth().getDay(i)));
				}
			}

			//设置选中单击事件
			textView.setOnClickListener(new View.OnClickListener(){
					public void onClick(View view)
					{
						if (null != Config.getSelectedView())
						{
							Config.getSelectedView().setSelected(false);
						}

						Config.setSelectedDate(date);
						Config.setSelectedView(textView);
						textView.setSelected(true);

						//设置周末
						if (position % 7 == 0 || position % 7 == 6)
						{
							Config.setWeekend(true);
						}
						else
						{
							Config.setWeekend(false);
						}

						//这儿写长按逻辑
						Intent intent = new Intent();
						intent.setClass(getContext(), UpdateActivity.class);
						getContext().startActivity(intent);
					}
				});

		}
		else
		{
			//不在范围之内的View，取消父类点击效果
			textView.setOnClickListener(new View.OnClickListener(){ public void onClick(View view)
					{} });
		}

		return view;
	}
}
