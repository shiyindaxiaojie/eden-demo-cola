# Dynamic-MQ Dynamic Message Queue

## What

Dynamic-MQ is a dynamic message queue component encapsulated in this project, supporting dynamic switching between RocketMQ and Kafka, providing unified abstraction for message middleware.

## Why

- Unified Abstraction: Shield differences between message middleware
- Dynamic Switching: Switch message middleware via configuration without code changes
- Smooth Migration: Easier migration from RocketMQ to Kafka or vice versa
- Multi-environment Adaptation: Different environments can use different message middleware

## How

### 1. Enable Configuration

Modify `application-dev.yml`:

```yaml
spring:
  message-queue:
    dynamic:
      primary: RocketMQ # Default message queue: RocketMQ or Kafka
```

### 2. RocketMQ Configuration

```yaml
rocketmq:
  enabled: true
  name-server: localhost:9876
  producer:
    namespace: ${spring.profiles.active}
    group: ${spring.application.name}
    send-message-timeout: 3000
    retry-times-when-send-failed: 2
    retry-times-when-send-async-failed: 2
  consumer:
    namespace: ${spring.profiles.active}
    group: ${spring.application.name}
    pull-batch-size: 500
    consume-message-batch-max-size: 100
    consume-mode: CONCURRENTLY
    consume-thread-min: 8
    consume-thread-max: 64
    consume-timeout: 15
```

### 3. Kafka Configuration

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    client-id: ${spring.application.name}
    producer:
      acks: all
      batch-size: 4KB
      buffer-memory: 40960
      retries: 3
      compression-type: lz4
    consumer:
      group-id: ${spring.application.name}
      enable-auto-commit: false
      auto-offset-reset: earliest
      heartbeat-interval: 5000
      max-poll-records: 100
      fetch-max-wait: 3000
      fetch-min-size: 4096
      isolation-level: READ_COMMITTED
    listener:
      type: BATCH
      ack-mode: MANUAL_IMMEDIATE
      concurrency: 5
      poll-timeout: 5000
```

### 4. Use Unified API

```java
@Autowired
private DynamicMessageQueueTemplate mqTemplate;

// Send message
public void send(String topic, String message) {
    mqTemplate.send(topic, message);
}

// Send message with key
public void sendWithKey(String topic, String key, String message) {
    mqTemplate.send(topic, key, message);
}
```

## Cases

### Case 1: Development Environment with RocketMQ

```yaml
# application-dev.yml
spring:
  message-queue:
    dynamic:
      primary: RocketMQ

rocketmq:
  enabled: true
  name-server: localhost:9876
```

### Case 2: Production Environment with Kafka

```yaml
# application-prod.yml
spring:
  message-queue:
    dynamic:
      primary: Kafka
  kafka:
    bootstrap-servers: kafka-cluster:9092
```

### Case 3: Using Both Message Queues

```java
@Autowired
@Qualifier("rocketMQTemplate")
private MessageQueueTemplate rocketMQTemplate;

@Autowired
@Qualifier("kafkaTemplate")
private MessageQueueTemplate kafkaTemplate;

public void sendToRocketMQ(String message) {
    rocketMQTemplate.send("topic", message);
}

public void sendToKafka(String message) {
    kafkaTemplate.send("topic", message);
}
```

## Pitfalls

1. **Feature Differences**: RocketMQ and Kafka features are not identical (delayed messages, transactional messages)
2. **Consumer Mode**: Consumers need reconfiguration after switching message queue
3. **Message Format**: Ensure consistent message serialization
4. **Topic Naming**: Different message queues may have different topic naming conventions

## Best Practices

1. Use unified message format for easier message queue switching
2. Avoid using proprietary features of specific message queues
3. Implement idempotency in consumers to handle message duplication
4. Use configuration files to distinguish message queues across environments

