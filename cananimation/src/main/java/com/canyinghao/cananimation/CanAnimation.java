package com.canyinghao.cananimation;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;



/**
 * Created by canyinghao on 16/2/17.
 */
public final class CanAnimation {


    /**
     * 属性动画永久循环
     * @param animator
     * @return
     */
    
    public static Animator valueAnimatorForever(Animator animator) {
        return valueAnimatorRepeat(-1, animator);
    }


    /**
     * 属性动画循环一定次数
     * @param count
     * @param animator
     * @return
     */
    
    public static Animator valueAnimatorRepeat(final int count,  Animator animator) {
        if (count <= 1 && count != -1)
            return animator;

        if (animator instanceof ValueAnimator) {
            ValueAnimator valueAnimator = (ValueAnimator) animator;

            valueAnimator.setRepeatCount(Math.max(-1, count - 1));


            return valueAnimator;
        }

        return animator;
    }

    /**
     * AnimatorSet循环播放
     * @param count
     * @param animator
     * @return
     */
    
    public static Animator animatorSetForever(final int count,  final Animator animator) {
        return animatorSetRepeat(-1, animator);
    }

    /**
     * AnimatorSet循环播放一定次数
     * @param count
     * @param animator
     * @return
     */
    
    public static Animator animatorSetRepeat(final int count,  final Animator animator) {

        if (!(animator instanceof AnimatorSet)) {
            return animator;
        }

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (count == -1) {
                    animatorSetRepeat(count, animator);
                    animator.start();

                } else if (count > 0) {
                    int tempCount = count;
                    tempCount--;


                    if (tempCount > 0) {
                        animator.removeAllListeners();
                        animatorSetRepeat(tempCount, animator);
                        animator.start();
                    }
                }


            }
        });

        return animator;


    }


    /**
     * 动画队列
     * @param animators
     * @return
     */
    
    public static Animator animationSequence( Animator... animators) {
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animators);

        return set;
    }


    /**
     * 动画同时播放
     * @param animators
     * @return
     */
    
    public static Animator animationTogether( Animator... animators) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        return set;


    }


    
    public static  Animator run( final Runnable runnable) {


        ValueAnimator animator = new ValueAnimator();
        animator.setDuration(0);
        animator.setIntValues(1);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                runnable.run();
            }
        });
        return animator;
    }


    /**
     * 放大并逐渐消失
     * @return
     */
    public static AnimationSet animScale2Alpha() {
        ScaleAnimation sa = new ScaleAnimation(0, 2, 0, 2,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        AlphaAnimation aa = new AlphaAnimation(1, 0);
        sa.setDuration(1000);
        sa.setRepeatMode(Animation.RESTART);
        sa.setRepeatCount(Integer.MAX_VALUE);
        aa.setDuration(1000);
        final AnimationSet set = new AnimationSet(true);
        set.addAnimation(sa);
        set.addAnimation(aa);
        return set;

    }


    /**
     * 变小然后变大，然后变回原始大小
     * @return
     */
    public static AnimationSet animSmall2Big() {

        AlphaAnimation alpha = new AlphaAnimation(0.5f, 1);
        alpha.setDuration(200);
        alpha.setInterpolator(new AccelerateInterpolator());

        ScaleAnimation scale1 = new ScaleAnimation(0.6f, 1.2f, 0.6f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scale1.setDuration(200);
        scale1.setInterpolator(new AccelerateInterpolator());
        ScaleAnimation scale2 = new ScaleAnimation(1.2f, 0.8f, 1.2f, 0.8f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scale2.setDuration(300);
        scale2.setInterpolator(new DecelerateInterpolator());
        ScaleAnimation scale3 = new ScaleAnimation(0.8f, 1f, 0.8f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scale3.setDuration(200);
        scale3.setInterpolator(new DecelerateInterpolator());

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(alpha);
        set.addAnimation(scale1);
        set.addAnimation(scale2);
        set.addAnimation(scale3);
        return set;

    }


    /**
     * 渐显
     * @param duration
     * @param delay
     * @return
     */
    public static Animation fadeIn(long duration, long delay) {

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(duration);
        fadeIn.setStartOffset(delay);

        return fadeIn;
    }


    /**
     * 渐隐
     * @param duration
     * @param delay
     * @return
     */
    public static Animation fadeOut(long duration, long delay) {

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(delay);
        fadeOut.setDuration(duration);

        return fadeOut;
    }


    /**
     * 从左进入
     * @param duration
     * @param interpolator
     * @return
     */
    public static Animation in2Left(long duration, Interpolator interpolator) {
        Animation in2Left = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
        );
        in2Left.setDuration(duration);
        in2Left.setInterpolator(interpolator == null ? new AccelerateInterpolator() : interpolator); //AccelerateInterpolator
        return in2Left;
    }

    /**
     * 从右进入
     * @param duration
     * @param interpolator
     * @return
     */
    public static Animation in2Right(long duration, Interpolator interpolator) {

        Animation in2Right = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
        );
        in2Right.setDuration(duration);
        in2Right.setInterpolator(interpolator == null ? new AccelerateInterpolator() : interpolator);
        return in2Right;
    }


    /**
     * 从右退出
     * @param duration
     * @param interpolator
     * @return
     */
    public static Animation out2Right(long duration, Interpolator interpolator) {
        Animation out2Right = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
        );
        out2Right.setDuration(duration);
        out2Right.setInterpolator(interpolator == null ? new AccelerateInterpolator() : interpolator);
        return out2Right;
    }


    /**
     * 从左退出
     * @param duration
     * @param interpolator
     * @return
     */
    public static Animation out2left(long duration, Interpolator interpolator) {
        Animation out2left = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
        );
        out2left.setDuration(duration);
        out2left.setInterpolator(interpolator == null ? new AccelerateInterpolator() : interpolator);
        return out2left;
    }


    /**
     * 从上进入
     * @param duration
     * @param interpolator
     * @return
     */
    public static Animation in2top(long duration, Interpolator interpolator) {
        Animation in2top = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f
        );
        in2top.setDuration(duration);
        in2top.setInterpolator(interpolator == null ? new AccelerateInterpolator() : interpolator);
        return in2top;
    }

    /**
     * 从下进入
     * @param duration
     * @param interpolator
     * @return
     */
    public static Animation in2bottom(long duration, Interpolator interpolator) {
        Animation in2top = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f
        );
        in2top.setDuration(duration);
        in2top.setInterpolator(interpolator == null ? new AccelerateInterpolator() : interpolator);
        return in2top;
    }


    /**
     * 从上退出
     * @param duration
     * @param interpolator
     * @return
     */
    public static Animation out2top(long duration, Interpolator interpolator) {
        Animation out2top = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f
        );
        out2top.setDuration(duration);
        out2top.setInterpolator(interpolator == null ? new AccelerateInterpolator() : interpolator);
        return out2top;
    }

    /**
     * 从下退出
     * @param duration
     * @param interpolator
     * @return
     */
    public static Animation out2bottom(long duration, Interpolator interpolator) {
        Animation in2top = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f
        );
        in2top.setDuration(duration);
        in2top.setInterpolator(interpolator == null ? new AccelerateInterpolator() : interpolator);
        return in2top;
    }


}
