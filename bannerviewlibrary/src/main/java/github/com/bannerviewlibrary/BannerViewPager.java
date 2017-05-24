package github.com.bannerviewlibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2017/5/23
 * Version  1.0
 * Description: 轮播栏
 */

public class BannerViewPager extends ViewPager {

    private String TAG="BannerViewPager";
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            setCurrentItem(getCurrentItem()+1);
            startRoll();
        }
    };
    //消息
    private static final int SCROLL_MESSAGE_WHAT=0X0005;
    //轮播间隔
    private int SCROLL_INTERVAL_TIME=3500;

    //adapter
    private BannerAdapter mBannerAdapter;

    private Context mContext;

    //界面复用
    private List<View> mConvertViews;


    public BannerViewPager(Context context) {
        this(context,null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;

        try {
            BannerScroller mBannerScroller=new BannerScroller(context);
            Field field =
                    ViewPager.class.getDeclaredField("mScroller");

            if(!field.isAccessible())
                field.setAccessible(true);
            field.set(this,mBannerScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //初始化界面复用列表
        mConvertViews=new ArrayList<>();

        //注册生命周期处理
        getActivity().getApplication().registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }


    public Activity getActivity(){
        return (Activity) mContext;
    }


    /**
     * 开始轮播
     */
    public void startRoll(){
        mHandler.removeMessages(SCROLL_MESSAGE_WHAT);
        mHandler.sendEmptyMessageDelayed(SCROLL_MESSAGE_WHAT,SCROLL_INTERVAL_TIME);
        Log.e(TAG,"====>startRoll");
    }

    //销毁handler的发送
    @Override
    protected void onDetachedFromWindow() {
        mHandler.removeMessages(SCROLL_MESSAGE_WHAT);
        mHandler=null;
        //取消注册生命周期处理
        getActivity().getApplication().unregisterActivityLifecycleCallbacks(lifecycleCallbacks);
        super.onDetachedFromWindow();
    }

    public void setAdapter(BannerAdapter adapter) {
        this.mBannerAdapter=adapter;
        setAdapter(new BannerPagerAdapter());
    }

    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view== object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if(mBannerAdapter.getCount()<=0){
                return null;
            }

            View view=mBannerAdapter.getView(position % mBannerAdapter.getCount(),getConvertView());
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            mConvertViews.add((View) object);
        }
    }

    /**
     * 获取复用界面
     * @return
     */
    private View getConvertView(){
        if(mConvertViews==null){
            return null;
        }
        for (View view : mConvertViews) {
            if(view.getParent()==null){
                return view;
            }

        }
        return  null;
    }

    /**
     * activty生命周期
     */
    Application.ActivityLifecycleCallbacks lifecycleCallbacks=new SimpleActivityLifecycleCallbacks(){
        @Override
        public void onActivityResumed(Activity activity) {
            super.onActivityResumed(activity);
            Log.e(TAG,"====>onActivityResumed");
            if(activity == getActivity()){
                mHandler.sendEmptyMessageDelayed(SCROLL_MESSAGE_WHAT,SCROLL_INTERVAL_TIME);
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            super.onActivityPaused(activity);
            Log.e(TAG,"====>onActivityPaused");
            if(activity == getActivity()){
                mHandler.removeMessages(SCROLL_MESSAGE_WHAT);
            }
        }
    };


}
