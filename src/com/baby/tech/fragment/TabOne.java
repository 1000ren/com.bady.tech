package com.baby.tech.fragment;

import java.util.ArrayList;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import net.youmi.android.dev.AppUpdateInfo;
import net.youmi.android.diy.DiyManager;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baby.tech.DialogTool;
import com.baby.tech.R;
import com.baby.tech.activity.TabActivity;
import com.baby.tech.activity.WeatherActivity;
import com.baby.tech.db.DBManager;
import com.baby.tech.db.TechdbInfo;
import com.palmcity.tts.NaviTTS;




@SuppressLint("NewApi")
public class TabOne extends Fragment{
//=======================================================================
    private TextView mTextShowone = null;
    private TextView mTextWord = null;
    private TextView mTextSentence = null;
    
    /** 语音播报 **/
    public NaviTTS tts = null;

    private String mstrAry[] = { "云", "日", "月", "山", "水", "田", "虫", "牛", "羊",
      "马", "鸭" };
    private String mstrAry2[] = { "白云", "落日", "月亮", "高山", "喝水", "农田", "虫子",
      "奶牛", "山羊", "战马", "鸭子" };
    private String mstrAry3[] = { "天上的白云飘呀飘", "天上有一轮落日", "弯弯的月亮像小船",
      "高山上有许多植物", "小朋友每天都要喝水", "农民伯伯在农田里撒种子", "小虫子爬呀爬", "会产奶的牛就是奶牛",
      "山羊会爬山", "你想骑马吗？", "小鸭子乖乖" };

    private int mDataNum = 0;
    private int mIndex = 0;
    private int mReadTotal = 0;
    private static int MAX_NUM = 20;

    private LinearLayout mDictSelectLayout = null;
    private TextView mDictSelectBtn = null;

    private TextView mDictSelectBtn13 = null;
    private TextView mDictSelectBtn35 = null;

    private Button mBtnLeft = null;
    private Button mBtnRight = null;

    private AdView adView = null;

    private DBManager mDBManager = null;
    private ArrayList<TechdbInfo> mTechdbInfoAry = null;
    
    private Context mContext;
    
    
//=======================================================================
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	    this.mContext = ((TabActivity) getActivity()).getmContext();
		View tab1= inflater.inflate(R.layout.tabone, container,false);
		 
		 
//======================================================================
//	 AdManager.getInstance(this.mContext).init("e0e6d19ce2bd64c5",
//	         "14c3ce968c367cef", false);
	       //requestWindowFeature(Window.FEATURE_NO_TITLE);
//	       this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//	         WindowManager.LayoutParams.FLAG_FULLSCREEN);
//	       setContentView(R.layout.activity_main);

	       mDBManager = new DBManager(this.mContext);
	       mTechdbInfoAry = new ArrayList<TechdbInfo>();
	       mTechdbInfoAry = mDBManager.getEvent();
	       mTextShowone = (TextView) tab1.findViewById(R.id.text_show_one);
	       mTextShowone.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                
                
            }
        });
	       // mDataNum = mstrAry.length;
	       mDataNum = mTechdbInfoAry.size();

	       tts = new NaviTTS(this.mContext);
	       findView(tab1);
	       
	       
	       // 开启用户统计模式
