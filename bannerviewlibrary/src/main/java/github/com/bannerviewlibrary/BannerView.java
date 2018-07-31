package github.com.bannerviewlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2017/5/24
 * Version  1.0
 * Description:
 */

public class BannerView extends RelativeLayout {

    //banner viewpager
    private BannerViewPager mBannerViewPager;
    //点指示器容器
    private LinearLayout mIndicatorContainer;
    //适配器
    private BannerAdapter mAdapter = null;
    //标题
    private TextView mTitleView;
    //指示器和标题的layout
    private RelativeLayout mTitleLayout;
    private Context mContext;
    /*************
     * 点 指示器
     *************/
    //正常点的drawable
    private Drawable mDotIndicatorNormalDrawable;
    //被点击点的drawable
    private Drawable mDotIndicatorFocusDrawable;
    //点大小
    int mDotIndicatorSize = 8;
    //点之间的距离
    int mDotIndicatorDistance = 2;

    //指示器布局位置
    int mIndicatorGravity = 1;
    //当前选中指示器位置
    private int mCurrentPostion = 0;
    //宽高比
    float mWidthProportion = 0f;
    float mHeightProportion = 0f;
    //显示样式
    private BannerStyle mBannerStyle = BannerStyle.INDICATOR;
    //标题颜色
    private int mTitleColor = Color.WHITE;
    //标题字体大小 (单位dp)
    private int mTitleTextSize = 15;
    //描述栏背景颜色
    private int mBottomBackgroundColor = Color.parseColor("#55000000");
    private boolean isAutoPlay;
    private ViewPager.SimpleOnPageChangeListener mSimpleOnPageChangeListener;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        inflate(context, R.layout.ui_bannerview_layout, this);
        initAttribute(attrs);
        initViews();
        initListener();
    }

    private void initListener() {
        mSimpleOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                pageSelected(position % mAdapter.getCount());
            }
        };
    }

    /**
     * 初始化属性
     *
     * @param attrs
     */
    private void initAttribute(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);
        // 获取点的位置
        int dotGravity = array.getInt(R.styleable.BannerView_indicatorGravity, mIndicatorGravity);
        mIndicatorGravity = obtainGravity(dotGravity);
        // 获取点的颜色（默认、选中）
        mDotIndicatorFocusDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
        if (mDotIndicatorFocusDrawable == null) {
            //如果在布局文件中没有配置点的颜色  有一个默认值
            mDotIndicatorFocusDrawable = new ColorDrawable(Color.RED);
        }
        mDotIndicatorNormalDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        if (mDotIndicatorNormalDrawable == null) {
            //如果在布局文件中没有配置点的颜色  有一个默认值
            mDotIndicatorNormalDrawable = new ColorDrawable(Color.WHITE);
        }
        //获取点的大小和距离
        mDotIndicatorSize = (int) array.getDimension(R.styleable.BannerView_dotSize, dip2px(mDotIndicatorSize));
        mDotIndicatorDistance = (int) array.getDimension(R.styleable.BannerView_dotDistance, dip2px(mDotIndicatorDistance));        // 获取底部的颜色        mBottomColor = array.getColor(R.styleable.BannerView_bottomColor,mBottomColor);
        //获取宽高比例
        mWidthProportion = array.getFloat(R.styleable.BannerView_withProportion, mWidthProportion);
        mHeightProportion = array.getFloat(R.styleable.BannerView_heightProportion, mHeightProportion);
        //样式
        int style = array.getInt(R.styleable.BannerView_bannerStyle, 2);
        mBannerStyle = obtianBannerStyle(style);
        //标题信息
        mTitleColor = array.getColor(R.styleable.BannerView_titleColor, mTitleColor);
        mTitleTextSize = px2sp(mContext,(int) array.getDimensionPixelSize(R.styleable.BannerView_titleTextSize, dip2px(mTitleTextSize)));
        array.recycle();
    }

    /**
     * 初始化视图属性
     */
    private void initViews() {
        mBannerViewPager = (BannerViewPager) findViewById(R.id.banner_viewpager);
        mTitleLayout = (RelativeLayout) findViewById(R.id.rl_desc);
        mIndicatorContainer = (LinearLayout) findViewById(R.id.dot_container);
        mTitleView = (TextView) findViewById(R.id.tv_desc);

        updateViews();
    }

    /**
     * 更新views的配置
     */
    private void updateViews() {
        if (isTitleVisiable() && isIndicatorVisiable()) {
            mTitleLayout.setBackgroundColor(mBottomBackgroundColor);
            mTitleLayout.setVisibility(VISIBLE);
            mTitleView.setVisibility(VISIBLE);
            mIndicatorContainer.setVisibility(VISIBLE);
        } else if (isTitleVisiable()) {
            mTitleLayout.setBackgroundColor(mBottomBackgroundColor);
            mTitleLayout.setVisibility(VISIBLE);
            mTitleView.setVisibility(VISIBLE);
            mIndicatorContainer.setVisibility(GONE);
        } else if (isIndicatorVisiable()) {
            mTitleLayout.setBackgroundColor(Color.TRANSPARENT);
            mTitleLayout.setVisibility(VISIBLE);
            mTitleView.setVisibility(GONE);
            mIndicatorContainer.setVisibility(VISIBLE);
        } else {
            mTitleLayout.setBackgroundColor(Color.TRANSPARENT);
            mTitleLayout.setVisibility(GONE);
            mTitleView.setVisibility(GONE);
            mIndicatorContainer.setVisibility(GONE);
        }
        mTitleView.setTextColor(mTitleColor);
        mTitleView.setTextSize(mTitleTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mHeightProportion != 0 && mWidthProportion != 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width * mHeightProportion / mWidthProportion);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public BannerView setAdapter(final BannerAdapter adapter) {
        this.mAdapter = adapter;
        return this;
    }

    /**
     * 更新显示数据
     */
    public void updateAdapterData() {
        if (mAdapter == null) {
            throw new RuntimeException("adapter is null pointer!please invoke setAdapter");
        }
        mBannerViewPager.setAdapter(mAdapter);
        mBannerViewPager.clearOnPageChangeListeners();
        mBannerViewPager.addOnPageChangeListener(mSimpleOnPageChangeListener);
        mBannerViewPager.update();
        initIndicator();
        setTitle(0);

    }

    /**
     * 轮播页切换
     *
     * @param position
     */
    private void pageSelected(int position) {
        //设置指示器显示
        changeIndicator(position);
        //设置描述
        setTitle(position);
    }


    /**
     * 初始化广告位置
     */
    private void setTitle(int position) {
        if (isTitleVisiable()) {
            String desc = mAdapter.getBannerTitle(position);
            if (!TextUtils.isEmpty(desc)) {
                mTitleView.setText(desc);
            } else {
                mTitleView.setText("");
            }
        }
    }

    /**
     * 初始化指示器
     */
    private void initIndicator() {
        if (isIndicatorVisiable()) {
            mIndicatorContainer.removeAllViews();
            mIndicatorContainer.setGravity(mIndicatorGravity);
            int count = mAdapter.getCount();
            for (int i = 0; i < count; i++) {
                DotIndicatorView dotView = new DotIndicatorView(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mDotIndicatorSize, mDotIndicatorSize);
                layoutParams.leftMargin = layoutParams.rightMargin = mDotIndicatorDistance;
                dotView.setLayoutParams(layoutParams);
                mCurrentPostion = 0;
                if (i == 0) {
                    dotView.setDrawable(mDotIndicatorFocusDrawable);
                } else {
                    dotView.setDrawable(mDotIndicatorNormalDrawable);
                }
                mIndicatorContainer.addView(dotView);
            }
        }
    }

    /**
     * 改变指示器显示
     *
     * @param position
     */
    private void changeIndicator(int position) {
        int oldPostion = mCurrentPostion;
        if (isIndicatorVisiable()) {
            DotIndicatorView oldView = (DotIndicatorView) mIndicatorContainer.getChildAt(oldPostion);
            oldView.setDrawable(mDotIndicatorNormalDrawable);

            DotIndicatorView curView = (DotIndicatorView) mIndicatorContainer.getChildAt(position);
            curView.setDrawable(mDotIndicatorFocusDrawable);
        }
        mCurrentPostion = position;
    }

    /**
     * 是否显示标题
     *
     * @return
     */
    private boolean isTitleVisiable() {
        return mBannerStyle == BannerStyle.INDICATOR_TITLE;
    }

    /**
     * 是否显示指示器
     *
     * @return
     */
    private boolean isIndicatorVisiable() {
        return mBannerStyle == BannerStyle.INDICATOR_TITLE || mBannerStyle == BannerStyle.INDICATOR;
    }

    /**
     * @return
     */
    private int obtainGravity(int dotGravity) {
        switch (dotGravity) {
            case 0:
                return Gravity.CENTER;
            case -1:
                return Gravity.LEFT;
            default:
                return Gravity.RIGHT;
        }
    }

    /**
     * 获取样式
     *
     * @param style
     * @return
     */
    private BannerStyle obtianBannerStyle(int style) {

        switch (style) {
            case 0:
                return BannerStyle.NON_INDICATOR_TITLE;
            case 1:
                return BannerStyle.INDICATOR_TITLE;
            default:
                return BannerStyle.INDICATOR;
        }
    }

    /**
     * dip 2 px
     *
     * @param dip
     * @return
     */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    /**
     * px转换成dp
     */
    private int px2dp(Context context,float pxValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }
    /**
     * sp转换成px
     */
    private int sp2px(Context context,float spValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*fontScale+0.5f);
    }
    /**
     * px转换成sp
     */
    private int px2sp(Context context,float pxValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale+0.5f);
    }

    /**
     * 开始滚动
     */
    public void startAutoPlay() {
        isAutoPlay = true;
        mBannerViewPager.startRoll();
    }

    /**
     * 停止滚动
     */
    public void stopAutoPlay() {
        isAutoPlay = false;
        mBannerViewPager.stopRoll();
    }

    /**
     * 开始初始化数据（必须调用）
     */
    public void start() {
        if (isAutoPlay) {
            mBannerViewPager.stopRoll();
        }
        updateViews();
        updateAdapterData();
        if (isAutoPlay) {
            startAutoPlay();
        }
    }

    /***********************************
     * 通过代码来配置属性
     *************************************************/
    /**
     * 设置轮播图片间隔时间
     *
     * @param delayTime
     * @return
     */
    public BannerView setDelayTime(int delayTime) {
        mBannerViewPager.setRollInterval(delayTime);
        return this;
    }

    /**
     * 指示器 没选中样式
     *
     * @param indicatorNormalId
     * @return
     */
    public BannerView setDotIndicatorNormalDrawable(@DrawableRes int indicatorNormalId) {
        this.mDotIndicatorNormalDrawable = ContextCompat.getDrawable(mContext, indicatorNormalId);
        ;
        return this;
    }

    /**
     * 指示器 选中样式
     *
     * @param indicatorFocusId
     * @return
     */
    public BannerView setDotIndicatorFocusDrawable(@DrawableRes int indicatorFocusId) {
        this.mDotIndicatorFocusDrawable = ContextCompat.getDrawable(mContext, indicatorFocusId);
        return this;
    }

    /**
     * 指示器 没选中样式
     *
     * @param indicatorNormalColorId
     * @return
     */
    public BannerView setDotIndicatorNormalColor(@ColorInt int indicatorNormalColorId) {
        this.mDotIndicatorNormalDrawable = new ColorDrawable(indicatorNormalColorId);
        return this;
    }

    /**
     * 指示器 选中样式
     *
     * @param indicatorFocusColorId
     * @return
     */
    public BannerView setDotIndicatorFocusColor(@ColorInt int indicatorFocusColorId) {
        this.mDotIndicatorFocusDrawable = new ColorDrawable(indicatorFocusColorId);
        return this;
    }

    /**
     * 设置指示器位置
     *
     * @param indicatorGravity
     * @return
     */
    public BannerView setIndicatorGravity(int indicatorGravity) {
        this.mIndicatorGravity = indicatorGravity;
        return this;
    }

    /**
     * 指示器大小
     *
     * @param indicatorSize 单位 ：dp
     */
    public BannerView setDotIndicatorSize(int indicatorSize) {
        this.mDotIndicatorSize = dip2px(indicatorSize);
        return this;
    }

    /**
     * 指示器间距
     *
     * @param indicatorDistance 单位 ：dp
     */
    public BannerView setDotIndicatorDistance(int indicatorDistance) {
        this.mDotIndicatorDistance = dip2px(indicatorDistance);
        return this;
    }

