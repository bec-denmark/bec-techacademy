package dk.bec.gradprogram.kafka;

import org.slf4j.Logger;

import static dk.bec.gradprogram.kafka.KafkaConsumerPrinter.createConsumerPrinter;
import static dk.bec.gradprogram.kafka.LoggerFactory.logRunning;

public class ConsumerTopic2Solution {
    public static void main(String[] args) {
        Logger logger = logRunning(ConsumerTopic2Solution.class);
        createConsumerPrinter().subscribeTo("topic2").poll(logger);
    }
}
