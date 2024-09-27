package com.mao.work2.view;

import android.view.*;
import android.content.*;
import android.util.*;
import android.content.res.*;
import android.graphics.*;
import android.widget.*;
import com.mao.work2.bean.*;
import android.text.*;
import com.mao.work2.config.*;
import com.mao.work2.enum.*;

public class DayView extends TextView
{
	private Day day;
	private boolean draw;
	private boolean today;
	private boolean selected;

	public DayView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initView();
	}

	public void setDay(Day day)
	{
		this.day = day;
		invalidate();
	}

	public Day getDay()
	{
		return day;
	}

	public void setDraw(boolean draw)
	{
		this.draw = draw;
	}

	public boolean isDraw()
	{
		return draw;
	}

	public void setToday(boolean today)
	{
		this.today = today;
		invalidate();
	}

	public boolean isToday()
	{
		return today;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
		invalidate();
	}

	public boolean isSelected()
	{
		return selected;
	}

	//初始化view
	public void initView()
	{

	}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
        super.onLayout(changed, left, top, right, bottom);
    }

	@Override
    protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		int R = getHeight()<getWidth() ? getHeight():getWidth();

		if (isDraw())
		{

			//绘制今天
			if (isToday())
			{
				setTextColor(Color.RED);
				paint.setColor(Color.parseColor("#4000FF00"));
				canvas.drawCircle(getWidth() / 2, getHeight() / 2, R/2-2, paint);
			}

			//绘制选中
			if (isSelected())
			{
				paint.setColor(Color.parseColor("#30000FF0"));
				canvas.drawCircle(getWidth() / 2, getHeight() / 2, R/2-2, paint);
			}

			//绘制加班信息
			if (null != day)
			{
				if (null != day.getShift())
				{
					String shift = day.getShift().getShiftName();
					int shift_color = Color.parseColor("#70F0000F");

					//分别设置绘制早中晚休班背景颜色
					if (day.getShift().equals(Shift.DAY))
					{
						shift_color = Color.parseColor("#70F0000F");
					}
					else if (day.getShift().equals(Shift.MIDDLE))
					{
						shift_color = Color.parseColor("#9000AFFF");
					}
					else if (day.getShift().equals(Shift.NIGHT))
					{
						shift_color = Color.parseColor("#700000F8");
					}
					else
					{
						shift_color = Color.parseColor("#8000000F");
					}

					//设置绘制请假字符
					if (null != day.getFake() && !day.getFake().equals(Fake.NORMAL))
					{
						shift = day.getFake().getFakeName();
					}

					//设置休班背景色
					if (day.getShift().equals(Shift.REST))
					{
						shift = day.getShift().getShiftName();
					}

					//开始绘制加班班别
					paint.setColor(shift_color);
					canvas.drawCircle( R / 6 + 1, R / 6 + 1, R / 6 - 1, paint );
					Rect rect = new Rect(2, 2, R / 3, R / 3-2);//画一个矩形

					paint.setColor(Color.parseColor("#FFFFFFFF"));
					paint.setTextSize(R/4-1);
					paint.setTextAlign(Paint.Align.CENTER);//该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center

					Paint.FontMetrics fontMetrics = paint.getFontMetrics();
					float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
					float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
					int baseLineY = (int) (rect.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式
					canvas.drawText(shift, 0, 1, rect.centerX(), baseLineY, paint);

				}

				if (null != day.getHour())
				{
					String hour = day.getHour().getHourName();
					int hour_color = Color.parseColor("#80000FFF");
					int hour_bkgcolor = Color.parseColor("#20FF0000");

					//分别绘制加班倍数下面背景颜色
					if (day.getRate().equals(Rate.ONE_AND_HALF))
					{
						hour_color = Color.parseColor("#900000FF");
					}
					else if (day.getRate().equals(Rate.TWO))
					{
						hour_color = Color.parseColor("#90FF0000");
					}
					else if (day.getRate().equals(Rate.THREE))
					{
						hour_color = Color.parseColor("#F0FF00FF");
					}

					//如果是请假，绘制字体和背景颜色
					if (null != day.getFake() && !day.getFake().equals(Fake.NORMAL))
					{
						hour_color = Color.parseColor("#A0000000");
						hour_bkgcolor = Color.parseColor("#30099090");
					}

					//如果是休班，绘制背景颜色
					if (Shift.REST.equals(day.getShift()))
					{
						hour = "";
						hour_bkgcolor = Color.parseColor("#21111111");	
					}
					
					//如果为零不显示小时
					if(day.getHour().equals(Hour.ZERO))
					{
						hour = "";
					}

					//开始绘制加班时数和背景
					Rect rect = new Rect(0, getHeight() - 30, getWidth(), getHeight());//画一个矩形
					paint.setColor(hour_bkgcolor);
					canvas.drawRect(rect, paint);

					paint.setColor(hour_color);
					paint.setTextSize(30);
					paint.setTextAlign(Paint.Align.CENTER);//该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center

					Paint.FontMetrics fontMetrics = paint.getFontMetrics();
					float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
					float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
					int baseLineY = (int) (rect.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式
					canvas.drawText(hour, rect.centerX(), baseLineY, paint);

				}
			}
		}
	}
}
