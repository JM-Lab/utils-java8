
package kr.jm.utils.helper;

import kr.jm.utils.enums.OS;
import kr.jm.utils.exception.JMExceptionManager;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static kr.jm.utils.exception.JMExceptionManager.handleExceptionAndReturn;
import static kr.jm.utils.exception.JMExceptionManager.handleExceptionAndReturnNull;
import static kr.jm.utils.helper.JMOptional.ifNotNull;

/**
 * The type Jm thread.
 */
public class JMThread {

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(JMThread.class);

    /**
     * Gets thread queue.
     *
     * @param executorService the executor service
     * @return the thread queue
     */
    public static BlockingQueue<Runnable> getThreadQueue(
            ExecutorService executorService) {
        if (executorService instanceof ThreadPoolExecutor)
            return ((ThreadPoolExecutor) executorService).getQueue();
        throw JMExceptionManager.handleExceptionAndReturnRuntimeEx(log,
                new IllegalArgumentException(
                        "Unsupported ExecutorService - Use ThrJMThread.newThreadPool Or newSingleThreadPool To Get ExecutorService !!!"),
                "getThreadQueue", executorService);
    }

    /**
     * Gets active count.
     *
     * @param executorService the executor service
     * @return the active count
     */
    public static int getActiveCount(ExecutorService executorService) {
        return ((ThreadPoolExecutor) executorService).getActiveCount();
    }

    /**
     * Gets completed task count.
     *
     * @param executorService the executor service
     * @return the completed task count
     */
    public static long getCompletedTaskCount(ExecutorService executorService) {
        return ((ThreadPoolExecutor) executorService).getCompletedTaskCount();
    }

    /**
     * Purge.
     *
     * @param executorService the executor service
     */
    public static void purge(ExecutorService executorService) {
        ((ThreadPoolExecutor) executorService).purge();
    }

    /**
     * Gets pool size.
     *
     * @param executorService the executor service
     * @return the pool size
     */
    public static long getPoolSize(ExecutorService executorService) {
        return ((ThreadPoolExecutor) executorService).getPoolSize();
    }

    /**
     * Shutdown and wait to be terminated.
     *
     * @param executorService the executor service
     */
    public static void shutdownAndWaitToBeTerminated(ExecutorService
            executorService) {
        if (executorService.isTerminated())
            return;
        log.warn("Start Shutdown !!! - {}", executorService);
        long startTimeMillis = System.currentTimeMillis();
        executorService.shutdown();
        while (!executorService.isTerminated())
            sleep(100);
        log.warn("Terminating !!! - {} over {} ms", executorService,
                startTimeMillis - System.currentTimeMillis());
    }

    /**
     * Shutdown now and wait to be terminated list.
     *
     * @param executorService the executor service
     * @return the list
     */
    public static List<Runnable> shutdownNowAndWaitToBeTerminated(
            ExecutorService executorService) {
        if (!executorService.isTerminated())
            return Collections.emptyList();
        log.warn("Shutdown Now!!! - {}", executorService);
        return executorService.shutdownNow();
    }


    /**
     * New thread pool executor service.
     *
     * @param numOfThreads the num of threads
     * @return the executor service
     */
    public static ExecutorService newThreadPool(int numOfThreads) {
        return numOfThreads < 1 ? Executors.newCachedThreadPool()
                : Executors.newFixedThreadPool(numOfThreads);
    }

    /**
     * New single thread pool executor service.
     *
     * @return the executor service
     */
    public static ExecutorService newSingleThreadPool() {
        return Executors.newFixedThreadPool(1);
    }

    /**
     * New thread pool with available processors executor service.
     *
     * @return the executor service
     */
    public static ExecutorService newThreadPoolWithAvailableProcessors() {
        return Executors.newFixedThreadPool(OS.getAvailableProcessors());
    }

    /**
     * New thread pool with available processors minus one executor service.
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
     * @param millis the millis
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            JMExceptionManager.logException(log, e, "sleep", millis);
        }
    }

    /**
     * Suspend.
     *
     * @param intervalAsMillis         the interval as millis
     * @param suspendConditionSupplier the suspend condition supplier
     */
    public static void suspend(long intervalAsMillis,
            Supplier<Boolean> suspendConditionSupplier) {
        if (!suspendConditionSupplier.get())
            return;
        log.warn("Start Suspending !!!");
        long startTimeMillis = System.currentTimeMillis();
        while (suspendConditionSupplier.get())
            sleep(intervalAsMillis);
        log.warn("Stop Suspending Over {} ms",
                System.currentTimeMillis() - startTimeMillis);
    }

