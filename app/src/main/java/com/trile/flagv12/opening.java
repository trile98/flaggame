package com.trile.flagv12;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class opening extends Activity {

    TextView label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        final Intent intent = new Intent(opening.this,MainActivity.class);
        label = (TextView) findViewById(R.id.label);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/moon.ttf");
        label.setTypeface(typeface);

        CountDownTimer countDownTimer = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                startActivity(intent);
                overridePendingTransition(R.anim.openingstart,R.anim.openingend);
            }
        };
        countDownTimer.start();
    }


}
