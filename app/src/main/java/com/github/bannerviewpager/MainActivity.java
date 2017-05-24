package com.github.bannerviewpager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import github.com.bannerviewlibrary.BannerAdapter;
import github.com.bannerviewlibrary.BannerView;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    @InjectView(R.id.banner_view)
    BannerView mBannerView;
    @InjectView(R.id.btn_jump)
    Button btn_jump;

    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity=this;
        ButterKnife.inject(this);

        initData();

        initListener();
    }

    private void initListener() {
        btn_jump.setOnClickListener(this);
    }

    int[] mData=new int[]{R.mipmap.banner_default,R.mipmap.splash_slogan};
    String[] mDesc=new String[]{"哈哈哈哈哈哈哈","呵呵呵呵呵呵呵呵呵呵呵"};
    private void initData() {
        mBannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                ImageView mImageView=null;
                if(convertView==null){
                    mImageView=new ImageView(mActivity);
                }else {
                    mImageView= (ImageView) convertView;
                }

                mImageView.setImageResource(mData[position]);
                return mImageView;
            }

            @Override
            public int getCount() {
                return mData.length;
            }

            @Override
            public String getBannerDesc(int position) {
                return mDesc[position];
            }
        });

        mBannerView.startRoll();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_jump){
            Intent intent=new Intent(mActivity,TestActivity.class);
            startActivity(intent);
        }
    }
}
