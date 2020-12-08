package dk.bec.gradprogram.kafka;

import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class ConsumerRunnable implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerRunnable.class);
    private final KafkaConsumerPrinter consumer;
    private final CountDownLatch latch;

    public ConsumerRunnable(KafkaConsumerPrinter consumerPrinter, CountDownLatch latch) {
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
            consumer.close();
        }
    }

    public void shutdown() {
        consumer.wakeup();
    }
}
