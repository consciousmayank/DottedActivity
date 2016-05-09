package com.mayank.dottedactivity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mayank Joshi on 9/5/16.
 */
public class CustomDottedProgressBar extends View {


    private float   mInActiveDotsRadius,                //Radius of active dot
                    mActiveDotsRadius;                  //Radius of in active dot

    private Paint mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);    //For filling color in dots
    private Handler mHandler = new Handler();                       //Handler to jump tp next dot
    private int mIndex = -1;                                        //Starting index for loop
    private int mViewWidth,                                         //Custom View's Width
                mViewHeight;                                        //Custom View's Height
    private int margin = 20;                                        //Margin for all dots
    private int mDotCount;                                          //Number of dots to be shown
    private boolean repeatInfiniteTimes;

    public CustomDottedProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DottedProgressBar,
                0, 0);

        try {
            mDotCount = a.getInteger(R.styleable.DottedProgressBar_dotsCount, 3);
            repeatInfiniteTimes = a.getBoolean(R.styleable.DottedProgressBar_infinite, false);
            mInActiveDotsRadius = 30;
            mActiveDotsRadius = mInActiveDotsRadius + 5;
        } finally {
            a.recycle();
        }




        init();

    }

    private void init() {
        mPaintFill.setStyle(Paint.Style.FILL);              // dot fill color
        mPaintFill.setColor(Color.BLUE);                   // dot background color
    }

    public void startForward() {
        mIndex = -1;
        mHandler.removeCallbacks(mStartSingleRunnable);
        if (repeatInfiniteTimes){
            mHandler.post(mStartInfiniteRunnable);
        }else{
            mHandler.post(mStartSingleRunnable);
        }
    }


    public void stop() {
        mHandler.removeCallbacks(mStartSingleRunnable);
    }

    private Runnable mStartSingleRunnable = new Runnable() {

        @Override
        public void run() {
            int step = 1;
            mIndex += step;
            invalidate();
            mHandler.postDelayed(mStartSingleRunnable, 300);
        }
    };

    private Runnable mStartInfiniteRunnable = new Runnable() {

        @Override
        public void run() {
            int step = 1;
            mIndex += step;
            if (mIndex < 0) {
                mIndex = 1;
                step = 1;
            } else if (mIndex > (mDotCount - 1)) {
                mIndex = 0;
                step = 1;
            }
            invalidate();
            mHandler.postDelayed(mStartInfiniteRunnable, 300);
        }
    };


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mViewHeight = (int) mInActiveDotsRadius * 2 + getPaddingBottom() + getPaddingTop();
        setMeasuredDimension(mViewWidth, mViewHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float xCoordinate = (mViewWidth - mDotCount * mInActiveDotsRadius * 2 - (mDotCount - 1) * margin) / 2.0f;
        float yCoordinate = mViewHeight / 2;
        for (int i = 0; i < mDotCount; i++) {
            if (i == mIndex) {
                canvas.drawCircle(xCoordinate, yCoordinate, mActiveDotsRadius, mPaintFill);
            } else {
                canvas.drawCircle(xCoordinate, yCoordinate, mInActiveDotsRadius, mPaintFill);
            }

            xCoordinate += (2 * mInActiveDotsRadius + margin);
        }

    }
}
