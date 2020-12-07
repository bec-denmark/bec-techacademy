package dk.bec.gradprogram.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dk.bec.gradprogram.kafka.ProducerFactory.createHelloWorldProducer;

public class ProducerTopic1Solution {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ProducerTopic1Solution.class);
        logger.info("Running");
        createHelloWorldProducer().sendData("topic1");
    }

}
