package com.mao.work2.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

public class PermissionUtil
{

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
		Manifest.permission.READ_EXTERNAL_STORAGE,
		Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //android6.0之后要动态获取权限
    public static void requestPermission(Activity activity)
	{
		if (android.os.Build.VERSION.SDK_INT >= 23)
		{
			int permission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
			//检测是否有写的权限
			if (permission != PackageManager.PERMISSION_GRANTED)
			{
				// 没有写的权限，去申请写的权限，会弹出对话框
				activity.requestPermissions(
					PERMISSIONS_STORAGE,
					REQUEST_EXTERNAL_STORAGE
				);
			}
		}
	}
}
