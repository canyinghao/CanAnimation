package com.canyinghao.cananimation;

import android.support.annotation.FloatRange;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.widget.ViewAnimator;


public class CanFlipAnimation {


    public enum FlipDirection {
        LEFT_RIGHT,
        RIGHT_LEFT,
        TOP_BOTTOM,
        BOTTOM_TOP;

        public float getStartDegreeForFirstView() {
            return 0;
        }

        public float getStartDegreeForSecondView() {
            switch (this) {
                case LEFT_RIGHT:
                case TOP_BOTTOM:
                    return -90;
                case RIGHT_LEFT:
                case BOTTOM_TOP:
                    return 90;
                default:
                    return 0;
            }
        }

        public float getEndDegreeForFirstView() {
            switch (this) {
                case LEFT_RIGHT:
                case TOP_BOTTOM:
                    return 90;
                case RIGHT_LEFT:
                case BOTTOM_TOP:
                    return -90;
                default:
                    return 0;
            }
        }

        public float getEndDegreeForSecondView() {
            return 0;
        }


    }

    private static Animation[] flipAnim(final View fromView, FlipDirection dir, long duration, Interpolator interpolator, @FloatRange(from = 0.0, to = 1.0) float scale) {
        Animation[] result = new Animation[2];
        float centerX;
        float centerY;

        centerX = fromView.getWidth() / 2.0f;
        centerY = fromView.getHeight() / 2.0f;

        FlipAnimation outFlip = new FlipAnimation(dir.getStartDegreeForFirstView(), dir.getEndDegreeForFirstView(), centerX, centerY, scale);
        outFlip.setDuration(duration);
        outFlip.setFillAfter(true);
        outFlip.setInterpolator(interpolator == null ? new AccelerateInterpolator() : interpolator);

        if (dir == FlipDirection.BOTTOM_TOP || dir == FlipDirection.TOP_BOTTOM) {
            outFlip.setIsX(true);
        } else {
            outFlip.setIsX(false);
        }

        AnimationSet outAnimation = new AnimationSet(true);
        outAnimation.addAnimation(outFlip);
        result[0] = outAnimation;


        FlipAnimation inFlip = new FlipAnimation(dir.getStartDegreeForSecondView(), dir.getEndDegreeForSecondView(), centerX, centerY, scale);
        inFlip.setDuration(duration);
        inFlip.setFillAfter(true);
        inFlip.setInterpolator(interpolator == null ? new AccelerateInterpolator() : interpolator);
        inFlip.setStartOffset(duration);

        if (dir == FlipDirection.BOTTOM_TOP || dir == FlipDirection.TOP_BOTTOM) {
            inFlip.setIsX(true);
        } else {
            inFlip.setIsX(false);
        }

        AnimationSet inAnimation = new AnimationSet(true);
        inAnimation.addAnimation(inFlip);
        result[1] = inAnimation;

        return result;

    }


    private static Animation[] flipTransition(final ViewAnimator viewAnimator, FlipDirection dir, long duration, Interpolator interpolator, @FloatRange(from = 0.0, to = 1.0) float scale) {


        Animation[] animations = flipAnim(viewAnimator, dir, duration, interpolator, scale);

        viewAnimator.setOutAnimation(animations[0]);
        viewAnimator.setInAnimation(animations[1]);
        viewAnimator.showNext();
        return animations;
    }


    /**
     * 翻转动画翻转一定次数
     *
     * @param count        int
     * @param viewAnimator ViewAnimator
     * @param dir          FlipDirection
     * @param duration     long
     * @param interpolator Interpolator
     * @param scale        float
     */
    public static void flipRepeat(final int count, final ViewAnimator viewAnimator, final FlipDirection dir, final long duration, final Interpolator interpolator, @FloatRange(from = 0.0, to = 1.0) final float scale) {


        if (count > 0) {
            if (viewAnimator.getTag() == null) {
                viewAnimator.setTag(1);
            } else {

                if (viewAnimator.getTag() instanceof Integer) {

                    int index = (int) viewAnimator.getTag();

                    if (index >= count) {
                        return;
                    }


                    viewAnimator.setTag(index + 1);
                }


            }
        } else {

            if (viewAnimator.getTag() instanceof Integer) {
                int index = (int) viewAnimator.getTag();

                if (index == -1) {
                    return;
                }
            }
        }



        try {
            Animation[] anims = flipTransition(viewAnimator, dir, duration, interpolator, scale);


            anims[1].setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation arg0) {


                }

                @Override
                public void onAnimationRepeat(Animation arg0) {


                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    flipRepeat(count, viewAnimator, dir, duration, interpolator, scale);


                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    public static void flipForever(final ViewAnimator viewAnimator, final FlipDirection dir, final long duration, final Interpolator interpolator, @FloatRange(from = 0.0, to = 1.0) final float scale) {

        flipRepeat(0, viewAnimator, dir, duration, interpolator, scale);
    }

}
