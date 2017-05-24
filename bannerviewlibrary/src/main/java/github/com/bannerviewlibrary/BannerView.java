package github.com.bannerviewlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
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
    private LinearLayout mDotContainer;
    //适配器
    private BannerAdapter mAdapter=null;

    private Context mContext;
    //正常点的drawable
    private Drawable mIndicatorNormalDrawable;
    //被点击点的drawable
    private Drawable mIndicatorFocusDrawable;

    //当前选中指示器位置
    private int mCurrentPostion=0;
    private TextView mDescView;

    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        inflate(context, R.layout.ui_bannerview_layout,this);

        init();


        initAttribute(attrs);

    }
    int mDotGravity=1;
    int mDotSize=8;
    int mDotDistance=2;
    float mWidthProportion=0f;
    float mHeightProportion=0f;

    private void initAttribute(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);

        // 获取点的位置
        mDotGravity = array.getInt(R.styleable.BannerView_dotGravity, mDotGravity);

        // 获取点的颜色（默认、选中）
        mIndicatorFocusDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
        if(mIndicatorFocusDrawable == null){
            //如果在布局文件中没有配置点的颜色  有一个默认值
            mIndicatorFocusDrawable = new ColorDrawable(Color.RED);
         }

         mIndicatorNormalDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
         if(mIndicatorNormalDrawable == null){
             //如果在布局文件中没有配置点的颜色  有一个默认值
             mIndicatorNormalDrawable = new ColorDrawable(Color.WHITE);
         }

         //获取点的大小和距离
         mDotSize = (int) array.getDimension(R.styleable.BannerView_dotSize,dip2px(mDotSize));
         mDotDistance = (int) array.getDimension(R.styleable.BannerView_dotDistance,dip2px(mDotDistance));        // 获取底部的颜色        mBottomColor = array.getColor(R.styleable.BannerView_bottomColor,mBottomColor);

        //获取宽高比例
         mWidthProportion = array.getFloat(R.styleable.BannerView_withProportion,mWidthProportion);
         mHeightProportion = array.getFloat(R.styleable.BannerView_heightProportion,mHeightProportion);
        array.recycle();
    }

    private void init() {
        mBannerViewPager = (BannerViewPager) findViewById(R.id.banner_viewpager);
        mDotContainer = (LinearLayout) findViewById(R.id.dot_container);
        mDescView = (TextView) findViewById(R.id.tv_desc);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 8.自适应高度 动态指定高度
        // if(mHeightProportion == 0 || mWidthProportion == 0){
        //           return;
        // }        // 动态指定宽高  计算高度
        // int width = getMeasuredWidth();        // 计算高度        int height = (int) (width*mHeightProportion/mWidthProportion);
        // 指定宽高        getLayoutParams().height = height;

        if(mHeightProportion != 0 && mWidthProportion != 0){
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width*mHeightProportion/mWidthProportion);
//            getLayoutParams().height = height;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置适配器
     * @param adapter
     */
    public void setAdapter(final BannerAdapter adapter){
        this.mAdapter=adapter;

        mBannerViewPager.setAdapter(adapter);

        initDotIndicator();
        initDesc();
        mBannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                pageSelected(position % adapter.getCount());
            }
        });
    }

    /**
     * 初始化广告位置
     */
    private void initDesc() {
        String desc = mAdapter.getBannerDesc(0);
        if(!TextUtils.isEmpty(desc)) {
            mDescView.setText(desc);
        }else {
            mDescView.setText("");
        }
    }

    /**
     * 轮播页切换
     * @param position
     */
    private void pageSelected(int position) {
        int oldPostion=mCurrentPostion;
        DotIndicatorView oldView = (DotIndicatorView) mDotContainer.getChildAt(oldPostion);
        oldView.setDrawable(mIndicatorNormalDrawable);

        DotIndicatorView curView = (DotIndicatorView) mDotContainer.getChildAt(position);
        curView.setDrawable(mIndicatorFocusDrawable);

        mCurrentPostion=position;

        //设置描述
        String desc = mAdapter.getBannerDesc(position);
        if(!TextUtils.isEmpty(desc)) {
            mDescView.setText(desc);
        }else {
            mDescView.setText("");
        }
    }

    /**
     * 初始化指示器
     */
    private void initDotIndicator() {
        mDotContainer.setGravity(obtainGravity());

        int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            DotIndicatorView dotView = new DotIndicatorView(mContext);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(mDotSize,mDotSize);
            layoutParams.leftMargin=layoutParams.rightMargin=mDotDistance;
            dotView.setLayoutParams(layoutParams);
            mCurrentPostion=0;
            if(i==0) {
                dotView.setDrawable(mIndicatorFocusDrawable);
            }else {
                dotView.setDrawable(mIndicatorNormalDrawable);
            }
            mDotContainer.addView(dotView);
        }
    }

    private int obtainGravity(){
        switch (mDotGravity){
            case 0:
                return Gravity.CENTER;

            case -1:
                return Gravity.LEFT;
            default:
                return Gravity.RIGHT;
        }
    }

    /**
     * dip 2 px
     * @param dip
     * @return
     */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());
    }

    public void startRoll(){
        mBannerViewPager.startRoll();
    }

}
