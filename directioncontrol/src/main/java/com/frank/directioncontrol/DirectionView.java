package com.frank.directioncontrol;

import android.content.Context;
import android.graphics.Canvas;
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
    //左三角颜色
    private int leftPressedColor = 0xFFFF0000;
    private int leftNormalColor = 0xFFAA0000;

    //上三角
    private int upPressedColor = 0xFF00FF00;
    private int upNormalColor = 0xFF00AA00;

    private int rigthPressedColor = 0xFF0000FF;
    private int rigthNormalColor = 0xFF0000AA;

    private int downPressedColor = 0xFFFFFF00;
    private int downNormalColor = 0xFFAAAA00;

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
//        initBackgroundTriangle();
        initArrow();
    }

    private void initBackgroundTriangle() {
        pathLeft.moveTo(0, 0);
        pathLeft.lineTo(width/2, heigth/2);
        pathLeft.lineTo(0, heigth);
        pathLeft.close();

        pathUp.moveTo(0, 0);
        pathUp.lineTo(width/2, heigth/2);
        pathUp.lineTo(width, 0);
        pathUp.close();

        pathRigth.moveTo(width, 0);
        pathRigth.lineTo(width/2, heigth/2);
        pathRigth.lineTo(width, heigth);
        pathRigth.close();

        pathDown.moveTo(0, heigth);
        pathDown.lineTo(width/2, heigth/2);
        pathDown.lineTo(width, heigth);
        pathDown.close();
    }

    private void initArrow() {
        final int arrowWidth = width / 4;
        final int arrowHeigth = heigth / 4;

        //左箭头
        int arrowStartX = width / 16;
        int arrowStartY = heigth / 2;
        int arrowX = arrowStartX;
        int arrowY = arrowStartY;

        pathLeftArrow.moveTo(arrowX, arrowY);
        pathLeftArrow.lineTo(arrowX += arrowWidth / 2, arrowY -= arrowHeigth / 2);
        pathLeftArrow.lineTo(arrowX, arrowY += arrowHeigth);
        pathLeftArrow.close();

        arrowX = arrowStartX;
        arrowY = arrowStartY;
        float left = arrowX + arrowWidth / 2;
        float top = arrowY - arrowHeigth / 4;
        float rigth = arrowX + arrowWidth;
        float bottom = arrowY + arrowHeigth / 4;
        pathLeftArrow.addRect(left, top, rigth, bottom, Path.Direction.CCW);

        //右箭头
        Matrix matrix = new Matrix();
        matrix.setRotate(180, width / 2, heigth / 2);
        pathLeftArrow.transform(matrix, pathRigthArrow);

        //上箭头
        arrowStartX = width / 2;
        arrowStartY = heigth / 16;
        arrowX = arrowStartX;
        arrowY = arrowStartY;

        pathUpArrow.moveTo(arrowX, arrowY);
        pathUpArrow.lineTo(arrowX += arrowWidth / 2, arrowY += arrowHeigth / 2);
        pathUpArrow.lineTo(arrowX -= arrowWidth, arrowY);
        pathUpArrow.close();

        arrowX = arrowStartX;
        arrowY = arrowStartY;
        left = arrowX - arrowWidth / 4;
        top = arrowY + arrowHeigth / 2;
        rigth = arrowX + arrowWidth / 4;
        bottom = arrowY + arrowHeigth;
        pathUpArrow.addRect(left, top, rigth, bottom, Path.Direction.CCW);

        //下箭头
        pathUpArrow.transform(matrix, pathDownArrow);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!initPath) {
            init(canvas);
            initPath = true;
        }

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
            default:
                reset(canvas);
                break;
        }

    }

    private void drawLeftPressed(Canvas canvas) {
        drawPath(pathLeft, leftPressedColor, canvas);
        drawPath(pathLeftArrow, arrowPressedColor, canvas);
    }
    private void drawLeftNormal(Canvas canvas) {
        drawPath(pathLeft, leftNormalColor, canvas);
        drawPath(pathLeftArrow, arrowNormalColor, canvas);
    }

    private void drawUpPressed(Canvas canvas) {
        drawPath(pathUp, upPressedColor, canvas);
        drawPath(pathUpArrow, arrowPressedColor, canvas);
    }

    private void drawUpNormal(Canvas canvas) {
        drawPath(pathUp, upNormalColor, canvas);
        drawPath(pathUpArrow, arrowNormalColor, canvas);
    }

    private void drawRigthPressed(Canvas canvas) {
        drawPath(pathRigth, rigthPressedColor, canvas);
        drawPath(pathRigthArrow, arrowPressedColor, canvas);
    }

    private void drawRigthNormal(Canvas canvas) {
        drawPath(pathRigth, rigthNormalColor, canvas);
        drawPath(pathRigthArrow, arrowNormalColor, canvas);
    }

    private void drawDownPressed(Canvas canvas) {
        drawPath(pathDown, downPressedColor, canvas);
        drawPath(pathDownArrow, arrowPressedColor, canvas);
    }

    private void drawDownNormal(Canvas canvas) {
        drawPath(pathDown, downNormalColor, canvas);
        drawPath(pathDownArrow, arrowNormalColor, canvas);
    }

    private void drawWhenLeftPressed(Canvas canvas) {
        drawLeftPressed(canvas);
        drawUpNormal(canvas);
        drawRigthNormal(canvas);
        drawDownNormal(canvas);
    }

    private void drawWhenUpPressed(Canvas canvas) {
        drawLeftNormal(canvas);
        drawUpPressed(canvas);
        drawRigthNormal(canvas);
        drawDownNormal(canvas);
    }

    private void drawWhenRigthPressed(Canvas canvas) {
        drawLeftNormal(canvas);
        drawUpNormal(canvas);
        drawRigthPressed(canvas);
        drawDownNormal(canvas);
    }

    private void drawWhenDownPressed(Canvas canvas) {
        drawLeftNormal(canvas);
        drawUpNormal(canvas);
        drawRigthNormal(canvas);
        drawDownPressed(canvas);
    }

    private void reset(Canvas canvas) {
        drawLeftNormal(canvas);
        drawUpNormal(canvas);
        drawRigthNormal(canvas);
        drawDownNormal(canvas);
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
            getDirection(x, y);
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
        float relativeX = x / width;
        float relativeY = y / heigth;
        if (relativeY > relativeX ) {
            if (relativeY > (1 - relativeX)) {
                currentDirection = Direction.down;
            } else {
                currentDirection = Direction.left;
            }

        } else {
            if (relativeY > (1 - relativeX)) {
                currentDirection = Direction.rigth;
            } else {
                currentDirection = Direction.up;
            }
        }
    }

    public void setOnDirectionListener(OnDirectionListener listener) {
        onDirectionListener = listener;
    }

    public interface OnDirectionListener {
        void OnDirectionChange(Direction direction);
    }
}
