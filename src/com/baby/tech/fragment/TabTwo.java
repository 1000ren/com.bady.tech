package com.baby.tech.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baby.tech.R;



@SuppressLint("NewApi")
public class TabTwo extends Fragment {
	TextView textView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View tab1= inflater.inflate(R.layout.tabtwo, container,false);
		 textView=(TextView) tab1.findViewById(R.id.textView1);
		 textView.setText("tab2");
		return tab1;
		
	}
}
