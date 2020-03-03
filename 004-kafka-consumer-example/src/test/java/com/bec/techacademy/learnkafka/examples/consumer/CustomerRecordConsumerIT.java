package com.bec.techacademy.learnkafka.examples.consumer;

import com.bec.techacademy.learnkafka.datamodel.event.Customer;
import com.bec.techacademy.learnkafka.datamodel.event.SpecificRecordAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(TestConfiguration.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        "spring.kafka.topic.customer=" + CustomerRecordConsumerIT.CUSTOMER_TOPIC,
        "spring.kafka.consumer.customer.group.id=test-customer-group",
        "spring.kafka.consumer.auto-offset-reset=earliest"
})
@EmbeddedKafka(
        topics = {CustomerRecordConsumerIT.CUSTOMER_TOPIC},
        bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
public class CustomerRecordConsumerIT {
    static final String CUSTOMER_TOPIC = "test-customer-topic";

    @Autowired
    private KafkaTemplate<String, SpecificRecordAdapter> producer;

    @Autowired
    private CustomerRecordConsumer customerRecordConsumer;
    private RecordConsumerFixture consumerFixture;

    @BeforeEach
    void setupRecordConsumerFixture() throws InterruptedException {
        consumerFixture = new RecordConsumerFixture(producer);
        customerRecordConsumer.setListener(consumerFixture);
    }

    @Test
    void consumeCustomerChange() throws InterruptedException {
        final Customer customer = Customer.newBuilder()
                .setFirstName("Michael")
                .setLastName("Hansen")
                .setAge(30)
                .setHeight(180)
                .setWeight(85)
                .setAutomatedEmail(true)
                .build();
        SpecificRecordAdapter expectedRecord = consumerFixture.sendCustomer(customer);
        SpecificRecordAdapter receivedRecord = consumerFixture.receivedRecord();
        assertEquals(expectedRecord, receivedRecord, "Record received");
    }
}