    /**
     * Suspend when null r.
     *
     * @param <R>              the type parameter
     * @param intervalAsMillis the interval as millis
     * @param objectSupplier   the object supplier
     * @return the r
     */
    public static <R> R suspendWhenNull(long intervalAsMillis,
            Supplier<R> objectSupplier) {
        R object = objectSupplier.get();
        if (object == null) {
            log.warn("Start Suspending !!!");
            long startTimeMillis = System.currentTimeMillis();
            while ((object = objectSupplier.get()) == null)
                sleep(intervalAsMillis);
            log.warn("Stop Suspending Over {} ms",
                    System.currentTimeMillis() - startTimeMillis);
        }
        return object;
    }

    /**
     * Run.
     *
     * @param runnableWork the runnable work
     * @param timeoutInSec the timeout in sec
     */
    public static void run(final Runnable runnableWork,
            final long timeoutInSec) {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        afterTimeout(timeoutInSec, threadPool, threadPool.submit(runnableWork));
    }

    private static void afterTimeout(long timeoutInSec,
            ExecutorService threadPool, Future<?> future) {
        threadPool.execute(() -> {
            try {
                future.get(timeoutInSec, TimeUnit.SECONDS);
            } catch (Exception e) {
                JMExceptionManager.logException(log, e, "afterTimeout",
                        timeoutInSec, threadPool, future);
            } finally {
                if (!threadPool.isShutdown())
                    threadPool.shutdownNow();
            }
        });
    }

    /**
     * Run future.
     *
     * @param <T>          the type parameter
     * @param callableWork the callable work
     * @param timeoutInSec the timeout in sec
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
     * Gets common pool.
     *
     * @return the common pool
     */
    public static ForkJoinPool getCommonPool() {
        return ForkJoinPool.commonPool();
    }

    /**
     * Run with schedule scheduled future.
     *
     * @param <V>         the type parameter
     * @param delayMillis the delay millis
     * @param callable    the callable
     * @return the scheduled future
     */
    public static <V> ScheduledFuture<V> runWithSchedule(long delayMillis,
            Callable<V> callable) {
        return newSingleScheduledThreadPool().schedule(
                buildCallableWithLogging("runWithSchedule", callable),
                delayMillis, TimeUnit.MILLISECONDS);
    }

    private static ScheduledExecutorService newSingleScheduledThreadPool() {
        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(1);
        OS.addShutdownHook(scheduledExecutorService::shutdown);
        return scheduledExecutorService;
    }

