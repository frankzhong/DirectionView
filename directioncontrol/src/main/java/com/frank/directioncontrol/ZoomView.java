package com.frank.directioncontrol;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by zhongchao on 2017/2/7.
 */

public class ZoomView extends View {

    private Paint mPaint;
    private int action = -10;
    private OnClickListener onClickListener;
    private int normalColor = 0xFFAAAAAA;
    private int pressedColor = 0xFFFFFFFF;
    private int width, heigth;
    private boolean initbtn = false;
    private int currentBtn = -1;

    public ZoomView(Context context) {
        super(context);
    }

    public ZoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!initbtn) {
            init(canvas);
            initbtn = true;
        }
        initBGRect(canvas);

        if (currentBtn == -1) {
            drawNormal(canvas);
        } else if (currentBtn == 0) {
            drawLeftPressed(canvas);
        } else if (currentBtn == 1) {
            drawRigthPressed(canvas);
        }

    }

    private void init(Canvas canvas) {
        width = canvas.getWidth();
        heigth = canvas.getHeight();
        mPaint = new Paint();
        drawNormal(canvas);
    }

    private void initBGRect(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(120);
        canvas.drawRoundRect(new RectF(0, 0, width, heigth), 5, 5, mPaint);
        mPaint.setColor(normalColor);
        mPaint.setTextSize(60);
        canvas.drawLine(width/2, 0, width/2, heigth, mPaint);
    }

    private void drawNormal(Canvas canvas) {
        canvas.drawText("+", (float)(width*0.2), (float)(heigth*0.7), mPaint);
        canvas.drawText("-", (float)(width*0.7), (float)(heigth*0.7), mPaint);
    }

    private void drawLeftPressed(Canvas canvas) {
        mPaint.setColor(pressedColor);
        canvas.drawText("+", (float)(width*0.2), (float)(heigth*0.7), mPaint);
        mPaint.setColor(normalColor);
        canvas.drawText("-", (float)(width*0.7), (float)(heigth*0.7), mPaint);
    }

    private void drawRigthPressed(Canvas canvas) {
        mPaint.setColor(normalColor);
        canvas.drawText("+", (float)(width*0.2), (float)(heigth*0.7), mPaint);
        mPaint.setColor(pressedColor);
        canvas.drawText("-", (float)(width*0.7), (float)(heigth*0.7), mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN){
            float x = event.getX();
            float y = event.getY();
            if (x > 0 && x < width/2 && y > 0 && y < heigth) {
                onClickListener.OnClick(0);
                currentBtn = 0;
            } else if (x > width/2 && x < width && y > 0 && y < heigth){
                onClickListener.OnClick(1);
                currentBtn = 1;
            }
            invalidate();
            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            currentBtn = -1;
            invalidate();
            return true;
        }

        return false;
    }

    public void setOnClickListener(OnClickListener listener) {
        onClickListener = listener;
    }

    public interface OnClickListener {
        void OnClick(int zoom);
    }
}
