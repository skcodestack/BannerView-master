package com.github.bannerviewpager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import github.com.bannerviewlibrary.BannerAdapter;
import github.com.bannerviewlibrary.BannerStyle;
import github.com.bannerviewlibrary.BannerView;
import github.com.bannerviewlibrary.Transformer;

public class MainActivity extends AppCompatActivity  {

    @InjectView(R.id.banner_view)
    BannerView mBannerView;
    private Activity mActivity;

    @OnClick(R.id.btn_non_title_indicator)
    void onClickNonTitleIndicator(){
        mData = new int[]{R.mipmap.b1, R.mipmap.b2, R.mipmap.b3, R.mipmap.splash_slogan};
        mDesc = new String[]{"哈哈哈哈哈哈哈", "呵呵呵呵呵呵呵呵呵呵呵", "你是谁", "你猜"};
        mBannerView
                .setPageTransformer(Transformer.ForegroundToBackground)
                .setBottomBackgroundColor(Color.BLUE)
                .setBannerStyle(BannerStyle.NON_INDICATOR_TITLE)
                .setDelayTime(2000)
                .setDotIndicatorDistance(5)
                .setDotIndicatorNormalDrawable(R.drawable.dot_normal)
                .setDotIndicatorFocusDrawable(R.drawable.dot_focus)
                .setDotIndicatorSize(10)
                .setIndicatorGravity(Gravity.RIGHT)
                .setTitleColor(Color.RED)
                .setTitleTextSize(20)
                .start();

    }
    @OnClick(R.id.btn_title_indicator)
    void onClickTitleIndicator(){
        mData = new int[]{R.mipmap.splash_slogan,R.mipmap.b1, R.mipmap.b2, R.mipmap.b3, R.mipmap.splash_slogan};
        mDesc = new String[]{"dfdfd","哈哈哈哈哈哈哈", "呵呵呵呵呵呵呵呵呵呵呵", "你是谁", "你猜"};
        mBannerView
                .setPageTransformer(Transformer.ForegroundToBackground)
                .setBottomBackgroundColor(Color.BLUE)
                .setBannerStyle(BannerStyle.INDICATOR_TITLE)
                .setDelayTime(2000)
                .setDotIndicatorDistance(5)
                .setDotIndicatorNormalDrawable(R.drawable.dot_normal)
                .setDotIndicatorFocusDrawable(R.drawable.dot_focus)
                .setDotIndicatorSize(10)
                .setIndicatorGravity(Gravity.RIGHT)
                .setTitleColor(Color.RED)
                .setTitleTextSize(20)
                .start();

    }
    @OnClick(R.id.btn_indicator)
    void onClickIndicator(){
        mData = new int[]{R.mipmap.b1, R.mipmap.b2, R.mipmap.b3, R.mipmap.splash_slogan};
        mDesc = new String[]{"哈哈哈哈哈哈哈", "呵呵呵呵呵呵呵呵呵呵呵", "你是谁", "你猜"};
        mBannerView
                .setPageTransformer(Transformer.ForegroundToBackground)
                .setBottomBackgroundColor(Color.BLUE)
                .setBannerStyle(BannerStyle.INDICATOR)
                .setDelayTime(2000)
                .setDotIndicatorDistance(5)
                .setDotIndicatorNormalDrawable(R.drawable.dot_normal)
                .setDotIndicatorFocusDrawable(R.drawable.dot_focus)
                .setDotIndicatorSize(10)
                .setIndicatorGravity(Gravity.CENTER)
                .setTitleColor(Color.RED)
                .setTitleTextSize(20)
                .start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        ButterKnife.inject(this);
        initData();
    }



    int[] mData = new int[]{R.mipmap.banner_default, R.mipmap.splash_slogan};
    String[] mDesc = new String[]{"哈哈哈哈哈哈哈", "呵呵呵呵呵呵呵呵呵呵呵"};

    private void initData() {
        mBannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                // convertView 如果不为空，则可以复用
                ImageView mImageView = null;
                if (convertView == null) {
                    mImageView = new ImageView(mActivity);
                } else {
                    mImageView = (ImageView) convertView;
                }
                mImageView.setImageResource(mData[position]);
                return mImageView;
            }

            @Override
            public int getCount() {
                return mData.length;
            }

            /**
             * 标题
             * @param position
             * @return
             */
            @Override
            public String getBannerTitle(int position) {
                return mDesc[position];
            }
        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBannerView.startAutoPlay();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mBannerView.stopAutoPlay();
    }
}
