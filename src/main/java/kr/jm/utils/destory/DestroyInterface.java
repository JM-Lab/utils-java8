package kr.jm.utils.destory;

import java.util.concurrent.ExecutorService;

import kr.jm.utils.helper.JMThread;

public interface DestroyInterface {

	public void cleanUp() throws RuntimeException;

	default void cleanUp(ExecutorService executorService)
			throws RuntimeException {
		executorService.shutdown();
		while (!executorService.isTerminated())
			JMThread.sleep(100);
	}
}
