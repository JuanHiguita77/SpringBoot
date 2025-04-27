# Creating a container with Kafka bitnami image

- docker run -d \
  --name kafka \
  --network internal-network \
  -p 9092:9092 -p 9094:9094 \
  -e KAFKA_CFG_NODE_ID=0 \
  -e KAFKA_CFG_PROCESS_ROLES=controller,broker \
  -e KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093,EXTERNAL://:9094 \       
  -e KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092,EXTERNAL://localhost:9094 \ 
  -e KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT,PLAINTEXT:PLAINTEXT \
  -e KAFKA_CFG_CONTROLLER_LISTENER_NAMES=CONTROLLER \
  -e KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=0@kafka:9093 \
  -e KAFKA_KRAFT_CLUSTER_ID= UUID generated paste here \
  bitnami/kafka:latest

## Create a new topic

- kafka-topics.sh \
  --create \
  --topic patient \
  --bootstrap-server localhost:9092 \
  --partitions 1 \
  --replication-factor 1

## List all topics

- kafka-topics.sh --list --bootstrap-server localhost29092