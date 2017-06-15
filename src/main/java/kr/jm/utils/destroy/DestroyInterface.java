
package kr.jm.utils.destroy;

import kr.jm.utils.helper.JMThread;

import java.util.concurrent.ExecutorService;

/**
 * The Interface DestroyInterface.
 */
public interface DestroyInterface {

	/**
	 * Clean up.
	 *
	 * @throws RuntimeException
	 *             the runtime exception
	 */
	void cleanUp() throws RuntimeException;

	default void cleanUp(ExecutorService executorService)
			throws RuntimeException {
		executorService.shutdown();
		while (!executorService.isTerminated())
			JMThread.sleep(100);
	}
}
