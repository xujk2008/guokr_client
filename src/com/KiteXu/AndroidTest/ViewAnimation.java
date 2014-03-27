package com.KiteXu.AndroidTest;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class ViewAnimation extends Animation implements AnimationListener{

	Camera camera = new Camera();
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		// TODO Auto-generated method stub
		final Matrix matrix = t.getMatrix();
		camera.save();
		camera.translate(0.0f, 0.0f, -1*(300-300.0f * (1-interpolatedTime)));
		camera.rotateY(-90*interpolatedTime);
		camera.getMatrix(matrix);
		camera.restore();
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		// TODO Auto-generated method stub
		super.initialize(width, height, parentWidth, parentHeight);
		setDuration(2500);
		setFillAfter(true);
		setInterpolator(new LinearInterpolator());
	}

	@Override
	public void onAnimationEnd(Animation arg0) {
		// TODO Auto-generated method stub
		Log.d("@@@@@@", "end.");
	}

	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub
		Log.d("@@@@@@", "repeat.");
	}

	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub
		Log.d("@@@@@@", "start.");
	}
	
}
