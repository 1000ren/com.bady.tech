package com.baby.tech.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.baby.tech.R;
import com.baby.tech.activity.ChildStoryActivity;
import com.baby.tech.activity.MediaPlayerActivity;
import com.baby.tech.activity.Mp3Activity;
import com.baby.tech.activity.TabActivity;
import com.baby.tech.adapter.ImageAdapter;



@SuppressLint("NewApi")
public class TabTwo extends Fragment {
	GridView mGridView ;
	private Context context;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	 context = ((TabActivity) getActivity()).getmContext();
		View tab1= inflater.inflate(R.layout.tabtwo, container,false);
		mGridView = (GridView) tab1.findViewById(R.id.GridMenu);
		
		ImageAdapter mAdapter = new ImageAdapter(context);
		mGridView.setAdapter(mAdapter);
		
		
		mGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                    long arg3) {
                Intent mIntent = new Intent() ;
                switch (position) {
                //儿童音乐
                case 0:
                    Toast.makeText(context, "1：儿童音乐", Toast.LENGTH_SHORT).show();
                    mIntent.setClass(context, Mp3Activity.class);
                    startActivity(mIntent);
                    break;
                //动漫视频
                case 1:
                    Toast.makeText(context, "2：动漫视频", Toast.LENGTH_SHORT).show();
                    mIntent.setClass(context, MediaPlayerActivity.class);
                    startActivity(mIntent);
                    break;
                //儿童故事
                case 2:
                    mIntent.setClass(context, ChildStoryActivity.class);
                    startActivity(mIntent);
                    Toast.makeText(context, "3：儿童故事", Toast.LENGTH_SHORT).show();
                    break;
                //儿童绘本    
                case 3:
                    Toast.makeText(context, "4：敬请期待", Toast.LENGTH_SHORT).show();
                    break;
                //育儿宝典
                case 4:
                    Toast.makeText(context, "5：敬请期待", Toast.LENGTH_SHORT).show();
                    
                    break;
                //儿童游戏
                case 5:
                    Toast.makeText(context, "6：敬请期待", Toast.LENGTH_SHORT).show();
                    
                    break;
               

                default:
                    break;
                }
               
                
            }

		
		});
		
		return tab1;
	}
}
