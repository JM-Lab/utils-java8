
package kr.jm.utils.helper;

import static kr.jm.utils.exception.JMExceptionManager.handleExceptionAndReturn;
import static kr.jm.utils.exception.JMExceptionManager.handleExceptionAndReturnNull;
import static kr.jm.utils.helper.JMOptional.ifNotNull;

import java.time.ZonedDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import kr.jm.utils.enums.OS;
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
		return Executors.newFixedThreadPool(OS.getAvailableProcessors());
	}

	/**
	 * New thread pool with available processors minus one.
	 *
	 * @return the executor service
	 */
	public static ExecutorService
			newThreadPoolWithAvailableProcessorsMinusOne() {
		return Executors.newFixedThreadPool(OS.getAvailableProcessors() - 1);
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
	public static <T> Future<T> run(Callable<T> callableWork,
			long timeoutInSec) {
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

	public static <V> ScheduledFuture<V> runWithSchedule(long delayMillis,
			Callable<V> callable) {
		return newSingleScheduledThreadPool().schedule(
				buildCallableWithLogging("runWithSchedule", callable),
				delayMillis, TimeUnit.MILLISECONDS);
	}

	private static ScheduledExecutorService newSingleScheduledThreadPool() {
		return Executors.newScheduledThreadPool(1);
	}

	public static <V> Callable<V> buildCallableWithLogging(String name,
			Callable<V> callable, Object... params) {
		return () -> {
			try {
				JMLog.info(log, name, System.currentTimeMillis(), params);
				return callable.call();
			} catch (Exception e) {
				return JMExceptionManager.handleExceptionAndReturn(log, e, name,
						() -> null, params);
			}
		};
	}

	public static Runnable buildRunnableWithLogging(String runnableName,
			Runnable runnable, Object... params) {
		return () -> {
			try {
				JMLog.info(log, runnableName, System.currentTimeMillis(),
						params);
				runnable.run();
			} catch (Exception e) {
				JMExceptionManager.logException(log, e, runnableName, params);
			}
		};
	}

	public static ScheduledFuture<?> runWithSchedule(long delayMillis,
			Runnable runnable) {
		return newSingleScheduledThreadPool()
				.schedule(
						buildRunnableWithLogging("runWithSchedule", runnable,
								delayMillis),
						delayMillis, TimeUnit.MILLISECONDS);
	}

	private static ScheduledFuture<?> runWithScheduleAtFixedRate(
			long initialDelayMillis, long periodMillis, String name,
			Runnable runnable) {
		return newSingleScheduledThreadPool().scheduleAtFixedRate(
				buildRunnableWithLogging(name, runnable, initialDelayMillis,
						periodMillis),
				initialDelayMillis, periodMillis, TimeUnit.MILLISECONDS);
	}

	public static ScheduledFuture<?> runWithScheduleAtFixedRate(
			long initialDelayMillis, long periodMillis, Runnable runnable) {
		return runWithScheduleAtFixedRate(initialDelayMillis, periodMillis,
				"runWithScheduleAtFixedRate", runnable);
	}

	public static ScheduledFuture<?> runWithScheduleAtFixedRateOnStartTime(
			ZonedDateTime startDateTime, long periodMillis, Runnable runnable) {
		return runWithScheduleAtFixedRate(calInitialDelayMillis(startDateTime),
				periodMillis, "runWithScheduleAtFixedRateOnStartTime",
				runnable);
	}

	private static ScheduledFuture<?> runWithScheduleWithFixedDelay(
			long initialDelayMillis, long delayMillis, String name,
			Runnable runnable) {
		return newSingleScheduledThreadPool().scheduleWithFixedDelay(
				buildRunnableWithLogging(name, runnable, initialDelayMillis,
						delayMillis),
				initialDelayMillis, delayMillis, TimeUnit.MILLISECONDS);
	}

	public static ScheduledFuture<?> runWithScheduleWithFixedDelay(
			long initialDelayMillis, long delayMillis, Runnable runnable) {
		return runWithScheduleWithFixedDelay(initialDelayMillis, delayMillis,
				"runWithScheduleWithFixedDelay", runnable);
	}

	private static long calInitialDelayMillis(ZonedDateTime startDateTime) {
		return startDateTime.toInstant().toEpochMilli()
				- System.currentTimeMillis();
	}

	public static ScheduledFuture<?> runWithScheduleWithFixedDelayOnStartTime(
			ZonedDateTime startDateTime, long delayMillis, Runnable runnable) {
		return runWithScheduleWithFixedDelay(
				calInitialDelayMillis(startDateTime), delayMillis,
				"runWithScheduleWithFixedDelayOnStartTime", runnable);
	}

	/**
	 * Run async.
	 *
	 * @param runnable
	 *            the runnable
	 * @return the completable future
	 */
	public static CompletableFuture<Void> runAsync(Runnable runnable) {
		return runAsync(runnable, getCommonPool());
	}

	/**
	 * Run async.
	 *
	 * @param runnable
	 *            the runnable
	 * @param executor
	 *            the executor
	 * @return
	 */
	public static CompletableFuture<Void> runAsync(Runnable runnable,
			Executor executor) {
		return runAsync(runnable,
				handleExceptionally("runAsync", runnable, executor), executor);
	}

	private static Consumer<Throwable> handleExceptionally(String methodName,
			Object... objects) {
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
	 * @param failureConsumer
	 *            the failure consumer
	 * @param executor
	 *            the executor
	 * @return
	 */
	public static CompletableFuture<Void> runAsync(Runnable runnable,
			Consumer<Throwable> failureConsumer, Executor executor) {
		return CompletableFuture.runAsync(runnable, executor)
				.exceptionally(e -> {
					ifNotNull(failureConsumer, c -> c.accept(e));
					return (Void) new Object();
				});
	}

	/**
	 * Supply async.
	 *
	 * @param <U>
	 *            the generic type
	 * @param supplier
	 *            the supplier
	 * @return the completable future
	 */
	public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
		return CompletableFuture
				.supplyAsync(handleSupplierWithException(supplier));
	}

	private static <U> Supplier<U>
			handleSupplierWithException(Supplier<U> supplier) {
		return () -> {
			try {
				return supplier.get();
			} catch (Exception e) {
				return handleExceptionAndReturnNull(log, e, "supplyAsync",
						supplier);
			}
		};
	}

	/**
	 * Supply async.
	 *
	 * @param <U>
	 *            the generic type
	 * @param supplier
	 *            the supplier
	 * @param executor
	 *            the executor
	 * @return the completable future
	 */
	public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier,
			Executor executor) {
		return CompletableFuture
				.supplyAsync(handleSupplierWithException(supplier), executor);
	}

	/**
	 * Supply async.
	 *
	 * @param <U>
	 *            the generic type
	 * @param supplier
	 *            the supplier
	 * @param failureFunction
	 *            the failure function
	 * @return the completable future
	 */
	public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier,
			Function<Throwable, U> failureFunction) {
		return CompletableFuture.supplyAsync(supplier)
				.exceptionally(throwable -> handleExceptionAndReturn(log,
						throwable, "supplyAsync",
						() -> failureFunction.apply(throwable), supplier));
	}

	/**
	 * Supply async.
	 *
	 * @param <U>
	 *            the generic type
	 * @param supplier
	 *            the supplier
	 * @param failureFunction
	 *            the failure function
	 * @param executor
	 *            the executor
	 * @return the completable future
	 */
	public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier,
			Function<Throwable, U> failureFunction, Executor executor) {
		return CompletableFuture.supplyAsync(supplier, executor)
				.exceptionally(throwable -> handleExceptionAndReturn(log,
						throwable, "supplyAsync",
						() -> failureFunction.apply(throwable), supplier,
						executor));
	}

}
