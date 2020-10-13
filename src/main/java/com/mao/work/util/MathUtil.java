package com.mao.work.util;

import java.math.*;

public class MathUtil
{
	//检测一个数f是否在范围之内，x是初始值，v是步长，n是步数
	public static boolean isOK(float f, float x, float v, int n)
	{
		boolean b = false;
		for (int i=0;i < n;i++)
		{
			if (f == x)
				b = true;
			x += v;
		}
		return b;
	}

	//设置保留位数
	public static float F(double num, int n)
	{
		BigDecimal bg = new BigDecimal(num);
		double num1 = bg.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
		return((float)num1);
	}
	
}
