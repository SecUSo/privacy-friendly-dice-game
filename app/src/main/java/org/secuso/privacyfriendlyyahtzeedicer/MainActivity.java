package org.secuso.privacyfriendlyyahtzeedicer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.secuso.privacyfriendlyyahtzeedicer.dice.DiceFive;
import org.secuso.privacyfriendlyyahtzeedicer.dice.DiceFour;
import org.secuso.privacyfriendlyyahtzeedicer.dice.DiceOne;
import org.secuso.privacyfriendlyyahtzeedicer.dice.DiceSix;
import org.secuso.privacyfriendlyyahtzeedicer.dice.DiceThree;
import org.secuso.privacyfriendlyyahtzeedicer.dice.DiceTwo;

public class MainActivity extends AppCompatActivity {

    private Button[] dice;
    private int[] oldResults;
    private float dotWidth;
    private boolean[] isLocked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ActionBar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#024265")));

        Display display = this.getWindowManager().getDefaultDisplay();

        dotWidth = display.getWidth() / 40;

        dice = new Button[5];
        dice[0] = (Button) findViewById(R.id.button_dice_one);
        dice[1] = (Button) findViewById(R.id.button_dice_two);
        dice[2] = (Button) findViewById(R.id.button_dice_three);
        dice[3] = (Button) findViewById(R.id.button_dice_four);
        dice[4] = (Button) findViewById(R.id.button_dice_five);

        for (int i = 0; i < dice.length; i++) {
            dice[i].setWidth(Math.round(display.getWidth() / 4));
            dice[i].setHeight(Math.round(display.getWidth() / 4));
        }

        isLocked = new boolean[5];
        for (int k = 0; k < isLocked.length; k++) {
            isLocked[k] = false;
        }

        setDice(new int[]{1, 2, 3, 4, 5});
        oldResults = new int[]{1, 2, 3, 4, 5};

        Button rollDiceButton = (Button) findViewById(R.id.button);

        final RelativeLayout diceContainer = (RelativeLayout) findViewById(R.id.dice_frame);
        final RelativeLayout diceRowTwo = (RelativeLayout) findViewById(R.id.dice_frame_second);

        rollDiceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (diceContainer.getChildCount() > 0) diceContainer.removeAllViews();
                if (diceRowTwo.getChildCount() > 0) diceRowTwo.removeAllViews();
                setDice(rollDice(5));
                flashResult();
            }
        });

        for (int j = 0; j < dice.length; j++) {
            final int finalJ = j;
            dice[j].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    isLocked[finalJ] = !isLocked[finalJ];
                    updateView();
                }
            });
        }
    }

    public void updateView() {
        for (int i = 0; i < dice.length; i++) {
            if (isLocked[i])
                dice[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.dice_locked));
        }

        for (int i = 0; i < dice.length; i++) {
            if (!isLocked[i])
                dice[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.dice));
        }
    }

    public void setDice(int[] results) {

        RelativeLayout diceContainer = (RelativeLayout) findViewById(R.id.dice_frame);
        RelativeLayout diceRowTwo = (RelativeLayout) findViewById(R.id.dice_frame_second);

        for (int j = 0; j < results.length; j++) {
            System.out.println("Result " + results[j]);
        }

        for (int i = 0; i < 3; i++) {
            diceContainer.addView(displayResults(results[i], dice[i]));
        }

        for (int k = 3; k < dice.length; k++) {
            diceRowTwo.addView(displayResults(results[k], dice[k]));
        }

    }

    public View displayResults(int result, Button button) {

        View resultView = new View(this);

        switch (result) {
            case 1:
                resultView = new DiceOne(this, button, dotWidth);
                break;
            case 2:
                resultView = new DiceTwo(this, button, dotWidth);
                break;
            case 3:
                resultView = new DiceThree(this, button, dotWidth);
                break;
            case 4:
                resultView = new DiceFour(this, button, dotWidth);
                break;
            case 5:
                resultView = new DiceFive(this, button, dotWidth);
                break;
            case 6:
                resultView = new DiceSix(this, button, dotWidth);
                break;
            default:
                break;
        }

        return resultView;
    }

    public int[] rollDice(int poolSize) {
        int[] dice = new int[poolSize];

        for (int i = 0; i < dice.length; i++) {
            if (isLocked[i]) {
                dice[i] = oldResults[i];
            } else {
                dice[i] = (int) (Math.random() * 6) + 1;
                oldResults[i] = dice[i];
            }
        }
        return dice;
    }

    public void flashResult() {

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        animation.setStartOffset(20);
        animation.setRepeatMode(Animation.REVERSE);

        for (int i=0;i<dice.length;i++) {
            dice[i].startAnimation(animation);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.action_about:
                intent.setClass(this, AboutActivity.class);
                startActivityForResult(intent, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
