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

import java.util.Random;

public class MainActivity extends ActionBarActivity {

    private Random rand = new Random();
    private Button bwaaahButton;
    private MediaPlayer mediaPlayer;

    private final String[] QUOTES = new String[] {
        "Dream a little bigger",
        "You know what you have to do",
        "Dreams feel real while we're in them",
        "I'm asking you to take a leap of faith",
        "Come back to reality",
    };
    private final String BWAAAH = "Bwaaah!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bwaaahButton = (Button) findViewById(R.id.bwaaah_button);
        bwaaahButton.setText(QUOTES[rand.nextInt(QUOTES.length)]);
        bwaaahButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        changeButtonText(BWAAAH);
                        playSound();
                        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                        bwaaahButton.startAnimation(shake);
                        break;

                    case MotionEvent.ACTION_UP:
                        bwaaahButton.setText(QUOTES[rand.nextInt(QUOTES.length)]);
                        stopSound();
                        bwaaahButton.clearAnimation();
                        break;
                }
                return false;
            }
        });
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
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
