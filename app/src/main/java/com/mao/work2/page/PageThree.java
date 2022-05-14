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
import android.widget.*;
import android.app.*;
import android.content.*;
import android.text.*;
import com.mao.work2.util.*;

/**
 * Created by mao on 2020/9/28 0028.
 */
public class PageThree
{

	private static View view;

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


	public static void updateView()
	{
		ListAdapter adapter = new MyAdapter3(view.getContext(), Config.getSettings().getList() );

		//添加适配器
		ListView listView = (ListView) view.findViewById(R.id.pagethreeListView);
		listView.setAdapter(adapter);
	}

	public static void saveSettings()
	{
		//保存修改
		Config.getSettings().update();
		PageOne.updateView();
		PageTwo.updateView();
	}
}
	class MyAdapter3 extends ArrayAdapter<String>
	{
		public MyAdapter3(Context context, String[] values) 
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
				et.setText((int)Config.getSettings().get(text) + "");
			else
				et.setText(Config.getSettings().get(text) + "");
			
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
											{
												Config.getSettings().set(text ,MathUtil.F(f, 1));
												Config.setStartDay((int)MathUtil.F(f, 1));
											}
											else
												Toast.makeText(et.getContext(), "填写正确日期", Toast.LENGTH_LONG).show();
										}
										else
										{
											Config.getSettings().set( text, MathUtil.F(f, 2));
											et.setText(Config.getSettings().get(text) + "");
										}
									}
									PageThree.saveSettings();
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

