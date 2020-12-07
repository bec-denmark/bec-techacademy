package dk.bec.gradprogram.kafka;

import static dk.bec.gradprogram.kafka.LoggerFactory.logRunning;
import static dk.bec.gradprogram.kafka.ProducerFactory.createHelloWorldProducer;

public class ProducerTopic2WithKeySolution {
    public static void main(String[] args) {
        logRunning(ProducerTopic2WithKeySolution.class);
        createHelloWorldProducer().sendDataWithKey("topic2");
    }
}
