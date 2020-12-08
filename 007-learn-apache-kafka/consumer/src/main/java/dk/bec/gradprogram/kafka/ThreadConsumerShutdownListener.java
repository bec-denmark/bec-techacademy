package dk.bec.gradprogram.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class ThreadConsumerShutdownListener {
    private static final Logger logger = LoggerFactory.getLogger(ThreadConsumerShutdownListener.class);
    private final CloseConsumerConnection closeConsumerConnection;
    private final CountDownLatch latch;

    private ThreadConsumerShutdownListener(KafkaConsumerPrinter consumerPrinter) {
        this.latch = new CountDownLatch(1);
        this.closeConsumerConnection = new CloseConsumerConnection(consumerPrinter, latch);
        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            logger.info("Caught application shutdown hook");
            closeConsumerConnection.shutdown();
            await("Application has exited");
        }));
    }

    public static ThreadConsumerShutdownListener createThreadedConsumerShutdownListener(KafkaConsumerPrinter consumerPrinter) {
        return new ThreadConsumerShutdownListener(consumerPrinter);
    }

    public void poll() {
        Thread thread = new Thread(closeConsumerConnection);
        thread.start();
        await("Application is closing");
    }

    private void await(String finalMessage) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.error("Application is interrupted");
        } finally {
            logger.info(finalMessage);
        }
    }
}
