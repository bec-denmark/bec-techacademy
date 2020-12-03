# Description
Releated code to the udemy course https://bec.udemy.com/course/apache-kafka/learn

#How to's

##Run Producer
Open a terminal and in directory ./producer
mvn compile exec:java

##Run consumer
Open a terminal and in directory ./consumer 
mvn compile exec:java

## Use full commands

###Producer to topic with console producer
./kafka-console-producer.sh --broker-list localhost:9092 --topic topic1

###Consume from topic with console consumer
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic1

###List Topics
./kafka-topics.sh --bootstrap-server localhost:9092 --describe

###See Consumer lag
./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --all-groups

