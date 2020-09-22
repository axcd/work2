package com.mao.work.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mao.work.*;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Context;
import com.mao.work.config.*;
import com.mao.work.bean.*;
import com.mao.work.enum.*;
import java.math.*;
import android.widget.*;
import android.app.*;
import android.content.*;
import android.text.*;
import com.mao.work.util.*;

/**
 * Created by mao on 2020/9/28 0028.
 */
public class PageThree
{

	private View view;
	private static float[] data = new float[15];

    public PageThree()
	{
		//空构造函数
    }

    public View onCreateView(LayoutInflater inflater)
	{
		view = inflater.inflate(R.layout.page_three, null);
		updateView();
        return view;
    }


	public void updateView()
	{
		//数组置零,获取数据
		String[] companies = new String[] {
			"周期开始(日期)", "基本工资(元)", "本月绩效(元)", "中班补贴(元/天)", "夜班补贴(元/天)" ,
			"岗位补贴(元)", "高温补贴(元)","交通补贴(元)", "社会保险(元)", "公积金(元)",
			"其他补贴(元)", "其他扣款(元)", "专项扣除(元)", "白班平时加班(时/天)", "夜班平时加班(时/天)" };
		ListAdapter adapter = new MyAdapter(view.getContext(), companies);
		getData();

		//添加适配器
		ListView listView = (ListView) view.findViewById(R.id.pagethreeListView);
		listView.setAdapter(adapter);
	}

	public void getData()
	{
		data[0] = Config.getSettings().getStartDay();
		data[1] = Config.getSettings().getBasePay();
		data[2] = Config.getSettings().getPerformance();
		data[3] = Config.getSettings().getMiddleShiftSubsidy();
		data[4] = Config.getSettings().getNightShiftSubsidy();
		data[5] = Config.getSettings().getPostSubsidy();
		data[6] = Config.getSettings().getTemperatureSubsidy();
		data[7] = Config.getSettings().getTransportationSubsidy();
		data[8] = Config.getSettings().getSocialInsurance();
		data[9] = Config.getSettings().getHousingFund();
		data[10] = Config.getSettings().getOtherSubsidy();
		data[11] = Config.getSettings().getOtherDeductions();
		data[12] = Config.getSettings().getSpecialDeduction();
		data[13] = Config.getSettings().getDayHour();
		data[14] = Config.getSettings().getNightHour();
	}

	public void saveSettings()
	{
		Config.getSettings().setStartDay((int)data[0]);
		Config.getSettings().setBasePay(data[1]);
		Config.getSettings().setPerformance(data[2]);
		Config.getSettings().setMiddleShiftSubsidy(data[3]);
		Config.getSettings().setNightShiftSubsidy(data[4]);
		Config.getSettings().setPostSubsidy(data[5]);
		Config.getSettings().setTemperatureSubsidy(data[6]);
		Config.getSettings().setTransportationSubsidy(data[7]);
		Config.getSettings().setSocialInsurance(data[8]);
		Config.getSettings().setHousingFund(data[9]);
		Config.getSettings().setOtherSubsidy(data[10]);
		Config.getSettings().setOtherDeductions(data[11]);
		Config.getSettings().setSpecialDeduction(data[12]);
		Config.getSettings().setDayHour(data[13]);
		Config.getSettings().setNightHour(data[14]);

		Config.save();
	}

	class MyAdapter extends ArrayAdapter<String>
	{
		public MyAdapter(Context context, String[] values) 
		{
			super(context, R.layout.page_three_entry, values);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			LayoutInflater inflater = LayoutInflater.from(getContext());
			View view = inflater.inflate(R.layout.page_three_entry, parent, false);

			final String text = getItem(position);

			TextView textView1 = (TextView) view.findViewById(R.id.entryTextView1);
			textView1.setText(text);

			final EditText et = (EditText) view.findViewById(R.id.entryEditText);
			
			if (position == 0)
				et.setText((int)data[position] + "");
			else
				et.setText(data[position] + "");

			et.setOnClickListener(new View.OnClickListener(){
					public void onClick(View view)
					{
						AlertDialog aldg;
						AlertDialog.Builder adBd=new AlertDialog.Builder(getContext());

						final LinearLayout dialogv = (LinearLayout)LayoutInflater.from(getContext()).inflate(R.layout.page_three_dialog, null);
						adBd.setTitle(text);
						adBd.setView(dialogv);

						adBd.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which)
								{
									EditText det = (EditText)(dialogv.getChildAt(0));
									if (!"".equals(det.getText().toString()))
									{
										float f = Float.parseFloat(det.getText().toString());
										if (position == 0)
										{
											if (MathUtil.isOK(f, 1, 1, 31))
												data[position] = MathUtil.F(f, 1);
											else
												Toast.makeText(et.getContext(), "填写正确日期", Toast.LENGTH_LONG).show();
										}
										else if (position == 13 || position == 14)
										{
											if (MathUtil.isOK(f, 0, 0.5f, 33))
												data[position] = MathUtil.F(f, 1);
											else
												Toast.makeText(et.getContext(), "填写正确时数", Toast.LENGTH_LONG).show();
										}
										else
										{
											data[position] = MathUtil.F(f, 1);
											et.setText(data[position] + "");
										}
									}
									saveSettings();
								}
							});
						adBd.setNegativeButton("取消", null);
						aldg = adBd.create();
						aldg.show();
					}
				});

			return view;
		}
	}
}