//	       AdManager.getInstance(this.mContext).setUserDataCollect(true);
	       
		
		
		
//======================================================================
		
		
		
		 
		return tab1;
		
	}
	

	@Override
    public void onDestroy() {
	 // TODO Auto-generated method stub
	    mDBManager.closeDB();
	    tts.closePlayer();
	    SpotManager.getInstance(this.mContext).unregisterSceenReceiver();

	    super.onDestroy();
    }
	

 
 @Override
 public boolean onOptionsItemSelected(MenuItem item) {
  // TODO Auto-generated method stub
  switch(item.getItemId()){
        case R.id.action_settings:
         Log.e("test", "action_settings");
         Intent intent = new Intent(this.mContext,
           WeatherActivity.class);
   startActivity(intent);   
         break;
        case R.id.action_share:
         Intent it = new Intent(Intent.ACTION_SEND);
   it.putExtra(Intent.EXTRA_TEXT,
     getResources().getString(R.string.share_content));
   it.setType("text/plain");
   startActivity(Intent.createChooser(it, "将报报分享到"));
         break;
        case R.id.action_recommend:
         Log.e("test", "action_recommend");
         // 展示应用推荐墙
      DiyManager.showRecommendAppWall(this.mContext);
         break;
        case R.id.action_games:
         Log.e("test", "action_games");
         // 展示游戏推荐墙
      DiyManager.showRecommendGameWall(this.mContext);
         break;
        case R.id.action_update:
         Log.e("test", "action_games");
         // 展示游戏推荐墙
         
         AppUpdateInfo updateInfo=AdManager.getInstance(this.mContext).syncCheckAppUpdate();
         // 注意，此方法务必在非 UI 线程调用，否则有可能不成功。
         //AdManager.getInstance(this).asyncCheckAppUpdate(callback);
         break;
        }
  return super.onOptionsItemSelected(item);
 }
	
	
 private void findView(View view) {
     RelativeLayout adLayout = (RelativeLayout) view.findViewById(R.id.AdLayout);
     adView = new AdView(mContext, AdSize.FIT_SCREEN);
     adLayout.addView(adView);

     initAdView();

     mDictSelectBtn = (TextView) view.findViewById(R.id.id_activity_main_dict);
     mDictSelectBtn.setOnClickListener(new MyBtnListener());

     mDictSelectLayout = (LinearLayout) view. findViewById(R.id.id_activity_dict_layout);

     mDictSelectBtn13 = (TextView) view. findViewById(R.id.id_activity_dict_13);
     mDictSelectBtn13.setOnClickListener(new MyBtnListener());
     mDictSelectBtn35 = (TextView)  view.findViewById(R.id.id_activity_dict_35);
     mDictSelectBtn35.setOnClickListener(new MyBtnListener());

     mBtnLeft = (Button) view. findViewById(R.id.id_activity_dict_left_word);
     mBtnLeft.setOnClickListener(new MyBtnListener());
     mBtnRight = (Button)  view.findViewById(R.id.id_activity_dict_right_word);
     mBtnRight.setOnClickListener(new MyBtnListener());

     mTextWord = (TextView) view. findViewById(R.id.id_activity_dict_word);
     mTextSentence = (TextView) view. findViewById(R.id.id_activity_dict_sentence);

     initWord();
    }


 private void initAdView() {
     // 监听广告条接口
     adView.setAdListener(new AdViewListener() {

      @Override
      public void onSwitchedAd(AdView arg0) {
       Log.i("YoumiAdDemo", "广告条切换");
      }

      @Override
      public void onReceivedAd(AdView arg0) {
       Log.i("YoumiAdDemo", "请求广告成功");

      }

      @Override
      public void onFailedToReceivedAd(AdView arg0) {
       Log.i("YoumiAdDemo", "请求广告失败");
      }
     });

     // 加载插播资源
     SpotManager.getInstance(this.mContext).loadSpotAds();
     // 设置展示超时时间，加载超时则不展示广告，默认0，代表不设置超时时间
     SpotManager.getInstance(this.mContext).setSpotTimeout(5000);// 设置5秒
     SpotManager.getInstance(this.mContext).setShowInterval(20);// 设置20秒的显示时间间隔
    }

 
 class MyBtnListener implements OnClickListener{

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int iResid = v.getId();
        switch (iResid) {
        case R.id.text_show_one:
         nextWord();
         break;
        case R.id.id_activity_main_dict:
         setDictSelectLayoutVisiable();
         break;
        case R.id.id_activity_dict_13:
         setDictSelectLayoutVisiable();
         break;
        case R.id.id_activity_dict_35:
         setDictSelectLayoutVisiable();
         break;
        case R.id.id_activity_dict_left_word:
         preWord();
         break;
        case R.id.id_activity_dict_right_word:
         nextWord();
         break;
        default:
         break;
        }
        
    }
     
 }

