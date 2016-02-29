package com.canyinghao.cananimation;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.TextView;

import static com.canyinghao.cananimation.CanAnimation.animationTogether;

/**
 * Created by canyinghao on 16/2/16.
 */
public final class CanObjectAnimator {


    private View mView;

    private CanObjectAnimator(View view) {
        this.mView = view;

    }


    public static CanObjectAnimator on(@NonNull final View view) {


        return new CanObjectAnimator(view);


    }

    private void setProperties(@NonNull Animator animator, float duration, @Nullable Interpolator interpolator) {
        if (duration < 0) {
            duration =0;
        }
        animator.setDuration((long) (duration * 1000));

        if (interpolator != null) {
            animator.setInterpolator(interpolator);
        }

    }


    @NonNull
    public Animator color(int from, int to, float duration, @Nullable Interpolator interpolator, boolean isText) {


        ObjectAnimator animator;


        if (mView instanceof TextView && isText) {

            TextView tv = (TextView) mView;

            tv.setTextColor(from);

            animator = ObjectAnimator.ofInt(mView, "textColor", from, to);
        } else {
            mView.setBackgroundColor(from);
            animator = ObjectAnimator.ofInt(mView, "backgroundColor", from, to);
        }

        setProperties(animator, duration, interpolator);


        animator.setEvaluator(new ArgbEvaluator());


        return animator;
    }


    @NonNull
    public Animator color(int from, int to) {
        return color(from, to, 1);
    }


    @NonNull
    public Animator color(int from, int to, float duration) {
        return color(from, to, duration, null, false);
    }

    @NonNull
    public Animator colorText(int to) {

        return colorText(to, 1);
    }


    @NonNull
    public Animator colorText(int to, float duration) {

        int from = Color.GRAY;

        if (mView instanceof TextView) {

            TextView tv = (TextView) mView;

            from = tv.getCurrentTextColor();
        }
        return color(from, to, duration, null, true);
    }


    @NonNull
    public Animator colorText(int from, int to) {
        return color(from, to, 1, null, true);
    }


    @NonNull
    public Animator colorText(int from, int to, float duration) {
        return color(from, to, duration, null, true);
    }


    /**
     * Animates transition between current  rotation to target rotation
     *
     * @param fromRotation
     * @param toRotation
     * @param duration
     * @param interpolator
     * @param isFrom
     * @return
     */
    @NonNull
    public Animator rotate(float fromRotation, float toRotation, float duration, @Nullable Interpolator interpolator, boolean isFrom) {

        Animator animator;
        if (isFrom) {
            animator = ObjectAnimator.ofFloat(mView, "rotation", toRotation, fromRotation);
        } else {
            animator = ObjectAnimator.ofFloat(mView, "rotation", fromRotation, toRotation);
        }


        setProperties(animator, duration, interpolator);

        return animator;
    }


    @NonNull
    public Animator rotate(float rotation, float duration, @Nullable Interpolator interpolator, boolean isFrom) {


        return rotate(mView.getRotation(), rotation, duration, interpolator, isFrom);


    }


    /**
     * Animates transition between current  rotationX to target rotationX
     *
     * @param rotationX
     * @param duration
     * @param interpolator
     * @param isFrom
     * @return
     */
    @NonNull
    public Animator rotateX(float rotationX, float duration, @Nullable Interpolator interpolator, boolean isFrom) {

        Animator animator;
        if (isFrom) {
            animator = ObjectAnimator.ofFloat(mView, "rotationX", rotationX, mView.getRotationX());
        } else {
            animator = ObjectAnimator.ofFloat(mView, "rotationX", mView.getRotationX(), rotationX);
        }


        setProperties(animator, duration, interpolator);

        return animator;
    }

    /**
     * Animates transition between current  rotateY to target rotateY
     *
     * @param rotationY
     * @param duration
     * @param interpolator
     * @param isFrom
     * @return
     */
    @NonNull
    public Animator rotateY(float rotationY, float duration, @Nullable Interpolator interpolator, boolean isFrom) {

        Animator animator;
        if (isFrom) {
            animator = ObjectAnimator.ofFloat(mView, "rotationY", rotationY, mView.getRotationX());
        } else {
            animator = ObjectAnimator.ofFloat(mView, "rotationY", mView.getRotationX(), rotationY);
        }


        setProperties(animator, duration, interpolator);

        return animator;
    }


    @NonNull
    public Animator rotateFrom(float rotation) {
        return rotateFrom(rotation, 1);
    }


    @NonNull
    public Animator rotateFrom(float rotation, float duration) {
        return rotateFrom(rotation, duration, null);
    }


    @NonNull
    public Animator rotateFrom(float rotation, float duration, @Nullable Interpolator interpolator) {
        return rotate(rotation, duration, interpolator, true);
    }


    @NonNull
    public Animator rotateFrom(float fromRotation, float toRotation, float duration) {
        return rotate(fromRotation, toRotation, duration, null, true);
    }

