package com.baby.tech.activity;

import net.youmi.android.AdManager;
import net.youmi.android.dev.AppUpdateInfo;
import net.youmi.android.dev.CheckAppUpdateCallBack;
import android.app.Activity;
import android.os.Bundle;

import com.baby.tech.R;

public class UpdateActivity extends Activity implements CheckAppUpdateCallBack {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		AdManager.getInstance(this).asyncCheckAppUpdate(this);
	}

	@Override
	public void onCheckAppUpdateFinish(AppUpdateInfo updateInfo) {
		if (updateInfo == null) {
			// 当前已经是最新版本
		} else {
			// 有更新信息
		}

	}

}
