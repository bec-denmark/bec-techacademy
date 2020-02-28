package com.bec.techacademy.learnkafka.examples.producer;

import dk.martincallesen.datamodel.event.SpecificRecordAdapter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;

@Component
public class SpecificRecordProducer {
    private SendResultLogger logger = new SendResultLogger(SpecificRecordProducer.class);
    private KafkaTemplate<String, SpecificRecordAdapter> kafkaTemplate;

    public SpecificRecordProducer(KafkaTemplate<String, SpecificRecordAdapter> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public ListenableFuture<SendResult<String, SpecificRecordAdapter>> send(String topic, SpecificRecordAdapter record) {
        String key = UUID.randomUUID().toString();
        final ListenableFuture<SendResult<String, SpecificRecordAdapter>> sendResult = kafkaTemplate.send(topic, key, record);
        sendResult.addCallback(logger.log(topic, record));

        return sendResult;
    }
}
