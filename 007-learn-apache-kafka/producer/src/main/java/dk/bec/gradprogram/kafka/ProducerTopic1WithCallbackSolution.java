package dk.bec.gradprogram.kafka;

import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Properties;

import static dk.bec.gradprogram.kafka.ProducerFactory.createHelloWorldProducer;

public class ProducerTopic1WithCallbackSolution {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ProducerTopic1WithCallbackSolution.class);
        logger.info("Running");

        //Todo Print "Record send successfully" to console when a record was successfully send
        //Todo Print "Record send failed" to console when a record was not successfully send
        Callback callback = (recordMetadata, e) -> {
            if (e == null) {
                logger.info("Record send successfully. " + recordMetadata);
            } else {
                logger.error("Record send failed", e);
            }
        };

        //Todo send data to topic
        createHelloWorldProducer().sendDataWithCallback("topic1", callback);
    }
}
