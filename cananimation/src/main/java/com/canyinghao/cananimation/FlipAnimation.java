package com.canyinghao.cananimation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.support.annotation.FloatRange;
import android.view.animation.Animation;
import android.view.animation.Transformation;


public class FlipAnimation extends Animation {


    private float mFromDegrees;
    private float mToDegrees;
    private Camera mCamera;
    private float mCenterX;
    private float mCenterY;
    private boolean mIsX;
    private float mScale;

    public FlipAnimation(float fromDegrees, float toDegrees, float centerX, float centerY, @FloatRange(from = 0.0, to = 1.0) float scale) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mScale = scale;
        if (mScale <= 0) {
            mScale = 1;
        }
    }


    public void setIsX(boolean isX) {
        this.mIsX = isX;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;

        final Matrix matrix = t.getMatrix();

        camera.save();

        if (mIsX) {
            camera.rotateX(degrees);
        } else {
            camera.rotateY(degrees);
        }


        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);

        matrix.preScale(mScale, mScale, centerX, centerY);


    }
}
