
package kr.jm.utils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.util.Pair;
import kr.jm.utils.datastructure.JMMap;
import kr.jm.utils.helper.JMThread;
import kr.jm.utils.stats.JMStats;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;

import static kr.jm.utils.exception.JMExceptionManager.handleExceptionAndReturnEmptyOptional;
import static kr.jm.utils.exception.JMExceptionManager.logException;
import static kr.jm.utils.helper.JMLog.info;
import static kr.jm.utils.helper.JMPredicate.negate;

/**
 * The Class JMProgressiveManager.
 *
 * @param <T> the generic type
 * @param <R> the generic type
 */
public class JMProgressiveManager<T, R> {

	private static final org.slf4j.Logger log =
			org.slf4j.LoggerFactory.getLogger(JMProgressiveManager.class);

	private Collection<T> targetCollection;
	private int totalCount;
	private SimpleIntegerProperty progressiveCount;
	private SimpleIntegerProperty progressivePercent;
	private SimpleObjectProperty<T> currentTarget;
	private SimpleObjectProperty<Optional<R>> lastResult;
	private SimpleObjectProperty<Pair<T, Exception>> lastFailure;
	private Map<T, Optional<R>> resultMap;
	private Map<T, Exception> failureMap;
	private List<Consumer<JMProgressiveManager<T, R>>> completedConsumerList;
	private CompletableFuture<Void> completableFuture;
	private boolean isStopped;
	private Function<T, Optional<R>> processFunction;

    /**
     * Instantiates a new JM progressive manager.
     *
     * @param targetCollection the target collection
     * @param processFunction  the process function
     */
    public JMProgressiveManager(Collection<T> targetCollection,
			Function<T, Optional<R>> processFunction) {
		this.targetCollection = targetCollection;
		this.processFunction = processFunction;
		this.totalCount = targetCollection.size();
		this.progressiveCount = new SimpleIntegerProperty();
		this.progressivePercent = new SimpleIntegerProperty();
		this.currentTarget = new SimpleObjectProperty<>();
		this.lastResult = new SimpleObjectProperty<>();
		this.lastFailure = new SimpleObjectProperty<>();
		this.resultMap = new HashMap<>();
		this.failureMap = new HashMap<>();
		this.isStopped = false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		AutoStringBuilder asb = new AutoStringBuilder(", ");
		return asb.append("totalCount=" + totalCount)
				.append("progressiveCount=" + progressiveCount)
				.append("progressivePercent=" + progressivePercent)
				.append("currentTarget=" + currentTarget)
				.append("lastResult=" + lastResult)
				.append("resultList=" + resultMap)
				.append("isStopped=" + isStopped).autoToString();
	}

    /**
     * Start.
     *
     * @return the JM progressive manager
     */
    public JMProgressiveManager<T, R> start() {
		info(log, "start");
		this.completableFuture = JMThread
				.runAsync(() -> targetCollection.stream()
						.filter(negate(isStopped())).map(this::moveNextTarget)
						.map(t -> JMMap.putGetNew(resultMap, t, process(t)))
						.forEach(lastResult::set))
				.thenRun(this::setStopped)
				.thenRun(() -> Optional.ofNullable(completedConsumerList)
						.ifPresent(list -> list.parallelStream()
								.forEach(c -> c.accept(this))));
		return this;
	}

	private Optional<R> process(T t) {
		try {
			Optional<R> resultAsOpt = processFunction.apply(t);
			if (resultAsOpt.isPresent())
				return resultAsOpt;
			throw new RuntimeException("Process Failure !!! - " + t);
		} catch (Exception e) {
			lastFailure.set(new Pair<>(t, e));
			failureMap.put(t, e);
			return handleExceptionAndReturnEmptyOptional(log, e, "start", t);
		}
	}

    /**
     * Stop async.
     *
     * @return the JM progressive manager
     */
    public JMProgressiveManager<T, R> stopAsync() {
		setStopped();
		return this;
	}

    /**
     * Stop sync.
     *
     * @return the JM progressive manager
     */
    public JMProgressiveManager<T, R> stopSync() {
		return stopAsync().getAfterCompletion();
	}

    /**
     * Gets the after completion.
     *
     * @return the after completion
     */
    public JMProgressiveManager<T, R> getAfterCompletion() {
		try {
			completableFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			logException(log, e, "stopSync");
		}
		return this;
	}

