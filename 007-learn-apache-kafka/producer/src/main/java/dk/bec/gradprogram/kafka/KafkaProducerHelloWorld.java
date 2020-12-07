package dk.bec.gradprogram.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.LocalDateTime;

public class KafkaProducerHelloWorld {
    private final KafkaProducer<String, String> producer;

    public KafkaProducerHelloWorld(KafkaProducer<String, String> producer) {
        this.producer = producer;
    }

    public void sendData(String topic) {
        for (int i = 0; i < 10; i++) {
            String value = "Hello world " + LocalDateTime.now();
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, value);
            producer.send(record);
        }

        producer.flush();
        producer.close();
    }

    public void sendDataWithCallback(String topic, Callback callback) {
        for (int i = 0; i < 10; i++) {
            String value = "Hello world " + LocalDateTime.now();
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, value);
            producer.send(record, callback);
        }

        producer.flush();
        producer.close();
    }

    public void sendDataWithKey(String topic) {
        for (int i = 0; i < 10; i++) {
            String value = "Hello world " + LocalDateTime.now();
            String key = "id_key_"+i;
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
            producer.send(record);
        }

        producer.flush();
        producer.close();
    }
}
