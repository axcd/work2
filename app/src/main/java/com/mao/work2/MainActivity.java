package com.mao.work2;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.mao.work2.page.*;
import com.mao.work2.config.*;
import com.mao.work2.util.*;
import android.support.v4.view.*;
import android.view.*;
import java.io.File;
import android.widget.*;
import android.os.*;
import android.content.*;

/**
 * Created by mao on 2020/09/28 0008.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener
{

    private ViewPager vpager;
    private ImageView img_cursor;
    private TextView tv_one;
    private TextView tv_two;
    private TextView tv_three;

    private ArrayList<View> listViews;
    private int offset = 0;//移动条图片的偏移量
    private int currIndex = 0;//当前页面的编号
    private int bmpWidth;// 移动条图片的长度
    private int one = 0; //移动条滑动一页的距离
    private int two = 0; //滑动条移动两页的距离

    @Override
    public void onCreate(Bundle savedInstanceState)
	{

		//申请读写权限
		PermissionUtil.requestPermission(this);

		//初始化config
		Config.init();
		//设置状态栏显示
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initViews();
    }


    private void initViews()
	{
        vpager = (ViewPager) findViewById(R.id.vpager);
        tv_one = (TextView) findViewById(R.id.tv_one);
        tv_two = (TextView) findViewById(R.id.tv_two);
        tv_three = (TextView) findViewById(R.id.tv_three);
        img_cursor = (ImageView) findViewById(R.id.img_cursor);

        //下划线动画的相关设置：
        bmpWidth = BitmapFactory.decodeResource(getResources(), R.mipmap.line).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW - bmpWidth) / 2;// 计算偏移量
		//移动的距离
        one = screenW / 3;// 移动一页的偏移量,比如1->2,或者2->3
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        img_cursor.setImageMatrix(matrix);// 设置动画初始位置

        //往ViewPager填充View，同时设置点击事件与页面切换事件
        listViews = new ArrayList<View>();
        LayoutInflater mInflater = getLayoutInflater();
		listViews.add(new PageOne().onCreateView(mInflater));
		listViews.add(new PageTwo().onCreateView(mInflater));
		listViews.add(new PageThree().onCreateView(mInflater));

        vpager.setAdapter(new MyPagerAdapter(listViews));
		currIndex = 1;
        vpager.setCurrentItem(1);          //设置ViewPager当前页，从0开始算

        tv_one.setOnClickListener(this);
        tv_two.setOnClickListener(this);
        tv_three.setOnClickListener(this);

		vpager.setOffscreenPageLimit(2);
        vpager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v)
	{
        switch (v.getId())
		{
            case R.id.tv_one:
                vpager.setCurrentItem(0);
                break;
            case R.id.tv_two:
                vpager.setCurrentItem(1);
                break;
            case R.id.tv_three:
                vpager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onPageSelected(int index)
	{
        Animation animation = null;
        switch (index)
		{
            case 0:
                if (currIndex == 1)
				{
                    animation = new TranslateAnimation(0, -one, 0, 0);
                }
				else if (currIndex == 2)
				{
                    animation = new TranslateAnimation(one, -one, 0, 0);
                }
                break;
            case 1:
                if (currIndex == 0)
				{
                    animation = new TranslateAnimation(-one, 0, 0, 0);
                }
				else if (currIndex == 2)
				{
                    animation = new TranslateAnimation(one, 0, 0, 0);
                }
                break;
            case 2:
                if (currIndex == 0)
				{
                    animation = new TranslateAnimation(-one, one, 0, 0);
                }
				else if (currIndex == 1)
				{
                    animation = new TranslateAnimation(0, one, 0, 0);
                }
                break;
        }
        currIndex = index;
        animation.setFillAfter(true);// true表示图片停在动画结束位置
        animation.setDuration(200); //设置动画时间为300毫秒
        img_cursor.startAnimation(animation);//开始动画
    }

    @Override
    public void onPageScrollStateChanged(int i)
	{

    }

    @Override
    public void onPageScrolled(int i, float v, int i1)
	{

    }

	public void num(View view)
	{
		String str = (String)((Button)view).getText();
		EditText et = (EditText)((LinearLayout)view.getParent().getParent()).getChildAt(0);
		String etstr = et.getText().toString();

		Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		char c = str.charAt(0);
		if(null==etstr) etstr = "";
		
		switch(c){
			case '0':
				if(etstr.equals("0"))
				{
					etstr = "0";
				}else if(((etstr.length()>5) && !etstr.contains("." )) || ((etstr.length()>3 && (etstr.charAt(etstr.length()-3)=='.'))))
				{
					// 震动0.5秒
					v.vibrate(50);
				}else{
					etstr += c;
				}
				break;
			case '.':
				if(etstr.equals(""))
				{
					etstr = "0.";
				}else if(!etstr.contains("."))
				{
					etstr +=".";
				}else{
					// 震动0.5秒
					v.vibrate(50);
				}
				break;
			case 'c':
				if(etstr.length()>0)
				{
					etstr = etstr.substring(0, etstr.length() - 1);
				}else{
					// 震动0.5秒
					v.vibrate(50);
				}
				break;
			default:
				if(etstr.equals("0"))
				{
					etstr = ""+c;
				}else if(((etstr.length()>5) && !etstr.contains("." )) || ((etstr.length()>3 && (etstr.charAt(etstr.length()-3)=='.'))))
				{
					// 震动0.5秒
					v.vibrate(50);
				}else{
					etstr += c;
				}
				System.out.println(etstr);
			    break;
		}
		
		if(Config.TEXT.equals("周期开始(日期)")&&etstr.length()>0){
			if(etstr.contains("."))
			{
				etstr = etstr.substring(0, etstr.length() - 1);
				if(etstr.equals("0")) etstr = "";
				// 震动0.5秒
				v.vibrate(50);
			}else if(Integer.parseInt(etstr)>0&&Integer.parseInt(etstr)<32){
				et.setText(etstr);
			}else{
				etstr = etstr.substring(0, etstr.length() - 1);
				// 震动0.5秒
				v.vibrate(50);
			}
		}
		et.setText(etstr);
	}
}

class MyPagerAdapter extends PagerAdapter
{
    private ArrayList<View> viewLists;

    public MyPagerAdapter()
	{
    }

    public MyPagerAdapter(ArrayList<View> viewLists)
	{
        super();
        this.viewLists = viewLists;
    }

    @Override
    public int getCount()
	{
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
	{
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
	{
        container.addView(viewLists.get(position));
        return viewLists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
	{
        container.removeView(viewLists.get(position));
    }
}
