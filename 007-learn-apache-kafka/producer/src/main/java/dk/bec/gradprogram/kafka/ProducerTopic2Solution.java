package dk.bec.gradprogram.kafka;

import static dk.bec.gradprogram.kafka.LoggerFactory.logRunning;
import static dk.bec.gradprogram.kafka.ProducerFactory.createHelloWorldProducer;

public class ProducerTopic2Solution {
    public static void main(String[] args) {
        logRunning(ProducerTopic2Solution.class);
        createHelloWorldProducer().sendData("topic2");
    }
}
