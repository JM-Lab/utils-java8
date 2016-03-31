
package kr.jm.utils.destory;

import java.util.Arrays;
import java.util.List;

import kr.jm.utils.exception.JMExceptionManager;
import kr.jm.utils.helper.JMThread;

/**
 * The Class Destroyer.
 */
public class Destroyer {

	private static final org.slf4j.Logger log =
			org.slf4j.LoggerFactory.getLogger(Destroyer.class);

	/**
	 * Clean up.
	 *
	 * @param <D>
	 *            the generic type
	 * @param destorys
	 *            the destorys
	 */
	public static <D extends DestroyInterface> void
			cleanUp(@SuppressWarnings("unchecked") D... destorys) {
		cleanUp(Arrays.asList(destorys));
	}

	/**
	 * Clean up.
	 *
	 * @param <D>
	 *            the generic type
	 * @param destroyList
	 *            the destroy list
	 */
	public static <D extends DestroyInterface> void
			cleanUp(List<D> destroyList) {
		for (DestroyInterface destroy : destroyList) {
			try {
				JMThread.sleep(10);
				log.info("Start Clean Up - {}", destroy);
				destroy.cleanUp();
				log.info("Complete Clean Up - {}", destroy);
			} catch (Exception e) {
				log.error("Fail Clean Up - {}", destroy);
				JMExceptionManager.logException(log, e, "cleanUp", destroy);
				continue;
			}
		}
	}

}
