package github.com.bannerviewlibrary;

import android.view.View;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2017/5/24
 * Version  1.0
 * Description:
 */

public abstract class BannerAdapter {

    /**
     * 创建BannerViewPager 的view
     * @param position
     * @param convertView  复用View
     * @return
     */
    public abstract View getView(int position, View convertView);

    /**
     * 播放条数
     * @return
     */
    public abstract int getCount();

    /**
     * 广告位
     * @param position
     * @return
     */
    public String getBannerDesc(int position) {
        return "";
    }
}