    @NonNull
    public Animator rotateFrom(float fromRotation, float toRotation, float duration, @Nullable Interpolator interpolator) {
        return rotate(fromRotation, toRotation, duration, interpolator, true);
    }


    @NonNull
    public Animator rotateTo(float rotation) {
        return rotateTo(rotation, 1);
    }


    @NonNull
    public Animator rotateTo(float rotation, float duration) {
        return rotateTo(rotation, duration, null);
    }


    @NonNull
    public Animator rotateTo(float rotation, float duration, @Nullable Interpolator interpolator) {
        return rotate(rotation, duration, interpolator, false);
    }

    @NonNull
    public Animator rotateTo(float fromRotation, float toRotation, float duration) {
        return rotate(fromRotation, toRotation, duration, null, false);
    }


    @NonNull
    public Animator rotateTo(float fromRotation, float toRotation, float duration, @Nullable Interpolator interpolator) {
        return rotate(fromRotation, toRotation, duration, interpolator, false);
    }


    /**
     * Animates transition between current  scaleX,scaleY to target scaleX,scaleY
     *
     * @param fromScaleX
     * @param toScaleX
     * @param fromScaleY
     * @param toScaleY
     * @param duration
     * @param interpolator
     * @param isFrom
     * @return
     */
    @NonNull
    public Animator scale(float fromScaleX, float toScaleX, float fromScaleY, float toScaleY, float duration, @Nullable Interpolator interpolator, boolean isFrom) {


        Animator animatorX;
        Animator animatorY;

        if (isFrom) {
            animatorX = ObjectAnimator.ofFloat(mView, "scaleX", toScaleX, fromScaleX);
            animatorY = ObjectAnimator.ofFloat(mView, "scaleY", toScaleY, fromScaleY);

        } else {
            animatorX = ObjectAnimator.ofFloat(mView, "scaleX", fromScaleX, toScaleX);
            animatorY = ObjectAnimator.ofFloat(mView, "scaleY", fromScaleY, toScaleY);
        }


        setProperties(animatorX, duration, interpolator);


        setProperties(animatorY, duration, interpolator);

        return animationTogether(animatorX, animatorY);
    }


    @NonNull
    public Animator scale(float targetX, float targetY, float duration, @Nullable Interpolator interpolator, boolean isFrom) {
        float currentX = mView.getScaleX();
        float currentY = mView.getScaleY();


        return scale(currentX, targetX, currentY, targetY, duration, interpolator, isFrom);
    }


    @NonNull
    public Animator scaleTo(float scaleX, float scaleY) {
        return scaleTo(scaleX, scaleY, 1);
    }


    @NonNull
    public Animator scaleTo(float scaleX, float scaleY, float duration) {
        return scaleTo(scaleX, scaleY, duration, null);
    }


    @NonNull
    public Animator scaleTo(float scaleX, float scaleY, float duration, @Nullable Interpolator interpolator) {
        return scale(scaleX, scaleY, duration, interpolator, false);
    }

    @NonNull
    public Animator scaleTo(float fromScaleX, float toScaleX, float fromScaleY, float toScaleY, float duration) {
        return scale(fromScaleX, toScaleX, fromScaleY, toScaleY, duration, null, false);
    }

    @NonNull
    public Animator scaleTo(float fromScaleX, float toScaleX, float fromScaleY, float toScaleY, float duration, @Nullable Interpolator interpolator) {
        return scale(fromScaleX, toScaleX, fromScaleY, toScaleY, duration, interpolator, false);
    }

    @NonNull
    public Animator scaleFrom(float scaleX, float scaleY) {
        return scaleFrom(scaleX, scaleY, 1);
    }


    public Animator scaleFrom(float scaleX, float scaleY, float duration) {
        return scaleFrom(scaleX, scaleY, duration, null);
    }


    @NonNull
    public Animator scaleFrom(float scaleX, float scaleY, float duration, @Nullable Interpolator interpolator) {
        return scale(scaleX, scaleY, duration, interpolator, true);
    }

    @NonNull
    public Animator scaleFrom(float fromScaleX, float toScaleX, float fromScaleY, float toScaleY, float duration) {
        return scale(fromScaleX, toScaleX, fromScaleY, toScaleY, duration, null, true);
    }

    @NonNull
    public Animator scaleFrom(float fromScaleX, float toScaleX, float fromScaleY, float toScaleY, float duration, @Nullable Interpolator interpolator) {
        return scale(fromScaleX, toScaleX, fromScaleY, toScaleY, duration, interpolator, true);
    }


    /**
     * Animates transition between current  x,y to target x,y
     *
     * @param fromX
     * @param toX
     * @param fromY
     * @param toY
     * @param duration
     * @param interpolator
     * @param isFrom
     * @return
     */
    @NonNull
    public Animator move(float fromX, float toX, float fromY, float toY, float duration, @Nullable Interpolator interpolator, boolean isFrom) {


        Animator animatorX;
        Animator animatorY;

        if (isFrom) {
            animatorX = ObjectAnimator.ofFloat(mView, "x", toX, fromX);
            animatorY = ObjectAnimator.ofFloat(mView, "y", toY, fromY);

        } else {
            animatorX = ObjectAnimator.ofFloat(mView, "x", fromX, toX);
            animatorY = ObjectAnimator.ofFloat(mView, "y", fromY, toY);
        }


        setProperties(animatorX, duration, interpolator);


        setProperties(animatorY, duration, interpolator);

        return animationTogether(animatorX, animatorY);


    }


