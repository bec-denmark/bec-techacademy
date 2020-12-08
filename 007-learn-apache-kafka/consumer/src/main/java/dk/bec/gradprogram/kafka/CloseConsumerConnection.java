package dk.bec.gradprogram.kafka;

import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class CloseConsumerConnection implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(CloseConsumerConnection.class);
    private final KafkaConsumerPrinter consumer;
    private final CountDownLatch latch;

    public CloseConsumerConnection(KafkaConsumerPrinter consumerPrinter, CountDownLatch latch) {
        this.consumer = consumerPrinter;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            consumer.poll();
        } catch (WakeupException e){
            logger.info("Received shutdown signal!");
        } finally {
            latch.countDown();
            logger.info("Consumer connection closing");
            consumer.close();
        }
    }

    public void shutdown() {
        consumer.wakeup();
    }
}
