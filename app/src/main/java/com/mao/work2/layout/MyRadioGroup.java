package com.mao.work2.layout;

import android.widget.*;
import android.content.*;
import android.util.*;
import android.view.*;
import com.mao.work2.*;
import android.content.res.*;
import com.mao.work2.config.*;

public class MyRadioGroup extends RadioGroup
{
	private int m;
	private int n;
	private int w;
	private int h;
	private int childCount = 49;
	
	public MyRadioGroup(Context context)
	{
		super(context);
	}

	public MyRadioGroup(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		//获取xml配置
		TypedArray t = context.obtainStyledAttributes(attrs,R.styleable.MyRadioGroup);
		m = t.getInteger(R.styleable.MyRadioGroup_m,15);
		n = t.getInteger(R.styleable.MyRadioGroup_n,6);
		
		for(int i=0;i<childCount;i++)
		{
			RadioButton rb = (RadioButton)LayoutInflater.from(context).inflate(R.layout.page_two_update_radio,null,false);
			if(i%2!=0) rb.setText(i*0.5+"h");else rb.setText(i/2+"h");
			this.addView(rb);
		}
		

		childCount = getChildCount();
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		//获取最大宽度和
		int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
		w = (maxWidth-m)/n-m;
		h = w*3/5;
		int maxHeight = (h+m)*(int)Math.ceil(childCount*1.0/n)+m;
		
		com.mao.work2.config.Config.setScroll(h+m);
		
		for(int i=0;i<childCount;i++)
		{
			final View child = getChildAt(i);
			int wm = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
			int hm = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
			//设置子类所需宽度和高度
			child.measure(wm,hm);
		}
		
		// 设置容器所需的宽度和高度
		setMeasuredDimension(maxWidth, maxHeight);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		
		for (int i = 0; i < childCount; i++)
		{
			View child = this.getChildAt(i);
			if (child.getVisibility() != View.GONE)
			{
				child.layout((i%n)*(w+m)+m, (i/n)*(h+m)+m, (i%n+1)*(w+m), (i/n+1)*(h+m));	
			}
		}
	}
}
