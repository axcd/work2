package com.mao.work.layout;

import android.widget.*;
import android.content.*;
import android.util.*;
import android.view.*;

public class MyRadioGroup extends RadioGroup
{

	public MyRadioGroup(Context context)
	{
		super(context);
	}

	public MyRadioGroup(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		//获取最大宽度
		int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
		int childCount = getChildCount();
		int n = 6, h = 75, m = 10;
		int row = (int)Math.ceil(childCount/n)+1;
		int maxHeight = row*h+m;
		h -= m;
		
		int w = (maxWidth-m)/n-m;
		for(int i=0;i<childCount;i++)
		{
			final View child = getChildAt(i);
			int wm = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
			int hm = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
			child.measure(wm,hm);
		}
		
		// 设置容器所需的宽度和高度
		setMeasuredDimension(maxWidth, maxHeight);
	}


	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		final int childCount = getChildCount();
		int maxWidth = r - l;
		int row = 0;
		int h = 75, n = 6, m = 10 ;
		for (int i = 0; i < childCount; i++)
		{
			final View child = this.getChildAt(i);
			if (child.getVisibility() != View.GONE)
			{
				if (i%6 == 0)
				{
					if (i != 0)
						row++;
				}
				child.layout((i%n)*(maxWidth-m)/n+m, row*h+m, (i%n+1)*(maxWidth-m)/n, (row+1)*h);
			}
		}
	}
}
