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
import java.util.function.Supplier;

public class JMThread {

	public static BlockingQueue<Runnable> getThreadQueue(
			ExecutorService executorService) {
		return ((ThreadPoolExecutor) executorService).getQueue();
	}

	public static int getActiveCount(ExecutorService executorService) {
		return ((ThreadPoolExecutor) executorService).getActiveCount();
	}

	public static long getCompletedTaskCount(ExecutorService executorService) {
		return ((ThreadPoolExecutor) executorService).getCompletedTaskCount();
	}

	public static void purge(ExecutorService executorService) {
		((ThreadPoolExecutor) executorService).purge();
	}

	public static long getPoolSize(ExecutorService executorService) {
		return ((ThreadPoolExecutor) executorService).getPoolSize();
	}

	public static ExecutorService newThreadPool(int numOfThreads) {
		return numOfThreads < 1 ? Executors.newCachedThreadPool()
				: Executors.newFixedThreadPool(numOfThreads);
	}

	public static ExecutorService newThreadPoolWithAvailableProcessors() {
		return Executors
				.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void run(final Runnable runnableWork,
			final long timeoutInSec) {
		final ExecutorService threadPool = Executors.newFixedThreadPool(2);
		afterTimeout(timeoutInSec, threadPool, threadPool.submit(runnableWork));
	}

	private static void afterTimeout(final long timeoutInSec,
			final ExecutorService threadPool, final Future<?> future) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					future.get(timeoutInSec, TimeUnit.SECONDS);
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					if (!threadPool.isShutdown())
						threadPool.shutdownNow();
				}
			}
		});
	}

	public static <T> Future<T> run(final Callable<T> callableWork,
			final long timeoutInSec) {
		final ExecutorService threadPool = Executors.newFixedThreadPool(2);
		Future<T> future = threadPool.submit(callableWork);
		afterTimeout(timeoutInSec, threadPool, future);
		return future;
	}

	public static ForkJoinPool getCommonPool() {
		return ForkJoinPool.commonPool();
	}

	public static void runAsync(Runnable runnable) {
		CompletableFuture.runAsync(runnable);
	}

	public static void runAsync(Runnable runnable, Executor executor) {
		CompletableFuture.runAsync(runnable, executor);
	}

	public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
		return CompletableFuture.supplyAsync(supplier);
	}

	public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier,
			Executor executor) {
		return CompletableFuture.supplyAsync(supplier, executor);
	}

}
