package dk.bec.gradprogram.kafka;

import org.slf4j.Logger;

import static dk.bec.gradprogram.kafka.ThreadConsumerShutdownListener.createThreadedConsumerShutdownListener;
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
        createThreadedConsumerShutdownListener(consumerPrinter).poll();
    }
}
