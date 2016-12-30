package com.qys.tools;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.example.tools.R;

/**
 * Created by Yishuai on 2016/9/24.
 *
 */

@TargetApi(Build.VERSION_CODES.M)
public class FitViewPager extends ViewPager implements View.OnScrollChangeListener {

    private String TAG = this.getClass().getName();
    private int width;//viewPage的宽度
    private int scrollX;
    private float maxHeight;

    public FitViewPager(Context context) {
        super(context);
    }

    public FitViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImageViewPager);
        initAttr(ta);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.setOnScrollChangeListener(this);
        }
        this.setOffscreenPageLimit(99);//加载所有的子view，这样getChildCount()获得的是所有子view的数量，
    }

    private void initAttr( TypedArray ta) {
        int n = ta.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = ta.getIndex(i);
            if(attr==R.styleable.ImageViewPager_maxHeight){
                maxHeight = ta.getDimension(R.styleable.ImageViewPager_maxHeight,500);
            }
        }
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(getChildCount()==0){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        width = this.getWidth();
        LogUtil.i(TAG, "width " + width + "");
        float height = 0;
        int indexLeft=0;
        int indexRight=0;
        if(width>0) {
            int relativeScrollX = scrollX % width;//在一页上面的X偏移量
            LogUtil.i(TAG, "relativeScrollX " + relativeScrollX + "");
            indexLeft=scrollX / width;//界面上左边那页的index
//            LogUtil.i(TAG, "ChildCount " + getChildCount() + "");
            LogUtil.i(TAG, "indexLeft " + indexLeft + "");
            View childLeft = getChildAt(indexLeft);
            childLeft.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            float heightLeft = childLeft.getMeasuredHeight();
            heightLeft=heightLeft>maxHeight?maxHeight:heightLeft;
            LogUtil.i(TAG, "heightLeft " + heightLeft);

            indexRight=indexLeft+1;
            LogUtil.i(TAG, "indexRight " + indexRight + "");
            if(indexRight<getChildCount()){
                View childRight = getChildAt(indexRight);
                childRight.measure(widthMeasureSpec,
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                float heightRight = childRight.getMeasuredHeight();
                heightRight=heightRight>maxHeight?maxHeight:heightRight;
                LogUtil.i(TAG, "heightRight " + heightRight);

                height = (int) (heightLeft + (heightRight - heightLeft) * ((float)relativeScrollX / width));
            }else {
                height=heightLeft;
            }
            LogUtil.i(TAG, "height " + height + "");
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) height,
                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        LogUtil.i(TAG, "scrollX " + scrollX + " scrollY " + scrollY + " oldScrollX " + oldScrollX + " oldScrollY " + oldScrollY);
        this.scrollX = scrollX;
        this.requestLayout();
    }
}
