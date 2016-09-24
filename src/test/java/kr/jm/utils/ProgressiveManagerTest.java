package kr.jm.utils;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import kr.jm.utils.helper.JMStream;
import kr.jm.utils.helper.JMThread;

public class ProgressiveManagerTest {
	private List<String> targetCollection = JMStream.numberRange(0, 1000, 1)
			.boxed().map(i -> "index-" + i).collect(toList());
	private ProgressiveManager<String, String> progressiveManager;

	@Before
	public void createProgressiveManager() throws Exception {
		progressiveManager =
				new ProgressiveManager<String, String>(targetCollection)
						.registerCountChangeListener(i -> System.out.println(
								getCurrentThread() + " count change - " + i))
						.registerPercentChangeListener(i -> System.out.println(
								getCurrentThread() + " percent change - " + i))
						.registerTargetChangeListener(i -> System.out.println(
								getCurrentThread() + " target change - " + i))
						.registerLastResultChangeListener(
								i -> System.out.println(getCurrentThread()
										+ " last result change - " + i))
						.addCompletedConsumer(progressiveManager -> System.out
								.println(getCurrentThread()
										+ " process complete - "
										+ progressiveManager));

	}

	@Test
	public void testStartAndStop() throws Exception {
		progressiveManager.start(s -> {
			System.out
					.println(getCurrentThread() + " [Async] processing - " + s);
			JMThread.sleep(1);
			return s;
		});
		JMThread.sleep(50);
		assertTrue(!progressiveManager.stopAsync().isDone());
		System.out.println(getCurrentThread() + " Stop !!! - "
				+ progressiveManager.getAfterCompletion());
		assertFalse(progressiveManager.toString().contains("null"));

		progressiveManager.start(s -> {
			System.out
					.println(getCurrentThread() + " [Sync] processing - " + s);
			JMThread.sleep(1);
			return s;
		});
		JMThread.sleep(50);
		assertTrue(progressiveManager.stopSync().isDone());
		assertFalse(progressiveManager.toString().contains("null"));
	}

	private String getCurrentThread() {
		return Thread.currentThread().getName();
	}
}
