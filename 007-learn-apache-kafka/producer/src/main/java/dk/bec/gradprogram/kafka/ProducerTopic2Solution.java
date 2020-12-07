package dk.bec.gradprogram.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dk.bec.gradprogram.kafka.ProducerFactory.createHelloWorldProducer;

public class ProducerTopic2Solution {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ProducerTopic2Solution.class);
        logger.info("ProducerTopic1Solution is running");
        createHelloWorldProducer().sendData("topic2");
    }
}
