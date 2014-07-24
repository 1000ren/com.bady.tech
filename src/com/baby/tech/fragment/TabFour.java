package com.baby.tech.fragment;


import net.youmi.android.diy.DiyManager;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baby.tech.R;
import com.baby.tech.activity.TabActivity;
import com.baby.tech.fragment.TabThree.btn_listen;



@SuppressLint("NewApi")
public class TabFour extends Fragment {
	TextView textView;
 private Context context;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View tab1= inflater.inflate(R.layout.tabfour, container,false);
		context = ((TabActivity) getActivity()).getmContext();
		
		
//		sharebnt
  RelativeLayout sharebnt = (RelativeLayout) tab1.findViewById(R.id.sharebnt);

  sharebnt.setOnClickListener(new btn_listen());
		
		return tab1;
		
		
		
		
		
	}
	
;
	
 class btn_listen implements OnClickListener{

     @Override
     public void onClick(View arg0) {
         switch (arg0.getId()) {
         case R.id.sharebnt:
           Intent it = new Intent(Intent.ACTION_SEND);
           it.putExtra(Intent.EXTRA_TEXT,
             getResources().getString(R.string.share_content));
           it.setType("text/plain");
           startActivity(Intent.createChooser(it, "将一一识字分享到"));
             break;
       

         default:
             break;
         }
         
     }
  
}
}
