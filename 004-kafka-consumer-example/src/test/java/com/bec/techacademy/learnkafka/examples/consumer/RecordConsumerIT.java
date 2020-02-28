package com.bec.techacademy.learnkafka.examples.consumer;

import dk.martincallesen.datamodel.event.Account;
import dk.martincallesen.datamodel.event.Customer;
import dk.martincallesen.datamodel.event.SpecificRecordAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(TestConfiguration.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        "spring.kafka.topic.account=" + RecordConsumerIT.ACCOUNT_TOPIC,
        "spring.kafka.topic.customer=" + RecordConsumerIT.CUSTOMER_TOPIC
})
@EmbeddedKafka(topics = RecordConsumerIT.ACCOUNT_TOPIC,
        bootstrapServersProperty = "spring.kafka.bootstrap-servers")
public class RecordConsumerIT implements RecordConsumerListener {
    public static final String ACCOUNT_TOPIC = "test-account-topic";
    public static final String CUSTOMER_TOPIC = "test-customer-topic";
    private CountDownLatch latch;
    private SpecificRecordAdapter receivedRecord;

    @Autowired
    private KafkaTemplate<String, SpecificRecordAdapter> producer;

    @Autowired
    private RecordConsumer consumer;

    @BeforeEach
    void setupConsumer() {
        latch = new CountDownLatch(1);
        consumer.setListener(this);
    }

    @Test
    void consumeAccountChange() throws InterruptedException {
        Account accountChange = Account.newBuilder()
                .setName("MyAccount")
                .setReg(1234)
                .setNumber(1234567890)
                .build();
        final SpecificRecordAdapter expectedRecord = new SpecificRecordAdapter(accountChange);
        producer.send(ACCOUNT_TOPIC, expectedRecord);
        latch.await(10, TimeUnit.SECONDS);
        assertEquals(expectedRecord, receivedRecord, "Record received");
    }

    @Test
    void consumeCustomerChange() throws InterruptedException {
        final Customer customerChange = Customer.newBuilder()
                .setFirstName("Michael")
                .setLastName("Hansen")
                .setAge(30)
                .setHeight(180)
                .setWeight(85)
                .setAutomatedEmail(true)
                .build();
        final SpecificRecordAdapter expectedRecord = new SpecificRecordAdapter(customerChange);
        producer.send(CUSTOMER_TOPIC, expectedRecord);
        latch.await(10, TimeUnit.SECONDS);
        assertEquals(expectedRecord, receivedRecord, "Record received");
    }

    @Override
    public void recordProcessed(String key, SpecificRecordAdapter record) {
        receivedRecord = record;
        latch.countDown();
    }
}
