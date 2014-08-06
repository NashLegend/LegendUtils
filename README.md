LegendUtils
===========

Various tools

**markdown test below**

``` Java
	public float getInterpolation(float x) {
		float result;
		if (x < 0.5) {
			result = (float) (1.0f - Math.pow((1.0f - 2 * x), 2 * mFactor)) / 2;
		} else {
			result = (float) Math.pow((x - 0.5) * 2, 2 * mFactor) / 2 + 0.5f;
		}
		return result;
	}
```

如你所见，那么ListDialog简直是画蛇添足……
