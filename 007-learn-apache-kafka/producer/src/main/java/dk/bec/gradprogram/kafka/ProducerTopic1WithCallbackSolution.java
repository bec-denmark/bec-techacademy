package dk.bec.gradprogram.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.slf4j.Logger;

import static dk.bec.gradprogram.kafka.LoggerFactory.logRunning;
import static dk.bec.gradprogram.kafka.ProducerFactory.createHelloWorldProducer;

public class ProducerTopic1WithCallbackSolution {
    public static void main(String[] args) {
        Logger logger = logRunning(ProducerTopic1WithCallbackSolution.class);
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