    /**
     * Build callable with logging callable.
     *
     * @param <V>      the type parameter
     * @param name     the name
     * @param callable the callable
     * @param params   the params
     * @return the callable
     */
    public static <V> Callable<V> buildCallableWithLogging(String name,
            Callable<V> callable, Object... params) {
        return () -> supplyAsync(() -> {
            try {
                JMLog.debug(log, name, System.currentTimeMillis(), params);
                return callable.call();
            } catch (Exception e) {
                return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                        name, params);
            }
        }).get();
    }

    /**
     * Build runnable with logging runnable.
     *
     * @param runnableName the runnable name
     * @param runnable     the runnable
     * @param params       the params
     * @return the runnable
     */
    public static Runnable buildRunnableWithLogging(String runnableName,
            Runnable runnable, Object... params) {
        return () -> {
            JMLog.debug(log, runnableName, System.currentTimeMillis(), params);
            runnable.run();
        };
    }

    /**
     * Run with schedule scheduled future.
     *
     * @param delayMillis the delay millis
     * @param runnable    the runnable
     * @return the scheduled future
     */
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
                () -> runAsync(buildRunnableWithLogging(name, runnable,
                        initialDelayMillis, periodMillis)),
                initialDelayMillis, periodMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Run with schedule at fixed rate scheduled future.
     *
     * @param initialDelayMillis the initial delay millis
     * @param periodMillis       the period millis
     * @param runnable           the runnable
     * @return the scheduled future
     */
    public static ScheduledFuture<?> runWithScheduleAtFixedRate(
            long initialDelayMillis, long periodMillis, Runnable runnable) {
        return runWithScheduleAtFixedRate(initialDelayMillis, periodMillis,
                "runWithScheduleAtFixedRate", runnable);
    }

    /**
     * Run with schedule at fixed rate on start time scheduled future.
     *
     * @param startDateTime the start date time
     * @param periodMillis  the period millis
     * @param runnable      the runnable
     * @return the scheduled future
     */
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
                () -> runAsync(buildRunnableWithLogging(name, runnable,
                        initialDelayMillis, delayMillis)).join(),
                initialDelayMillis, delayMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Run with schedule with fixed delay scheduled future.
     *
     * @param initialDelayMillis the initial delay millis
     * @param delayMillis        the delay millis
     * @param runnable           the runnable
     * @return the scheduled future
     */
    public static ScheduledFuture<?> runWithScheduleWithFixedDelay(
            long initialDelayMillis, long delayMillis, Runnable runnable) {
        return runWithScheduleWithFixedDelay(initialDelayMillis, delayMillis,
                "runWithScheduleWithFixedDelay", runnable);
    }

    private static long calInitialDelayMillis(ZonedDateTime startDateTime) {
        return startDateTime.toInstant().toEpochMilli()
                - System.currentTimeMillis();
    }

    /**
     * Run with schedule with fixed delay on start time scheduled future.
     *
     * @param startDateTime the start date time
     * @param delayMillis   the delay millis
     * @param runnable      the runnable
     * @return the scheduled future
     */
    public static ScheduledFuture<?> runWithScheduleWithFixedDelayOnStartTime(
            ZonedDateTime startDateTime, long delayMillis, Runnable runnable) {
        return runWithScheduleWithFixedDelay(
                calInitialDelayMillis(startDateTime), delayMillis,
                "runWithScheduleWithFixedDelayOnStartTime", runnable);
    }

    /**
     * Run async completable future.
     *
     * @param runnable the runnable
     * @return the completable future
     */
    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return runAsync(runnable, getCommonPool());
    }

    /**
     * Run async completable future.
     *
     * @param runnable the runnable
     * @param executor the executor
     * @return the completable future
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
     * Run async completable future.
     *
     * @param runnable        the runnable
     * @param failureConsumer the failure consumer
     * @param executor        the executor
     * @return the completable future
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
     * Supply async completable future.
     *
     * @param <U>      the type parameter
     * @param supplier the supplier
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
     * Supply async completable future.
     *
     * @param <U>      the type parameter
     * @param supplier the supplier
     * @param executor the executor
     * @return the completable future
     */
    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier,
            Executor executor) {
        return CompletableFuture
                .supplyAsync(handleSupplierWithException(supplier), executor);
    }

    /**
     * Supply async completable future.
     *
     * @param <U>             the type parameter
     * @param supplier        the supplier
     * @param failureFunction the failure function
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
     * Supply async completable future.
     *
     * @param <U>             the type parameter
     * @param supplier        the supplier
     * @param failureFunction the failure function
     * @param executor        the executor
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

    /**
     * Start with single executor service executor service.
     *
     * @param message  the message
     * @param runnable the runnable
     * @return the executor service
     */
    public static ExecutorService startWithSingleExecutorService(String message,
            Runnable runnable) {
        return startWithExecutorService(newSingleThreadPool(), message,
                runnable);
    }

    /**
     * Start with executor service executor service.
     *
     * @param message  the message
     * @param runnable the runnable
     * @return the executor service
     */
    public static ExecutorService startWithExecutorService(String message,
            Runnable runnable) {
        return startWithExecutorService(newThreadPoolWithAvailableProcessors(),
                message, runnable);
    }

    /**
     * Start with executor service executor service.
     *
     * @param executorService the executor service
     * @param message         the message
     * @param runnable        the runnable
     * @return the executor service
     */
    public static ExecutorService startWithExecutorService(
            ExecutorService executorService, String message,
            Runnable runnable) {
        runAsync(() -> {
            JMLog.info(log, "startWithExecutorService", message);
            runnable.run();
        }, executorService);
        return executorService;
    }
}
