package com.qys.tools;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tools.R;


/**
 * Created by Yishuai on 2016/12/29.
 * 评分ratingbar
 */
public class EvaluateRatingBar extends LinearLayout implements View.OnClickListener {

    private final String TAG=this.getClass().getName();
    private int star_num;
    private float star_gap;
    private float star_selected;
    private boolean isIndicator;
    public EvaluateRatingBar(Context context) {
        super(context);
    }

    public EvaluateRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setGravity(Gravity.CENTER_VERTICAL);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.EvaluateRatingBar, 0, 0);

        star_num = typedArray.getInt(R.styleable.EvaluateRatingBar_starNum, 5);
        star_gap = typedArray.getDimension(R.styleable.EvaluateRatingBar_starGap, DimenUtils.dip2px(context,10));
        star_selected = typedArray.getFloat(R.styleable.EvaluateRatingBar_starSelected, 0);
        isIndicator = typedArray.getBoolean(R.styleable.EvaluateRatingBar_isIndicator, false);
        typedArray.recycle();
        LogUtil.d(TAG, "star_num: " + star_num + " star_gap: " + star_gap + " star_selected: " + star_selected);
        initView();
    }

    private void initView() {
        this.removeAllViews();
        for(int i=0;i<star_num;i++){
            ImageView imageView=new ImageView(getContext());
            if(star_selected>=i+1) {
                imageView.setImageResource(R.drawable.star_full);
            }else if(star_selected>=i+0.5){
                imageView.setImageResource(R.drawable.star_half);
            }else {
                imageView.setImageResource(R.drawable.star_empty);
            }
            this.addView(imageView);
            if(i!=star_num-1){
                LayoutParams layoutParams= (LayoutParams) imageView.getLayoutParams();
                layoutParams.rightMargin= (int) star_gap;
                imageView.setLayoutParams(layoutParams);
            }
            if(!isIndicator){
                imageView.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int index=this.indexOfChild(v);
        star_selected=index+1;
        initView();
    }

    public float getRating(){
        return star_selected;
    }
}
