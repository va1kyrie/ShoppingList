package hu.ait.android.helen.shoppinglist;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;


public class ShoppingList extends ActionBarActivity {

    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Timer runSplash = new Timer();

        TimerTask showSplash = new TimerTask() {
            @Override
            public void run() {
                finish();

                Intent i = new Intent(ShoppingList.this, MainActivity.class);
                startActivity(i);
            }//run
        };

        runSplash.schedule(showSplash, SPLASH_TIME_OUT);

    }//onCreate

}//Intro
