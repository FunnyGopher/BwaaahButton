package com.github.funnygopher.bwaaahbutton;

import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

    private Button bwaaahButton;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bwaaahButton = (Button) findViewById(R.id.bwaaah_button);
        bwaaahButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        changeButtonText("Bwaaah!");
                        playSound();
                        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                        bwaaahButton.startAnimation(shake);
                        break;

                    case MotionEvent.ACTION_UP:
                        changeButtonText("Dream a Little Bigger");
                        stopSound();
                        bwaaahButton.clearAnimation();
                        break;
                }
                return false;
            }
        });
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

    private void playSound() {
        if(mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.inception);
        }

        mediaPlayer.start();
    }

    private void stopSound() {
        mediaPlayer.pause();
        mediaPlayer.seekTo(0);
    }

    private void changeButtonText(String text) {
        bwaaahButton.setText(text);
    }

    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }
}
