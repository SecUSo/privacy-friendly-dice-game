/*
 This file is part of Privacy Friendly Dice Game.

 Privacy Friendly PDice Game is free software:
 you can redistribute it and/or modify it under the terms of the
 GNU General Public License as published by the Free Software Foundation,
 either version 3 of the License, or any later version.

 Privacy Friendly Dice Game is distributed in the hope
 that it will be useful, but WITHOUT ANY WARRANTY; without even
 the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Privacy Friendly Dice Game. If not, see <http://www.gnu.org/licenses/>.
 */

package org.secuso.privacyfriendlydicegame.dice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * @author Karola Marky
 */

public class DiceSix extends View {

    Paint paint;
    View diceButton;
    float radius;

    public DiceSix(Context context, View diceButton, float dotWidth) {
        super(context);

        this.diceButton = diceButton;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        this.radius = dotWidth;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int firstX = diceButton.getLeft() + diceButton.getWidth()/3;
        int firstY = diceButton.getTop() + diceButton.getHeight()/4;

        int secondX = diceButton.getRight() - diceButton.getWidth()/3;
        int secondY = diceButton.getBottom() - diceButton.getHeight()/4;

        int thirdX = diceButton.getLeft() + diceButton.getWidth()/3;
        int thirdY = diceButton.getBottom() - diceButton.getHeight()/4;

        int fourthX = diceButton.getRight() - diceButton.getWidth()/3;
        int fourthY = diceButton.getTop() + diceButton.getHeight()/4;

        int fiveX = diceButton.getRight() - diceButton.getWidth()/3;
        int fiveY = diceButton.getTop() + diceButton.getHeight()/2;

        int sixX = diceButton.getLeft() + diceButton.getWidth()/3;
        int sixY = diceButton.getTop() + diceButton.getHeight()/2;

        canvas.drawCircle(firstX, firstY, radius, paint);
        canvas.drawCircle(secondX, secondY, radius, paint);
        canvas.drawCircle(thirdX, thirdY, radius, paint);
        canvas.drawCircle(fourthX, fourthY, radius, paint);
        canvas.drawCircle(fiveX, fiveY, radius, paint);
        canvas.drawCircle(sixX, sixY, radius, paint);
    }

}