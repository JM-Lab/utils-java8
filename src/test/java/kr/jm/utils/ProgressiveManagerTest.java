package kr.jm.utils;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import kr.jm.utils.helper.JMStream;
import kr.jm.utils.helper.JMThread;

/**
 * The Class ProgressiveManagerTest.
 */
public class ProgressiveManagerTest {
	private List<String> targetCollection = JMStream.numberRange(0, 1000, 1)
			.boxed().map(i -> "index-" + i).collect(toList());
	private ProgressiveManager<String, String> progressiveManager;

	/**
	 * Test start and stop.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testStartAndStop() throws Exception {
		progressiveManager = new ProgressiveManager<>(targetCollection, s -> {
			System.out
					.println(getCurrentThread() + " [Async] processing - " + s);
			JMThread.sleep(1);
			return Optional.of(s);
		}).registerCountChangeListener(i -> System.out
				.println(getCurrentThread() + " count change - " + i))
				.registerPercentChangeListener(i -> System.out
						.println(getCurrentThread() + " percent change - " + i))
				.registerTargetChangeListener(i -> System.out
						.println(getCurrentThread() + " target change - " + i))
				.registerLastResultChangeListener(i -> System.out.println(
						getCurrentThread() + " last result change - " + i))
				.addCompletedConsumer(progressiveManager -> System.out
						.println(getCurrentThread() + " process complete - "
								+ progressiveManager));
		progressiveManager.start();
		JMThread.sleep(50);
		assertTrue(!progressiveManager.stopAsync().isDone());
		System.out.println(getCurrentThread() + " Stop !!! - "
				+ progressiveManager.getAfterCompletion());
		assertFalse(progressiveManager.toString().contains("null"));
		progressiveManager = new ProgressiveManager<>(targetCollection, s -> {
			System.out
					.println(getCurrentThread() + " [Sync] processing - " + s);
			JMThread.sleep(1);
			return Optional.of(s);
		}).registerCountChangeListener(i -> System.out
				.println(getCurrentThread() + " count change - " + i))
				.registerPercentChangeListener(i -> System.out
						.println(getCurrentThread() + " percent change - " + i))
				.registerTargetChangeListener(i -> System.out
						.println(getCurrentThread() + " target change - " + i))
				.registerLastResultChangeListener(i -> System.out.println(
						getCurrentThread() + " last result change - " + i))
				.addCompletedConsumer(progressiveManager -> System.out
						.println(getCurrentThread() + " process complete - "
								+ progressiveManager));
		progressiveManager.start();
		JMThread.sleep(50);
		assertTrue(progressiveManager.stopSync().isDone());
		assertFalse(progressiveManager.toString().contains("null"));
	}

	private String getCurrentThread() {
		return Thread.currentThread().getName();
	}
}
