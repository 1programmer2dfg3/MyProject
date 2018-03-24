package com.example.y.lodingview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.y.lodingview.R;


/**
 * by dfg on 2016/5/24.
 */
public class LodingBarView extends View {

    //空心
    public static final int STROKE = 1;
    //实心
    public static final int FILL = 0;


    private Paint mPaint;
    private int style;

    //圆环的宽度
    private float circlesWidth;
    //圆环的颜色
    private int circlesColor;
    //进度字体的粗细程度
    private float textCrude;
    //字体颜色
    private int textColor;
    //字体大小
    private float textSize;
    //设置字体
    private Typeface mTypeface;
    //当前进度
    private int currentProgress;
    //进度颜色
    private int currentProgressColor;
    //进度圆环的宽度
    private float currentScheduleWidth;
    //是否显示百分比
    private boolean isPercent;
    private RectF rectF;


    public LodingBarView(Context context) {
        super(context);
    }

    public LodingBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LodingBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        mPaint = new Paint();
        rectF = new RectF();
        mPaint.setAntiAlias(true);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CirclesProgressBar);


        //初始化圆环变量
        circlesWidth = mTypedArray.getDimension(R.styleable.CirclesProgressBar_circlesWidth, LodingDefaults.CIRCLES_WIDTH);
        circlesColor = mTypedArray.getColor(R.styleable.CirclesProgressBar_circlesColor, LodingDefaults.CIRCLES_COLOR);
        textCrude = mTypedArray.getDimension(R.styleable.CirclesProgressBar_textCrude, LodingDefaults.TEXT_CRUDE);
        textColor = mTypedArray.getColor(R.styleable.CirclesProgressBar_textColor, LodingDefaults.TEXT_COLOR);
        textSize = mTypedArray.getDimension(R.styleable.CirclesProgressBar_textSize, LodingDefaults.TEXT_SIZE);
        currentProgress = mTypedArray.getInt(R.styleable.CirclesProgressBar_currentProgress, LodingDefaults.CURRENT_PROGRESS);
        currentProgressColor = mTypedArray.getColor(R.styleable.CirclesProgressBar_currentProgressColor, LodingDefaults.CURRENT_PROGRESS_COLOR);
        isPercent = mTypedArray.getBoolean(R.styleable.CirclesProgressBar_isPercent, LodingDefaults.IS_PERCENT);
        style = mTypedArray.getInt(R.styleable.CirclesProgressBar_style, LodingDefaults.STYLE);
        currentScheduleWidth = mTypedArray.getDimension(R.styleable.CirclesProgressBar_currentScheduleWidth, LodingDefaults.CURRENT_SCHEDULE_WIDTH);

        mTypedArray.recycle();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int anInt = getWidth() / 2;
        int circlesRadius = (int) (anInt - circlesWidth / 2);//半径


        //圆环
        mPaint.setColor(circlesColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(circlesWidth);
        canvas.drawCircle(anInt, anInt, circlesRadius, mPaint);

        //百分比
        if (isPercent && style == STROKE) {
            mPaint.setStrokeWidth(textCrude);
            mPaint.setColor(textColor);
            mPaint.setTextSize(textSize);
            mPaint.setTypeface(mTypeface);
            int percent = (int) (((float) currentProgress / (float) LodingDefaults.PROGRESS_BAR_MAX) * 100);
            float textWidth = mPaint.measureText(percent + "%");
            canvas.drawText(percent + "%", anInt - textWidth / 2, anInt + textSize / 2, mPaint);
        }


        //进度的圆环
        mPaint.setColor(currentProgressColor);
        mPaint.setStrokeWidth(currentScheduleWidth);
        rectF.set(anInt - circlesRadius, anInt - circlesRadius, anInt + circlesRadius, anInt + circlesRadius);
        //选择风格
        switch (style) {

            case STROKE:
                if (currentProgress != 0) {
                    mPaint.setStyle(Paint.Style.STROKE);
                    canvas.drawArc(rectF, 0, 360 * currentProgress / LodingDefaults.PROGRESS_BAR_MAX, false, mPaint);
                }
                break;
            case FILL:
                if (currentProgress != 0) {
                    mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                    canvas.drawArc(rectF, 0, 360 * currentProgress / LodingDefaults.PROGRESS_BAR_MAX, true, mPaint);
                }
                break;
        }
        Log.i("onDraw---->", "onDraw current");
    }


    public synchronized void setCurrentProgress(int currentProgress) {
        if (currentProgress < LodingDefaults.CURRENT_PROGRESS) {
            currentProgress = LodingDefaults.CURRENT_PROGRESS;
        }
        if (currentProgress > LodingDefaults.PROGRESS_BAR_MAX) {
            currentProgress = LodingDefaults.PROGRESS_BAR_MAX;
        }
        if (currentProgress <= LodingDefaults.PROGRESS_BAR_MAX) {
            this.currentProgress = currentProgress;
            postInvalidate();
        }
    }

    public synchronized float getCurrentProgress() {
        return currentProgress;
    }


    public void setScheduleWidth(float currentScheduleWidth) {
        this.currentScheduleWidth = currentScheduleWidth;
    }

    public float getScheduleWidth() {
        return currentScheduleWidth;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getStyle() {
        return style;
    }

    public void setPercent(boolean isPercent) {
        this.isPercent = isPercent;
    }

    public boolean getPercent() {
        return isPercent;
    }

    public void setCurrentProgressColor(int currentProgressColor) {
        if (currentProgressColor < LodingDefaults.MIN) {
            currentProgressColor = LodingDefaults.CURRENT_PROGRESS_COLOR;
        }
        this.currentProgressColor = currentProgressColor;
    }

    public int getCurrentProgressColor() {
        return currentProgressColor;
    }

    public void setTypeface(Typeface typeface) {
        this.mTypeface = typeface;
    }

    public void setCirclesWidth(float circlesWidth) {
        if (circlesWidth < LodingDefaults.MIN) {
            circlesWidth = LodingDefaults.CIRCLES_WIDTH;
        }
        this.circlesWidth = circlesWidth;
    }

    public float getCirclesWidth() {
        return circlesWidth;
    }

    public void setCirclesColor(int circlesColor) {
        if (circlesColor < LodingDefaults.MIN) {
            circlesColor = LodingDefaults.CIRCLES_COLOR;
        }
        this.circlesColor = circlesColor;
    }

    public int getCirclesColor() {
        return circlesColor;
    }

    public void setTextCrude(float textCrude) {
        if (textCrude < LodingDefaults.MIN) {
            textCrude = LodingDefaults.TEXT_CRUDE;
        }
        this.textCrude = textCrude;
    }

    public float getTextCrude() {
        return textCrude;
    }

    public void setTextColor(int textColor) {
        if (textColor < LodingDefaults.MIN) {
            textColor = LodingDefaults.TEXT_COLOR;
        }
        this.textColor = textColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextSize(float textSize) {
        if (textSize < LodingDefaults.MIN) {
            textSize = LodingDefaults.TEXT_SIZE;
        }
        this.textSize = textSize;
    }
}
