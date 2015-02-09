package net.nashlegend.legendutils.Tools;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Pair;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * 先进先出顺序执行任务的队列，来自Android Launcher2
 * 
 * @author Google.Inc
 * 
 */
public class DeferredHandler {
	private LinkedList<Pair<Runnable, Integer>> mQueue = new LinkedList<Pair<Runnable, Integer>>();
	private MessageQueue mMessageQueue = Looper.myQueue();
	private Impl mHandler = new Impl();

	private class Impl extends Handler implements MessageQueue.IdleHandler {
		public void handleMessage(Message msg) {
			Pair<Runnable, Integer> p;
			Runnable r;
			synchronized (mQueue) {
				if (mQueue.size() == 0) {
					return;
				}
				p = mQueue.removeFirst();
				r = p.first;
			}
			r.run();
			synchronized (mQueue) {
				scheduleNextLocked();
			}
		}

		public boolean queueIdle() {
			handleMessage(null);
			return false;
		}
	}

	private class IdleRunnable implements Runnable {
		Runnable mRunnable;

		IdleRunnable(Runnable r) {
			mRunnable = r;
		}

		public void run() {
			mRunnable.run();
		}
	}

	public DeferredHandler() {
	}

	/**
	 * 任务队列全部执行完毕时执行此runnable
	 * 
	 * @param runnable
	 */
	public void post(Runnable runnable) {
		post(runnable, 0);
	}

	/**
	 * 任务队列全部执行完毕时执行此runnable
	 * 
	 * @param runnable
	 * @param type
	 */
	public void post(Runnable runnable, int type) {
		synchronized (mQueue) {
			mQueue.add(new Pair<Runnable, Integer>(runnable, type));
			if (mQueue.size() == 1) {
				scheduleNextLocked();
			}
		}
	}

	/**
	 * 线程空闲时执行runnable
	 * 
	 * @param runnable
	 */
	public void postIdle(final Runnable runnable) {
		postIdle(runnable, 0);
	}

	/**
	 * 线程空闲时执行runnable
	 * 
	 * @param runnable
	 * @param type
	 */
	public void postIdle(final Runnable runnable, int type) {
		post(new IdleRunnable(runnable), type);
	}

	public void cancelRunnable(Runnable runnable) {
		synchronized (mQueue) {
			while (mQueue.remove(runnable)) {
			}
		}
	}

	/**
	 * 取消任务队列中某一类型的所有任务，不再有等待空闲什么的
	 * 
	 * @param type
	 */
	public void cancelAllRunnablesOfType(int type) {
		synchronized (mQueue) {
			ListIterator<Pair<Runnable, Integer>> iter = mQueue.listIterator();
			Pair<Runnable, Integer> p;
			while (iter.hasNext()) {
				p = iter.next();
				if (p.second == type) {
					iter.remove();
				}
			}
		}
	}

	/**
	 * 清空任务列表
	 */
	public void cancel() {
		synchronized (mQueue) {
			mQueue.clear();
		}
	}

	/**
	 * 在本线程中一次性将任务队列里的任务全部执行
	 */
	public void flush() {
		LinkedList<Pair<Runnable, Integer>> queue = new LinkedList<Pair<Runnable, Integer>>();
		synchronized (mQueue) {
			queue.addAll(mQueue);
			mQueue.clear();
		}
		for (Pair<Runnable, Integer> p : queue) {
			p.first.run();
		}
	}

	void scheduleNextLocked() {
		if (mQueue.size() > 0) {
			Pair<Runnable, Integer> p = mQueue.getFirst();
			Runnable peek = p.first;
			if (peek instanceof IdleRunnable) {
				mMessageQueue.addIdleHandler(mHandler);
			} else {
				mHandler.sendEmptyMessage(1);
			}
		}
	}
}
