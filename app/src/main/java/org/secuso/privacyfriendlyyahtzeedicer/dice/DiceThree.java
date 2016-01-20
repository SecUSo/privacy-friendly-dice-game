package org.secuso.privacyfriendlyyahtzeedicer.dice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by yonjuni on 16.01.16.
 */
public class DiceThree extends View {

    Paint paint;
    View diceButton;
    float radius;

    public DiceThree(Context context, View diceButton, float dotWidth) {
        super(context);

        this.diceButton = diceButton;
        this.radius = dotWidth;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int buttonCenterX = diceButton.getLeft() + diceButton.getWidth()/2;
        int buttonCenterY = diceButton.getTop() + diceButton.getHeight()/2;

        int firstX = diceButton.getLeft() + diceButton.getWidth()/4;
        int firstY = diceButton.getTop() + diceButton.getHeight()/4;

        int secondX = diceButton.getRight() - diceButton.getWidth()/4;
        int secondY = diceButton.getBottom() - diceButton.getHeight()/4;

        canvas.drawCircle(firstX, firstY, radius, paint);
        canvas.drawCircle(secondX, secondY, radius, paint);
        canvas.drawCircle(buttonCenterX, buttonCenterY, radius, paint);
    }
}