//    /**
//     * 设置宽高比例
//     *
//     * @param widthProportion
//     * @param heightProportion
//     * @return
//     */
//    public BannerView setProportion(float widthProportion, float heightProportion) {
//        this.mWidthProportion = widthProportion;
//        this.mHeightProportion = heightProportion;
//        return this;
//    }

    /**
     * 设置样式
     *
     * @param style
     * @return
     */
    public BannerView setBannerStyle(BannerStyle style) {
        mBannerStyle = style;
        return this;
    }

    /**
     * 设置标题颜色
     *
     * @param titleColor
     */
    public BannerView setTitleColor(int titleColor) {
        this.mTitleColor = titleColor;
        return this;
    }

    /**
     * 标题字体大小
     *
     * @param titleTextSize
     */
    public BannerView setTitleTextSize(int titleTextSize) {
        this.mTitleTextSize = titleTextSize;
        return this;
    }

    /**
     * 标题栏 背景色
     *
     * @param bottomBackgroundColor
     */
    public BannerView setBottomBackgroundColor(int bottomBackgroundColor) {
        this.mBottomBackgroundColor = bottomBackgroundColor;
        return this;
    }

    /**
     * 设置动画
     *
     * @param reverseDrawingOrder
     * @param transformer
     * @return
     */
    public BannerView setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        mBannerViewPager.setPageTransformer(reverseDrawingOrder, transformer);
        return this;
    }

    /**
     * 设置动画
     *
     * @param transformer
     * @return
     */
    public BannerView setPageTransformer(Class<? extends ViewPager.PageTransformer> transformer) {
        try {
            setPageTransformer(true, transformer.newInstance());
        } catch (Exception e) {

        }
        return this;
    }
}
