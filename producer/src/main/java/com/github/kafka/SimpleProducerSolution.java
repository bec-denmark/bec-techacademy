package com.github.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class SimpleProducerSolution {
    public static void main(String[] args) {
        System.out.println("Producer is running");
        //Todo Create producer properties for connection to local kafka instance
        String bootstrapServers = "127.0.0.1:9092";

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //Todo Create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //Todo send data to topic
        String topic = "topic1";
        String value = "Hello world";
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, value);
        producer.send(record);
        producer.flush();
        producer.close();
    }
}