private void initWord() {
    // mTextShowone.setText(mstrAry[mIndex]);
    // mTextWord.setText("单词：" + mstrAry2[mIndex]);
    // mTextSentence.setText("造句：" + mstrAry3[mIndex]);
    
    String str1 = "小胖猪嘟嘟成天呆在家里，一点儿意思也没有，他心里好烦啊！";
    String str2 = "妈妈说：“嘟嘟，你出去走走吧，也许会找到快乐的。”嘟嘟走出家门，暖暖的阳光照在身上，好惬意。嘟嘟伸了个懒腰，打了个哈欠，向田野里望去。田野上空荡荡的，一个人影也没有，嘟嘟心里涌出个念头，到森林里走走，也许会有令人惊喜的收获。";

    //tts.play(0, str1 + str2);
    mTextShowone.setText(mTechdbInfoAry.get(mIndex).mZi);
    mTextWord.setText("单词：" + mTechdbInfoAry.get(mIndex).mCi);
    mTextSentence.setText("造句：" + mTechdbInfoAry.get(mIndex).mJu);
    
    tts.play(0, mTechdbInfoAry.get(mIndex).mZi +","+ mTechdbInfoAry.get(mIndex).mCi +","+ mTechdbInfoAry.get(mIndex).mJu);
  //  tts.play(1, mTechdbInfoAry.get(mIndex).mCi);
  //  tts.play(1, mTechdbInfoAry.get(mIndex).mJu);
   }

   private void preWord() {
    mIndex--;

    if (mIndex < 0) {
     mIndex = mDataNum - 1;
    }

    mTextShowone.setText(mTechdbInfoAry.get(mIndex).mZi);
    mTextWord.setText("单词：" + mTechdbInfoAry.get(mIndex).mCi);
    mTextSentence.setText("造句：" + mTechdbInfoAry.get(mIndex).mJu);
    tts.stop();
    tts.play(0, mTechdbInfoAry.get(mIndex).mZi +","+ mTechdbInfoAry.get(mIndex).mCi +","+ mTechdbInfoAry.get(mIndex).mJu);
  //  tts.play(1, mTechdbInfoAry.get(mIndex).mCi);
  //  tts.play(1, mTechdbInfoAry.get(mIndex).mJu);
   }
   private void takeRest()
   {
    android.content.DialogInterface.OnClickListener positiveBtnListener = new DialogInterface.OnClickListener() {
     public void onClick(DialogInterface dialog, int which) {
      if (which == -1) {
       showSpotAds();
      }
     }
    };
    DialogTool.createConfirmDialog(this.mContext, "提示", "您已识字有一会了，是否休息一下？", "休息一下", "继续学习",
      positiveBtnListener, positiveBtnListener, R.drawable.icon)
      .show();
   }
   
   private void showSpotAds() {
       // 展示插播广告，可以不调用loadSpot独立使用
       SpotManager.getInstance(this.mContext).showSpotAds(
         this.mContext, new SpotDialogListener() {
          @Override
          public void onShowSuccess() {
           Log.i("YoumiAdDemo", "展示成功");
          }

          @Override
          public void onShowFailed() {
           Log.i("YoumiAdDemo", "展示失败");
          }

         }); // //

      }
   
   private void nextWord() {
    mIndex++;
    mReadTotal++;
    if (mIndex >= mDataNum) {
     takeRest();
     mIndex = 0;
    }
    if(mReadTotal >= MAX_NUM)
    {
     mReadTotal = 0; 
     takeRest();
    }

    mTextShowone.setText(mTechdbInfoAry.get(mIndex).mZi);
    mTextWord.setText("单词：" + mTechdbInfoAry.get(mIndex).mCi);
    mTextSentence.setText("造句：" + mTechdbInfoAry.get(mIndex).mJu);
    tts.stop();
    tts.play(0, mTechdbInfoAry.get(mIndex).mZi +","+ mTechdbInfoAry.get(mIndex).mCi +","+ mTechdbInfoAry.get(mIndex).mJu);
  //  tts.play(1, mTechdbInfoAry.get(mIndex).mCi);
  //  tts.play(1, mTechdbInfoAry.get(mIndex).mJu);
   }

   private void setDictSelectLayoutVisiable() {
    if (mDictSelectLayout.getVisibility() == View.GONE) {
     Animation anim = AnimationUtils.loadAnimation(
       this.mContext, R.anim.top_in);
     mDictSelectLayout.setAnimation(anim);

     mDictSelectLayout.setVisibility(View.VISIBLE);
    } else {
     Animation anim = AnimationUtils.loadAnimation(
             this.mContext, R.anim.top_out);
     mDictSelectLayout.setAnimation(anim);

     mDictSelectLayout.setVisibility(View.GONE);
    }
   }

   
	
	
	
	
	
}
