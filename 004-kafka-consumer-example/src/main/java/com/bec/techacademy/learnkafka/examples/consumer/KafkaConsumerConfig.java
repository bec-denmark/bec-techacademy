package com.bec.techacademy.learnkafka.examples.consumer;

import com.bec.techacademy.learnkafka.datamodel.event.Account;
import com.bec.techacademy.learnkafka.datamodel.event.Customer;
import com.bec.techacademy.learnkafka.datamodel.event.SpecificRecordAdapter;
import com.bec.techacademy.learnkafka.datamodel.event.SpecificRecordDeserializer;
import com.bec.techacademy.learnkafka.datamodel.event.SpecificRecordAdapter;
import com.bec.techacademy.learnkafka.examples.serializer.KafkaSerializer;

import com.bec.techacademy.learnkafka.examples.serializer.KafkaDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.topic.account}")
    private String accountTopic;
    @Value("${spring.kafka.topic.customer}")
    private String customerTopic;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        return props;
    }

    @Bean
    public ConsumerFactory<String, SpecificRecordAdapter> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), keySerializer(), valueSerializer());
    }

    @Bean
    public Map<String, SpecificRecordDeserializer> topicDeserializer(){
        final HashMap<String, SpecificRecordDeserializer> map = new HashMap<>();
        map.put(accountTopic, new SpecificRecordDeserializer<Account>(new Account().getSchema()));
        map.put(customerTopic, new SpecificRecordDeserializer<Customer>(new Customer().getSchema()));

        return map;
    }

    @Bean
    public KafkaDeserializer valueSerializer() {
        return new KafkaDeserializer(topicDeserializer());
    }

    @Bean
    public StringDeserializer keySerializer() {
        return new StringDeserializer();
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, SpecificRecordAdapter>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SpecificRecordAdapter> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);

        return factory;
    }
}
