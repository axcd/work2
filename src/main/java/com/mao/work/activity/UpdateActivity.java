package com.mao.work.activity;

import android.app.*;
import android.os.*;
import com.mao.work.*;
import android.view.*;
import android.view.WindowManager.*;
import java.text.*;
import com.mao.work.config.*;
import com.mao.work.bean.*;
import com.mao.work.io.*;
import com.mao.work.enum.*;
import android.widget.*;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton.*;
import android.graphics.drawable.*;
import com.mao.work.page.*;
import com.mao.work.layout.*;
import java.math.*;

public class UpdateActivity extends AppCompatActivity
{
	private Shift shift = Shift.DAY;
	private Rate rate = Rate.ONE_AND_HALF;
	private Fake fake = Fake.NORMAL;
	private float dh = (Config.getSettings().getDayHour());
	private float nh = Config.getSettings().getNightHour();
	private Hour hour = Hour.get(dh+"h");
	private int hourIndex = Hour.getIndex(dh+"h");

	private ObjectIO<Month> io = new ObjectIO<Month>();

	private int d;
	private String m;
	private Month month;
	private String date;
	private int y = (hourIndex/6-1)*MyRadioGroup.x+10;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_two_update);MyLog.d(dh+"");

		//从下面插入效果
		Window window = getWindow();	
		window.setGravity(Gravity.BOTTOM);	
		window.setWindowAnimations(R.style.MyDialogAnimation);

		//获取选中的月份
		SimpleDateFormat msdf = new SimpleDateFormat("yyyy/MM/dd");
		date = msdf.format(Config.getSelectedDate());
		d =  Integer.parseInt(date.substring(8));
		m = date.substring(0, 7);
		if (m.equals(Config.getPreMonth().getIndex()))
		{
			month = Config.getPreMonth();
		}
		else
		{
			month = Config.getNextMonth();
		}

		//设置显示日期
		final TextView dateText = (TextView)findViewById(R.id.txt_topbar);
		dateText.setText(date);
		
		final RadioGroup shiftRadioGroup = (RadioGroup)findViewById(R.id.shiftRadioGroup);
		final RadioGroup rateRadioGroup = (RadioGroup)findViewById(R.id.rateRadioGroup);
		final RadioGroup fakeRadioGroup = (RadioGroup)findViewById(R.id.fakeRadioGroup);
		final RadioGroup hourRadioGroup = (RadioGroup)findViewById(R.id.hourRadioGroup);
		final ScrollView hourScrollView = (ScrollView)findViewById(R.id.hourScrollView);

		//增加监听
		shiftRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
				public void onCheckedChanged(RadioGroup p1, int p2)
				{
					RadioButton rb = (RadioButton) findViewById(p2);
					String str = rb.getText().toString();
					shift = Shift.get(str);
					
					//选中白班，修改hour
					if(shift.equals(Shift.DAY))
					{
						hour = Hour.get(dh+"h");
						hourIndex = Hour.getIndex(dh+"h");
						((RadioButton)hourRadioGroup.getChildAt(hourIndex)).setChecked(true);
						if(Config.isWeekend())
						{
							hour = Hour.get((dh+8)+"h");
							hourIndex += 16;
							((RadioButton)hourRadioGroup.getChildAt(hourIndex)).setChecked(true);
						}
						setY((hourIndex/6-1)*MyRadioGroup.x+10);
						setScroll(hourScrollView);
					}
					//如果选中夜班，修改hour
					if(shift.equals(Shift.NIGHT))
					{
						hour = Hour.get(nh+"h");
						hourIndex = Hour.getIndex(nh+"h");
						((RadioButton)hourRadioGroup.getChildAt(hourIndex)).setChecked(true);
						if(Config.isWeekend())
						{
							hour = Hour.get((nh+8)+"h");
							hourIndex += 16;
							((RadioButton)hourRadioGroup.getChildAt(hourIndex)).setChecked(true);
						}
						setY((hourIndex/6-1)*MyRadioGroup.x+10);
						setScroll(hourScrollView);
					}
				}
			});

		rateRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
				public void onCheckedChanged(RadioGroup p1, int p2)
				{
					RadioButton rb = (RadioButton) findViewById(p2);
					String str = rb.getText().toString();
					rate = Rate.get(str);
				}
			});

		fakeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
				public void onCheckedChanged(RadioGroup p1, int p2)
				{
					RadioButton rb = (RadioButton) findViewById(p2);
					String str = rb.getText().toString();
					fake = Fake.get(str);
				}
			});

		hourRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
				public void onCheckedChanged(RadioGroup p1, int p2)
				{
					RadioButton rb = (RadioButton) findViewById(p2);
					String str = rb.getText().toString();
					hour = Hour.get(str);
				}
			});

		//设置点开默认选中
		((RadioButton)shiftRadioGroup.getChildAt(0)).setChecked(true);
		((RadioButton)rateRadioGroup.getChildAt(0)).setChecked(true);
		((RadioButton)fakeRadioGroup.getChildAt(0)).setChecked(true);
		((RadioButton)hourRadioGroup.getChildAt(hourIndex)).setChecked(true);

		//周末设置双倍倍数
		if (Config.isWeekend())
		{
			setY((hourIndex/6-1)*MyRadioGroup.x+10);
			((RadioButton)rateRadioGroup.getChildAt(1)).setChecked(true);
			((RadioButton)hourRadioGroup.getChildAt(hourIndex)).setChecked(true);
		}
		//回显加班信息
		if (null != month.getDay(d))
		{
			int n = shiftRadioGroup.getChildCount();
			for (int i=0;i < n;i++)
			{
				RadioButton rb = (RadioButton)shiftRadioGroup.getChildAt(i);
				if (rb.getText().toString().equals(month.getDay(d).getShift().getShiftName()))
				{
					rb.setChecked(true);
					break;
				}
			}

			n = rateRadioGroup.getChildCount();
			for (int i=0;i < n;i++)
			{
				RadioButton rb = (RadioButton)rateRadioGroup.getChildAt(i);
				if (rb.getText().toString().equals(month.getDay(d).getRate().getRateName()))
				{
					rb.setChecked(true);
					break;
				}
			}

			n = fakeRadioGroup.getChildCount();
			for (int i=0;i < n;i++)
			{
				RadioButton rb = (RadioButton)fakeRadioGroup.getChildAt(i);
				if (rb.getText().toString().equals(month.getDay(d).getFake().getFakeName()))
				{
					rb.setChecked(true);
					break;
				}
			}

			n = hourRadioGroup.getChildCount();
			for (int i=0;i < n;i++)
			{
				RadioButton rb = (RadioButton)hourRadioGroup.getChildAt(i);
				if (rb.getText().toString().equals(month.getDay(d).getHour().getHourName()))
				{
					rb.setChecked(true);
					setY((i/6-1)*MyRadioGroup.x+10);
					break;
				}
			}
		}
		setScroll(hourScrollView);
	}
	
	public void setScroll(final ScrollView hourScrollView){
		
		hourScrollView.post(new Runnable(){
				public void run()
				{
					hourScrollView.scrollTo(0, getY());
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
	
	public void setY(int y)
	{
		this.y = y;
	}

	public int getY()
	{
		return y;
	}

	public void onDelete(View view)
	{
		//View和记录置空
		Config.getSelectedView().setDay(null);
		month.setDay(d, null);

		//判断month是否为空
		int length = month.getDays().length;
		for (int i = 0;i < length;i++)
		{
			if (month.getDay(i) != null)
			{
				break;
			}
		
			if(i == length-1)
			{
				month = null;
			}
		}

		//保存
		io.outObject(month, m);
		PageOne.updateView();

		finish();
	}

	public void onInsert(View view)
	{
		//添加加班
		Day day = new Day(date, shift, fake, rate, hour);
		Config.getSelectedView().setDay(day);
		month.setDay(d, day);
		io.outObject(month, m);
		PageOne.updateView();
		finish();
	}

	@Override
	public void finish()
	{
		super.finish();
		overridePendingTransition(R.anim.dialog_exit, 0); 
	}

}