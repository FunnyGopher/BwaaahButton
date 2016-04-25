package com.github.funnygopher.bwaaahbutton;

import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button bwaaahButton;
    private MediaPlayer mediaPlayer;

    private Random rand;
    private String[] quotes;
    private String currQuote;
    private String bwaaah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // We need the volume rockers to control the music audio volume and not the ring volume
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        rand = new Random(System.currentTimeMillis());

        // Grab the quotes and bwaaah string from resources
        Resources res = getResources();
        quotes = res.getStringArray(R.array.quotes);
        bwaaah = res.getString(R.string.bwaaah);
        currQuote = "";

        // Initialize the button
        bwaaahButton = (Button) findViewById(R.id.bwaaah_button);
        changeQuote();
        bwaaahButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        onTouchDown();
                        break;

                    case MotionEvent.ACTION_UP:
                        onTouchUp();
                        break;
                }
                return false;
            }
        });
    }

    private void onTouchDown() {
        bwaaahButton.setText(bwaaah);
        playSound();
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
        bwaaahButton.startAnimation(shake);
    }

    private void onTouchUp() {
        changeQuote();
        stopSound();
        bwaaahButton.clearAnimation();
    }

    private void playSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.inception);
        mediaPlayer.start();
    }

    private void stopSound() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    private void changeQuote() {
        String newQuote;
        do {
            newQuote = quotes[rand.nextInt(quotes.length)];
        } while(newQuote.equals(currQuote));

        currQuote = newQuote;
        bwaaahButton.setText(currQuote);
    }
}
