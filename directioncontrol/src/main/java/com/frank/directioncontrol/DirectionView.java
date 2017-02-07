package com.frank.directioncontrol;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static com.frank.directioncontrol.Direction.left;
import static com.frank.directioncontrol.Direction.none;

/**
 * Created by zhongchao on 2017/1/22.
 */

public class DirectionView extends View {
    private static final String TAG = "DirectionView";

    private int arrowPressedColor = 0xFFFFFFFF;
    private int arrowNormalColor = 0xFFAAAAAA;
    private Paint paint= new Paint();
    private Path pathLeft = new Path();
    private Path pathUp = new Path();
    private Path pathRigth = new Path();
    private Path pathDown = new Path();
    private boolean initPath = false;
    private int action = -10;
    private Direction currentDirection = none;
    private int width, heigth;
    private Path pathLeftArrow = new Path();
    private Path pathUpArrow = new Path();
    private Path pathRigthArrow = new Path();
    private Path pathDownArrow = new Path();
    private Path pathLeftUpArrow = new Path();
    private Path pathRigthUpArrow = new Path();
    private Path pathLeftDownArrow = new Path();
    private Path pathRigthDownArrow = new Path();
    private OnDirectionListener onDirectionListener = null;

    public DirectionView(Context context) {
        super(context);
    }

