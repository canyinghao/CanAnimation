package com.canyinghao.cananimation;



import android.view.View;
import android.view.animation.Interpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.lang.reflect.Method;


/**
 * Created by canyinghao on 16/2/16.
 */
public class CanValueAnimator extends ValueAnimator {

    public final static int ALPHA_TO = 1;
    public final static int ALPHA_FROM = 2;
    public final static int SCALE_TO = 3;
    public final static int SCALE_FROM = 4;
    public final static int ROTATE_TO = 5;
    public final static int ROTATE_FROM = 6;
    public final static int MOVE_TO = 7;
    public final static int MOVE_FROM = 8;
    public final static int COLOR = 9;
    public final static int COLOR_TEXT = 10;

    public final static int ROTATE_X_TO = 11;
    public final static int ROTATE_X_FROM = 12;

    public final static int ROTATE_Y_TO = 13;
    public final static int ROTATE_Y_FROM = 14;


    private int type;
    private float[] floatTargets;
    private int[] intTargets;
    private View view;

    public void setFloatTargets(float... floatTargets) {
        this.floatTargets = floatTargets;
    }

    public void setIntTargets(int... intTargets) {
        this.intTargets = intTargets;
    }

    public void setType(int type) {
        this.type = type;
    }


    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void start() {
        updateValues();
        super.start();
    }

    private void updateValues() {
        if (type <= 0) {
            throw new IllegalStateException("type is not specified!");
        }
        if (view == null) {
            throw new IllegalStateException("view is not specified!");
        }
        switch (type) {

            case ALPHA_TO:
                alpha(false, "alpha");
                break;
            case ALPHA_FROM:
                alpha(true, "alpha");
                break;
            case SCALE_TO:
                scale(false, "scaleX", "scaleY");
                break;
            case SCALE_FROM:
                scale(true, "scaleX", "scaleY");
                break;

            case ROTATE_TO:
                rotate(false, "rotation");
                break;
            case ROTATE_FROM:
                rotate(true, "rotation");
                break;
            case COLOR:
                color("backgroundColor");
                break;
            case COLOR_TEXT:
                color("textColor");
                break;
            case MOVE_TO:
                move(false, "x", "y");
                break;
            case MOVE_FROM:
                move(true, "x", "y");
                break;

            case ROTATE_X_TO:
                rotate(false, "rotationX");
                break;
            case ROTATE_X_FROM:
                rotate(true, "rotationX");
                break;

            case ROTATE_Y_TO:
                rotate(false, "rotationY");
                break;
            case ROTATE_Y_FROM:
                rotate(true, "rotationY");
                break;
        }
    }


