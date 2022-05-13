package com.mao.work2.config;

import java.util.*;
import com.mao.work2.settings.*;
import com.mao.work2.io.*;
import android.os.*;
import com.mao.work2.bean.*;
import com.mao.work2.enum.*;
import com.mao.work2.util.*;
import com.mao.work2.*;
import java.text.*;

public class Report
{
	private Calendar calendar;
	private cfg cfg;
	public Map<String,Float> report = new HashMap<String,Float>();
	
	public Report(Calendar calendar)
	{
		this.calendar = calendar;
		this.cfg = new cfg(this.calendar);
		this.setMapDefaultValue(new Settings(cfg.getNMonth().getIndex()));
		this.getReport();
	}

	public void set(String key, Float value)
	{
		report.put(key,value);
	}

	public float get(String key)
	{
		Float value = report.get(key);
		if(null == value)
		{
			value = 0f;
		}
		return value;
	}
	
	public void setMapDefaultValue(Settings settings)
	{
		for(String key : settings.getList())
		{
			set(key, settings.get(key));
		}
	}
	
	public void getReport()
	{
		//把两月组合成一个月
		Month month  = new Month("");
		int n = Config.getStartDay();
		if (n == 1)n = 32;
		for (int i=n ;i <= 31 ;i++)
		{
			month.setDay(i, cfg.getPMonth().getDay(i));
		}
		
		for (int i=1; i < n; i++)
		{
			month.setDay(i, cfg.getNMonth().getDay(i));
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
							this.set(key, this.get(key) + hour);
						}
						//周末加班
						else if (Rate.TWO.equals(month.getDay(i).getRate()))
						{
							String key = "周末加班(时)";
							this.set(key, this.get(key) + hour);
						}
						//节假日加班
						else if (Rate.THREE.equals(month.getDay(i).getRate()))
						{
							String key = "节假日加班(时)";
							this.set(key, this.get(key) + hour);
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
								this.set(key, this.get(key) - 1);
							}
							//夜班天数
							if (Shift.NIGHT.equals(month.getDay(i).getShift()))
							{
								String key = "夜班天数(天)";
								this.set(key, this.get(key) - 1);
							}
						}
					}

					//中班天数
					if (Shift.MIDDLE.equals(month.getDay(i).getShift()))
					{
						String key = "中班天数(天)";
						this.set(key, this.get(key) + 1);
					}
					//夜班天数
					if (Shift.NIGHT.equals(month.getDay(i).getShift()))
					{
						String key = "夜班天数(天)";
						this.set(key, this.get(key) + 1);
					}
					//调休
					if (Fake.TAKEOFF.equals(month.getDay(i).getFake()))
					{
						String key = "调休(时)";
						this.set(key, this.get(key) + hour);
//						rateXhour += rate * hour;
						//调休平时加班
						if (Rate.ONE_AND_HALF.equals(month.getDay(i).getRate()))
						{
							String key1 = "调休1.5(时)";
							this.set(key1, this.get(key1) + hour);
						}
						//调休周末加班
						else if (Rate.TWO.equals(month.getDay(i).getRate()))
						{
							String key2 = "调休2(时)";
							this.set(key2, this.get(key2) + hour);
						}
						//调休节假日加班
						else if (Rate.THREE.equals(month.getDay(i).getRate()))
						{
							String key3 = "调休3(时)";
							this.set(key3, this.get(key3) + hour);
						}
					}
					//事假
					if (Fake.LEAVE.equals(month.getDay(i).getFake()))
					{
						String key = "事假(时)";
						this.set(key, this.get(key) + hour);
					}
					//病假
					if (Fake.SICK.equals(month.getDay(i).getFake()))
					{
						String key = "病假(时)";
						this.set(key, this.get(key) + hour);
					}
					//年假
					if (Fake.PAID.equals(month.getDay(i).getFake()))
					{
						String key = "年假(时)";
						this.set(key, this.get(key) + hour);
					}
				}
			}
		}
		//夜班补贴
		this.set("夜班补贴(元)", MathUtil.F(this.get("夜班天数(天)") * this.get("夜班补贴(元/天)"), 2));
		//本月绩效
