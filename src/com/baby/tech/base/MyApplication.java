package com.baby.tech.base;

import com.palmcity.tts.NaviTTS;

import net.youmi.android.dev.AppUpdateInfo;
import android.app.Application;
import android.content.SharedPreferences;

public class MyApplication extends Application {
	String smscurrenttime;
	public static MyApplication app;
	public SharedPreferences sharedPrefs;// 轻量级的存储类
	// 开始时间（统计软件使用时长时使用）

	public String mbeginTime = "0";

 /** 语音播报 **/
 public NaviTTS tts = null;

	// app update info
	public AppUpdateInfo mAppUpdateInfo = new AppUpdateInfo();
	// user login info
	// terminal info


	
	
	// 判断是否是返回键
	public boolean mIsBack = false;

	public MyApplication() {
	  tts = new NaviTTS(this);
		app = this;
	}

	public static MyApplication instance() {
		return app;
	}

	public long getCurrentTime() {
		// TODO Auto-generated method stub
		long data = System.currentTimeMillis();
		return data;
	}
 



}

	





