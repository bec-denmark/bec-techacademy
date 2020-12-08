package dk.bec.gradprogram.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.Properties;

import static java.util.Collections.singletonList;

public class KafkaConsumerPrinter {
    private KafkaConsumer<String, String> consumer;
    private Logger logger;

    public static KafkaConsumerPrinter createConsumerPrinter(Logger logger) {
        Properties properties = ConsumerProperties.createLocalConsumerProperties();
        KafkaConsumerPrinter pollPrinter = new KafkaConsumerPrinter();
        pollPrinter.consumer = new KafkaConsumer<>(properties);
        pollPrinter.logger = logger;

        return pollPrinter;
    }

    public KafkaConsumerPrinter subscribeTo(String topic) {
        consumer.subscribe(singletonList(topic));

        return this;
    }

    public void poll() {
        while (true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for(ConsumerRecord<String, String> record : records){
                logger.info("Consuming :"+record);
            }
        }
    }

    public void close() {
        consumer.close();
    }

    public void wakeup() {
        consumer.wakeup();
    }
}
