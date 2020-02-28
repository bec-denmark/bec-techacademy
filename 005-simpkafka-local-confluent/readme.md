# Simple Kafka Producer (and Consumer without auto-commit) without forced Grammar

## Pre-Requisites
Local setup of docker, docker-compose
On linux/Mac, install docker compose using:
   a. sudo curl -L https://github.com/docker/compose/releases/download/1.21.2/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
   b. sudo chmod +x /usr/local/bin/docker-compose
   c. Verify docker compose version using "docker-compose --version"
Local setup of https://github.com/confluentinc/examples/tree/5.4.0-post/cp-all-in-one running

## Kafka Topic

```shell
bta-test-kafka-topic
```

## Creating a Kafka Topic

```cd``` into the Kafka directory, and run the following command to create a new topic:

```shell
./bin/kafka-topics.sh --create --topic bta-test-kafka-topic --replication-factor 1 --partitions 1 --zookeeper localhost:2181
```

## Running the SpringBoot application

```cd``` into the project directory and run the following command to create a ```.jar``` file of the project:

```shell
mvn clean install
```

This will create a ```.jar``` file in the ```target``` directory, inside the project directory. Now to run the project, run the following command from the same project directory:

```shell
java -jar target/<name_of_jar_file>.jar
```

You should now be seeing the output in the terminal
