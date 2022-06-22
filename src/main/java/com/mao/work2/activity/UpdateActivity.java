package com.mao.work2.activity;

import android.app.*;
import android.os.*;
import com.mao.work2.*;
import android.view.*;
import android.view.WindowManager.*;
import java.text.*;
import com.mao.work2.config.*;
import com.mao.work2.bean.*;
import com.mao.work2.io.*;
import com.mao.work2.enum.*;
import android.widget.*;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton.*;
import android.graphics.drawable.*;
import com.mao.work2.page.*;
import com.mao.work2.layout.*;
import java.math.*;
import com.mao.work2.settings.*;

public class UpdateActivity extends AppCompatActivity
{
	private RadioGroup shiftRadioGroup;
	private RadioGroup rateRadioGroup;
	private RadioGroup fakeRadioGroup;
	private RadioGroup hourRadioGroup;
	private TextView dateText;
	private ScrollView hourScrollView;
	
	private static Shift shift = Shift.DAY;
	private static Rate rate = Rate.ONE_AND_HALF;
	private static Fake fake = Fake.NORMAL;
	private static Hour hour = Hour.THREE;
	
	//保留1.5倍时数
	private static Shift shift0 = Shift.DAY;
	private static Hour hour0 = Hour.THREE;
	
	private int d;
	private Month month;
	private String date;
	private int i;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_two_update);

		//从下面插入效果
		Window window = getWindow();	
		window.setGravity(Gravity.BOTTOM);	
		window.setWindowAnimations(R.style.MyDialogAnimation);
		
		shiftRadioGroup = (RadioGroup)findViewById(R.id.shiftRadioGroup);
		rateRadioGroup = (RadioGroup)findViewById(R.id.rateRadioGroup);
		fakeRadioGroup = (RadioGroup)findViewById(R.id.fakeRadioGroup);
		hourRadioGroup = (RadioGroup)findViewById(R.id.hourRadioGroup);
		dateText = (TextView)findViewById(R.id.txt_topbar);
		hourScrollView = (ScrollView)findViewById(R.id.hourScrollView);

		//获取选中的月份
		SimpleDateFormat msdf = new SimpleDateFormat("yyyy/MM/dd");
		String date = msdf.format(Config.getSelectedDate());
		d =  Integer.parseInt(date.substring(8));
		month = new Month(date.substring(0, 7));

		//设置显示日期
		dateText.setText(date);
		//增加监听
		addListener();
		//回显
		display();

	}
	
	public void addListener()
	{
		//增加监听
		shiftRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
				public void onCheckedChanged(RadioGroup p1, int p2)
				{
					RadioButton rb = (RadioButton) findViewById(p2);
					String str = rb.getText().toString();
					shift = Shift.getByString(str);
				}
			});

		rateRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
				public void onCheckedChanged(RadioGroup p1, int p2)
				{
					RadioButton rb = (RadioButton) findViewById(p2);
					String str = rb.getText().toString();
					rate = Rate.getByString(str);
				}
			});

		fakeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
				public void onCheckedChanged(RadioGroup p1, int p2)
				{
					RadioButton rb = (RadioButton) findViewById(p2);
					String str = rb.getText().toString();
					fake = Fake.getByString(str);
				}
			});

		hourRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
				public void onCheckedChanged(RadioGroup p1, int p2)
				{
					RadioButton rb = (RadioButton) findViewById(p2);
					String str = rb.getText().toString();
					hour = Hour.getByString(str);
				}
			});
		//display();
		}
		
	//回显加班信息
	public void display()
	{
		if(Config.isWeekend())
		{
			rate = Rate.TWO;
			int h = Hour.indexOf(hour0);
			h += 16;
			h = h > 48 ?48: h;
			hour = Hour.getByIndex(h);
		}
		
		Day day = month.getDay(d);
		if (null != day)
		{
			shift = day.getShift();
			rate = day.getRate();
			fake = day.getFake();
			hour = day.getHour();
		}	

		int n = Shift.indexOf(shift);
		((RadioButton)shiftRadioGroup.getChildAt(n)).setChecked(true);
		
		n = Rate.indexOf(rate);
		((RadioButton)rateRadioGroup.getChildAt(n)).setChecked(true);
		
		n = fake.indexOf(fake);
		((RadioButton)fakeRadioGroup.getChildAt(n)).setChecked(true);
	
		n = Hour.indexOf(hour);
		((RadioButton)hourRadioGroup.getChildAt(n)).setChecked(true);
		
		i = n;
		setScroll(hourScrollView);
	}

	public void setScroll(final ScrollView hourScrollView)
	{

		hourScrollView.post(new Runnable(){
				public void run()
				{
					int x = 0;
					if (i < 36)
						x = i/6 * MyRadioGroup.y + 10;
					else
						x = 6 * MyRadioGroup.y + 10;
					hourScrollView.scrollTo(0, x);
				}
			});
	}

	//设置保留位数
	public static float F(double num, int n)
	{
		BigDecimal bg = new BigDecimal(num);
		double num1 = bg.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
		return((float)num1);
	}

	public void onDelete(View view)
	{
		//View和记录置空
		Config.getSelectedView().setDay(null);
		month.setDay(d, null);

		finish();
	}

	public void onInsert(View view)
	{
		//添加加班
		if(shift.equals(Shift.REST))
		{
			hour = Hour.ZERO;
			fake = Fake.NORMAL;
		}
		if (!shift.equals(Shift.REST))
		{
			shift0 = shift;
			if (fake.equals(Fake.NORMAL) && rate.equals(Rate.ONE_AND_HALF))
			{
				//保留1.5倍
				hour0 = hour;
			}
		} 

		Day day = new Day(date, shift, fake, rate, hour);
		Config.getSelectedView().setDay(day);
		month.setDay(d, day);	
		
		finish();
	}

	@Override
	public void finish()
	{
		super.finish();
		
		//保存
		shift = shift0;
		rate = Rate.ONE_AND_HALF;
		fake = Fake.NORMAL;
		hour = hour0;

		//保存到文件
		Config.getSettings().save();
		month.saveDays();
		PageOne.updateView();
		
		overridePendingTransition(R.anim.dialog_exit, 0); 
		//Config.getSelectedView().setClickable(true);
	}

}
