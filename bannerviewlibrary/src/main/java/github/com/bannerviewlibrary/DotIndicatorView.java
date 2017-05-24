package github.com.bannerviewlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2017/5/24
 * Version  1.0
 * Description:
 */

public class DotIndicatorView extends View {


    private Drawable drawable;

    public DotIndicatorView(Context context) {
        super(context);
    }

    public DotIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DotIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        Bitmap bitmap = drawable2Bitmap(drawable);

        Bitmap circleBitmap= getCircleBitmap(bitmap);

        canvas.drawBitmap(circleBitmap,0,0,null);
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap bm = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas=new Canvas(bm);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);


        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,getMeasuredWidth()/2,paint);

        //取交集，且是下面的颜色
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,0,0,paint);

        return bm;
    }

    /**
     * drawble  转  bitmap
     * @param drawable
     * @return
     */
    private Bitmap drawable2Bitmap(Drawable drawable){
        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas=new Canvas(bitmap);
        drawable.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        invalidate();
    }
}
