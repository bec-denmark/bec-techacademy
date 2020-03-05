package com.bec.techacademy.learnkafka.examples.consumer;

import com.bec.techacademy.learnkafka.datamodel.event.Account;
import com.bec.techacademy.learnkafka.datamodel.event.Customer;
import com.bec.techacademy.learnkafka.datamodel.event.SpecificRecordAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class RecordConsumerFixture implements RecordConsumerListener {
    private final KafkaTemplate<String, SpecificRecordAdapter> producer;
    private final Logger logger = LoggerFactory.getLogger(RecordConsumerFixture.class);
    private final CountDownLatch latch;
    private SpecificRecordAdapter receivedRecord;

    public RecordConsumerFixture(KafkaTemplate<String, SpecificRecordAdapter> producer) throws InterruptedException {
        latch = new CountDownLatch(1);
        latch.await(300, TimeUnit.MILLISECONDS);
        this.producer = producer;
    }

    public SpecificRecordAdapter sendAccount(Account accountRecord) {
        SpecificRecordAdapter expectedRecord = new SpecificRecordAdapter(accountRecord);
        sendRecord(AccountRecordConsumerIT.ACCOUNT_TOPIC, expectedRecord);

        return expectedRecord;
    }

    public void sendRecord(String topic, SpecificRecordAdapter expectedRecord) {
        logger.info("Sending {} to {}", expectedRecord, topic);
        producer.send(topic, expectedRecord);
        producer.flush();
        logger.info("Send {} to {}", expectedRecord, topic);
    }

    public SpecificRecordAdapter sendCustomer(Customer customer) {
        SpecificRecordAdapter expectedRecord = new SpecificRecordAdapter(customer);
        sendRecord(CustomerRecordConsumerIT.CUSTOMER_TOPIC, expectedRecord);

        return expectedRecord;
    }

    public SpecificRecordAdapter receivedRecord() throws InterruptedException {
        latch.await(10, TimeUnit.SECONDS);

        return receivedRecord;
    }

    @Override
    public void recordProcessed(String key, SpecificRecordAdapter record) {
        receivedRecord = record;
        latch.countDown();
    }
}
