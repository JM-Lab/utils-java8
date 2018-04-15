package kr.jm.utils;

import kr.jm.utils.exception.JMExceptionManager;
import kr.jm.utils.helper.JMOptional;
import kr.jm.utils.helper.JMThread;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * The type Std in line consumer.
 */
public class StdInLineConsumer implements AutoCloseable {
    private static final Logger log =
            org.slf4j.LoggerFactory.getLogger(StdInLineConsumer.class);
    private ExecutorService executorService;
    private Consumer<String> stdInLineConsumer;

    /**
     * Instantiates a new Std in line consumer.
     *
     * @param stdInLineConsumer the std in line consumer
     */
    public StdInLineConsumer(Consumer<String> stdInLineConsumer) {
        this.stdInLineConsumer = stdInLineConsumer;
    }

    /**
     * Consume std in std in line consumer.
     *
     * @return the std in line consumer
     */
    public StdInLineConsumer consumeStdIn() {
        JMThread.runAsync(this::startStdIn,
                this.executorService = JMThread.newSingleThreadPool());
        return this;
    }

    private void startStdIn() {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(System.in))) {
            while (!executorService.isShutdown())
                JMOptional.getOptional(bufferedReader.readLine())
                        .ifPresent(stdInLineConsumer);
        } catch (Exception e) {
            JMExceptionManager.logException(log, e, "startStdIn");
        } finally {
            close();
        }

    }

    @Override
    public void close() {
        if (Objects.nonNull(executorService))
            JMThread.shutdownNowAndWaitToBeTerminated(executorService);
    }
}
