package com.baby.tech.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.baby.tech.R;
import com.baby.tech.StudyImgActivity;
import com.baby.tech.activity.AudioActivity;
import com.baby.tech.activity.ChildStoryActivity;
import com.baby.tech.activity.MainActivity;
import com.baby.tech.activity.MediaPlayerActivity;
import com.baby.tech.adapter.ImageAdapter;
import com.baby.tech.base.MyApplication;
import com.baby.tech.entity.BaseEntity;
import com.baby.tech.entity.NetInfo;
import com.baby.tech.entity.NetInfoRsp;
import com.baby.tech.net.INetDataCallBack;
import com.baby.tech.net.NetSocket;
import com.baby.tech.utils.Util;

public class Fragment02 extends BaseFragment {

	private static final String TAG = "CollectFragment";
	private TextView mTitleTv;

	
	//==============================================
 GridView mGridView;
 private Context context;
 MyApplication myApplication;
 int Index ; 
	
	//==============================================
	
	public static Fragment02 newInstance() {
		Fragment02 collectFragment = new Fragment02();

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
		
		
		
	 context = ((MainActivity) getActivity()).getContext();
  
  myApplication = (MyApplication) getActivity().getApplication();
  View view = inflater.inflate(R.layout.tabtwo, container, false);
  mGridView = (GridView) view.findViewById(R.id.GridMenu);

  ImageAdapter mAdapter = new ImageAdapter(context);
  mGridView.setAdapter(mAdapter);

  mGridView.setOnItemClickListener(new btn_listen());

		return view;
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
		mTitleTv.setText("学习空间");
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

 class btn_listen implements OnItemClickListener, INetDataCallBack {

     @Override
     public void onItemClick(AdapterView<?> arg0, View arg1, int position,
             long arg3) {
         
         
         Intent mIntent = new Intent();
         switch (position) {
         // 儿童音乐
         case 0:
             Toast.makeText(context, "1：儿童音乐", Toast.LENGTH_SHORT)
                     .show();
             Index  = 0 ;
             final String s = "";
             Toast.makeText(context, "发送请求", Toast.LENGTH_SHORT).show();
             // {"infotype":"resinfo","cmd":202,"simid":"A0000049922077"}
             // NetSocket.SendData(this, "ssssss");
             // NetSocket.SendData(this, registerImg());
             // NetSocket.SendData(this, registerAuio());
             NetSocket.SendData(this, NetSocket.registerAuio());
             
             
             
//             mIntent.setClass(context, Mp3Activity.class);
//             startActivity(mIntent);
             break;
         // 动漫视频
         case 1:
             Index  = 1 ;
             Toast.makeText(context, "2：动漫视频", Toast.LENGTH_SHORT)
                     .show();
             mIntent.setClass(context, MediaPlayerActivity.class);
             startActivity(mIntent);
             break;
         // 儿童故事
         case 2:
             Index  = 2 ;
             mIntent.setClass(context, ChildStoryActivity.class);
             startActivity(mIntent);
             Toast.makeText(context, "3：儿童故事", Toast.LENGTH_SHORT)
                     .show();
             break;
         // 儿童绘本
         case 3:
             Index  = 3 ;
             Toast.makeText(context, "4：敬请期待", Toast.LENGTH_SHORT)
                     .show();
             break;
         // 育儿宝典
         case 4:
             Index  = 4 ;
             Toast.makeText(context, "5：敬请期待", Toast.LENGTH_SHORT)
                     .show();

             break;
         // 看图识字
         case 5:
             Index  = 5 ;
             NetSocket.SendData(this, NetSocket.registerImg());
            
             Toast.makeText(context, "6：看图识字", Toast.LENGTH_SHORT)
                     .show();

             break;

         default:
             break;
         }
     }

     @Override
     public void onDataStart() {
     }

     @Override
     public void onDataFinish(BaseEntity entity) {
         // TODO Auto-generated method stub
         try {
             if (entity == null || entity.data == null) {
                 return;
             }
             NetInfoRsp mStroyInfoRsp = new NetInfoRsp();
             JSONObject jsonObject = new JSONObject(
                     Util.unCompressByGzip(entity.data));
             int ret = jsonObject.getInt("ret");
             mStroyInfoRsp.setRet(ret);
             if (ret == 0) {
                 /*
                  * "ret": 0, "num": 20, "cmd": 10202, "infotype": "resinfo",
                  * "ResDataAry": [{ "resclass": "10001", "resname": "10001",
                  * "respath": "\/onestudy\/shizi_pic\/", "name": "鸡",
                  * "classname": "动物" }, { "resclass": "10001", "resname":
                  * "10002", "respath": "\/onestudy\/shizi_pic\/", "name":
                  * "鸭", "classname": "动物" },
                  */

                 int num = jsonObject.getInt("num");
                 int cmd = jsonObject.getInt("cmd");
                 String infotype = jsonObject.getString("infotype");
                 mStroyInfoRsp.setCmd(cmd);
                 mStroyInfoRsp.setNum(num);
                 mStroyInfoRsp.setInfotype(infotype);

                 JSONArray JsonResDataAry = jsonObject
                         .getJSONArray("ResDataAry");
                 //判断 多媒体 列表 集合 不为空 
                 
                 if(myApplication.getNetInfoList()!=null
                         &&myApplication.getNetInfoList().size()!=0){
                     //先清空多媒体 列表集合
                     myApplication.getNetInfoList().clear();
                 }
                 List<NetInfo> netInfoList = new ArrayList<NetInfo>();
                 
                 for (int i = 0; i < JsonResDataAry.length(); i++) {
                     JSONObject jsonary = JsonResDataAry.getJSONObject(i);
                     NetInfo mStoryInfo = new NetInfo();
                     mStoryInfo.setResclass(jsonary.getString("resclass"));
                     mStoryInfo.setResname(jsonary.getString("resname"));
                     mStoryInfo.setRespath(jsonary.getString("respath"));
                     mStoryInfo.setName(jsonary.getString("name"));
                     mStoryInfo.setClassname(jsonary.getString("classname"));
                     netInfoList.add(mStoryInfo);
                 }
                 myApplication.setNetInfoList(netInfoList);
                 // Util.setPrefString(context,
                 // Constant.PREF_AUTH_NAME, mstrUser);
                 Intent intent = null;
                 switch (Index) {
                 case 0:
                     //打开多媒体列表
                     intent = new Intent(context, AudioActivity.class);
                     startActivity(intent);
                     break;
                 case 1:
                     
                     break;
                 case 2:
                     
                     break;
                 case 3:
                     
                     break;
                 case 4:
                     
                     break;
                 case 5:
                     intent = new Intent(context, StudyImgActivity.class);
                     startActivity(intent);
                     break;

                 default:
                     break;
                 }
               
                
                 
                 

             } else {
                 Toast.makeText(context, "数据解析错误", Toast.LENGTH_LONG).show();
             }
         } catch (Exception e) {
             // throw new RuntimeException(e);
             e.printStackTrace();
         }
         
     }

     @Override
     public void onDataError() {
     }
     
 }
	
}