    private void addListener(final String... strings) {


        addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                for (String str : strings) {

                    invokeMethod(view, str, valueAnimator.getAnimatedValue(str));
                }
            }
        });


    }

    private void color(String str) {


        PropertyValuesHolder color = PropertyValuesHolder.ofInt(str, intTargets[0], intTargets[1]);


        setValues(color);
        setEvaluator(new ArgbEvaluator());

        addListener(str);


    }

    private void rotate(boolean isFrom, String str) {
        float rotation = ViewHelper.getRotation(view);



        PropertyValuesHolder rotate = PropertyValuesHolder.ofFloat(str, !isFrom ? rotation : floatTargets[0], isFrom ? rotation : floatTargets[0]);


        setValues(rotate);


        addListener(str);
    }

    private void move(boolean isFrom, String strX, String strY) {
        float currentX = ViewHelper.getX(view);
        float currentY = ViewHelper.getY(view);

        PropertyValuesHolder x = PropertyValuesHolder.ofFloat(strX, !isFrom ? currentX : floatTargets[0], isFrom ? currentX : floatTargets[0]);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat(strY, !isFrom ? currentY : floatTargets[1], isFrom ? currentY : floatTargets[1]);
        setValues(x, y);
        addListener(strX, strY);
    }

    private void scale(boolean isFrom, String strX, String strY) {
        float currentScaleX = ViewHelper.getScaleX(view);
        float currentScaleY = ViewHelper.getScaleY(view);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(strX, !isFrom ? currentScaleX : floatTargets[0], isFrom ? currentScaleX : floatTargets[0]);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(strY, !isFrom ? currentScaleY : floatTargets[1], isFrom ? currentScaleY : floatTargets[1]);
        setValues(scaleX, scaleY);
        addListener(strX, strY);
    }

    private void alpha(boolean isFrom, String str) {
        float currentAlpha = ViewHelper.getAlpha(view);

        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat(str, !isFrom ? currentAlpha : floatTargets[0], isFrom ? currentAlpha : floatTargets[0]);

        setValues(alpha);
        addListener(str);
    }


    public Object invokeMethod( Object owner,  String fieldName,  Object obj) {
        try {

            Class objectClass = owner.getClass();
            Class[] parameterTypes = new Class[1];

            parameterTypes[0] = obj.getClass();
            if (obj instanceof Integer) {
                parameterTypes[0] = int.class;
            } else if (obj instanceof Float) {
                parameterTypes[0] = float.class;
            } else if (obj instanceof Double) {
                parameterTypes[0] = double.class;
            }

            StringBuffer sb = new StringBuffer();
            sb.append("set");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
            Method method = objectClass.getMethod(sb.toString(), parameterTypes);
            return method.invoke(owner, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static class Builder {


        private View view;


        private Builder(View view) {
            this.view = view;
        }

        public static Builder on(View view) {

            return new Builder(view);

        }


        private void setProperties( CanValueAnimator animator, float duration,  Interpolator interpolator) {
            if (duration < 0) {
                duration = 0;
            }
            animator.setDuration((long) (duration * 1000));
            if (interpolator != null) {
                animator.setInterpolator(interpolator);
            }
            animator.setView(view);
        }

        public Animator color(int from, int to, float duration,  Interpolator interpolator) {
            CanValueAnimator action = new CanValueAnimator();
            setProperties(action, duration, interpolator);
            action.setType(COLOR);
            action.setIntTargets(from, to);
            return action;
        }

        
        public Animator color(int from, int to) {
            return color(from, to, 1);
        }


        
        public Animator color(int from, int to, float duration) {
            return color(from, to, duration, null);
        }

        public Animator colorText(int from, int to, float duration,  Interpolator interpolator) {
            CanValueAnimator action = new CanValueAnimator();
            setProperties(action, duration, interpolator);
            action.setType(COLOR_TEXT);
            action.setIntTargets(from, to);
            return action;
        }

        
        public Animator colorText(int from, int to) {
            return colorText(from, to, 1);
        }


        
        public Animator colorText(int from, int to, float duration) {
            return colorText(from, to, duration, null);
        }


        
        public Animator rotate(float rotation, float duration,  Interpolator interpolator, boolean isFrom) {
            CanValueAnimator action = new CanValueAnimator();
            setProperties(action, duration, interpolator);
            action.setType(isFrom ? ROTATE_FROM : ROTATE_TO);
            action.setFloatTargets(rotation);
            return action;
        }


        
        public Animator rotateFrom(float rotation) {
            return rotateFrom(rotation, 1);
        }


        
        public Animator rotateFrom(float rotation, float duration) {
            return rotateFrom(rotation, duration, null);
        }


        
        public Animator rotateFrom(float rotation, float duration,  Interpolator interpolator) {
            return rotate(rotation, duration, interpolator, true);
        }


        
        public Animator rotateTo(float rotation) {
            return rotateTo(rotation, 1);
        }


        
        public Animator rotateTo(float rotation, float duration) {
            return rotateTo(rotation, duration, null);
        }


        
        public Animator rotateTo(float rotation, float duration,  Interpolator interpolator) {
            return rotate(rotation, duration, interpolator, false);
        }


        
        public Animator rotateX(float rotation, float duration,  Interpolator interpolator, boolean isFrom) {
            CanValueAnimator action = new CanValueAnimator();
            setProperties(action, duration, interpolator);
            action.setType(isFrom ? ROTATE_X_FROM : ROTATE_X_TO);
            action.setFloatTargets(rotation);
            return action;
        }

        
        public Animator rotateY(float rotation, float duration,  Interpolator interpolator, boolean isFrom) {
            CanValueAnimator action = new CanValueAnimator();
            setProperties(action, duration, interpolator);
            action.setType(isFrom ? ROTATE_Y_FROM : ROTATE_Y_TO);
            action.setFloatTargets(rotation);
            return action;
        }


        
        public Animator move(float x, float y, float duration,  Interpolator interpolator, boolean isFrom) {
            CanValueAnimator action = new CanValueAnimator();
            setProperties(action, duration, interpolator);
            action.setType(isFrom ? MOVE_FROM : MOVE_TO);
            action.setFloatTargets(x, y);
            return action;
        }

        
        public Animator moveFrom(float x, float y) {
            return moveFrom(x, y, 1);
        }


        
        public Animator moveFrom(float x, float y, float duration) {
            return moveFrom(x, y, duration, null);
        }


        
        public Animator moveFrom(float x, float y, float duration,  Interpolator interpolator) {
            return move(x, y, duration, interpolator, true);
        }


        
        public Animator moveTo(float x, float y) {
            return moveTo(x, y, 1);
        }


        
        public Animator moveTo(float x, float y, float duration) {
            return moveTo(x, y, duration, null);
        }


        
        public Animator moveTo(float x, float y, float duration,  Interpolator interpolator) {
            return move(x, y, duration, interpolator, false);
        }


        
        public Animator scale(float scaleX, float scaleY, float duration,  Interpolator interpolator, boolean isFrom) {
            CanValueAnimator action = new CanValueAnimator();
            setProperties(action, duration, interpolator);
            action.setType(isFrom ? SCALE_FROM : SCALE_TO);
            action.setFloatTargets(scaleX, scaleY);
            return action;
        }


        
        public Animator scaleTo(float scaleX, float scaleY) {
            return scaleTo(scaleX, scaleY, 1);
        }


        
        public Animator scaleTo(float scaleX, float scaleY, float duration) {
            return scaleTo(scaleX, scaleY, duration, null);
        }


        
        public Animator scaleTo(float scaleX, float scaleY, float duration,  Interpolator interpolator) {
            return scale(scaleX, scaleY, duration, interpolator, false);
        }


        
        public Animator scaleFrom(float scaleX, float scaleY) {
            return scaleFrom(scaleX, scaleY, 1);
        }


        public Animator scaleFrom(float scaleX, float scaleY, float duration) {
            return scaleFrom(scaleX, scaleY, duration, null);
        }


        
        public Animator scaleFrom(float scaleX, float scaleY, float duration,  Interpolator interpolator) {
            return scale(scaleX, scaleY, duration, interpolator, true);
        }


        
        public Animator alpha(float alpha, float duration,  Interpolator interpolator, boolean isFrom) {
            CanValueAnimator action = new CanValueAnimator();
            setProperties(action, duration, interpolator);
            action.setType(isFrom ? ALPHA_FROM : ALPHA_TO);
            action.setFloatTargets(alpha);
            return action;
        }


        
        public Animator alphaFrom(float toAlpha) {
            return alphaFrom(toAlpha, 1);
        }


        
        public Animator alphaFrom(float toAlpha, float duration) {
            return alphaFrom(toAlpha, duration, null);
        }


        
        public Animator alphaFrom(float toAlpha, float duration,  Interpolator interpolator) {
            return alpha(toAlpha, duration, interpolator, true);
        }


        
        public Animator alphaTo(float toAlpha) {
            return alphaTo(toAlpha, 1);
        }


        
        public Animator alphaTo(float toAlpha, float duration) {
            return alphaTo(toAlpha, duration, null);
        }


        
        public Animator alphaTo(float toAlpha, float duration,  Interpolator interpolator) {
            return alpha(toAlpha, duration, interpolator, false);
        }




    }


}
