package com.mao.work.layout;

import android.widget.*;
import android.content.*;
import android.util.*;
import android.view.*;
import com.mao.work.*;

public class MyRadioGroup extends RadioGroup
{
	public static int x = 70;
	private int m = 10;
	private int n = 6;
	private int w;
	private int h;
	
	public MyRadioGroup(Context context)
	{
		super(context);
	}

	public MyRadioGroup(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		for(int i=0;i<49;i++)
		{
			RadioButton rb = (RadioButton)LayoutInflater.from(context).inflate(R.layout.page_two_update_radio,null,false);
			
//			if(i%2==1)
				rb.setText(i*0.5+"h");
//			else 
//				rb.setText((i+1)/2+"h");
				
			this.addView(rb);
		}
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		//获取最大宽度
		int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
		int childCount = getChildCount();
		int row = (int)Math.floor(childCount/n);
		
		w = (maxWidth-m)/n-m;
		h = (w+m)*2/3-m;
		int maxHeight = (h+m)*(row+1)+m;
		
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
		
		int childCount = getChildCount();
		int maxWidth = r - l;
		int row = 0;
		
		w = (maxWidth-m)/n;
		h = w*2/3;
		x = h;
		
		for (int i = 0; i < childCount; i++)
		{
			View child = this.getChildAt(i);
			if (child.getVisibility() != View.GONE)
			{
				if (i%6 == 0)
				{
					if (i != 0)
						row++;
				}
				child.layout((i%n)*w+m, row*h+m, (i%n+1)*w, (row+1)*h);
			}
		}
	}
}