    public DirectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Canvas canvas) {
        width = canvas.getWidth();
        heigth = canvas.getHeight();

        // 让图形边界的锯齿模糊，看起来图形边界将更加光滑。
        paint.setAntiAlias(true);
        initArrow();
    }

    private void initBgCircle(Canvas canvas) {
        paint.setColor(Color.BLACK);
        canvas.drawCircle(width/2, heigth/2, width/2, paint);
    }


    private void initArrow() {
        final int arrowWidth = width / 4;
        final int arrowHeigth = heigth / 6;

        //左箭头
        int arrowStartX = width / 16;
        int arrowStartY = heigth / 2;
        int arrowX = arrowStartX;
        int arrowY = arrowStartY;

        pathLeftArrow.moveTo(arrowX, arrowY);
        pathLeftArrow.lineTo(arrowX += arrowWidth / 2, arrowY -= arrowHeigth / 2);
        pathLeftArrow.lineTo(arrowX -= arrowWidth / 4, arrowY += arrowHeigth / 2);
        pathLeftArrow.lineTo(arrowX += arrowWidth / 4, arrowY += arrowHeigth / 2);
        pathLeftArrow.close();

//        arrowX = arrowStartX;
//        arrowY = arrowStartY;
//        float left = arrowX + arrowWidth / 2;
//        float top = arrowY - arrowHeigth / 4;
//        float rigth = arrowX + arrowWidth;
//        float bottom = arrowY + arrowHeigth / 4;
//        pathLeftArrow.addRect(left, top, rigth, bottom, Path.Direction.CCW);

        //上箭头
        Matrix matrix = new Matrix();
        matrix.setRotate(90, width / 2, heigth / 2);
        pathLeftArrow.transform(matrix, pathUpArrow);

       //右箭头
        matrix.setRotate(180, width / 2, heigth / 2);
        pathLeftArrow.transform(matrix, pathRigthArrow);

        //下箭头
        matrix.setRotate(270, width / 2, heigth / 2);
        pathLeftArrow.transform(matrix, pathDownArrow);

        //左上箭头
        matrix.setRotate(45, width / 2, heigth / 2);
        pathLeftArrow.transform(matrix, pathLeftUpArrow);

        //右上箭头
        matrix.setRotate(135, width / 2, heigth / 2);
        pathLeftArrow.transform(matrix, pathRigthUpArrow);

        //左下箭头
        matrix.setRotate(315, width / 2, heigth / 2);
        pathLeftArrow.transform(matrix, pathLeftDownArrow);

        //右下箭头
        matrix.setRotate(225, width / 2, heigth / 2);
        pathLeftArrow.transform(matrix, pathRigthDownArrow);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!initPath) {
            init(canvas);
            initPath = true;
        }
        initBgCircle(canvas);

        switch(currentDirection) {
            case left:
                drawWhenLeftPressed(canvas);
                break;
            case up:
                drawWhenUpPressed(canvas);
                break;
            case rigth:
                drawWhenRigthPressed(canvas);
                break;
            case down:
                drawWhenDownPressed(canvas);
                break;
            case left_up:
                drawWhenLeftUpPressed(canvas);
                break;
            case rigth_up:
                drawWhenRigthUpPressed(canvas);
                break;
            case left_down:
                drawWhenLeftDownPressed(canvas);
                break;
            case rigth_down:
                drawWhenRigthDownPressed(canvas);
                break;
            default:
                reset(canvas);
                break;
        }

    }

    private void drawLeftPressed(Canvas canvas) {
        drawPath(pathLeftArrow, arrowPressedColor, canvas);
    }
    private void drawLeftNormal(Canvas canvas) {
        drawPath(pathLeftArrow, arrowNormalColor, canvas);
    }

    private void drawUpPressed(Canvas canvas) {
        drawPath(pathUpArrow, arrowPressedColor, canvas);
    }

    private void drawUpNormal(Canvas canvas) {
        drawPath(pathUpArrow, arrowNormalColor, canvas);
    }

    private void drawRigthPressed(Canvas canvas) {
        drawPath(pathRigthArrow, arrowPressedColor, canvas);
    }

    private void drawRigthNormal(Canvas canvas) {
        drawPath(pathRigthArrow, arrowNormalColor, canvas);
    }

    private void drawDownPressed(Canvas canvas) {
        drawPath(pathDownArrow, arrowPressedColor, canvas);
    }

    private void drawDownNormal(Canvas canvas) {
        drawPath(pathDownArrow, arrowNormalColor, canvas);
    }

    private void drawLeftUpPressed(Canvas canvas) {
        drawPath(pathLeftUpArrow, arrowPressedColor, canvas);
    }

    private void drawLeftUpNormal(Canvas canvas) {
        drawPath(pathLeftUpArrow, arrowNormalColor, canvas);
    }

    private void drawRigthUpPressed(Canvas canvas) {
        drawPath(pathRigthUpArrow, arrowPressedColor, canvas);
    }

    private void drawRigthUpNormal(Canvas canvas) {
        drawPath(pathRigthUpArrow, arrowNormalColor, canvas);
    }

    private void drawLeftDownPressed(Canvas canvas) {
        drawPath(pathLeftDownArrow, arrowPressedColor, canvas);
    }

    private void drawLeftDownNormal(Canvas canvas) {
        drawPath(pathLeftDownArrow, arrowNormalColor, canvas);
    }

    private void drawRigthDownPressed(Canvas canvas) {
        drawPath(pathRigthDownArrow, arrowPressedColor, canvas);
    }

    private void drawRigthDownNormal(Canvas canvas) {
        drawPath(pathRigthDownArrow, arrowNormalColor, canvas);
    }

    private void drawWhenLeftPressed(Canvas canvas) {
        drawLeftPressed(canvas);
        drawUpNormal(canvas);
        drawRigthNormal(canvas);
        drawDownNormal(canvas);
        drawLeftUpNormal(canvas);
        drawRigthUpNormal(canvas);
        drawLeftDownNormal(canvas);
        drawRigthDownNormal(canvas);
    }

    private void drawWhenUpPressed(Canvas canvas) {
        drawLeftNormal(canvas);
        drawUpPressed(canvas);
        drawRigthNormal(canvas);
        drawDownNormal(canvas);
        drawLeftUpNormal(canvas);
        drawRigthUpNormal(canvas);
        drawLeftDownNormal(canvas);
        drawRigthDownNormal(canvas);
    }

    private void drawWhenRigthPressed(Canvas canvas) {
        drawLeftNormal(canvas);
        drawUpNormal(canvas);
        drawRigthPressed(canvas);
        drawDownNormal(canvas);
        drawLeftUpNormal(canvas);
        drawRigthUpNormal(canvas);
        drawLeftDownNormal(canvas);
        drawRigthDownNormal(canvas);
    }

    private void drawWhenDownPressed(Canvas canvas) {
        drawLeftNormal(canvas);
        drawUpNormal(canvas);
        drawRigthNormal(canvas);
        drawDownPressed(canvas);
        drawLeftUpNormal(canvas);
        drawRigthUpNormal(canvas);
        drawLeftDownNormal(canvas);
        drawRigthDownNormal(canvas);
    }

    private void drawWhenLeftUpPressed(Canvas canvas) {
        drawLeftNormal(canvas);
        drawUpNormal(canvas);
        drawRigthNormal(canvas);
        drawDownNormal(canvas);
        drawLeftUpPressed(canvas);
        drawRigthUpNormal(canvas);
        drawLeftDownNormal(canvas);
        drawRigthDownNormal(canvas);
    }

    private void drawWhenRigthUpPressed(Canvas canvas) {
        drawLeftNormal(canvas);
        drawUpNormal(canvas);
        drawRigthNormal(canvas);
        drawDownNormal(canvas);
        drawLeftUpNormal(canvas);
        drawRigthUpPressed(canvas);
        drawLeftDownNormal(canvas);
        drawRigthDownNormal(canvas);
    }

    private void drawWhenLeftDownPressed(Canvas canvas) {
        drawLeftNormal(canvas);
        drawUpNormal(canvas);
        drawRigthNormal(canvas);
        drawDownNormal(canvas);
        drawLeftUpNormal(canvas);
        drawRigthUpNormal(canvas);
        drawLeftDownPressed(canvas);
        drawRigthDownNormal(canvas);
    }

    private void drawWhenRigthDownPressed(Canvas canvas) {
        drawLeftNormal(canvas);
        drawUpNormal(canvas);
        drawRigthNormal(canvas);
        drawDownNormal(canvas);
        drawLeftUpNormal(canvas);
        drawRigthUpNormal(canvas);
        drawLeftDownNormal(canvas);
        drawRigthDownPressed(canvas);
    }

    private void reset(Canvas canvas) {
        drawLeftNormal(canvas);
        drawUpNormal(canvas);
        drawRigthNormal(canvas);
        drawDownNormal(canvas);
        drawLeftUpNormal(canvas);
        drawRigthUpNormal(canvas);
        drawLeftDownNormal(canvas);
        drawRigthDownNormal(canvas);
    }

    private void drawPath(Path path, int color, Canvas canvas) {
        paint.setColor(color);
        canvas.drawPath(path, paint);
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        action = event.getAction();
        if (action == event.ACTION_DOWN ) {
            float x = event.getX();
            float y = event.getY();
            System.out.println("width:"+width+"--heigth:"+heigth);
            System.out.println("x:"+x+"--y:"+y);
            float relativeX = (x - width/2) / width;
            float relativeY = (-y + width/2) / heigth;
            System.out.println("relativeX:"+relativeX+"--relativeY:"+relativeY);
            getDirection(relativeX, relativeY);
            if (onDirectionListener != null) {
                Log.e(TAG, "onTouchEvent: " + currentDirection);
                onDirectionListener.OnDirectionChange(currentDirection);
            }
            invalidate();
            return true;
        } else if (action == event.ACTION_UP ) {
            currentDirection = Direction.none;
            invalidate();
            return true;
        }
        return false;
    }

    private void getDirection(float x, float y) {
//        double mod = Math.sqrt(x*x + y*y);
        double arg = Math.round(Math.atan2(y, x) / Math.PI * 180);
        Log.e(TAG, "getDirection: " + arg);
        if ( (arg > -180 && arg < -157.5) ||  (arg > 157.5 && arg < 180)) {
            currentDirection = Direction.left;
        } else if (arg > 112.5 && arg < 157.5) {
            currentDirection = Direction.left_up;
        } else if (arg > 67.5 && arg < 112.5) {
            currentDirection = Direction.up;
        }else if (arg > 22.5 && arg < 67.5) {
            currentDirection = Direction.rigth_up;
        }else if (arg > -22.5 && arg < 22.5) {
            currentDirection = Direction.rigth;
        }else if (arg > -67.5 && arg < -22.5) {
            currentDirection = Direction.rigth_down;
        }else if (arg > -112.5 && arg < -67.5) {
            currentDirection = Direction.down;
        }else if (arg > -157.5 && arg < -112.5) {
            currentDirection = Direction.left_down;
        } else {
            currentDirection = Direction.none;
        }

    }

    public void setOnDirectionListener(OnDirectionListener listener) {
        onDirectionListener = listener;
    }

    public interface OnDirectionListener {
        void OnDirectionChange(Direction direction);
    }
}