//		this.set("本月绩效(元)",MathUtil.F(this.get("本月绩效(元)")*(this.get("本月得分(分)")*0.01), 2));
		//加班费基数
		float base = this.get("基本工资(元)") + this.get("本月绩效(元)");
		base = this.get("基本工资(元)") + this.get("本月绩效(元)") + this.get("岗位补贴(元)") 
		        + this.get("夜班天数(时)") * this.get("夜班补贴(元/天)") + this.get("中班天数(天)") * this.get("中班补贴(元/天)")
		        + this.get("交通补贴(元)")+this.get("高温补贴(元)");
		        //+ this.get("其他补贴(元)");
		
		this.set("加班费基数(元)", MathUtil.F(base, 2));
		//平时加班(H)
		this.set("平时加班(元/时)", MathUtil.F(base / 21.75 / 8 * 1.5 , 2));
		//平时加班费
		this.set("平时加班费(元)",  MathUtil.F((this.get("平时加班(元/时)") * (this.get("平时加班(时)") - this.get("调休1.5(时)"))), 2));
		//周末加班(H)
		this.set("周末加班(元/时)", MathUtil.F(base / 21.75 / 8 * 2, 2));
		//周末加班费
		this.set("周末加班费(元)",  MathUtil.F((this.get("周末加班(元/时)") * (this.get("周末加班(时)") - this.get("调休2(时)"))), 2));
		//节假日加班(H)
		this.set("节假日加班(元/时)", MathUtil.F(base / 21.75 / 8 * 3 , 2));
		//节假日加班费
		this.set("节假日加班费(元)",  MathUtil.F((this.get("节假日加班(元/时)") * (this.get("节假日加班(时)") - this.get("调休3(时)"))), 2));

		//事假费(元)
		this.set("事假费(元)", MathUtil.F(base / 21.75 / 8 * 0.4 * this.get("事假(时)"), 2));
		//病假费(元)
		this.set("病假费(元)", MathUtil.F(base / 21.75 / 8 * this.get("病假(时)") , 2));

		//应发工资
		this.set("本月应发(元)", MathUtil.F(this.get("基本工资(元)") + this.get("本月绩效(元)") + this.get("岗位补贴(元)")
										 + this.get("夜班天数(天)") * this.get("夜班补贴(元/天)") + this.get("中班天数(天)") * this.get("中班补贴(元/天)")
										 + this.get("交通补贴(元)") + this.get("高温补贴(元)") + this.get("其他补贴(元)")
										 + this.get("平时加班费(元)") + this.get("周末加班费(元)") + this.get("节假日加班费(元)") , 2));

		//实发工资
		this.set("本月实发(元)", MathUtil.F(this.get("本月应发(元)") 
		                                 - this.get("事假费(元)") - this.get("病假费(元)")
										 - this.get("养老保险(元)") - this.get("医疗保险(元)") - this.get("失业保险(元)") 
										 - this.get("公积金(元)")
										 - this.get("工会费(元)") - this.get("其他扣款(元)") , 2));
										 
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}
	
}

class cfg
{
	private Calendar calendar;
	private Month pMonth;
	private Month nMonth;

	public cfg(Calendar calendar)
	{
		this.calendar = calendar;

		setPreMonth();
		setNextMonth();
	}

	public void setPMonth(Month pMonth)
	{
		this.pMonth = pMonth;
	}

	public Month getPMonth()
	{
		return pMonth;
	}

	public void setNMonth(Month nMonth)
	{
		this.nMonth = nMonth;
	}

	public Month getNMonth()
	{
		return nMonth;
	}

	public void setPreMonth()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");

		Calendar cal = (Calendar)this.calendar.clone();
		cal.add(Calendar.MONTH,-1);
		String pmonth = sdf.format(cal.getTime());
		this.pMonth = new Month(pmonth);	
	}

	public void setNextMonth()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");

		Calendar cal = (Calendar)this.calendar.clone();
		String nmonth = sdf.format(cal.getTime());
		this.nMonth = new Month(nmonth);
	}

}
