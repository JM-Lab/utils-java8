package kr.jm.utils.destory;

import java.util.concurrent.ExecutorService;

public interface DestroyInterface {
	
	public void cleanUp() throws RuntimeException;

	default void cleanUp(ExecutorService executorService)
			throws RuntimeException {
		executorService.shutdown();
		while (!executorService.isTerminated()) {
		}
	}
}
