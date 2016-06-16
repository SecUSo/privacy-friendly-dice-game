package org.secuso.privacyfriendlyyahtzeedicer.dice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by yonjuni on 18.12.15.
 */
public class DiceFour extends View {

    Paint paint;
    View diceButton;
    float radius;

    public DiceFour(Context context, View diceButton, float dotWidth) {
        super(context);

        this.diceButton = diceButton;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        this.radius = dotWidth;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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
    }

}