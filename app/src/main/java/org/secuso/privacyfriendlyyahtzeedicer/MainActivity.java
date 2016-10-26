package org.secuso.privacyfriendlyyahtzeedicer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.secuso.privacyfriendlyyahtzeedicer.dice.DiceFive;
import org.secuso.privacyfriendlyyahtzeedicer.dice.DiceFour;
import org.secuso.privacyfriendlyyahtzeedicer.dice.DiceOne;
import org.secuso.privacyfriendlyyahtzeedicer.dice.DiceSix;
import org.secuso.privacyfriendlyyahtzeedicer.dice.DiceThree;
import org.secuso.privacyfriendlyyahtzeedicer.dice.DiceTwo;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button[] dice;
    private int[] oldResults, backResults;
    private float dotWidth;
    private int diceSize;
    private boolean[] isLocked;
    private int roundCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Display display = getWindowManager().getDefaultDisplay();
        setSizes(display.getWidth() / 40, Math.round(display.getWidth() / 4));

        final TextView finalResult = (TextView) findViewById(R.id.resultTextView);

        dice = new Button[5];
        dice[0] = (Button) findViewById(R.id.button_dice_one);
        dice[1] = (Button) findViewById(R.id.button_dice_two);
        dice[2] = (Button) findViewById(R.id.button_dice_three);
        dice[3] = (Button) findViewById(R.id.button_dice_four);
        dice[4] = (Button) findViewById(R.id.button_dice_five);

        for (int i = 0; i < dice.length; i++) {
            dice[i].setWidth(Math.round(diceSize));
            dice[i].setHeight(Math.round(diceSize));
        }

        isLocked = new boolean[5];
        for (int k = 0; k < isLocked.length; k++) {
            isLocked[k] = false;
        }

        setDice(new int[]{1, 2, 3, 4, 5});
        oldResults = new int[]{1, 2, 3, 4, 5};
        backResults = new int[]{1, 2, 3, 4, 5};

        roundCounter = 0;

        final RelativeLayout diceContainer = (RelativeLayout) findViewById(R.id.dice_frame);
        final RelativeLayout diceRowTwo = (RelativeLayout) findViewById(R.id.dice_frame_second);

        //buttons
        final Button rollDiceButton = (Button) findViewById(R.id.button);
        rollDiceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (allTrue(isLocked)) {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.all_locked_hint), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
                else {

                    if (diceContainer.getChildCount() > 0) diceContainer.removeAllViews();
                    if (diceRowTwo.getChildCount() > 0) diceRowTwo.removeAllViews();
                    setDice(rollDice(5));

                    if (roundCounter == 2) {
                        for (int k=0;k<dice.length;k++) {
                            dice[k].setBackground(getResources().getDrawable(R.drawable.dice_final));
                        }
                        finalResult.setVisibility(View.VISIBLE);
                    }

                    if (roundCounter == 3) {
                        resetInterface();
                        finalResult.setVisibility(View.INVISIBLE);

                    } else {
                        roundCounter++;
                    }
                    TextView roundCounterTextView = (TextView) findViewById(R.id.roundTextView);
                    roundCounterTextView.setText(Integer.toString(roundCounter));
                    flashResult();
                }
            }
        });

        Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (roundCounter != 0) {
                    resetInterface();
                    finalResult.setVisibility(View.INVISIBLE);
                }

            }
        });

        for (int j = 0; j < dice.length; j++) {
            final int finalJ = j;
            dice[j].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (roundCounter == 0) {
                        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.locked_hint), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    } else if (roundCounter != 3) {
                        setLock(finalJ, v);
                    }
                }
            });
        }

    }

    public void resetInterface() {
        roundCounter = 0;
        TextView roundCounterTextView = (TextView) findViewById(R.id.roundTextView);
        roundCounterTextView.setText(Integer.toString(roundCounter));

        for (int k = 0; k < isLocked.length; k++) {
            isLocked[k] = false;
        }

        for (int i = 0; i < dice.length; i++) {
            dice[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.dice));
        }

        RelativeLayout diceContainer = (RelativeLayout) findViewById(R.id.dice_frame);
        RelativeLayout diceRowTwo = (RelativeLayout) findViewById(R.id.dice_frame_second);

        if (diceContainer.getChildCount() > 0) diceContainer.removeAllViews();
        if (diceRowTwo.getChildCount() > 0) diceRowTwo.removeAllViews();

        setDice(new int[]{1, 2, 3, 4, 5});
        oldResults = new int[]{1, 2, 3, 4, 5};
        backResults = new int[]{1, 2, 3, 4, 5};

    }

    public void setLock(int finalJ, View button) {
        boolean locked = isLocked[finalJ];
        if (!locked) {
            button.setBackgroundDrawable(getResources().getDrawable(R.drawable.dice_locked));
        } else {
            button.setBackgroundDrawable(getResources().getDrawable(R.drawable.dice));
        }
        isLocked[finalJ] = !isLocked[finalJ];
    }

    public void setDice(int[] results) {

        RelativeLayout diceContainer = (RelativeLayout) findViewById(R.id.dice_frame);
        RelativeLayout diceRowTwo = (RelativeLayout) findViewById(R.id.dice_frame_second);

        for (int i = 0; i < 3; i++) {
            diceContainer.addView(displayResults(results[i], dice[i]));
        }

        for (int k = 3; k < dice.length; k++) {
            diceRowTwo.addView(displayResults(results[k], dice[k]));
        }

    }

    public View displayResults(int result, Button button) {

        View resultView = new View(getApplicationContext());

        switch (result) {
            case 1:
                resultView = new DiceOne(getApplicationContext(), button, dotWidth);
                break;
            case 2:
                resultView = new DiceTwo(getApplicationContext(), button, dotWidth);
                break;
            case 3:
                resultView = new DiceThree(getApplicationContext(), button, dotWidth);
                break;
            case 4:
                resultView = new DiceFour(getApplicationContext(), button, dotWidth);
                break;
            case 5:
                resultView = new DiceFive(getApplicationContext(), button, dotWidth);
                break;
            case 6:
                resultView = new DiceSix(getApplicationContext(), button, dotWidth);
                break;
            default:
                break;
        }

        return resultView;
    }

    public int[] rollDice(int poolSize) {

        for (int j = 0; j < 5; j++) {
            backResults[j] = oldResults[j];
        }

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

        TextView finalResult = (TextView) findViewById(R.id.resultTextView);

        for (int i = 0; i < dice.length; i++) {
            if (!isLocked[i])
                dice[i].startAnimation(animation);
        }

        if (roundCounter == 3) {
            finalResult.startAnimation(animation);
        }

    }

    public static boolean allTrue(boolean[] values) {
        for (boolean value : values) {
            if (!value)
                return false;
        }
        return true;
    }

    public void setSizes(float dotWidth, int diceSize) {
        this.dotWidth = dotWidth;
        this.diceSize = diceSize;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;

        switch (item.getItemId()) {
            case R.id.nav_about:
                intent = new Intent(this, AboutActivity.class);
                startActivityForResult(intent, 0);
                return true;

            case R.id.nav_help:
                intent = new Intent(this, HelpActivity.class);
                startActivityForResult(intent, 0);
                return true;
            default:
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
