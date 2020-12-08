package dk.bec.gradprogram.kafka;

import static dk.bec.gradprogram.kafka.LoggerFactory.logRunning;

public class ProducerTopic1WithCallbackExercise {
    public static void main(String[] args) {
        logRunning(ProducerTopic1WithCallbackExercise.class);
        //Todo Create producer properties for connection to local kafka instance

        //Todo Create the Producer

        //Todo send data to topic1
        //Todo Print "Record send successfully" to console when a record was successfully send
        //Todo Print "Record send failed" to console when a record was not successfully send
        //Question Which partition(s) was produced to (See in the application log)
        //Question What happens when you try to send and the broker is stopped
    }
}
