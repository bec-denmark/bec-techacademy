package dk.bec.gradprogram.kafka;

import static dk.bec.gradprogram.kafka.LoggerFactory.logRunning;

/**
 * Quoting from "Kafka - the definitive guide" By Gwen Shapira, Neha Narkhede, Todd Palino (O' Reilly Media) :
 *
 * Always close() the consumer before exiting.
 * This will close the network connections and sockets.
 * It will also trigger a rebalance immediately rather than wait for the group coordinator to discover that the consumer
 * stopped sending heartbeats and is likely dead, which will take longer and therefore result in a longer period of time
 * in which consumers can't consume messages from a subset of the partitions.
 */
public class ConsumerTopic2WithCloseExercise {
    public static void main(String[] args) {
        logRunning(ConsumerTopic2WithCloseExercise.class);
        //Todo Create consumer configuration for connection to local kafka instance

        //Todo Create consumer

        //Todo subscribe consumer to topic2

        //Todo Poll for new data and write key, val, partition and offset to console

        //Todo close consumer connection before exiting.

        //Todo How fast is consumer removed from group by GroupCoordinator
    }
}
