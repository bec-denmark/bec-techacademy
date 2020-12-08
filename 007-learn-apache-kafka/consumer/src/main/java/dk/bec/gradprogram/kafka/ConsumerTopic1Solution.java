package dk.bec.gradprogram.kafka;

import org.slf4j.Logger;

import static dk.bec.gradprogram.kafka.KafkaConsumerPrinter.createConsumerPrinter;
import static dk.bec.gradprogram.kafka.LoggerFactory.logRunning;

public class ConsumerTopic1Solution {
    public static void main(String[] args) {
        Logger logger = logRunning(ConsumerTopic1Solution.class);
        createConsumerPrinter(logger).subscribeTo("topic1").poll();
    }

}
