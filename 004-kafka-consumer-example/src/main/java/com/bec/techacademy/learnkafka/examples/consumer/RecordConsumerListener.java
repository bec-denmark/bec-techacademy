package com.bec.techacademy.learnkafka.examples.consumer;


import com.bec.techacademy.learnkafka.datamodel.event.SpecificRecordAdapter;

public interface RecordConsumerListener {
    void recordProcessed(String key, SpecificRecordAdapter record);
}
