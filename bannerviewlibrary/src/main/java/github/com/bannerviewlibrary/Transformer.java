package github.com.bannerviewlibrary;

import android.support.v4.view.ViewPager.PageTransformer;

import github.com.bannerviewlibrary.transformer.AccordionTransformer;
import github.com.bannerviewlibrary.transformer.BackgroundToForegroundTransformer;
import github.com.bannerviewlibrary.transformer.CubeInTransformer;
import github.com.bannerviewlibrary.transformer.CubeOutTransformer;
import github.com.bannerviewlibrary.transformer.DefaultTransformer;
import github.com.bannerviewlibrary.transformer.DepthPageTransformer;
import github.com.bannerviewlibrary.transformer.FlipHorizontalTransformer;
import github.com.bannerviewlibrary.transformer.FlipVerticalTransformer;
import github.com.bannerviewlibrary.transformer.ForegroundToBackgroundTransformer;
import github.com.bannerviewlibrary.transformer.RotateDownTransformer;
import github.com.bannerviewlibrary.transformer.RotateUpTransformer;
import github.com.bannerviewlibrary.transformer.ScaleInOutTransformer;
import github.com.bannerviewlibrary.transformer.StackTransformer;
import github.com.bannerviewlibrary.transformer.TabletTransformer;
import github.com.bannerviewlibrary.transformer.ZoomInTransformer;
import github.com.bannerviewlibrary.transformer.ZoomOutSlideTransformer;
import github.com.bannerviewlibrary.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
