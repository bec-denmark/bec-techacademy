package dk.bec.gradprogram.kafka;

public interface KafkaConsumerClosable {
    void poll();

    void close();

    void wakeup();
}
