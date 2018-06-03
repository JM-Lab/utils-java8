
package kr.jm.utils.destroy;

import kr.jm.utils.helper.JMThread;

import java.util.concurrent.ExecutorService;

/**
 * The interface Destroy interface.
 */
public interface DestroyInterface {

    /**
     * Clean up.
     *
     * @throws RuntimeException the runtime exception
     */
    void cleanUp() throws RuntimeException;

    /**
     * Clean up.
     *
     * @param executorService the executor service
     * @throws RuntimeException the runtime exception
     */
    default void cleanUp(ExecutorService executorService)
			throws RuntimeException {
		executorService.shutdown();
		while (!executorService.isTerminated())
			JMThread.sleep(100);
	}
}
