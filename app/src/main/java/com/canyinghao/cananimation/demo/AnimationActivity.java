package com.canyinghao.cananimation.demo;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.canyinghao.cananimation.CanAnimation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


/**
 * Created by canyignhao on 16/2/18.
 */
public class AnimationActivity extends AppCompatActivity {



    Toolbar toolbar;

    FrameLayout animContainer;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar =  findViewById(R.id.toolbar);
        animContainer =  findViewById(R.id.animContainer);
        fab =  findViewById(R.id.fab);

        setSupportActionBar(toolbar);

        animContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                playAnimation();
            }
        }, 500);
    }


    private void playAnimation() {

        animContainer.removeAllViews();
        final Point center = new Point(animContainer.getMeasuredWidth() / 2, animContainer.getMeasuredHeight() / 2);
        int size = getResources().getDimensionPixelSize(R.dimen.circle_size);

        for (int i = 0; i < 6; i++) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, size);
            final TextView view = new TextView(this);
            view.setLayoutParams(params);
            view.setBackgroundColor(Color.BLUE);
            view.setText("" + i);
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            view.setTextSize(20);
            final Point newPos = new Point();
            switch (i) {
                case 0:
                    newPos.set(0, 0);
                    break;

                case 1:
                    newPos.set(0, center.y);
                    break;

                case 2:
                    newPos.set(0, center.y * 2 - size);
                    break;

                case 3:
                    newPos.set(center.x * 2 - size, 0);
                    break;

                case 4:
                    newPos.set(center.x * 2 - size, center.y);
                    break;

                case 5:
                    newPos.set(center.x * 2 - size, center.y * 2 - size);
                    break;
            }


            Animation small2Big = CanAnimation.animSmall2Big();
            small2Big.setFillAfter(true);
            view.startAnimation(small2Big);




            small2Big.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Animation move = new TranslateAnimation(
                            Animation.ABSOLUTE, 0
                            , Animation.ABSOLUTE, newPos.x,
                            Animation.ABSOLUTE, 0, Animation.ABSOLUTE, newPos.y
                    );
                    move.setDuration(2000);
                    move.setFillAfter(true);
                    view.startAnimation(move);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


            animContainer.addView(view);


        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAnimation();
            }
        });
    }


}
