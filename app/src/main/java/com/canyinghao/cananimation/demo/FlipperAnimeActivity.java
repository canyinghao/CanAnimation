package com.canyinghao.cananimation.demo;

import android.os.Bundle;
import android.widget.ViewFlipper;

import com.canyinghao.cananimation.CanFlipAnimation;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Created by canyignhao on 16/2/17.
 */
public class FlipperAnimeActivity extends AppCompatActivity {



    ViewFlipper vf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flipper);
        vf = findViewById(R.id.vf);


        vf.postDelayed(new Runnable() {
            @Override
            public void run() {
                CanFlipAnimation.flipForever(vf, CanFlipAnimation.FlipDirection.LEFT_RIGHT,1000,null,0);
            }
        },1000);
    }
}
