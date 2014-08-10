package com.baby.tech.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baby.tech.R;
import com.baby.tech.entity.NetInfo;
import com.baby.tech.utils.Player;



@SuppressLint("HandlerLeak")
public class ExpandableListViewAdapter extends BaseAdapter {
    List<String> group = new ArrayList<String>() ;
    LayoutInflater mInflater;
    Context context;

    //
    
    public  int paynum = 0 ;
    public  Player player;
    public int progress = 0;
    
    public ExpandableListViewAdapter(Context context,List<NetInfo> netInfoList) {
        // TODO Auto-generated constructor stub
        mInflater = LayoutInflater.from(context);
        if(group!=null&&group.size()>0){
            group.clear();
        }
        for (int i = 0; i < netInfoList.size(); i++) {
            group.add(netInfoList.get(i).getName());
        }
        this.context = context;
        
        
    }

    @Override
    public int getCount() {
        return group.size();
        	
    }

    @Override
    public Object getItem(int arg0) {
        return null;
        	
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
        	
    }

    @Override
    public View getView(int arg0, View view, ViewGroup arg2) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.channel_expandablelistview, null); 
            TextView channel_group_name = (TextView) view.findViewById(R.id.channel_group_name);
            String name = group.get(arg0);
            channel_group_name.setText(name);
        }
//        if (convertView == null) {
//            mViewChild = new ViewChild();
//            convertView = mInflater.inflate(R.layout.channel_expandablelistview, null);
//            mViewChild.textView = (TextView) convertView.findViewById(R.id.channel_group_name);
//            mViewChild.imageView = (ImageView) convertView.findViewById(R.id.channel_imageview_orientation);
//            convertView.setTag(mViewChild);
//        } else {
//            mViewChild = (ViewChild) convertView.getTag();
//        }

        return view;
        	
    }
  
    
}
