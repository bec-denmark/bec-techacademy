package com.bec.techacademy.learnkafka.confluent;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class SimpleConsumer  {

	
    @Value("${kafka.topic.thetechcheck}")
    private String theTechCheckTopicName;

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;

    @Value("${zookeeper.groupId}")
    private String zookeeperGroupId;

    @Value("${zookeeper.host}")
    String zookeeperHost;
    
    private static final Logger logger = Logger.getLogger(SimpleConsumer.class);

    private KafkaConsumer<String, String> kafkaConsumer;

    public SimpleConsumer(String theTechCheckTopicName, Properties consumerProperties) {

        kafkaConsumer = new KafkaConsumer<>(consumerProperties);
        kafkaConsumer.subscribe(Arrays.asList(theTechCheckTopicName));
    }
    


    
    public void runSingleWorker() {

        while(true) {

            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);

            for (ConsumerRecord<String, String> record : records) {


                String message = record.value();


                logger.info("Received message: " + message);

                try {
                    JSONObject receivedJsonObject = new JSONObject(message);

                    logger.info("Index of deserialized JSON object: " + receivedJsonObject.getInt("index"));
                } catch (JSONException e) {
                    logger.error(e.getMessage());
                }

                /*
                Once we finish processing a Kafka message, we have to commit the offset so that we don't end up consuming 
                the same message endlessly. By default, the consumer object takes care of this. 
                But to demonstrate how it can be done, we have turned this default behaviour off, instead, we're going 
                to manually commit the offsets
                 */
                {
                    Map<TopicPartition, OffsetAndMetadata> commitMessage = new HashMap<>();

                    commitMessage.put(new TopicPartition(record.topic(), record.partition()),
                            new OffsetAndMetadata(record.offset() + 1));

                    kafkaConsumer.commitSync(commitMessage);

                    logger.info("Offset committed to Kafka.");
                }
            }
        }
    }
}
