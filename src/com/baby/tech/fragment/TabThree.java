package com.baby.tech.fragment;

import net.youmi.android.diy.DiyManager;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baby.tech.R;
import com.baby.tech.activity.TabActivity;



@SuppressLint("NewApi")
public class TabThree extends Fragment {
	TextView textView;
 private Context context;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View tab1= inflater.inflate(R.layout.tabthree, container,false);
		context = ((TabActivity) getActivity()).getmContext();
		RelativeLayout rec_btnOne = (RelativeLayout) tab1.findViewById(R.id.rec_btnOne);
		RelativeLayout rec_btnTwo = (RelativeLayout) tab1.findViewById(R.id.rec_btnTwo);
		
		rec_btnOne.setOnClickListener(new btn_listen());
		rec_btnTwo.setOnClickListener(new btn_listen());
		 
		 
		 
		return tab1;
		
	}
	
	class btn_listen implements OnClickListener{

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
}
