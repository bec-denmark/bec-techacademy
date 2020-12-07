package com.github.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Properties;

import static java.util.Collections.singletonList;

public class ConsumerTopic2Solution {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ConsumerTopic2Solution.class);
        logger.info("ConsumerTopic2Solution is running");
        //Todo Create consumer configuration for connection to local kafka instance
        String bootstrapServers = "127.0.0.1:9092";
        String groupId = "java-consumer-group";

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        //Todo Create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        //Todo subscribe consumer to topics
        consumer.subscribe(singletonList("topic2"));

        //Todo Poll for new data and write key, val, partition and offset to console
        while (true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for(ConsumerRecord<String, String> record : records){
                logger.info("Consuming :"+record);
            }
        }
    }
}
