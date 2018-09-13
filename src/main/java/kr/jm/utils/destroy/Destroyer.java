package kr.jm.utils.destroy;

import kr.jm.utils.exception.JMExceptionManager;
import kr.jm.utils.helper.JMOptional;
import kr.jm.utils.helper.JMThread;

import java.util.Arrays;
import java.util.Collection;

/**
 * The type Destroyer.
 */
public class Destroyer {

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(Destroyer.class);

    /**
     * Clean up.
     *
     * @param <D>      the type parameter
     * @param destroys the destroys
     */
    @SafeVarargs
    public static <D extends DestroyInterface> void cleanUp(D... destroys) {
        JMOptional.getOptional(destroys).map(Arrays::asList)
                .ifPresent(Destroyer::cleanUp);
    }

    /**
     * Clean up.
     *
     * @param <D>               the type parameter
     * @param destroyCollection the destroy collection
     */
    public static <D extends DestroyInterface> void
    cleanUp(Collection<D> destroyCollection) {
        JMOptional.getOptional(destroyCollection).ifPresent(
                collection -> collection.forEach(Destroyer::cleanUp));
    }

    /**
     * Clean up.
     *
     * @param destroy the destroy
     */
    public static void cleanUp(DestroyInterface destroy) {
        try {
            JMThread.sleep(10);
            log.info("Start Clean Up - {}", destroy);
            destroy.cleanUp();
            log.info("Complete Clean Up - {}", destroy);
        } catch (Exception e) {
            log.error("Fail Clean Up - {}", destroy);
            JMExceptionManager.handleException(log, e, "cleanUp", destroy);
        }
    }

}
