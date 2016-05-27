
package kr.jm.utils.helper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

import kr.jm.utils.exception.JMExceptionManager;

/**
 * The Class JMThread.
 */
public class JMThread {

	private static final org.slf4j.Logger log =
			org.slf4j.LoggerFactory.getLogger(JMThread.class);

	/**
	 * Gets the thread queue.
	 *
	 * @param executorService
	 *            the executor service
	 * @return the thread queue
	 */
	public static BlockingQueue<Runnable>
			getThreadQueue(ExecutorService executorService) {
		if (executorService instanceof ThreadPoolExecutor)
			return ((ThreadPoolExecutor) executorService).getQueue();
		throw JMExceptionManager.handleExceptionAndReturnRuntimeEx(log,
				new IllegalArgumentException(
						"Unsupport ExecutorService - Use ThrJMThread.newThreadPool Or newSingleThreadPool To Get ExecutorService !!!"),
				"getThreadQueue", executorService);
	}

	/**
	 * Gets the active count.
	 *
	 * @param executorService
	 *            the executor service
	 * @return the active count
	 */
	public static int getActiveCount(ExecutorService executorService) {
		return ((ThreadPoolExecutor) executorService).getActiveCount();
	}

	/**
	 * Gets the completed task count.
	 *
	 * @param executorService
	 *            the executor service
	 * @return the completed task count
	 */
	public static long getCompletedTaskCount(ExecutorService executorService) {
		return ((ThreadPoolExecutor) executorService).getCompletedTaskCount();
	}

	/**
	 * Purge.
	 *
	 * @param executorService
	 *            the executor service
	 */
	public static void purge(ExecutorService executorService) {
		((ThreadPoolExecutor) executorService).purge();
	}

	/**
	 * Gets the pool size.
	 *
	 * @param executorService
	 *            the executor service
	 * @return the pool size
	 */
	public static long getPoolSize(ExecutorService executorService) {
		return ((ThreadPoolExecutor) executorService).getPoolSize();
	}

	/**
	 * New thread pool.
	 *
	 * @param numOfThreads
	 *            the num of threads
	 * @return the executor service
	 */
	public static ExecutorService newThreadPool(int numOfThreads) {
		return numOfThreads < 1 ? Executors.newCachedThreadPool()
				: Executors.newFixedThreadPool(numOfThreads);
	}

	/**
	 * New single thread pool.
	 *
	 * @return the executor service
	 */
	public static ExecutorService newSingleThreadPool() {
		return Executors.newFixedThreadPool(1);
	}

	/**
	 * New thread pool with available processors.
	 *
	 * @return the executor service
	 */
	public static ExecutorService newThreadPoolWithAvailableProcessors() {
		return Executors
				.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	}

	/**
	 * Sleep.
	 *
	 * @param millis
	 *            the millis
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			JMExceptionManager.logException(log, e, "sleep", millis);
		}
	}

	/**
	 * Run.
	 *
	 * @param runnableWork
	 *            the runnable work
	 * @param timeoutInSec
	 *            the timeout in sec
	 */
	public static void run(final Runnable runnableWork,
			final long timeoutInSec) {
		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		afterTimeout(timeoutInSec, threadPool, threadPool.submit(runnableWork));
	}

	private static void afterTimeout(long timeoutInSec,
			ExecutorService threadPool, Future<?> future) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					future.get(timeoutInSec, TimeUnit.SECONDS);
				} catch (Exception e) {
					JMExceptionManager.logException(log, e, "afterTimeout",
							timeoutInSec, threadPool, future);
				} finally {
					if (!threadPool.isShutdown())
						threadPool.shutdownNow();
				}
			}
		});
	}

	/**
	 * Run.
	 *
	 * @param <T>
	 *            the generic type
	 * @param callableWork
	 *            the callable work
	 * @param timeoutInSec
	 *            the timeout in sec
	 * @return the future
	 */
	public static <T> Future<T> run(final Callable<T> callableWork,
			final long timeoutInSec) {
		final ExecutorService threadPool = Executors.newFixedThreadPool(2);
		Future<T> future = threadPool.submit(callableWork);
		afterTimeout(timeoutInSec, threadPool, future);
		return future;
	}

	/**
	 * Gets the common pool.
	 *
	 * @return the common pool
	 */
	public static ForkJoinPool getCommonPool() {
		return ForkJoinPool.commonPool();
	}

	/**
	 * Run async.
	 *
	 * @param runnable
	 *            the runnable
	 */
	public static void runAsync(Runnable runnable) {
		CompletableFuture.runAsync(runnable)
				.exceptionally(handleExceptionally("runAsync", runnable));
	}

	private static Function<Throwable, ? extends Void>
			handleExceptionally(String methodName, Object... objects) {
		return throwable -> {
			throw JMExceptionManager.handleExceptionAndReturnRuntimeEx(log,
					throwable, methodName, objects);
		};
	}

	/**
	 * Run async.
	 *
	 * @param runnable
	 *            the runnable
	 * @param executor
	 *            the executor
	 */
	public static void runAsync(Runnable runnable, Executor executor) {
		CompletableFuture.runAsync(runnable, executor).exceptionally(
				handleExceptionally("runAsync", runnable, executor));
	}

	/**
	 * Supply async.
	 *
	 * @param <U>
	 *            the generic type
	 * @param supplier
	 *            the supplier
	 * @param failureSupplier
	 *            the failure supplier
	 * @return the completable future
	 */
	public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier,
			Supplier<U> failureSupplier) {
		return CompletableFuture.supplyAsync(supplier)
				.exceptionally(throwable -> JMExceptionManager
						.handleExceptionAndReturn(log, throwable, "supplyAsync",
								failureSupplier, supplier));
	}

	/**
	 * Supply async.
	 *
	 * @param <U>
	 *            the generic type
	 * @param supplier
	 *            the supplier
	 * @param failureSupplier
	 *            the failure supplier
	 * @param executor
	 *            the executor
	 * @return the completable future
	 */
	public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier,
			Supplier<U> failureSupplier, Executor executor) {
		return CompletableFuture.supplyAsync(supplier, executor)
				.exceptionally(throwable -> JMExceptionManager
						.handleExceptionAndReturn(log, throwable, "supplyAsync",
								failureSupplier, supplier, executor));
	}

}
