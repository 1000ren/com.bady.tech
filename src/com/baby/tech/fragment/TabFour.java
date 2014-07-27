package com.baby.tech.fragment;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baby.tech.R;
import com.baby.tech.activity.TabActivity;
import com.baby.tech.entity.BaseEntity;
import com.baby.tech.entity.StoryInfo;
import com.baby.tech.entity.StroyInfoRsp;
import com.baby.tech.net.INetDataCallBack;
import com.baby.tech.net.NetSocket;
import com.baby.tech.utils.Util;



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
  RelativeLayout mybnt = (RelativeLayout) tab1.findViewById(R.id.mybbbtn);

  sharebnt.setOnClickListener(new btn_listen());
  mybnt.setOnClickListener(new btn_listen());

		return tab1;
		
	}
	
;
	
 class btn_listen implements OnClickListener,INetDataCallBack{

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
       
         case R.id.mybbbtn:
             final String s= "" ;
             Toast.makeText(context, "发送请求", Toast.LENGTH_SHORT).show();
             //{"infotype":"resinfo","cmd":202,"simid":"A0000049922077"}
//             NetSocket.SendData(this, "ssssss");
//             NetSocket.SendData(this, registerImg());
//             NetSocket.SendData(this, registerAuio());
             NetSocket.SendData(this, registerVedio());
             break;
             

         default:
             break;
         }
         
     }

     
     @Override
     public void onDataFinish(BaseEntity entity) {
      // TODO Auto-generated method stub
      try {
       if (entity == null || entity.data == null) {
        return;
       }
       StroyInfoRsp mStroyInfoRsp = new StroyInfoRsp();
       JSONObject jsonObject = new JSONObject(Util.unCompressByGzip(entity.data));
       int ret = jsonObject.getInt("ret");
       mStroyInfoRsp.setRet(ret);
       if (ret == 0) {
      /*     "ret": 0,
           "num": 20,
           "cmd": 10202,
           "infotype": "resinfo",
           "ResDataAry": [{
            "resclass": "10001",
            "resname": "10001",
            "respath": "\/onestudy\/shizi_pic\/",
            "name": "鸡",
            "classname": "动物"
           },
           {
            "resclass": "10001",
            "resname": "10002",
            "respath": "\/onestudy\/shizi_pic\/",
            "name": "鸭",
            "classname": "动物"
           },  */
           
           int num = jsonObject.getInt("num");  
           int cmd = jsonObject.getInt("cmd");  
           String infotype = jsonObject.getString("infotype");  
           mStroyInfoRsp.setCmd(cmd);
           mStroyInfoRsp.setNum(num);
           mStroyInfoRsp.setInfotype(infotype);
           
           
           
           
           JSONArray JsonResDataAry = jsonObject
                   .getJSONArray("ResDataAry");
           List<StoryInfo> storyInfoList = new ArrayList<StoryInfo>();
          
           for (int i = 0; i < JsonResDataAry.length(); i++) {
               JSONObject jsonary = JsonResDataAry.getJSONObject(i);
               StoryInfo mStoryInfo = new StoryInfo();
               mStoryInfo.setResclass(jsonary.getString("resclass"));
               mStoryInfo.setResname(jsonary.getString("resname"));
               mStoryInfo.setRespath(jsonary.getString("respath"));
               mStoryInfo.setName(jsonary.getString("name"));
               mStoryInfo.setClassname(jsonary.getString("classname"));
               storyInfoList.add(mStoryInfo);
              }
           
           
           
//        Util.setPrefString(context,
//          Constant.PREF_AUTH_NAME, mstrUser);
       
       
       
       
       
       } else {
        Toast.makeText(context, "错误",
          Toast.LENGTH_LONG).show();
       }

      } catch (Exception e) {
       // throw new RuntimeException(e);
       e.printStackTrace();
      }
     }
     
     
    @Override
    public void onDataStart() {
    }

   

    @Override
    public void onDataError() {
    }
  
     
     
}
 
 /**
  * 
 		* 功能描述：图片
 		*
 		* @author 
 		* <p>创建日期 ：2014-7-27 下午3:57:05</p>
 		*
 		* @return
 		*
 		* <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
  */
 public static String registerImg() {
     try {
         //{"infotype":"resinfo","cmd":202,"simid":"A0000049922077"}
      JSONObject JsonObject = new JSONObject();   
      JsonObject.put("infotype", "resinfo");
      //图片
      JsonObject.put("cmd", 202);
      JsonObject.put("simid", "A0000049922077");
      return JsonObject.toString();

     } catch (Exception e) {
      throw new RuntimeException(e);
     }
    }
 
 /**
  * 
 		* 功能描述： 音频
 		*
 		* @author 
 		* <p>创建日期 ：2014-7-27 下午3:56:53</p>
 		*
 		* @return
 		*
 		* <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
  */
 public static String registerAuio() {
     try {
         //{"infotype":"resinfo","cmd":202,"simid":"A0000049922077"}
         JSONObject JsonObject = new JSONObject();   
         JsonObject.put("infotype", "resinfo");
         //音频
         JsonObject.put("cmd", 203);
         JsonObject.put("simid", "A0000049922077");
         return JsonObject.toString();
         
     } catch (Exception e) {
         throw new RuntimeException(e);
     }
 }
 /***
  * 
 		* 功能描述：视频
 		*
 		* @author 
 		* <p>创建日期 ：2014-7-27 下午3:56:39</p>
 		*
 		* @return
 		*
 		* <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
  */
 public static String registerVedio() {
     try {
         //{"infotype":"resinfo","cmd":202,"simid":"A0000049922077"}
         JSONObject JsonObject = new JSONObject();   
         JsonObject.put("infotype", "resinfo");
         //视频
         JsonObject.put("cmd", 204);
         JsonObject.put("simid", "A0000049922077");
         return JsonObject.toString();
         
     } catch (Exception e) {
         throw new RuntimeException(e);
     }
 }


}
