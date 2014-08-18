package com.baby.tech.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.GridView;

import com.baby.tech.R;
import com.baby.tech.activity.MainActivity;
import com.baby.tech.base.MyApplication;
import com.baby.tech.db.Constant;

public class ChildStoryFragment extends BaseFragment  {
    
   GridView audioGridMenu;
   
   Context mContext ;
   MyApplication mCommonApplication;
   
   private WebView webView;
   
	public static ChildStoryFragment newInstance() {
		ChildStoryFragment collectFragment = new ChildStoryFragment();

		return collectFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	    mContext= ((MainActivity) getActivity()).getContext();
	    mCommonApplication = (MyApplication) getActivity().getApplication();
    View view = inflater.inflate(R.layout.activity_childstory, container, false);
    initViews(view);
    openBrowser();

		return view;
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
	
 
     

 // 利用webView的loadUrl方法
 public void openBrowser() {
     // webView.loadUrl("http://192.168.1.104/map/weather.html");
     // webView.loadUrl("http://www.baidu.com");
     webView.loadUrl(Constant.HTTP_SERVER_IP + "onestudy/edu/gushi.htm");

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
	
 private void initViews(View view) {
     
     webView = (WebView) view.findViewById(R.id.Weather_webView);
     webView.getSettings().setJavaScriptEnabled(true);
     webView.setScrollBarStyle(0);
     webView.setWebViewClient(new webViewClient());
     WebSettings webSettings = webView.getSettings();
     webSettings.setAllowFileAccess(true);
     webSettings.setBuiltInZoomControls(true);
     
    }
 
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	 
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
	 if (webView.canGoBack()) {
	     webView.goBack(); // goBack()表示返回webView的上一页面
  }
	}

	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

    @Override
    public String getFragmentName() {
        return null;
        	
    }

 


}