    @NonNull
    public Animator move(float targetX, float targetY, float duration, @Nullable Interpolator interpolator, boolean isFrom) {

        float currentX = mView.getX();
        float currentY = mView.getY();


        return move(currentX, targetX, currentY, targetY, duration, interpolator, isFrom);


    }


    @NonNull
    public Animator moveFrom(float x, float y) {
        return moveFrom(x, y, 1);
    }


    @NonNull
    public Animator moveFrom(float x, float y, float duration) {
        return moveFrom(x, y, duration, null);
    }


    @NonNull
    public Animator moveFrom(float x, float y, float duration, @Nullable Interpolator interpolator) {
        return move(x, y, duration, interpolator, true);
    }

    @NonNull
    public Animator moveFrom(float fromX, float toX, float fromY, float toY, float duration) {
        return move(fromX, toX, fromY, toY, duration, null, true);
    }

    @NonNull
    public Animator moveFrom(float fromX, float toX, float fromY, float toY, float duration, @Nullable Interpolator interpolator) {
        return move(fromX, toX, fromY, toY, duration, interpolator, true);
    }

    @NonNull
    public Animator moveTo(float x, float y) {
        return moveTo(x, y, 1);
    }


    @NonNull
    public Animator moveTo(float x, float y, float duration) {
        return moveTo(x, y, duration, null);
    }


    @NonNull
    public Animator moveTo(float x, float y, float duration, @Nullable Interpolator interpolator) {
        return move(x, y, duration, interpolator, false);
    }

    @NonNull
    public Animator moveTo(float fromX, float toX, float fromY, float toY, float duration) {
        return move(fromX, toX, fromY, toY, duration, null, false);
    }

    @NonNull
    public Animator moveTo(float fromX, float toX, float fromY, float toY, float duration, @Nullable Interpolator interpolator) {
        return move(fromX, toX, fromY, toY, duration, interpolator, false);
    }


    /**
     * Animates transition between current  alpha to target alpha
     *
     * @param fromAlpha
     * @param toAlpha
     * @param duration
     * @param interpolator
     * @param isFrom
     * @return
     */
    @NonNull
    public Animator alpha(float fromAlpha, float toAlpha, float duration, @Nullable Interpolator interpolator, boolean isFrom) {

        Animator animator;
        if (isFrom) {
            animator = ObjectAnimator.ofFloat(mView, "alpha", toAlpha, fromAlpha);
        } else {
            animator = ObjectAnimator.ofFloat(mView, "alpha", fromAlpha, toAlpha);
        }


        setProperties(animator, duration, interpolator);

        return animator;
    }


    @NonNull
    public Animator alpha(float toAlpha, float duration, @Nullable Interpolator interpolator, boolean isFrom) {


        return alpha(mView.getAlpha(), toAlpha, duration, interpolator, isFrom);
    }

    @NonNull
    public Animator alphaFrom(float toAlpha) {
        return alphaFrom(toAlpha, 1);
    }


    @NonNull
    public Animator alphaFrom(float toAlpha, float duration) {
        return alphaFrom(toAlpha, duration, null);
    }


    @NonNull
    public Animator alphaFrom(float toAlpha, float duration, @Nullable Interpolator interpolator) {
        return alpha(toAlpha, duration, interpolator, true);
    }

    @NonNull
    public Animator alphaFrom(float fromAlpha, float toAlpha, float duration) {
        return alpha(fromAlpha, toAlpha, duration, null, true);
    }

    @NonNull
    public Animator alphaFrom(float fromAlpha, float toAlpha, float duration, @Nullable Interpolator interpolator) {
        return alpha(fromAlpha, toAlpha, duration, interpolator, true);
    }


    @NonNull
    public Animator alphaTo(float toAlpha) {
        return alphaTo(toAlpha, 1);
    }


    @NonNull
    public Animator alphaTo(float toAlpha, float duration) {
        return alphaTo(toAlpha, duration, null);
    }


    @NonNull
    public Animator alphaTo(float toAlpha, float duration, @Nullable Interpolator interpolator) {
        return alpha(toAlpha, duration, interpolator, false);
    }

    @NonNull
    public Animator alphaTo(float fromAlpha, float toAlpha, float duration) {
        return alpha(fromAlpha, toAlpha, duration, null, false);
    }

    @NonNull
    public Animator alphaTo(float fromAlpha, float toAlpha, float duration, @Nullable Interpolator interpolator) {
        return alpha(fromAlpha, toAlpha, duration, interpolator, false);
    }




}
