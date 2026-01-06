# Kafka Message Engine

## What

Apache Kafka is a high-throughput distributed messaging system. This project integrates Spring Kafka for message production and consumption.

## Why

- High Throughput: Single machine can process millions of messages per second
- Persistence: Messages persisted to disk, supports message replay
- Distributed: Native support for partitioning and replication
- Rich Ecosystem: Seamless integration with big data ecosystem (Spark, Flink)

## How

### 1. Install Kafka

```bash
# Docker method
docker run -d --name zookeeper -p 2181:2181 zookeeper:3.7
docker run -d --name kafka -p 9092:9092 \
  -e KAFKA_ZOOKEEPER_CONNECT=host.docker.internal:2181 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
  wurstmeister/kafka:2.13-2.8.1
```

### 2. Enable Configuration

Modify `application-dev.yml`:

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    client-id: ${spring.application.name}
    producer:
      acks: all                    # Acknowledgment mechanism
      batch-size: 4KB              # Batch size
      buffer-memory: 40960         # Send buffer size
      retries: 3                   # Retry count
      compression-type: lz4        # Compression type
    consumer:
      group-id: ${spring.application.name}
      enable-auto-commit: false    # Disable auto commit
      auto-offset-reset: earliest  # Reset to earliest on reconnect
      heartbeat-interval: 5000     # Heartbeat frequency
      max-poll-records: 100        # Max records per poll
      fetch-max-wait: 3000         # Fetch wait time
      fetch-min-size: 4096         # Min fetch size
      isolation-level: READ_COMMITTED
    listener:
      type: BATCH                  # Batch listener
      ack-mode: MANUAL_IMMEDIATE   # Manual commit
      concurrency: 5               # Consumer threads
      poll-timeout: 5000           # Poll timeout
```

### 3. Send Messages

```java
@Autowired
private KafkaTemplate<String, String> kafkaTemplate;

// Sync send
public void sendSync(String topic, String message) {
    kafkaTemplate.send(topic, message).get();
}

// Async send
public void sendAsync(String topic, String key, String message) {
    kafkaTemplate.send(topic, key, message)
        .addCallback(
            result -> log.info("Send success: {}", result),
            ex -> log.error("Send failed", ex)
        );
}
```

### 4. Consume Messages

```java
@Component
public class DemoConsumer {

    @KafkaListener(topics = "demo-topic", groupId = "demo-group")
    public void consume(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try {
            log.info("Received message: key={}, value={}", record.key(), record.value());
            // Business processing
            ack.acknowledge(); // Manual commit
        } catch (Exception e) {
            log.error("Consume failed", e);
            // Don't commit, message will be reconsumed
        }
    }
}
```

## Cases

### Case 1: Batch Consumption

```java
@KafkaListener(topics = "batch-topic", groupId = "batch-group")
public void batchConsume(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
    log.info("Batch received {} messages", records.size());
    for (ConsumerRecord<String, String> record : records) {
        // Process each message
    }
    ack.acknowledge(); // Batch commit
}
```

### Case 2: Partition-specific Consumption

```java
@KafkaListener(
    topicPartitions = @TopicPartition(
        topic = "demo-topic",
        partitions = {"0", "1"}
    ),
    groupId = "partition-group"
)
public void consumePartition(ConsumerRecord<String, String> record) {
    log.info("Partition {} message: {}", record.partition(), record.value());
}
```

## Pitfalls

1. **Auto Commit**: Disable `enable-auto-commit`, use manual commit to avoid message loss
2. **Consumer Group Instances**: Instance count cannot exceed partition count
3. **Rebalancing**: Consumer join/leave triggers rebalancing
4. **Message Order**: Only messages within same partition are ordered

## Best Practices

1. Use manual commit mode to ensure message processing completion before commit
2. Set partition count >= consumer instance count
3. Use key to ensure messages with same key go to same partition
4. Batch consumption improves throughput, but watch single batch processing time

