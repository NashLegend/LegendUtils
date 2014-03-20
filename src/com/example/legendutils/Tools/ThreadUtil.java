package com.example.legendutils.Tools;

import java.util.Timer;
import java.util.TimerTask;

public class ThreadUtil {

	public ThreadUtil() {

	}

	public static Timer setTimeOut(TimerTask runnable, int delayMillis) {
		Timer timer = new Timer();
		timer.schedule(runnable, delayMillis);
		return timer;
	}

	public static Timer setTimeOut(final Runnable runnable, int delayMillis) {

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				runnable.run();
			}
		}, delayMillis);
		return timer;
	}

	public static void clearTimeOut(Timer timer) {
		if (timer != null) {
			timer.cancel();
		}
	}

	public static Timer setInterval(TimerTask runnable, int delayMillis,
			int period) {
		Timer timer = new Timer();
		timer.schedule(runnable, delayMillis, period);
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

}
