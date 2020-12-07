package dk.bec.gradprogram.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dk.bec.gradprogram.kafka.ProducerFactory.createHelloWorldProducer;

public class ProducerTopic2WithKeySolution {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ProducerTopic2WithKeySolution.class);
        logger.info("Running");
        createHelloWorldProducer().sendDataWithKey("topic2");
    }
}
