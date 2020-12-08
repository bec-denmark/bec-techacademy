package dk.bec.gradprogram.kafka;

import static dk.bec.gradprogram.kafka.LoggerFactory.logRunning;

public class ConsumerTopic1Exercise {
    public static void main(String[] args) {
        logRunning(ConsumerTopic1Exercise.class);
        //Todo Create consumer configuration for connection to local kafka instance

        //Todo Create consumer

        //Todo subscribe consumer to topics

        //Todo Poll for new data and write key, val, partition and offset to console

        //Question Which partition(s) was consumed from (See in the consumer application log)

        //Question How much time did it take before a rebalance happen? (Look in the broker log)
    }
}
