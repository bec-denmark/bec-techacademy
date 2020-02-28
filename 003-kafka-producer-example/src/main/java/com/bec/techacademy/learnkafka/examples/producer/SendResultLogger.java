package com.bec.techacademy.learnkafka.examples.producer;

import dk.martincallesen.datamodel.event.SpecificRecordAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class SendResultLogger {
    private final Logger logger;

    public SendResultLogger(Class aClass){
        this.logger = LoggerFactory.getLogger(aClass);
    }

    public ListenableFutureCallback<SendResult<String, SpecificRecordAdapter>> log(String topic, SpecificRecordAdapter record) {
        return new ListenableFutureCallback<SendResult<String, SpecificRecordAdapter>>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.warn("Failed to send record {} on topic {}", record, topic);
            }

            @Override
            public void onSuccess(SendResult<String, SpecificRecordAdapter> sendResult) {
                logger.info("Successfully send record {} on topic {}", record, topic);
            }
        };
    }
}
