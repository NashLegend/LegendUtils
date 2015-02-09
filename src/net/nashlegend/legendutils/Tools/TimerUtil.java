package net.nashlegend.legendutils.Tools;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import android.R.integer;
import android.os.Handler;

public class TimerUtil {

	public TimerUtil() {

	}

	public static Timer setTimeOut(final Runnable runnable, int delayMillis) {
		final Handler handler = new Handler();
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				handler.post(runnable);
				timer.cancel();
			}
		}, delayMillis);
		return timer;
	}

	public static Timer setInterval(final Runnable runnable, int delayMillis,
			int period) {
		final Handler handler = new Handler();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.post(runnable);
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
