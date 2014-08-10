package com.baby.tech.fragment;

import net.youmi.android.diy.DiyManager;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baby.tech.R;
import com.baby.tech.activity.MainActivity;
import com.baby.tech.base.MyApplication;

public class Fragment03 extends BaseFragment {

	private static final String TAG = "CollectFragment";
	private TextView mTitleTv;

	
	//=============================================================
	TextView textView;
 private Context context;
 MyApplication myApplication;
 //=============================================================
	
	public static Fragment03 newInstance() {
		Fragment03 collectFragment = new Fragment03();

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
	    View view = inflater.inflate(R.layout.tabthree, container, false);
     context = ((MainActivity) getActivity()).getContext();
     RelativeLayout rec_btnOne = (RelativeLayout) view
             .findViewById(R.id.rec_btnOne);
     RelativeLayout rec_btnTwo = (RelativeLayout) view
             .findViewById(R.id.rec_btnTwo);

     rec_btnOne.setOnClickListener(new btn_listen());
     rec_btnTwo.setOnClickListener(new btn_listen());
     myApplication = (MyApplication) getActivity().getApplication();
     return view;
	}

 class btn_listen implements OnClickListener {

     @Override
     public void onClick(View arg0) {
         switch (arg0.getId()) {
         case R.id.rec_btnOne:
             DiyManager.showRecommendAppWall(context);
             break;
         case R.id.rec_btnTwo:
             DiyManager.showRecommendGameWall(context);
             break;

         default:
             break;
         }

     }

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

	private void initViews(View view) {
		mTitleTv = (TextView) view.findViewById(R.id.title_tv);
		mTitleTv.setText("推荐");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public String getFragmentName() {
		return TAG;
	}

}
