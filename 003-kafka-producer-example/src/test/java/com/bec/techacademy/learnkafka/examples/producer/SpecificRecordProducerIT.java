package com.bec.techacademy.learnkafka.examples.producer;

import com.bec.techacademy.learnkafka.datamodel.event.Account;
import com.bec.techacademy.learnkafka.datamodel.event.Customer;
import com.bec.techacademy.learnkafka.datamodel.event.SpecificRecordAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(topics = {SpecificRecordProducerIT.ACCOUNT_TOPIC, SpecificRecordProducerIT.CUSTOMER_TOPIC},
        bootstrapServersProperty = "spring.kafka.bootstrap-servers")
public class SpecificRecordProducerIT implements ListenableFutureCallback<SendResult<String, SpecificRecordAdapter>>{
    public static final String ACCOUNT_TOPIC = "test-account-topic";
    public static final String CUSTOMER_TOPIC = "test-customer-topic";

    @Autowired
    private SpecificRecordProducer producer;

    private SpecificRecordAdapter actualRecord;
    private CountDownLatch latch;

    @BeforeEach
    void setup(){
        actualRecord = null;
        latch = new CountDownLatch(1);
    }

    @Test
    void isAccountChangeSend() throws InterruptedException {
        Account accountChange = Account.newBuilder()
                .setName("MyAccount")
                .setReg(1234)
                .setNumber(1234567890)
                .build();
        final SpecificRecordAdapter expectedRecord = new SpecificRecordAdapter(accountChange);
        producer.send(ACCOUNT_TOPIC, expectedRecord).addCallback(this);
        latch.await(10, TimeUnit.SECONDS);
        assertEquals(expectedRecord, actualRecord, "Sending record");
    }

    @Override
    public void onFailure(Throwable throwable) {}

    @Override
    public void onSuccess(SendResult<String, SpecificRecordAdapter> sendResult) {
        this.actualRecord = sendResult.getProducerRecord().value();
        latch.countDown();
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
        producer.send(CUSTOMER_TOPIC, expectedRecord).addCallback(this);
        latch.await(10, TimeUnit.SECONDS);
        Assertions.assertEquals(expectedRecord, actualRecord);
    }
}
