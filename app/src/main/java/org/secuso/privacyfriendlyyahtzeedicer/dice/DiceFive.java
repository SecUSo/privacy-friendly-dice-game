package org.secuso.privacyfriendlyyahtzeedicer.dice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by yonjuni on 16.01.16.
 */
public class DiceFive extends View {

    Paint paint;
    View diceButton;
    float radius;

    public DiceFive(Context context, View diceButton, float dotWidth) {
        super(context);

        this.diceButton = diceButton;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        this.radius = dotWidth;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int buttonCenterX = diceButton.getLeft() + diceButton.getWidth()/2;
        int buttonCenterY = diceButton.getTop() + diceButton.getHeight()/2;

        int firstX = diceButton.getLeft() + diceButton.getWidth()/4;
        int firstY = diceButton.getTop() + diceButton.getHeight()/4;

        int secondX = diceButton.getRight() - diceButton.getWidth()/4;
        int secondY = diceButton.getBottom() - diceButton.getHeight()/4;

        int thirdX = diceButton.getLeft() + diceButton.getWidth()/4;
        int thirdY = diceButton.getBottom() - diceButton.getHeight()/4;

        int fourthX = diceButton.getRight() - diceButton.getWidth()/4;
        int fourthY = diceButton.getTop() + diceButton.getHeight()/4;

        canvas.drawCircle(firstX, firstY, radius, paint);
        canvas.drawCircle(secondX, secondY, radius, paint);
        canvas.drawCircle(thirdX, thirdY, radius, paint);
        canvas.drawCircle(fourthX, fourthY, radius, paint);
        canvas.drawCircle(buttonCenterX, buttonCenterY, radius, paint);
    }


}

