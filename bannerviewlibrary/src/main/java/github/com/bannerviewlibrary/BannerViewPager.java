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
import android.view.MotionEvent;
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

    private String TAG = "BannerViewPager";
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if( mBannerPagerAdapter != null && mBannerPagerAdapter.getCount() > 1 && isAutoPlay) {
                setCurrentItem(getCurrentItem() + 1);
                startRoll();
            }
        }
    };
    //消息
    private static final int SCROLL_MESSAGE_WHAT = 0X0005;
    //轮播间隔
    private int ROLL_INTERVAL_TIME = 3500;
    //adapter
    private BannerAdapter mBannerAdapter;
    private Context mContext;
    //界面复用
    private List<View> mConvertViews;
    private BannerScroller mBannerScroller;
    private BannerPagerAdapter mBannerPagerAdapter;
    private boolean isAutoPlay;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        try {
            mBannerScroller = new BannerScroller(context);
            Field field =
                    ViewPager.class.getDeclaredField("mScroller");

            if (!field.isAccessible())
                field.setAccessible(true);
            field.set(this, mBannerScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //初始化界面复用列表
        mConvertViews = new ArrayList<>();
    }

    public Activity getActivity() {
        return (Activity) mContext;
    }

    /**
     * 设置轮播间隔
     *
     * @param interval
     */
    public void setRollInterval(int interval) {
        if (interval > 0) {
            this.ROLL_INTERVAL_TIME = interval;
        }
    }

    /**
     * 设置图片滑动速度
     *
     * @param duration
     */
    public void setScrollDuration(int duration) {
        if (mBannerScroller != null && duration > 0) {
            mBannerScroller.setBannerDuration(duration);
        }
    }

    /**
     * 开始轮播
     */
    public void startRoll() {
        isAutoPlay = true;
        mHandler.removeMessages(SCROLL_MESSAGE_WHAT);
        mHandler.sendEmptyMessageDelayed(SCROLL_MESSAGE_WHAT, ROLL_INTERVAL_TIME);
    }

    /**
     * 停止轮播
     */
    public void stopRoll() {
        isAutoPlay = false;
        mHandler.removeMessages(SCROLL_MESSAGE_WHAT);
    }

    //销毁handler的发送
    @Override
    protected void onDetachedFromWindow() {
        //销毁handler的发送，防止内存泄漏
        mHandler.removeMessages(SCROLL_MESSAGE_WHAT);
        mHandler = null;
        super.onDetachedFromWindow();
    }

    public void setAdapter(BannerAdapter adapter) {
        this.mBannerAdapter = adapter;
        mBannerPagerAdapter = new BannerPagerAdapter();
        setAdapter(mBannerPagerAdapter);
    }

    public void update(){
        mBannerPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAutoPlay) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_OUTSIDE) {
                startRoll();
            } else if (action == MotionEvent.ACTION_DOWN) {
                mHandler.removeMessages(SCROLL_MESSAGE_WHAT);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (mBannerAdapter.getCount() <= 0) {
                return null;
            }
            View view = mBannerAdapter.getView(position % mBannerAdapter.getCount(), getConvertView());
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
     *
     * @return
     */
    private View getConvertView() {
        if (mConvertViews == null) {
            return null;
        }
        for (View view : mConvertViews) {
            if (view.getParent() == null) {
                return view;
            }
        }
        return null;
    }


}
