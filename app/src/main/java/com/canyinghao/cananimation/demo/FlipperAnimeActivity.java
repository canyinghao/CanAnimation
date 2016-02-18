package com.canyinghao.cananimation.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ViewFlipper;

import com.canyinghao.cananimation.CanFlipAnimation;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yangjian on 16/2/17.
 */
public class FlipperAnimeActivity extends AppCompatActivity {


    @Bind(R.id.vf)
    ViewFlipper vf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flipper);
        ButterKnife.bind(this);


        vf.postDelayed(new Runnable() {
            @Override
            public void run() {
                CanFlipAnimation.flipForever(vf, CanFlipAnimation.FlipDirection.LEFT_RIGHT,1000,null,0);
            }
        },1000);
    }
}
