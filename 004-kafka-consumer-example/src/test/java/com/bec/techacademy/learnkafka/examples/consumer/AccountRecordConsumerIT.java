package com.bec.techacademy.learnkafka.examples.consumer;

import com.bec.techacademy.learnkafka.datamodel.event.Account;
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
        "spring.kafka.topic.account=" + AccountRecordConsumerIT.ACCOUNT_TOPIC,
        "spring.kafka.consumer.account.group.id=test-account-group",
        "spring.kafka.consumer.auto-offset-reset=earliest"
})
@EmbeddedKafka(
        topics = {AccountRecordConsumerIT.ACCOUNT_TOPIC},
        bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
public class AccountRecordConsumerIT {
    static final String ACCOUNT_TOPIC = "test-account-topic";

    @Autowired
    private KafkaTemplate<String, SpecificRecordAdapter> producer;

    @Autowired
    private AccountRecordConsumer accountRecordConsumer;
    private RecordConsumerFixture consumerFixture;

    @BeforeEach
    void setupRecordConsumerFixture() throws InterruptedException {
        consumerFixture = new RecordConsumerFixture(producer);
        accountRecordConsumer.setListener(consumerFixture);
    }

    @Test
    void consumeAccountChange() throws InterruptedException {
        Account account = Account.newBuilder()
                .setName("MyAccount")
                .setReg(1234)

                .setNumber(1234567890)
                .build();
        SpecificRecordAdapter expectedRecord = consumerFixture.sendAccount(account);
        SpecificRecordAdapter receivedRecord = consumerFixture.receivedRecord();
        assertEquals(expectedRecord, receivedRecord, "Record received");
    }
}
