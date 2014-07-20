package com.baby.tech;

import java.util.ArrayList;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import net.youmi.android.dev.AppUpdateInfo;
import net.youmi.android.diy.DiyManager;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baby.tech.db.DBManager;
import com.baby.tech.db.TechdbInfo;
import com.palmcity.tts.NaviTTS;

public class MainActivity extends Activity implements OnClickListener {

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AdManager.getInstance(this).init("e0e6d19ce2bd64c5",
				"14c3ce968c367cef", false);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		mDBManager = new DBManager(this);
		mTechdbInfoAry = new ArrayList<TechdbInfo>();
		mTechdbInfoAry = mDBManager.getEvent();
		mTextShowone = (TextView) findViewById(R.id.text_show_one);
		mTextShowone.setOnClickListener(this);
		// mDataNum = mstrAry.length;
		mDataNum = mTechdbInfoAry.size();

		tts = new NaviTTS(this);
		findView();
		
		
		// 开启用户统计模式
		AdManager.getInstance(this).setUserDataCollect(true);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mDBManager.closeDB();
		tts.closePlayer();
		SpotManager.getInstance(this).unregisterSceenReceiver();

		super.onDestroy();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.blank, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
        case R.id.action_settings:
        	Log.e("test", "action_settings");
        	Intent intent = new Intent(MainActivity.this,
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
    		DiyManager.showRecommendAppWall(MainActivity.this);
        	break;
        case R.id.action_games:
        	Log.e("test", "action_games");
        	// 展示游戏推荐墙
    		DiyManager.showRecommendGameWall(MainActivity.this);
        	break;
        case R.id.action_update:
        	Log.e("test", "action_games");
        	// 展示游戏推荐墙
        	
        	AppUpdateInfo updateInfo=AdManager.getInstance(this).syncCheckAppUpdate();
        	// 注意，此方法务必在非 UI 线程调用，否则有可能不成功。
        	//AdManager.getInstance(this).asyncCheckAppUpdate(callback);
        	break;
        }
		return super.onOptionsItemSelected(item);
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
		SpotManager.getInstance(this).loadSpotAds();
		// 设置展示超时时间，加载超时则不展示广告，默认0，代表不设置超时时间
		SpotManager.getInstance(this).setSpotTimeout(5000);// 设置5秒
		SpotManager.getInstance(this).setShowInterval(20);// 设置20秒的显示时间间隔
	}

	private void findView() {
		RelativeLayout adLayout = (RelativeLayout) findViewById(R.id.AdLayout);
		adView = new AdView(this, AdSize.FIT_SCREEN);
		adLayout.addView(adView);

		initAdView();

		mDictSelectBtn = (TextView) findViewById(R.id.id_activity_main_dict);
		mDictSelectBtn.setOnClickListener(this);

		mDictSelectLayout = (LinearLayout) findViewById(R.id.id_activity_dict_layout);

		mDictSelectBtn13 = (TextView) findViewById(R.id.id_activity_dict_13);
		mDictSelectBtn13.setOnClickListener(this);
		mDictSelectBtn35 = (TextView) findViewById(R.id.id_activity_dict_35);
		mDictSelectBtn35.setOnClickListener(this);

		mBtnLeft = (Button) findViewById(R.id.id_activity_dict_left_word);
		mBtnLeft.setOnClickListener(this);
		mBtnRight = (Button) findViewById(R.id.id_activity_dict_right_word);
		mBtnRight.setOnClickListener(this);

		mTextWord = (TextView) findViewById(R.id.id_activity_dict_word);
		mTextSentence = (TextView) findViewById(R.id.id_activity_dict_sentence);

		initWord();
	}

	@Override
	public void onBackPressed() {
		// 如果有需要，可以点击后退关闭插播广告。
		if (!SpotManager.getInstance(MainActivity.this).disMiss(true)) {
			super.onBackPressed();
		}
	}

	@Override
	protected void onStop() {
		// 如果不调用此方法，则按home键的时候会出现图标无法显示的情况。
		SpotManager.getInstance(MainActivity.this).disMiss(false);
		super.onStop();
	}

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
	
	private void takeRest()
	{
		android.content.DialogInterface.OnClickListener positiveBtnListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (which == -1) {
					showSpotAds();
				}
			}
		};
		DialogTool.createConfirmDialog(this, "提示", "您已识字有一会了，是否休息一下？", "休息一下", "继续学习",
				positiveBtnListener, positiveBtnListener, R.drawable.icon)
				.show();
	}

	private void showSpotAds() {
		// 展示插播广告，可以不调用loadSpot独立使用
		SpotManager.getInstance(MainActivity.this).showSpotAds(
				MainActivity.this, new SpotDialogListener() {
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
//		tts.play(1, mTechdbInfoAry.get(mIndex).mCi);
//		tts.play(1, mTechdbInfoAry.get(mIndex).mJu);
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
//		tts.play(1, mTechdbInfoAry.get(mIndex).mCi);
//		tts.play(1, mTechdbInfoAry.get(mIndex).mJu);
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
//		tts.play(1, mTechdbInfoAry.get(mIndex).mCi);
//		tts.play(1, mTechdbInfoAry.get(mIndex).mJu);
	}

	private void setDictSelectLayoutVisiable() {
		if (mDictSelectLayout.getVisibility() == View.GONE) {
			Animation anim = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.top_in);
			mDictSelectLayout.setAnimation(anim);

			mDictSelectLayout.setVisibility(View.VISIBLE);
		} else {
			Animation anim = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.top_out);
			mDictSelectLayout.setAnimation(anim);

			mDictSelectLayout.setVisibility(View.GONE);
		}
	}

	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			android.content.DialogInterface.OnClickListener positiveBtnListener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if (which == -1) {
						// startActivity(new Intent(CarTabActivity.this,
						// BaseRegisterActivity.class));
						finish();
					}
				}
			};
			DialogTool.createConfirmDialog(this, "提示", "确定退出程序？", "退出", "再玩玩",
					positiveBtnListener, positiveBtnListener, R.drawable.icon)
					.show();
			return true;
		}
		return false;
	}

}
