package dk.bec.gradprogram.kafka.streaming;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;

public class KTableDemo {

    private static String INPUT_TOPIC = "t1.party.customer.test.v1";

    static Properties getStreamsConfig() {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "StreamingKTable-AMI");
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-bootstrap-inpexp-t1.test.inp.ocp.bec.dk:443");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "grad-consumer-group-stream");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        //No constants for below - https://github.com/confluentinc/confluent-kafka-dotnet/issues/941
        properties.put("security.protocol", "SSL");
        properties.put("ssl.truststore.location", "C://bec//tools//kafka//testWild.jks");
        properties.put("ssl.truststore.password", "password");
        properties.put("ssl.truststore.type", "jks");
        return properties;
    }

    private static String CUSTOMER_LIST_OUTPUT_TOPIC ="t1.party.unique.customer.grad.ami";
    static void createUniqueCustomerTable(final StreamsBuilder builder) {

        KeyValueBytesStoreSupplier storeSupplier = Stores.inMemoryKeyValueStore("KTableDemoStore-ami");
        KTable<String, String> table = builder.table(
                INPUT_TOPIC,
                Materialized.<String, String>as(storeSupplier)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(Serdes.String())
        );
        final Topology topology = builder.build();
        table.toStream().to(CUSTOMER_LIST_OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.String()));
    }

    public static void main(String[] args) {

        final Properties props = getStreamsConfig();

        final StreamsBuilder builder = new StreamsBuilder();
        createUniqueCustomerTable(builder);
        final KafkaStreams streams = new KafkaStreams(builder.build(), props);
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-customertable-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        } catch (final Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}

