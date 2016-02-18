package com.canyinghao.cananimation.demo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.canyinghao.cananimation.CanValueAnimator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.canyinghao.cananimation.CanAnimation.animationSequence;
import static com.canyinghao.cananimation.CanAnimation.animationTogether;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.animContainer)
    FrameLayout animContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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

            CanValueAnimator.Builder builder = CanValueAnimator.Builder.on(view);


            animationSequence(builder.moveTo(center.x, center.y, 0),

                    animationTogether(builder.moveTo(newPos.x, newPos.y, 2),
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
                            builder.moveTo(center.x, center.y, 2),
                            builder.scaleTo(2, 2, 2),
                            builder.rotateTo(360, 2),
                            builder.color(Color.BLUE, Color.GRAY, 2),
                            builder.colorText(Color.BLUE, Color.WHITE, 2)

                    ),


                    builder.run(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar.make(toolbar,"动画播放完毕",Snackbar.LENGTH_SHORT).show();
                        }
                    })








            ).start();


            animContainer.addView(view);


        }
    }



    @OnClick(R.id.fab)
    public void click(View v) {
        playAnimation();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (id) {
            case R.id.item_1:
                startActivity(new Intent(MainActivity.this, FlipperAnimeActivity.class));

                break;

            case R.id.item_2:
                startActivity(new Intent(MainActivity.this, FlipperDemoActivity.class));
                break;
            case R.id.item_3:
                startActivity(new Intent(MainActivity.this, AnimationActivity.class));
                break;
            case R.id.item_4:
                startActivity(new Intent(MainActivity.this, AnimatorActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
