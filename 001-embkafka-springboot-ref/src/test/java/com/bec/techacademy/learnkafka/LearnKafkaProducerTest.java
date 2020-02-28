package com.bec.techacademy.learnkafka;

import com.bec.techacademy.learnkafka.datamodel.event.AccountPostingsEvent;
import com.bec.techacademy.learnkafka.datamodel.event.ChangeDataEvent;
import com.bec.techacademy.learnkafka.service.ChangedDataMessageProducerService;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Map;

import static org.junit.Assert.*;


@ExtendWith(SpringExtension.class)
@DirtiesContext
@SpringBootTest()
@EmbeddedKafka(partitions = 2, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class LearnKafkaProducerTest {  //Test Producer for Topic: CDC_Account_Postings
    private static final Logger LOGGER = LoggerFactory.getLogger(LearnKafkaProducerTest.class);
    private static String TOPIC_NAME = "CDC_Account_Postings";

    @Autowired
    private ChangedDataMessageProducerService kafkaMessageProducerService;

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, TOPIC_NAME).kafkaPorts(9092);

    @Before
    public void setUp() {

    }

    @Test
    public void it_should_send_account_posting_event() {
        KafkaMessageListenerContainer<String, ChangeDataEvent> container = createContainer();

        AccountPostingsEvent accountChangePosting = new AccountPostingsEvent("Debit", "Transfer to Sweden", 500.0, "DKK");

        ListenableFutureCallback<SendResult<String, ChangeDataEvent>> futureCallback = new ListenableFutureCallback<SendResult<String, ChangeDataEvent>>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failed to send event");
            }

            @Override
            public void onSuccess(SendResult<String, ChangeDataEvent> sendResult) {
                assertNotNull(sendResult);
                ChangeDataEvent dataEvent = sendResult.getProducerRecord().value();
                assertEquals("Event", accountChangePosting, dataEvent);
            }
        };

        kafkaMessageProducerService.send(accountChangePosting).addCallback(futureCallback);
        container.stop();
    }

    private KafkaMessageListenerContainer<String, ChangeDataEvent> createContainer() {
        ContainerProperties containerProperties = new ContainerProperties(TOPIC_NAME);
        EmbeddedKafkaBroker kafkaBroker = LearnKafkaProducerTest.embeddedKafka.getEmbeddedKafka();
        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps("sender", "false", kafkaBroker);
        DefaultKafkaConsumerFactory<String, ChangeDataEvent> consumer = new DefaultKafkaConsumerFactory<>(consumerProperties);
        KafkaMessageListenerContainer<String, ChangeDataEvent> container = new KafkaMessageListenerContainer<>(consumer, containerProperties);
        container.setupMessageListener((MessageListener<String, String>) record -> LOGGER.debug("Listened message='{}'", record.toString()));
        container.start();
        ContainerTestUtils.waitForAssignment(container, kafkaBroker.getPartitionsPerTopic());

        return container;
    }
}
