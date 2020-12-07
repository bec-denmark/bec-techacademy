package dk.bec.gradprogram.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dk.bec.gradprogram.kafka.KafkaConsumerPrinter.createConsumerPrinter;

public class ConsumerTopic2Solution {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ConsumerTopic2Solution.class);
        logger.info("Running");
        createConsumerPrinter().subscribeTo("topic2").poll(logger);
    }
}
