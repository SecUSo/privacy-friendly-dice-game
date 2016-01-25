package org.secuso.privacyfriendlyyahtzeedicer;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by yonjuni on 21.01.16.
 */
public class YahtzeeFragment extends Fragment {

    Activity activity;
    private Button[] dice;
    private int[] oldResults;
    private float dotWidth;
    private int diceSize;
    private boolean[] isLocked;
    private int roundCounter;
    View rootView;

    public YahtzeeFragment(float dotWidth, int diceSize) {
        super();
        this.dotWidth = dotWidth;
        this.diceSize = diceSize;

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_yahtzee, container, false);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.action_playing);
        container.removeAllViews();

        doFirstRun();

        dice = new Button[5];
        dice[0] = (Button) rootView.findViewById(R.id.button_dice_one);
        dice[1] = (Button) rootView.findViewById(R.id.button_dice_two);
        dice[2] = (Button) rootView.findViewById(R.id.button_dice_three);
        dice[3] = (Button) rootView.findViewById(R.id.button_dice_four);
        dice[4] = (Button) rootView.findViewById(R.id.button_dice_five);

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

        roundCounter = 0;

        final RelativeLayout diceContainer = (RelativeLayout) rootView.findViewById(R.id.dice_frame);
        final RelativeLayout diceRowTwo = (RelativeLayout) rootView.findViewById(R.id.dice_frame_second);

        final View finalRootView = rootView;

        //buttons
        Button rollDiceButton = (Button) rootView.findViewById(R.id.button);
        rollDiceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (allTrue(isLocked)) {
                    Toast toast = Toast.makeText(activity.getApplicationContext(), getString(R.string.all_locked_hint), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
                if (diceContainer.getChildCount() > 0) diceContainer.removeAllViews();
                if (diceRowTwo.getChildCount() > 0) diceRowTwo.removeAllViews();
                setDice(rollDice(5));
                if (roundCounter == 3) {
                    resetInterface();
                } else {
                    roundCounter++;
                }
                TextView roundCounterTextView = (TextView) finalRootView.findViewById(R.id.roundTextView);
                roundCounterTextView.setText(Integer.toString(roundCounter));
                flashResult();
            }
        });

        Button resetButton = (Button) rootView.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetInterface();
            }
        });

        for (int j = 0; j < dice.length; j++) {
            final int finalJ = j;
            dice[j].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setLock(finalJ, v);
                }
            });
        }
        return rootView;
    }

    public void setDiceSize(int diceSize) {
        this.diceSize = diceSize;
    }

    public void setDotWidth(float dotWidth){
        this.dotWidth = dotWidth;
    }

    public void resetInterface() {
        roundCounter = 0;
        TextView roundCounterTextView = (TextView) rootView.findViewById(R.id.roundTextView);
        roundCounterTextView.setText(Integer.toString(roundCounter));

        for (int k = 0; k < isLocked.length; k++) {
            isLocked[k] = false;
        }

        for (int i=0;i<dice.length;i++) {
            dice[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.dice));
        }


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

        RelativeLayout diceContainer = (RelativeLayout) rootView.findViewById(R.id.dice_frame);
        RelativeLayout diceRowTwo = (RelativeLayout) rootView.findViewById(R.id.dice_frame_second);

        /*for (int j = 0; j < results.length; j++) {
            System.out.println("Result " + results[j]);
        }*/

        for (int i = 0; i < 3; i++) {
            diceContainer.addView(displayResults(results[i], dice[i]));
        }

        for (int k = 3; k < dice.length; k++) {
            diceRowTwo.addView(displayResults(results[k], dice[k]));
        }

    }

    private void doFirstRun() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        sharedPreferences.edit().putString("firstShow", "").commit();
        SharedPreferences settings = activity.getSharedPreferences("firstShow", activity.getBaseContext().MODE_PRIVATE);
        if (settings.getBoolean("isFirstRun", true)) {
            ((MainActivity)getActivity()).tutorialDialogYahtzee();
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("isFirstRun", false);
            editor.commit();
        }
    }

    public View displayResults(int result, Button button) {

        View resultView = new View(activity.getApplicationContext());

        switch (result) {
            case 1:
                resultView = new DiceOne(activity.getApplicationContext(), button, dotWidth);
                break;
            case 2:
                resultView = new DiceTwo(activity.getApplicationContext(), button, dotWidth);
                break;
            case 3:
                resultView = new DiceThree(activity.getApplicationContext(), button, dotWidth);
                break;
            case 4:
                resultView = new DiceFour(activity.getApplicationContext(), button, dotWidth);
                break;
            case 5:
                resultView = new DiceFive(activity.getApplicationContext(), button, dotWidth);
                break;
            case 6:
                resultView = new DiceSix(activity.getApplicationContext(), button, dotWidth);
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
            if (!isLocked[i])
                dice[i].startAnimation(animation);
        }
    }


    public static boolean allTrue(boolean[] values) {
        for (boolean value : values) {
            if (!value)
                return false;
        }
        return true;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

}
