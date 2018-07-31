### Android 图片轮播工具

现在市面上图片轮播工具还是很多的，也很优秀，我写这个工具的意义在于：
> 第一，对自己的知识做个汇总
> 第二，如果有特别的功能那么自己也能在现有库的基础上来进行修改和优化

#### 功能介绍：
>#### 1.标题，指示器样式的切换
>#### 2.根据自己的需求对主页面自定义
>#### 3.自由切换动画效果(使用第三方动态库)
>#### 4.api链式调用，让代码更加清晰 

<br/>

>### 效果图：

<br/>

#### 使用

>### Step 1.依赖
 Maven
>
    <dependency> 
      <groupId>github.skcodestack</groupId> 
      <artifactId>bannerviewlibrary</artifactId> 
      <version>1.0</version> 
      <type>pom</type> 
    </dependency>
>
Gradle
>
   compile 'github.skcodestack:bannerviewlibrary:1.0'
>

<br/>

>### Step 2.添加到布局中
>
    <github.com.bannerviewlibrary.BannerView
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/banner_view"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:withProportion="8"
        app:heightProportion="4"
        app:dotSize="8dp"
        app:dotDistance="3dp"
        app:indicatorGravity="right"
        app:bottomBackgroundColor="@color/banner_bottom_bar_bg_day"
        app:dotIndicatorFocus="@color/dot_select_color"
        app:dotIndicatorNormal="@color/dot_unselect_color"
        app:bannerStyle="indicator_title"
        app:titleColor="@color/colorAccent"
        app:titleTextSize="15sp">
    </github.com.bannerviewlibrary.BannerView>
>
| 名称 | 描述 |
|----------|:-------------:|
| withProportion | 宽高比例（也可以直接设置view的高度） | 
| heightProportion | 宽高比例（也可以直接设置view的高度） |
| dotSize | 圆点的大小 |
| dotDistance | 圆点的间距 |
| indicatorGravity | 指示器的位置 |
| bottomBackgroundColor | 有标题时，横幅背景色 |
| dotIndicatorFocus | 圆点指示器选中颜色 |
| dotIndicatorNormal | 圆点指示器正常颜色 |
| bannerStyle | bannder样式 |
| titleColor | 标题的颜色 |
| titleTextSize | 标题字体大小 |

<br/>

>### Step 3.添加数据(设置Adapter)
>
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
        })
>

<br/>

>### Step 4.使设置生效（开始）
>
    //必须调用，更新配置后，也要调用
    mBannerView.start()
>

<br/>

>### Step 5.如果需要轮播功能
>
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        mBannerView.startAutoPlay();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //停止轮播
        mBannerView.stopAutoPlay();
    }
>

<br/>

#### 代码调用（可选）

>### 样式
| 名称 | 描述 |
|----------|:-------------:|
| BannerStyle.NON_INDICATOR_TITLE | 都不显示 | 
| BannerStyle.INDICATOR | 显示指示器 |
| BannerStyle.INDICATOR_TITLE | 显示指示器 + 标题 |

代码：

    mBannerView.setBannerStyle(BannerStyle.INDICATOR)
    
    
    
>### 动画

| 名称 |
|----------|
|Transformer.Default|
|Transformer.Accordion|
|Transformer.BackgroundToForeground|
|Transformer.ForegroundToBackground|
|Transformer.CubeIn|
|Transformer.CubeOut|
|Transformer.DepthPage|
|Transformer.FlipHorizontal|
|Transformer.FlipVertical|
|Transformer.RotateDown|
|Transformer.RotateUp|
|Transformer.ScaleInOut|
|Transformer.Stack|
|Transformer.Tablet|
|Transformer.ZoomIn|
|Transformer.ZoomOut|
|Transformer.ZoomOutSlide|

代码：
    mBannerView..setPageTransformer(Transformer.ForegroundToBackground)
    
    
    
    
>### 其他配置
| 方法 | 功能 |
|----------|:-------------:|
| setBottomBackgroundColor() | 下面横幅的背景色 | 
| setDelayTime() | 滚动延迟时间 |
| setDotIndicatorDistance() | 圆点的间距 |
| setDotIndicatorNormalDrawable() | 圆点没选中drawable |
| setDotIndicatorFocusDrawable() | 圆点被选中drawable |
| setDotIndicatorSize() | 指示器圆点大小 |
| setIndicatorGravity() | 指示器位置 |
| setTitleColor() | 标题颜色 |
| setTitleTextSize() | 标题字体大小 |

>
作者：skcodestack
链接：https://github.com/skcodestack
來源：gitbug
简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
>



