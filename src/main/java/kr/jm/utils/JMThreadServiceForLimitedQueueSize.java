package kr.jm.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import kr.jm.utils.helper.JMThread;

/**
 * The Class JMThreadServiceForLimitedQueueSize.
 */
public class JMThreadServiceForLimitedQueueSize {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(JMThreadServiceForLimitedQueueSize.class);
	private int waitMillis;
	private int queueSize;
	private ExecutorService threadPool;
	private BlockingQueue<Runnable> threadQueue;

	/**
	 * Instantiates a new JM thread service for limited queue size.
	 *
	 * @param numOfThread
	 *            the num of thread
	 * @param waitMillis
	 *            the wait millis
	 * @param queueSize
	 *            the queue size
	 */
	public JMThreadServiceForLimitedQueueSize(int numOfThread, int waitMillis,
			int queueSize) {
		this(numOfThread, waitMillis, queueSize,
				JMThread.newThreadPool(numOfThread));
	}

	/**
	 * Instantiates a new JM thread service for limited queue size.
	 *
	 * @param numOfThread
	 *            the num of thread
	 * @param waitMillis
	 *            the wait millis
	 * @param queueSize
	 *            the queue size
	 * @param threadPool
	 *            the thread pool
	 */
	public JMThreadServiceForLimitedQueueSize(int numOfThread, int waitMillis,
			int queueSize, ExecutorService threadPool) {
		this.waitMillis = waitMillis;
		this.queueSize = queueSize;
		this.threadQueue = JMThread.getThreadQueue(threadPool);
		log.info(
				"JMThreadService Start !!! - Number Of Worker = {}, Wait Millis = {}, Queue Size = {}",
				numOfThread, waitMillis, queueSize);
	}

	/**
	 * Gets the current queue size.
	 *
	 * @return the current queue size
	 */
	public int getCurrentQueueSize() {
		return threadQueue.size();
	}

	/**
	 * Gets the queue size.
	 *
	 * @return the queue size
	 */
	public int getQueueSize() {
		return queueSize;
	}

	/**
	 * Submit.
	 *
	 * @param runnable
	 *            the runnable
	 * @return the future
	 */
	public Future<?> submit(Runnable runnable) {
		checkThreadQueue();
		return threadPool.submit(runnable);
	}

	/**
	 * Submit.
	 *
	 * @param <T>
	 *            the generic type
	 * @param callable
	 *            the callable
	 * @return the future
	 */
	public <T> Future<T> submit(Callable<T> callable) {
		checkThreadQueue();
		return threadPool.submit(callable);
	}

	/**
	 * Execute.
	 *
	 * @param runnable
	 *            the runnable
	 */
	public void execute(Runnable runnable) {
		checkThreadQueue();
		threadPool.execute(runnable);
	}

	private void checkThreadQueue() {
		while (threadQueue.size() > queueSize) {
			log.warn(
					"Hit The Maximum Queue Size Of Thread Pool !!! - Wait Millis = {}, Queue Size = {}",
					waitMillis, queueSize);
			JMThread.sleep(waitMillis);
		}
	}
}
