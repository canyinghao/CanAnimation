package com.canyinghao.cananimation.demo;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.canyinghao.cananimation.CanAnimation;
import com.canyinghao.cananimation.CanObjectAnimator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.canyinghao.cananimation.CanAnimation.animationSequence;
import static com.canyinghao.cananimation.CanAnimation.animationTogether;

/**
 * Created by canyignhao on 16/2/18.
 */
public class AnimatorActivity extends AppCompatActivity {



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
        Point center = new Point(animContainer.getMeasuredWidth() / 2, animContainer.getMeasuredHeight() / 2);
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
            Point newPos = new Point();
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

            CanObjectAnimator builder = CanObjectAnimator.on(view);


            animationSequence(builder.moveTo(center.x, center.y, 0),

                    animationTogether(builder.moveTo(center.x,newPos.x,center.y, newPos.y, 2),
                            builder.rotateTo(360, 2),
                            builder.color(Color.BLUE, Color.RED, 2),
                            builder.colorText(Color.WHITE, Color.BLUE, 2)


                    ),
                    animationTogether(builder.scaleTo(0.5f, 0.5f,2),
                            builder.rotateTo(720,2),
                            animationSequence(
                                    builder.color(Color.RED, Color.BLACK, 0.3f),
                                    builder.color(Color.BLACK, Color.RED, 0.3f),
                                    builder.color(Color.RED, Color.GREEN, 0.3f),
                                    builder.color(Color.GREEN, Color.RED, 0.3f),
                                    builder.color(Color.RED, Color.YELLOW, 0.3f),
                                    builder.color(Color.YELLOW, Color.RED, 0.3f),
                                    builder.color(Color.RED, Color.BLUE, 0.3f)
                            )
                    ),

                    animationTogether(
                            builder.moveTo(newPos.x,center.x, newPos.y,center.y, 2),
                            builder.scaleTo(0.5f,2, 0.5f,2, 2),
                            builder.rotateTo(360, 2),
                            builder.color(Color.BLUE, Color.GRAY, 2),
                            builder.colorText(Color.BLUE, Color.WHITE, 2)

                    ),


                    CanAnimation.run(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar.make(toolbar, "动画播放完毕", Snackbar.LENGTH_SHORT).show();
                        }
                    })

            ).start();


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
