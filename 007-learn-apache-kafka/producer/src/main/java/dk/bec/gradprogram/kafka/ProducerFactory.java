package dk.bec.gradprogram.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class ProducerFactory {
    public static KafkaProducer<String, String> createKafkaProducer() {
        //Todo Create producer properties for connection to local kafka instance
        Properties properties = ProducerProperties.createProperties();

        //Todo Create the Producer
        return new KafkaProducer<>(properties);
    }

    public static KafkaProducerHelloWorld createHelloWorldProducer() {
        return new KafkaProducerHelloWorld(createKafkaProducer());
    }
}
