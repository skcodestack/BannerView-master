package github.com.bannerviewlibrary;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2017/5/24
 * Version  1.0
 * Description:
 */

public class BannerScroller extends Scroller {

    private int mBannerDuration=850;

    public void setBannerDuration(int mBannerDuration) {
        this.mBannerDuration = mBannerDuration;
    }

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {

        super.startScroll(startX, startY, dx, dy, mBannerDuration);
    }
}
