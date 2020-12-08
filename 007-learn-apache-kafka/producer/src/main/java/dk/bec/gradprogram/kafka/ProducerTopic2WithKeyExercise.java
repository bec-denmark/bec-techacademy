package dk.bec.gradprogram.kafka;

import static dk.bec.gradprogram.kafka.LoggerFactory.logRunning;

public class ProducerTopic2WithKeyExercise {
    public static void main(String[] args) {
        logRunning(ProducerTopic2WithKeyExercise.class);
        //Todo Create producer properties for connection to local kafka instance

        //Todo Create the Producer

        //Todo send data to topic with name topic2 with key

        //Todo If you run this multiple times what partitions is the message send to

        //Question Which partition(s) was produced to (See in the application log)
        //Question Which partition(s) was produced to when you run the application multiple times(See in the consumer application log)
    }
}
