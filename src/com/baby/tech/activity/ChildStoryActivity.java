package com.baby.tech.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.baby.tech.R;
import com.baby.tech.activity.base.BaseActivity;
import com.baby.tech.db.Constant;

public class ChildStoryActivity extends BaseActivity {

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_childstory);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		webView = (WebView) findViewById(R.id.Weather_webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setScrollBarStyle(0);
		webView.setWebViewClient(new webViewClient());
		WebSettings webSettings = webView.getSettings();
		webSettings.setAllowFileAccess(true);
		webSettings.setBuiltInZoomControls(true);
		openBrowser();
	}

	public boolean onKeyDown(int keyCoder, KeyEvent event) {
		if (webView.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
			webView.goBack(); // goBack()表示返回webView的上一页面

			return true;
		}
		finish();
		return false;
	}

	// 利用webView的loadUrl方法
	public void openBrowser() {
		// webView.loadUrl("http://192.168.1.104/map/weather.html");
		// webView.loadUrl("http://www.baidu.com");
		webView.loadUrl(Constant.HTTP_SERVER_IP+"onestudy/edu/gushi.htm");

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					// MainActivity.this.setTitle("加载完成");
				} else {
					// MainActivity.this.setTitle("加载中.......");

				}
			}
		});

	}

	class webViewClient extends WebViewClient {
		// 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			// 如果不需要其他对点击链接事件的处理返回true，否则返回false
			return true;
		}
	}

	// @Override
	// public void onClick(View v) {
	// int iResid = v.getId();
	// switch (iResid) {
	// case R.id.id_layout_title_back:
	// finish();
	// break;
	// default:
	// break;
	// }
	//
	// }
	//
	// @Override
	// protected String setTitle() {
	// // TODO Auto-generated method stub
	// return "天气预报";
	//
	// }
	//
	// @Override
	// protected String setTitleBack() {
	// // TODO Auto-generated method stub
	// return "返回";
	// }

}
