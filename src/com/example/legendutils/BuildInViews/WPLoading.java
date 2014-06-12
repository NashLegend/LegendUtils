package com.example.legendutils.BuildInViews;

import java.util.ArrayList;

import com.example.legendutils.Tools.DisplayUtil;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WPLoading extends RelativeLayout {

	private int size = 10;
	private int delay = 300;
	private int duration = 3200;
	private String color = "#0000ff";

	AnimatorSet animatorSet = new AnimatorSet();

	public WPLoading(Context context) {
		super(context);
		LayoutParams params = new LayoutParams(size, size);
		AnimatorSet animatorSet = new AnimatorSet();
		ArrayList<Animator> animators = new ArrayList<Animator>();

		for (int i = 0; i < 5; i++) {
			View view = new View(getContext());
			view.setBackgroundColor(Color.parseColor(color));
			addView(view);
			view.setLayoutParams(params);
			view.setX(-size);

			ObjectAnimator headAnimator = ObjectAnimator.ofFloat(view, "x",
					view.getX(), DisplayUtil.getScreenWidth(context));
			headAnimator.setDuration(duration);
			headAnimator
					.setInterpolator(new TailDecelerateAccelerateInterpolator());
			headAnimator.setStartDelay(delay * i);
			headAnimator.setRepeatCount(-1);
			animators.add(headAnimator);
		}
		animatorSet.playTogether(animators);
		animatorSet.start();
	}

	public WPLoading(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WPLoading(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// 先减速，再加速，再停止一会儿
	class TailDecelerateAccelerateInterpolator implements
			android.view.animation.Interpolator {
		private float mFactor = 1.0f;
		private float tailFactor = 0.6f;

		public TailDecelerateAccelerateInterpolator() {
		}

		public TailDecelerateAccelerateInterpolator(float factor) {
			mFactor = factor;
		}

		public float getInterpolation(float x) {
			float result;
			if (x > tailFactor) {
				result = 1;
			} else if (x > tailFactor / 2) {
				result = (float) Math.pow(
						(x - tailFactor / 2) * 2 / tailFactor, 2 * mFactor) / 2 + 0.5f;
			} else {
				result = (float) (1.0f - Math.pow((tailFactor - 2 * x)
						/ tailFactor, 2 * mFactor)) / 2;
			}
			return result;
		}
	}
}
