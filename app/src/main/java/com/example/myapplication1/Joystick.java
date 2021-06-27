package com.example.myapplication1;

// importing the required libraries
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.NonNull;


public class Joystick extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    public IOnChange onChange;
    private float XPosition;
    private float YPosition;
    private float totalRadius;
    private float joystickRadius;

    public Joystick(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof IOnChange) {
            this.onChange = (IOnChange) context;
        }
    }

    public Joystick(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof IOnChange) {
            this.onChange = (IOnChange) context;
        }
    }

    public Joystick(Context context, AttributeSet attributeSet, int style) {
        super(context, attributeSet, style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof IOnChange) {
            this.onChange = (IOnChange) context;
        }
    }

    private void draw(float x, float y) {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = this.getHolder().lockCanvas();
            canvas.drawColor(Color.WHITE);
            Paint paint = new Paint();
            paint.setARGB(20, 80, 80, 80);
            canvas.drawCircle(this.XPosition, this.YPosition, this.totalRadius, paint);
            paint.setARGB(255, 102, 0, 155);
            canvas.drawCircle(x, y, this.joystickRadius, paint);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        this.totalRadius = (float)(getWidth() * 0.3);
        this.joystickRadius = (float)(getWidth() * 0.15);
        this.XPosition = (float)(getWidth() * 0.5);
        this.YPosition = (float)(getHeight() * 0.5);
        this.draw(this.XPosition, this.YPosition);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {}

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.equals(this) && this.onChange != null) {
            if (event.getAction() != MotionEvent.ACTION_UP) {
                float newRadius = (float) Math.sqrt(Math.pow(event.getX() - this.XPosition, 2) +
                        Math.pow(event.getY() - this.YPosition, 2));
                if (newRadius < totalRadius) {
                    this.draw(event.getX(), event.getY());
                    double aileron = (event.getX() - this.XPosition) / this.totalRadius,
                            elevator = (this.YPosition - event.getY()) / this.totalRadius;
                    this.onChange.move(aileron, elevator);
                } else {
                    float x = this.XPosition + (event.getX() - this.XPosition) * (this.totalRadius / newRadius),
                            y = this.YPosition + (event.getY() - this.YPosition) * (this.totalRadius / newRadius);
                    this.draw(x, y);
                    double aileron = (x - this.XPosition) / this.totalRadius,
                            elevator = (this.YPosition - y) / this.totalRadius;
                    this.onChange.move(aileron, elevator);
                }
            } else {
                this.draw(this.XPosition, this.YPosition);
                this.onChange.move(0, 0);
            }
        }
        return true;
    }
}
