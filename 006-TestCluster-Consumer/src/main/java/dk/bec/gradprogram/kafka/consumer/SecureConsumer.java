package dk.bec.gradprogram.kafka.consumer;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;

public class SecureConsumer {

    public static void main(String[] args) {

        Properties properties=new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-bootstrap-inpexp-t1.test.inp.ocp.bec.dk:443");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"grad-consumer-group");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        //No constants for below - https://github.com/confluentinc/confluent-kafka-dotnet/issues/941
        properties.put("security.protocol","SSL");
        properties.put("ssl.truststore.location","C://bec//tools//kafka//testWild.jks");
        properties.put("ssl.truststore.password","password");
        properties.put("ssl.truststore.type","jks");

        KafkaConsumer< String, String> consumer=null;

        try {
            ArrayList<String> topics=new ArrayList<String>();
            topics.add("t1.party.customer.test.v1"); //subscribe to any number of topics.
            consumer = new KafkaConsumer<String, String>(properties);
            consumer.subscribe(topics);

            while(true){
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for(ConsumerRecord<String, String> record : records){
                    System.out.println("Incoming Message from Topic " + record.topic()  + " received. Full Contents: " +  record.toString()  );
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Inside exception loop : ");
            e.printStackTrace();
        }finally{
            consumer.close();
        }
    }
}