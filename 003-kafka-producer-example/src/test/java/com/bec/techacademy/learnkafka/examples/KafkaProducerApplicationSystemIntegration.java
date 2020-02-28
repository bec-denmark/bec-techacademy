package com.bec.techacademy.learnkafka.examples;

import com.bec.techacademy.learnkafka.examples.producer.KafkaProducerConfig;
import com.bec.techacademy.learnkafka.examples.producer.SpecificRecordProducer;
import dk.martincallesen.datamodel.event.Account;
import dk.martincallesen.datamodel.event.Customer;
import dk.martincallesen.datamodel.event.SpecificRecordAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class KafkaProducerApplicationSystemIntegration implements ListenableFutureCallback<SendResult<String, SpecificRecordAdapter>> {

    @Autowired
    private SpecificRecordProducer producer;

    private SpecificRecordAdapter actualRecord;
    private CountDownLatch latch;

    @BeforeEach
    void setupLatch(){
        actualRecord = null;
        latch = new CountDownLatch(1);
    }

    @Test
        void isAccountChangeSend() throws InterruptedException {
        Account accountChange = Account.newBuilder()
                .setName("CommonAccount")
                .setReg(4321)
                .setNumber(1987654321)
                .build();
        final SpecificRecordAdapter expectedRecord = new SpecificRecordAdapter(accountChange);
        producer.send(KafkaProducerConfig.ACCOUNT_TOPIC, expectedRecord).addCallback(this);
        latch.await(10, TimeUnit.SECONDS);
        Assertions.assertEquals(expectedRecord, actualRecord);
    }

    @Override
    public void onFailure(Throwable throwable) {}

    @Override
    public void onSuccess(SendResult<String, SpecificRecordAdapter> sendResult) {
        this.actualRecord = sendResult.getProducerRecord().value();
        this.latch.countDown();
    }

    @Test
    void isCustomerChangeSend() throws InterruptedException {
        final Customer customerChange = Customer.newBuilder()
                .setFirstName("Michael")
                .setLastName("Hansen")
                .setAge(30)
                .setHeight(180)
                .setWeight(85)
                .setAutomatedEmail(true)
                .build();
        final SpecificRecordAdapter expectedRecord = new SpecificRecordAdapter(customerChange);
        producer.send(KafkaProducerConfig.CUSTOMER_TOPIC, expectedRecord).addCallback(this);
        latch.await(10, TimeUnit.SECONDS);
        Assertions.assertEquals(expectedRecord, actualRecord);
    }
}
