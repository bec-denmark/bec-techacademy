package com.bec.techacademy.learnkafka.service;

import com.bec.techacademy.learnkafka.datamodel.event.AccountChangesEvent;
import com.bec.techacademy.learnkafka.datamodel.event.AccountPostingsEvent;
import com.bec.techacademy.learnkafka.datamodel.event.ChangeDataEvent;
import com.bec.techacademy.learnkafka.datamodel.event.CustomerChangesEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class ChangedDataMessageProducerService {

    private static final String TOPIC_ACCOUNT_POSTINGS = "CDC_Account_Postings";
    private static final String TOPIC_CUSTOMER_CHANGES = "CDC_Customer_Changes";
    private static final String TOPIC_ACCOUNT_CHANGES = "CDC_Account_Changes";

    private final KafkaTemplate<String,ChangeDataEvent> kafkaTemplate;

    @Autowired
    public ChangedDataMessageProducerService(KafkaTemplate<String, ChangeDataEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /*
    public ListenableFuture<SendResult<String, ChangeDataEvent>> sendAccountChange(ChangeDataEvent changeDataEventObj) {
        ListenableFuture<SendResult<String, GenericRecord>> future = null;
        future = kafkaTemplate.send(TOPIC_ACCOUNT_CHANGES, changeDataEventObj);
        return future;
    }

    */

    public ListenableFuture<SendResult<String, ChangeDataEvent>> send(ChangeDataEvent changeDataEvent) {
        //Best Practice says Producer to decide where to publish,
        //  ... so we decide here instead of three different send methods based on instanceof;)

        ListenableFuture<SendResult<String, ChangeDataEvent>> future = null;

        if (changeDataEvent instanceof AccountChangesEvent) {
            future = kafkaTemplate.send(TOPIC_ACCOUNT_CHANGES, changeDataEvent);

        } else if (changeDataEvent instanceof AccountPostingsEvent) {
            future = kafkaTemplate.send(TOPIC_ACCOUNT_POSTINGS, changeDataEvent);

        } else if (changeDataEvent instanceof CustomerChangesEvent) {
            future = kafkaTemplate.send(TOPIC_CUSTOMER_CHANGES, changeDataEvent);
        }

        return future;
    }

}
