
package kr.jm.utils.destory;

import java.util.Arrays;
import java.util.Collection;

import kr.jm.utils.exception.JMExceptionManager;
import kr.jm.utils.helper.JMOptional;
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
		JMOptional.getOptional(destorys).map(Arrays::asList)
				.ifPresent(Destroyer::cleanUp);
	}

	/**
	 * Clean up.
	 *
	 * @param <D>
	 *            the generic type
	 * @param destroyCollection
	 *            the destroy list
	 */
	public static <D extends DestroyInterface> void
			cleanUp(Collection<D> destroyCollection) {
		JMOptional.getOptional(destroyCollection)
				.ifPresent(collection -> collection.forEach(Destroyer::cleanUp));
	}

	/**
	 * Clean up.
	 *
	 * @param destroy
	 *            the destroy
	 */
	public static void cleanUp(DestroyInterface destroy) {
		try {
			JMThread.sleep(10);
			log.info("Start Clean Up - {}", destroy);
			destroy.cleanUp();
			log.info("Complete Clean Up - {}", destroy);
		} catch (Exception e) {
			log.error("Fail Clean Up - {}", destroy);
			JMExceptionManager.logException(log, e, "cleanUp", destroy);
			return;
		}
	}

}
