package com.canyinghao.cananimation.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by canyignhao on 16/2/16.
 */
public class FlipperDemoActivity extends AppCompatActivity implements android.view.GestureDetector.OnGestureListener{


    @BindView(R.id.vf)
    ViewFlipper viewFlipper;
    GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flipper);
        ButterKnife.bind(this);


        gestureDetector = new GestureDetector(this);    // 声明检测手势事件

        viewFlipper.setAutoStart(true);         // 设置自动播放功能（点击事件，前自动播放）
        viewFlipper.setFlipInterval(3000);
        Animation lInAnim = AnimationUtils.loadAnimation(this, R.anim.push_left_in);       // 向左滑动左侧进入的渐变效果（alpha 0.1  -> 1.0）
        Animation lOutAnim = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
        viewFlipper.setInAnimation(lInAnim);
        viewFlipper.setOutAnimation(lOutAnim);
        if(viewFlipper.isAutoStart() && !viewFlipper.isFlipping()){
            viewFlipper.startFlipping();
        }




    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewFlipper.stopFlipping();             // 点击事件后，停止自动播放
        viewFlipper.setAutoStart(false);
        return gestureDetector.onTouchEvent(event);         // 注册手势事件
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e2.getX() - e1.getX() > 120) {            // 从左向右滑动（左进右出）
            Animation rInAnim = AnimationUtils.loadAnimation(this, R.anim.push_right_in);  // 向右滑动左侧进入的渐变效果（alpha  0.1 -> 1.0）
            Animation rOutAnim = AnimationUtils.loadAnimation(this, R.anim.push_right_out); // 向右滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）

            viewFlipper.setInAnimation(rInAnim);
            viewFlipper.setOutAnimation(rOutAnim);
            viewFlipper.showPrevious();
            return true;
        } else if (e2.getX() - e1.getX() < -120) {        // 从右向左滑动（右进左出）
            Animation lInAnim = AnimationUtils.loadAnimation(this, R.anim.push_left_in);       // 向左滑动左侧进入的渐变效果（alpha 0.1  -> 1.0）
            Animation lOutAnim = AnimationUtils.loadAnimation(this, R.anim.push_left_out);     // 向左滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）


            viewFlipper.setInAnimation(lInAnim);
            viewFlipper.setOutAnimation(lOutAnim);
            viewFlipper.showNext();
            return true;
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
}
