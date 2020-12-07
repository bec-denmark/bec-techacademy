package dk.bec.gradprogram.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dk.bec.gradprogram.kafka.KafkaConsumerPrinter.createConsumerPrinter;

public class ConsumerTopic1Solution {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ConsumerTopic1Solution.class);
        logger.info("Running");
        createConsumerPrinter().subscribeTo("topic1").poll(logger);
    }
}
