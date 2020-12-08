package dk.bec.gradprogram.kafka;

import org.slf4j.Logger;

import java.util.concurrent.CountDownLatch;

import static dk.bec.gradprogram.kafka.KafkaConsumerPrinter.createConsumerPrinter;
import static dk.bec.gradprogram.kafka.LoggerFactory.logRunning;

/**
 * Quoting from "Kafka - the definitive guide" By Gwen Shapira, Neha Narkhede, Todd Palino (O' Reilly Media) :
 * <p>
 * Always close() the consumer before exiting.
 * This will close the network connections and sockets.
 * It will also trigger a rebalance immediately rather than wait for the group coordinator to discover that the consumer
 * stopped sending heartbeats and is likely dead, which will take longer and therefore result in a longer period of time
 * in which consumers can't consume messages from a subset of the partitions.
 */
public class ConsumerTopic2WithCloseSolution {
    public static void main(String[] args) {
        Logger logger = logRunning(ConsumerTopic2WithCloseSolution.class);
        KafkaConsumerPrinter consumerPrinter = createConsumerPrinter(logger).subscribeTo("topic2");
        CountDownLatch latch = new CountDownLatch(1);
        ConsumerRunnable consumerRunnable = new ConsumerRunnable(consumerPrinter, latch);
        Thread thread = new Thread(consumerRunnable);
        thread.start();

        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            logger.info("Caught shutdown hook");
            consumerRunnable.shutdown();
            await("Application has exited", logger, latch);
        }));

        await("Application is closing", logger, latch);
        //Todo subscribe consumer to topic2

        //Todo Poll for new data and write key, val, partition and offset to console

        //Todo close consumer connection before exiting.
    }

    private static void await(String finalMessage, Logger logger, CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.error("Application is interrupted");
        } finally {
            logger.info(finalMessage);
        }
    }
}
