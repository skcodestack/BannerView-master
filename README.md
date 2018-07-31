# Android 图片轮播工具

现在市面上图片轮播工具还是很多的，也很优秀，我写这个工具的意义在于：
> 第一，对自己的知识做个汇总
> 第二，如果有特别的功能那么自己也能在现有库的基础上来进行修改和优化

### 功能介绍：
>#### 1.标题，指示器样式的切换
>#### 2.根据自己的需求对主页面自定义
>#### 3.自由切换动画效果(使用第三方动态库)
>#### 4.api链式调用，让代码更加清晰 

>### 效果图：


>### 使用

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


>### 样式
| 名称 | 描述 |
|----------|:-------------:|
| col 1 is | left-aligned | 
| col 2 is | centered |
| col 3 is | right-aligned |


作者：skcodestack
链接：https://github.com/skcodestack
來源：gitbug
简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。




