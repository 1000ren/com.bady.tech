/**
 * ImageAdapter.java V1.0 2014-7-24 上午8:56:34
 *
 * Copyright Talkweb Information System Co. ,Ltd. All rights reserved.
 *
 * Modification history(By Time Reason):
 *
 * Description:
 */

package com.baby.tech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baby.tech.R;

public class StudyImageMenuAdapter extends BaseAdapter {
    private Context context;

    public StudyImageMenuAdapter(Context context) {
        this.context = context;
    }

    private Integer[] images = {
            // 九宫格图片的设置
            R.drawable.animal_normal, R.drawable.plant_normal,
            R.drawable.fruits_normal,

            R.drawable.vegetables_normal, R.drawable.car_normal,
            R.drawable.food_btn_normal,

    };

    private String[] texts = {
            // 九宫格图片下方文字的设置
            "动物", "植物", "水果",

            "蔬菜", "汽车", "食物", };

    // get the number
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    // get the current selector's id number
    @Override
    public long getItemId(int position) {
        return position;
    }

    // create view method
    @Override
    public View getView(int position, View view, ViewGroup viewgroup) {
        ImgTextWrapper wrapper;
        if (view == null) {
            wrapper = new ImgTextWrapper();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item, null);
            view.setTag(wrapper);
            view.setPadding(5, 5, 5, 5); // 每格的间距
        } else {
            wrapper = (ImgTextWrapper) view.getTag();
        }

        wrapper.imageView = (ImageView) view
                .findViewById(R.id.MainActivityImage);
        wrapper.imageView.setBackgroundResource(images[position]);
        wrapper.textView = (TextView) view.findViewById(R.id.MainActivityText);
        wrapper.textView.setText(texts[position]);

        return view;
    }
}




