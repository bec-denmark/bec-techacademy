package com.bec.techacademy.learnkafka.datamodel.event;

import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.specific.SpecificRecordBase;

import java.util.Objects;

public class SpecificRecordAdapter extends SpecificRecordBase {
    private SpecificRecord specificRecord;

    public SpecificRecordAdapter(SpecificRecord specificRecord) {
        this.specificRecord = specificRecord;
    }

    @Override
    public void put(int i, Object o) {
        specificRecord.put(i, 0);
    }

    @Override
    public Object get(int i) {
        return specificRecord.get(i);
    }

    @Override
    public Schema getSchema() {
        return specificRecord.getSchema();
    }

    public Object getRecord(){
        return specificRecord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecificRecordAdapter that = (SpecificRecordAdapter) o;
        return Objects.equals(specificRecord, that.specificRecord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), specificRecord);
    }

    @Override
    public String toString() {
        return "SpecificRecordAdapter{" +
                "specificRecord=" + specificRecord +
                '}';
    }
}
