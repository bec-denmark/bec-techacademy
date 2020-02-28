package com.bec.techacademy.learnkafka.examples.consumer;

import dk.martincallesen.datamodel.event.SpecificRecordAdapter;

public interface RecordConsumerListener {
    void recordProcessed(String key, SpecificRecordAdapter record);
}