	private void setStopped() {
		this.isStopped = true;
	}

    /**
     * Checks if is stopped.
     *
     * @return true, if is stopped
     */
    public boolean isStopped() {
		return this.isStopped;
	}

    /**
     * Register completed consumer.
     *
     * @param completedConsumer the completed consumer
     * @return the JM progressive manager
     */
    public JMProgressiveManager<T, R> registerCompletedConsumer(
			Consumer<JMProgressiveManager<T, R>> completedConsumer) {
		Optional.ofNullable(completedConsumerList)
				.orElseGet(() -> completedConsumerList = new ArrayList<>())
				.add(completedConsumer);
		return this;
	}

    /**
     * Gets the result list sync.
     *
     * @return the result list sync
     */
    public Map<T, Optional<R>> getResultMapSync() {
		return new HashMap<>(getAfterCompletion().resultMap);
	}

    /**
     * Gets the failure map sync.
     *
     * @return the failure map sync
     */
    public Map<T, Exception> getFailureMapSync() {
		return new HashMap<>(getAfterCompletion().failureMap);
	}

    /**
     * Register last result change listener.
     *
     * @param resultChangeListener the result change listener
     * @return the JM progressive manager
     */
    public JMProgressiveManager<T, R> registerLastResultChangeListener(
			Consumer<Optional<R>> resultChangeListener) {
		return registerListener(lastResult, resultChangeListener);
	}

    /**
     * Register last failure change listener.
     *
     * @param failureChangeListener the failure change listener
     * @return the JM progressive manager
     */
    public JMProgressiveManager<T, R> registerLastFailureChangeListener(
			Consumer<Pair<T, Exception>> failureChangeListener) {
		return registerListener(lastFailure, failureChangeListener);
	}

	private <P> JMProgressiveManager<T, R> registerListener(
			ObservableValue<P> property, Consumer<P> listener) {
		property.addListener((op, o, n) -> listener.accept(n));
		return this;
	}

    /**
     * Gets the total count.
     *
     * @return the total count
     */
    public int getTotalCount() {
		return this.totalCount;
	}

    /**
     * Checks if is done.
     *
     * @return true, if is done
     */
    public boolean isDone() {
		return completableFuture.isDone();
	}

    /**
     * Register count change listener.
     *
     * @param countChangeListener the count change listener
     * @return the JM progressive manager
     */
    public JMProgressiveManager<T, R>
			registerCountChangeListener(Consumer<Number> countChangeListener) {
		return registerListener(progressiveCount, countChangeListener);
	}

    /**
     * Gets the progressive count.
     *
     * @return the progressive count
     */
    public int getProgressiveCount() {
		return this.progressiveCount.get();
	}

	private T moveNextTarget(T target) {
		this.progressiveCount.set(getProgressiveCount() + 1);
		this.progressivePercent
				.set(JMStats.calPercent(getProgressiveCount(), totalCount));
		this.currentTarget.set(target);
		return target;
	}

    /**
     * Register percent change listener.
     *
     * @param percentChangeListener the percent change listener
     * @return the JM progressive manager
     */
    public JMProgressiveManager<T, R> registerPercentChangeListener(
			Consumer<Number> percentChangeListener) {
		return registerListener(progressivePercent, percentChangeListener);
	}

    /**
     * Gets the progressive percent.
     *
     * @return the progressive percent
     */
    public int getProgressivePercent() {
		return this.progressivePercent.get();
	}

    /**
     * Register target change listener.
     *
     * @param targetChangeListener the target change listener
     * @return the JM progressive manager
     */
    public JMProgressiveManager<T, R>
			registerTargetChangeListener(Consumer<T> targetChangeListener) {
		return registerListener(currentTarget, targetChangeListener);
	}

    /**
     * Gets the current target.
     *
     * @return the current target
     */
    public T getCurrentTarget() {
		return this.currentTarget.get();
	}

    /**
     * Gets the target list.
     *
     * @return the target list
     */
    public List<T> getTargetList() {
		return new ArrayList<>(targetCollection);
	}

    /**
     * Gets the process function.
     *
     * @return the process function
     */
    public Function<T, Optional<R>> getProcessFunction() {
		return processFunction;
	}

}
