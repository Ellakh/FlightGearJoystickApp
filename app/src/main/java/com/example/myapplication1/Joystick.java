package com.example.myapplication1;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;

public class Joystick extends View {
    public IOnChange onChange;
    public Joystick(Context context) {
        super(context);
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        onChange.move((event.getX()-540)/540, (event.getY()-1680)/540);
        findViewById(R.id.joystickButton).setX(event.getX());
        findViewById(R.id.joystickButton).setY(event.getY());
        return super.onDragEvent(event);
    }

/*    @Override
    protected void onDraw(Canvas canvas) {
        Paint circle = new Paint();
        circle.setColor(Color.GREEN);
        circle.setAntiAlias(true);
        canvas.drawCircle(540, 1650, 40, circle);
    }*/

}
