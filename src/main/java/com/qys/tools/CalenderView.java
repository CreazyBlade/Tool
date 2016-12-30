package com.qys.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.tools.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Yishuai on 2016/7/20.
 */
public class CalenderView extends View{
    private DisplayMetrics mDisplayMetrics;
    private int mColumnSize,mRowSize;
    private int [][] daysString;
    private int mSelYear,mSelMonth,mSelDay;
    private int mDaySize = 18;
    private int weekRow;
    private static final int NUM_COLUMNS = 7;
    private static final int NUM_ROWS = 6;
    int weekTitleWidth;
    int weekTitleHeight;

    private DateClick dateClick;
    private String TAG=this.getClass().getName();
    int downX=0;
    int downY=0;
    private int weekTitleMargin=20;
    /**
     * 设置日期的点击回调事件
     * @author shiwei.deng
     *
     */
    public interface DateClick{
        public void onClickOnDate();
    }

    public CalenderView(Context context) {
        this(context, null);
    }

    public CalenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDisplayMetrics=getResources().getDisplayMetrics();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        setSelectYearMonth(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }

    public CalenderView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        int width=getWidth();
        int height=getHeight();

        //画星期几------------------------------------------------------------------------------
        Paint weekPaint=new Paint();
        weekPaint.setAntiAlias(true);
        weekPaint.setColor(Color.RED);

        String weekArry[]=getResources().getStringArray(R.array.week_ch);

        weekTitleWidth=width/7;
        weekTitleHeight=(int) (-(weekPaint.ascent() + weekPaint.descent()));

        for(int i=0;i<7;i++){
            String text=weekArry[i];
            int fontWidth = (int) weekPaint.measureText(text);
            int startX = weekTitleWidth * i + (weekTitleWidth - fontWidth)/2;
            int startY = weekTitleHeight+weekTitleMargin/2;
            canvas.drawText(text,startX,startY,weekPaint);
        }
        canvas.drawLine(0, weekTitleHeight+weekTitleMargin, width, weekTitleHeight+weekTitleMargin, weekPaint);
        //画日期---------------------------------------------------------------------------------
        Paint datePaint=new Paint();
        datePaint.setAntiAlias(true);
        mColumnSize = getWidth() / NUM_COLUMNS;
        mRowSize = (getHeight()-weekTitleHeight-weekTitleMargin) / NUM_ROWS;

        daysString = new int[6][7];
        datePaint.setTextSize(mDaySize*mDisplayMetrics.scaledDensity);
        String dayString;
        int mMonthDays = TimeUtil.getMonthDays(mSelYear, mSelMonth+1);
        int weekNumber = TimeUtil.getFirstDayWeek(mSelYear, mSelMonth);
        Log.d("DateView", "DateView:" + (mSelMonth+1)+"月1号星期" + weekNumber);
        for(int day = 0;day < mMonthDays;day++){
            dayString = (day + 1) + "";
            int column = (day+weekNumber - 1) % 7;
            int row = (day+weekNumber - 1) / 7;
            daysString[row][column]=day + 1;
            int startX = (int) (mColumnSize * column + (mColumnSize - datePaint.measureText(dayString))/2);
            int startY = (int) (mRowSize * row + mRowSize/4 - (datePaint.ascent() + datePaint.descent())/2+weekTitleHeight+weekTitleMargin);
            if(dayString.equals(mSelDay+"")){
                //绘制背景色矩形
                int startRecX = mColumnSize * column;
                int startRecY = mRowSize * row;
                int endRecX = startRecX + mColumnSize;
                int endRecY = startRecY + mRowSize;

                datePaint.setColor(Color.RED);
                canvas.drawRect(startRecX, startRecY+weekTitleHeight+weekTitleMargin, endRecX, endRecY+weekTitleHeight+weekTitleMargin, datePaint);
                datePaint.setColor(Color.BLACK);
                //记录第几行，即第几周
                weekRow = row + 1;

            }
            //绘制事务圆形标志
            drawCircle(row,column,day + 1,canvas);
//            if(dayString.equals(mSelDay+"")){
//                mPaint.setColor(mSelectDayColor);
//            }else if(dayString.equals(mCurrDay+"") && mCurrDay != mSelDay && mCurrMonth == mSelMonth){
//                //正常月，选中其他日期，则今日为红色
//                mPaint.setColor(mCurrentColor);
//            }else{
//                mPaint.setColor(mDayColor);
//            }

            Calendar calendar=Calendar.getInstance();
            calendar.set(mSelYear,mSelMonth,Integer.parseInt(dayString));
            Lunar lunar=new Lunar(calendar);
            String lunarStr=Lunar.getChinaDayString(lunar.getDay());

            canvas.drawText(dayString, startX, startY, datePaint);
            canvas.drawText(lunarStr, startX-datePaint.getTextSize()/2, startY+mRowSize/2, datePaint);
            if(column==6||day==mMonthDays-1){
                canvas.drawLine(0, mRowSize * row+ mRowSize+weekTitleHeight+weekTitleMargin, width, mRowSize * row+ mRowSize+weekTitleHeight+weekTitleMargin, datePaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventCode=  event.getAction();

        switch(eventCode){
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if(Math.abs(upX-downX) < 10 && Math.abs(upY - downY) < 10){//点击事件
                    performClick();
                    doClickAction((upX + downX)/2,(upY + downY)/2);
                }
                break;
        }
        return true;
    }

    private void drawCircle(int row,int column,int day,Canvas canvas){
//        if(daysHasThingList != null && daysHasThingList.size() >0){
//            if(!daysHasThingList.contains(day))return;
//            mPaint.setColor(mCircleColor);
//            float circleX = (float) (mColumnSize * column + mColumnSize*0.8);
//            float circley = (float) (mRowSize * row + mRowSize*0.2);
//            canvas.drawCircle(circleX, circley, mCircleRadius, mPaint);
//        }
        int mCircleRadius = 6;
        Paint mPaint=new Paint();
        mPaint.setColor(Color.BLUE);
        float circleX = (float) (mColumnSize * column + mColumnSize*0.8);
        float circley = (float) (mRowSize * row + mRowSize*0.2+weekTitleHeight+weekTitleMargin);
        canvas.drawCircle(circleX, circley, mCircleRadius, mPaint);
    }

    private void doClickAction(int x,int y){
        if(y<weekTitleHeight+weekTitleMargin){
            return;
        }
        int row = (y-weekTitleHeight-weekTitleMargin) / mRowSize;
        int column = x / mColumnSize;
        LogUtil.i(TAG,"CLICK: row:"+row+" Column:"+column);
        setSelectYearMonth(mSelYear,mSelMonth,daysString[row][column]);
        invalidate();
        LogUtil.i(TAG,"day:"+daysString[row][column]);
        //执行activity发送过来的点击处理事件
        if(dateClick != null){
            dateClick.onClickOnDate();
        }
    }

    /**
     * 设置年月
     * @param year
     * @param month
     */
    private void setSelectYearMonth(int year,int month,int day){
        mSelYear = year;
        mSelMonth = month;
        mSelDay = day;
    }

    /**
     * 设置日期点击事件
     * @param dateClick
     */
    public void setDateClick(DateClick dateClick) {
        this.dateClick = dateClick;
    }
}
