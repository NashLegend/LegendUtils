/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.legendutils.Animation;

import android.view.animation.Interpolator;

/**
 * 一个先减速再加速的插值器，是DecelerateInterpolator和AccelerateInterpolator的结合体
 * 
 */
public class DecelerateAccelerateInterpolator implements Interpolator {
	private float mFactor = 1.0f;

	public DecelerateAccelerateInterpolator() {
	}

	public DecelerateAccelerateInterpolator(float factor) {
		mFactor = factor;
	}

	public float getInterpolation(float x) {
		float result;
		if (x < 0.5) {
			result = (float) (1.0f - Math.pow((1.0f - 2 * x), 2 * mFactor)) / 2;
		} else {
			result = (float) Math.pow((x - 0.5) * 2, 2 * mFactor) / 2 + 0.5f;
		}
		return result;
	}
}
