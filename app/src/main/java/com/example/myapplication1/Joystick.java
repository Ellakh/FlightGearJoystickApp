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

// Joystick class - the movement logic of the joystick
public class Joystick extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    // declaring onChange field to call the given function on it, X/YPosition to hold the center coordinates of the
    // joystick, totalRadius to hold the radius of the background circle and joystickRadius to hold the radius of the
    // joystick itself
    public IOnChange onChange;
    private float XPosition;
    private float YPosition;
    private float totalRadius;
    private float joystickRadius;

    // Joystick - constructor of the Joystick class
    public Joystick(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof IOnChange) {
            this.onChange = (IOnChange) context;
        }
    }

    // Joystick - constructor of the Joystick class
    public Joystick(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof IOnChange) {
            this.onChange = (IOnChange) context;
        }
    }

    // Joystick - constructor of the Joystick class
    public Joystick(Context context, AttributeSet attributeSet, int style) {
        super(context, attributeSet, style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof IOnChange) {
            this.onChange = (IOnChange) context;
        }
    }

    // draw - draws the whole surface of the joystick based on the given location of the joystick
    private void draw(float x, float y) {
        if (getHolder().getSurface().isValid()) {
            // creating a canvas object and drawing on it the background circle and the joystick's circle
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

    // surfaceCreated - initializes the size and position of the joystick's circles
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        // initializing the size and position of the joystick's circles
        this.totalRadius = (float)(getWidth() * 0.3);
        this.joystickRadius = (float)(getWidth() * 0.15);
        this.XPosition = (float)(getWidth() * 0.5);
        this.YPosition = (float)(getHeight() * 0.5);
        // drawing the joystick
        this.draw(this.XPosition, this.YPosition);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {}

    // onTouch - moves the joystick according to the touch event
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.equals(this) && this.onChange != null) {
            if (event.getAction() != MotionEvent.ACTION_UP) {
                // calculating the distance between the touch position and the center of the joystick
                float newRadius = (float) Math.sqrt(Math.pow(event.getX() - this.XPosition, 2) +
                        Math.pow(event.getY() - this.YPosition, 2));
                // checking if the touch position is within the larger background circle
                if (newRadius < totalRadius) {
                    // drawing the joystick in the new position and calling the given move method on the onChange field
                    this.draw(event.getX(), event.getY());
                    double aileron = (event.getX() - this.XPosition) / this.totalRadius,
                            elevator = (this.YPosition - event.getY()) / this.totalRadius;
                    this.onChange.move(aileron, elevator);
                } else {
                    // drawing the joystick in perimeter of the background circle and calling the given move method on
                    // the onChange field
                    float x = this.XPosition + (event.getX() - this.XPosition) * (this.totalRadius / newRadius),
                            y = this.YPosition + (event.getY() - this.YPosition) * (this.totalRadius / newRadius);
                    this.draw(x, y);
                    double aileron = (x - this.XPosition) / this.totalRadius,
                            elevator = (this.YPosition - y) / this.totalRadius;
                    this.onChange.move(aileron, elevator);
                }
            } else {
                // drawing the joystick in the default position and calling the given move method on the onChange field
                this.draw(this.XPosition, this.YPosition);
                this.onChange.move(0, 0);
            }
        }
        return true;
    }
}
