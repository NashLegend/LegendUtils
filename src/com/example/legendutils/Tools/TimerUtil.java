package com.example.legendutils.Tools;

import java.util.Timer;
import java.util.TimerTask;

public class TimerUtil {

	public TimerUtil() {

	}

	public static Timer setTimeOut(final Runnable runnable, int delayMillis) {

		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				runnable.run();
				timer.cancel();
			}
		}, delayMillis);
		return timer;
	}

	public static Timer setInterval(final Runnable runnable, int delayMillis,
			int period) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				runnable.run();
			}
		}, delayMillis, period);
		return timer;
	}

	public static void clearInterval(Timer timer) {
		if (timer != null) {
			timer.cancel();
		}
	}

	public static void clearTimeOut(Timer timer) {
		if (timer != null) {
			timer.cancel();
		}
	}

}
