
package kr.jm.utils;

import static kr.jm.utils.exception.JMExceptionManager.handleExceptionAndReturnNull;
import static kr.jm.utils.exception.JMExceptionManager.logException;
import static kr.jm.utils.helper.JMLog.info;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import kr.jm.utils.helper.JMStats;
import kr.jm.utils.helper.JMThread;

/**
 * The Class ProgressiveManager.
 *
 * @param <T>
 *            the generic type
 * @param <R>
 *            the generic type
 */
public class ProgressiveManager<T, R> {

	private static final org.slf4j.Logger log =
			org.slf4j.LoggerFactory.getLogger(ProgressiveManager.class);

	private Collection<T> targetCollection;
	private int totalCount;
	private SimpleIntegerProperty progressiveCount;
	private SimpleIntegerProperty progressivePercent;
	private SimpleObjectProperty<T> currentTarget;
	private SimpleObjectProperty<Optional<R>> lastResult;
	private List<Optional<R>> resultList;
	private List<Consumer<ProgressiveManager<T, R>>> completedConsumerList;
	private CompletableFuture<Void> completableFuture;
	private boolean isStoped;

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
				.append("resultList=" + resultList)
				.append("isStoped=" + isStoped).autoToString();
	}

	/**
	 * Instantiates a new progressive manager.
	 *
	 * @param targetCollection
	 *            the target collection
	 */
	public ProgressiveManager(Collection<T> targetCollection) {
		this.targetCollection = targetCollection;
		this.totalCount = targetCollection.size();
		this.progressiveCount = new SimpleIntegerProperty();
		this.progressivePercent = new SimpleIntegerProperty();
		this.currentTarget = new SimpleObjectProperty<>();
		this.lastResult = new SimpleObjectProperty<>();
		this.resultList = new ArrayList<>();
		this.completedConsumerList = new ArrayList<>();
		this.isStoped = true;
	}

	/**
	 * Start.
	 *
	 * @param processFunction
	 *            the process function
	 * @return the progressive manager
	 */
	public ProgressiveManager<T, R> start(Function<T, R> processFunction) {
		info(log, "start", processFunction);
		this.isStoped = false;
		this.completableFuture = JMThread.runAsync(
				() -> targetCollection.stream().filter(t -> !isStoped())
						.peek(this::setNextTarget).map(t -> {
							try {
								return processFunction.apply(t);
							} catch (Exception e) {
								return handleExceptionAndReturnNull(log, e,
										"start", t, processFunction);
							}
						}).map(Optional::ofNullable).peek(lastResult::set)
						.forEach(resultList::add))
				.thenRun(this::setStoped).thenRun(() -> completedConsumerList
						.forEach(c -> c.accept(this)));
		return this;
	}

	/**
	 * Stop sync.
	 *
	 * @return the progressive manager
	 */
	public ProgressiveManager<T, R> stopSync() {
		setStoped();
		return getAfterCompletion();
	}

	public ProgressiveManager<T, R> getAfterCompletion() {
		try {
			completableFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			logException(log, e, "stopSync");
		}
		return this;
	}

	/**
	 * Stop async.
	 *
	 * @return the progressive manager
	 */
	public ProgressiveManager<T, R> stopAsync() {
		setStoped();
		return this;
	}

	private void setStoped() {
		this.isStoped = true;
	}

	public boolean isStoped() {
		return this.isStoped;
	}

	/**
	 * Adds the completed consumer.
	 *
	 * @param completedConsumer
	 *            the completed consumer
	 * @return the progressive manager
	 */
	public ProgressiveManager<T, R> addCompletedConsumer(
			Consumer<ProgressiveManager<T, R>> completedConsumer) {
		completedConsumerList.add(completedConsumer);
		return this;
	}

	public List<Optional<R>> getResultList() {
		return new ArrayList<>(resultList);
	}

	/**
	 * Register last result change listener.
	 *
	 * @param resultChangeListener
	 *            the result change listener
	 * @return the progressive manager
	 */
	public ProgressiveManager<T, R> registerLastResultChangeListener(
			Consumer<Optional<R>> resultChangeListener) {
		return registerListener(lastResult, resultChangeListener);
	}

	private <P> ProgressiveManager<T, R> registerListener(
			ObservableValue<P> property, Consumer<P> listener) {
		property.addListener((op, o, n) -> listener.accept(n));
		return this;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public boolean isDone() {
		return completableFuture.isDone();
	}

	/**
	 * Register count change listener.
	 *
	 * @param countChangeListener
	 *            the count change listener
	 * @return the progressive manager
	 */
	public ProgressiveManager<T, R>
			registerCountChangeListener(Consumer<Number> countChangeListener) {
		return registerListener(progressiveCount, countChangeListener);
	}

	public int getProgressiveCount() {
		return this.progressiveCount.get();
	}

	private void setNextTarget(T target) {
		int progressiveCount = getProgressiveCount() + 1;
		int percent = JMStats.calPercent(progressiveCount, totalCount);
		this.progressivePercent.set(percent);
		this.progressiveCount.set(progressiveCount);
		this.currentTarget.set(target);
	}

	/**
	 * Register percent change listener.
	 *
	 * @param percentChangeListener
	 *            the percent change listener
	 * @return the progressive manager
	 */
	public ProgressiveManager<T, R> registerPercentChangeListener(
			Consumer<Number> percentChangeListener) {
		return registerListener(progressivePercent, percentChangeListener);
	}

	public int getProgressivePercent() {
		return this.progressivePercent.get();
	}

	/**
	 * Register target change listener.
	 *
	 * @param targetChangeListener
	 *            the target change listener
	 * @return the progressive manager
	 */
	public ProgressiveManager<T, R>
			registerTargetChangeListener(Consumer<T> targetChangeListener) {
		return registerListener(currentTarget, targetChangeListener);
	}

	public T getCurrentTarget() {
		return this.currentTarget.get();
	}

	public List<T> getTargetList() {
		return new ArrayList<>(targetCollection);
	}

}
