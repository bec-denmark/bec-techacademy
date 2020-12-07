package dk.bec.gradprogram.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Properties;

public class ProducerTopic1WithCallbackSolution {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ProducerTopic1WithCallbackSolution.class);
        logger.info("ProducerTopic1WithCallbackSolution is running");
        //Todo Create producer properties for connection to local kafka instance
        String bootstrapServers = "127.0.0.1:9092";

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //Todo Create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //Todo send data to topic
        //Todo Print "Record send successfully" to console when a record was successfully send
        //Todo Print "Record send failed" to console when a record was not successfully send
        String topic = "topic1";
        String value = "Hello world "+ LocalDateTime.now();
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, value);
        producer.send(record, (recordMetadata, e) -> {
            if(e == null){
                logger.info("Record send successfully. "+recordMetadata);
            } else {
                logger.error("Record send failed", e);
            }
        });
        producer.flush();
        producer.close();
    }
}
