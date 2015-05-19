package com.zerokstudios.socratesman;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * main activity for the game
 */
public class MainActivity extends ActionBarActivity {
    public static final GameController GAME_CONTROLLER = new GameController(); //have a single game instance
    private Panel panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        Vector screenDimensions = new Vector(metrics.widthPixels, metrics.heightPixels);
        GAME_CONTROLLER.setActivity(this);
        panel = (Panel) findViewById(R.id.mainPanel);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GAME_CONTROLLER.pause();
        panel.pause();
        boolean retry = true;
        while (retry) {
            try {
                GAME_CONTROLLER.join();
                panel.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        panel.restart();
        GAME_CONTROLLER.restart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * input handling
     * @param view view of event
     */
    public void gameControlHandler(View view) {
        OI oi = GAME_CONTROLLER.getOi();
        switch (view.getId()) {
            case R.id.bUp:
                oi.set(OI.Control.UP, true);
                break;
            case R.id.bDown:
                oi.set(OI.Control.DOWN, true);
                break;
            case R.id.bLeft:
                oi.set(OI.Control.LEFT, true);
                break;
            case R.id.bRight:
                oi.set(OI.Control.RIGHT, true);
                break;
        }
    }
}
