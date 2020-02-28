package com.bec.techacademy.learnkafka.confluent;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class SimpleProducer implements CommandLineRunner {

    @Value("${kafka.topic.thetechcheck}")
    private String theTechCheckTopicName;

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;

    @Value("${zookeeper.groupId}")
    private String zookeeperGroupId;

    @Value("${zookeeper.host}")
    String zookeeperHost;

    private static final Logger logger = Logger.getLogger(SimpleProducer.class);

    public static void main( String[] args ) {
        SpringApplication.run(SimpleProducer.class, args);
    }

    @Override
    public void run(String... args) {

        Properties producerProperties = new Properties();
        producerProperties.put("bootstrap.servers", kafkaBootstrapServers);
        producerProperties.put("acks", "all");
        producerProperties.put("retries", 0);
        producerProperties.put("batch.size", 16384);
        producerProperties.put("linger.ms", 1);
        producerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProperties);

        //generate some random test messages and send to Kafka
        sendTestMessagesToKafka(producer);

        
        //Defining Kafka consumer properties.
        Properties consumerProperties = new Properties();
        consumerProperties.put("bootstrap.servers", kafkaBootstrapServers);
        consumerProperties.put("group.id", zookeeperGroupId);
        consumerProperties.put("zookeeper.session.timeout.ms", "6000");
        consumerProperties.put("zookeeper.sync.time.ms","2000");
        consumerProperties.put("auto.commit.enable", "false"); //Each message after consuming, need to be committed for offset move
        consumerProperties.put("auto.commit.interval.ms", "1000");
        consumerProperties.put("consumer.timeout.ms", "-1");
        consumerProperties.put("max.poll.records", "1");
        consumerProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProperties.put("allow.auto.create.topics", false);

        //Thread to listen to the kafka topic
        
       
        Thread kafkaConsumerThread = new Thread(() -> {
            logger.info("Starting Kafka consumer thread");
            SimpleConsumer kafkaConsumer = new SimpleConsumer(theTechCheckTopicName,consumerProperties );
            kafkaConsumer.runSingleWorker();
        });

        kafkaConsumerThread.start();
        
    }


    private void sendTestMessagesToKafka(KafkaProducer<String, String> producer) {
        // Loop iterates 10 times, from 0 to 9, and sending a message to Kafka.
        
        for (int index = 0; index < 10; index++) {
            sendKafkaMessage("The index is now: " + index, producer, theTechCheckTopicName);
        }

        //Creates an instance of JSONObject and sending this object to Kafka
        for (int index = 0; index < 10; index++) {

            JSONObject jsonObject = new JSONObject();
            JSONObject nestedJsonObject = new JSONObject();

            try {
                jsonObject.put("index", index);
                jsonObject.put("message", "The index is now: " + index);
                nestedJsonObject.put("nestedObjectMessage", "This is a nested JSON object with index: " + index);
                jsonObject.put("nestedJsonObject", nestedJsonObject);

            } catch (JSONException e) {
                logger.error(e.getMessage());
            }

            //Serialize the JSON object and send it to the same topic in Kafka,
            sendKafkaMessage(jsonObject.toString(), producer, theTechCheckTopicName);
        }
    }


    private static void sendKafkaMessage(String payload, KafkaProducer<String, String> producer, String topic)
    {
        logger.info("Sending-Kafka-Message: " + payload);
        producer.send(new ProducerRecord<>(topic, payload));
    }
}
