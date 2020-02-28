package com.bec.techacademy.learnkafka.examples.producer;

import com.bec.techacademy.learnkafka.datamodel.event.SpecificRecordAdapter;
import com.bec.techacademy.learnkafka.examples.serializer.KafkaSerializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {
    public static final String ACCOUNT_TOPIC = "account";
    public static final String CUSTOMER_TOPIC = "customer";

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaSerializer.class);

        return props;
    }

    @Bean
    public ProducerFactory<String, SpecificRecordAdapter> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, SpecificRecordAdapter> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic accountTopic() {
        return TopicBuilder.name(ACCOUNT_TOPIC)
                .partitions(10)
                .replicas(1)
                .compact()
                .build();
    }
    @Bean
    public NewTopic customerTopic() {
        return TopicBuilder.name(CUSTOMER_TOPIC)
                .partitions(10)
                .replicas(1)
                .compact()
                .build();
    }
}
