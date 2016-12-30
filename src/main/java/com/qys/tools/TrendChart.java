package com.qys.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.example.tools.R;


/**
 * Created by Yishuai on 2016/7/21.
 */
public class TrendChart extends View{

    private static final String TAG = "TrendChart";
    float temputers[]=null;

    public TrendChart(Context context) {
        this(context,null);
    }

    public TrendChart(Context context, AttributeSet attrs) {
        super(context, attrs);
//        temputers=new int[]{19,22,20,18};
    }

    public TrendChart(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

//        if(heightMode == MeasureSpec.AT_MOST){
//            heightSize = mDisplayMetrics.densityDpi * 30;
//        }
//        if(widthMode == MeasureSpec.AT_MOST){
//            widthSize = mDisplayMetrics.densityDpi * 300;
//        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(temputers==null)
            return;
        int width=getWidth();
        int height=getHeight();

        Paint pathPaint=new Paint();
        pathPaint.setAntiAlias(true);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setColor(getResources().getColor(R.color.split));

        Paint circlePaint=new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(getResources().getColor(R.color.split));

        float minTemputer=temputers[0];
        float maxTemputer=temputers[0];
        for(int i=0;i<temputers.length;i++){
            if(minTemputer>temputers[i])
                minTemputer=temputers[i];
            if(maxTemputer<temputers[i])
                maxTemputer=temputers[i];
        }

        //第一个点在x轴的偏移量
        float begin=width/(temputers.length)/2;
        //最大温差
        float diff=maxTemputer-minTemputer;
        int r= (int) getResources().getDimension(R.dimen.trend_radius);
        Path path = new Path();
        path.moveTo(0+r+begin,(height-2*r)/diff * (maxTemputer-temputers[0])+r);
        for(int i=0;i<temputers.length;i++) {
            path.lineTo((width-2*r)/(temputers.length)*i+r+begin,(height-2*r)/diff * (maxTemputer-temputers[i])+r);
            float cx=(width-2*r)/(temputers.length)*i+r+begin;
            float cy=(height-2*r)/diff * (maxTemputer-temputers[i])+r;
            canvas.drawCircle(cx,cy,r,circlePaint);
            LogUtil.i(TAG,"cx: "+cx+"  cy: "+cy);
        }
        canvas.drawPath(path,pathPaint);
    }

    public float[] getTemputers() {
        return temputers;
    }

    public void setTemputers(float[] temputers) {
        this.temputers = temputers;
        invalidate();
    }
}
