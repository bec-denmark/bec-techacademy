package dk.bec.gradprogram.kafka;

import static dk.bec.gradprogram.kafka.LoggerFactory.logRunning;
import static dk.bec.gradprogram.kafka.ProducerFactory.createHelloWorldProducer;

public class ProducerTopic1WithKeySolution {
    public static void main(String[] args) {
        logRunning(ProducerTopic1WithKeySolution.class);
        createHelloWorldProducer().sendDataWithKey("topic1");
    }
}
