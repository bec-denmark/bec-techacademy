package com.bec.techacademy.learnkafka.datamodel.event;

import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificRecordBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SpecificRecordSerializer<T extends SpecificRecordBase> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecificRecordSerializer.class);

    public byte[] serialize(String topic, T data) throws IOException {
        byte[] result = null;

        if (data != null) {
            LOGGER.debug("data='{}'", data);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BinaryEncoder binaryEncoder =
                    EncoderFactory.get().binaryEncoder(byteArrayOutputStream, null);

            DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(data.getSchema());
            datumWriter.write(data, binaryEncoder);

            binaryEncoder.flush();
            byteArrayOutputStream.close();

            result = byteArrayOutputStream.toByteArray();
            LOGGER.debug("serialized data='{}'", DatatypeConverter.printHexBinary(result));
        }
        return result;
    }
}