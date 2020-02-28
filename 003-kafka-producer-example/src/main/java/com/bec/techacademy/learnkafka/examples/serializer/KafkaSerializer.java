package com.bec.techacademy.learnkafka.examples.serializer;

import com.bec.techacademy.learnkafka.datamodel.event.SpecificRecordSerializer;
import com.bec.techacademy.learnkafka.datamodel.event.SpecificRecordAdapter;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class KafkaSerializer<T extends SpecificRecordAdapter> implements Serializer<T> {
    private SpecificRecordSerializer<T> recordSerializer;

    public KafkaSerializer() {
        this.recordSerializer = new SpecificRecordSerializer<>();
    }

    @Override
    public void close() {
        // No-op
    }

    @Override
    public void configure(Map<String, ?> arg0, boolean arg1) {
        // No-op
    }

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            return recordSerializer.serialize(topic, data);
        } catch (IOException ex) {
            throw new SerializationException("Can't serialize data='" + data + "' for topic='" + topic + "'", ex);
        }
    }
}