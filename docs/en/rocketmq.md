# RocketMQ Message Queue

## What

Apache RocketMQ is Alibaba's open-source distributed message middleware. This project integrates RocketMQ for asynchronous messaging, peak shaving, and more.

## Why

- High Performance: Single machine supports millions of message backlog
- High Availability: Supports master-slave sync, multi-replica mechanism
- Rich Features: Supports ordered messages, delayed messages, transactional messages
- Alibaba Ecosystem: Seamless integration with Dubbo, Nacos, etc.

## How

### 1. Install RocketMQ

```bash
# Docker method
docker run -d --name rmqnamesrv -p 9876:9876 apache/rocketmq:4.9.4 sh mqnamesrv
docker run -d --name rmqbroker -p 10911:10911 -p 10909:10909 \
  -e "NAMESRV_ADDR=host.docker.internal:9876" \
  apache/rocketmq:4.9.4 sh mqbroker
```

### 2. Enable Configuration

Modify `application-dev.yml`:

```yaml
rocketmq:
  enabled: true # Switch
  name-server: localhost:9876
  producer:
    namespace: ${spring.profiles.active}
    group: ${spring.application.name}
    send-message-timeout: 3000           # Send timeout (ms)
    retry-times-when-send-failed: 2      # Sync mode retry count
    retry-times-when-send-async-failed: 2 # Async mode retry count
  consumer:
    namespace: ${spring.profiles.active}
    group: ${spring.application.name}
    pull-batch-size: 500                 # Messages per pull
    consume-message-batch-max-size: 100  # Messages per consume
    consume-mode: CONCURRENTLY           # Concurrent mode
    consume-thread-min: 8                # Min consumer threads
    consume-thread-max: 64               # Max consumer threads
    consume-timeout: 15                  # Consume timeout (minutes)
```

### 3. Send Messages

```java
@Autowired
private RocketMQTemplate rocketMQTemplate;

// Sync send
public void sendSync(String topic, String message) {
    rocketMQTemplate.syncSend(topic, message);
}

// Async send
public void sendAsync(String topic, String message) {
    rocketMQTemplate.asyncSend(topic, message, new SendCallback() {
        @Override
        public void onSuccess(SendResult sendResult) {
            log.info("Send success: {}", sendResult);
        }
        @Override
        public void onException(Throwable e) {
            log.error("Send failed", e);
        }
    });
}
```

### 4. Consume Messages

```java
@Component
@RocketMQMessageListener(
    topic = "demo-topic",
    consumerGroup = "demo-consumer-group"
)
public class DemoConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("Received message: {}", message);
        // Business processing
    }
}
```

## Cases

### Case 1: Delayed Messages

```java
// Delay levels: 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
rocketMQTemplate.syncSend("demo-topic", 
    MessageBuilder.withPayload("Delayed message").build(), 
    3000,  // Timeout
    3);    // Delay level 3 = 10s
```

### Case 2: Ordered Messages

```java
// Producer: Messages with same hashKey go to same queue
rocketMQTemplate.syncSendOrderly("order-topic", message, orderId);

// Consumer: Use ordered consume mode
@RocketMQMessageListener(
    topic = "order-topic",
    consumerGroup = "order-consumer",
    consumeMode = ConsumeMode.ORDERLY
)
public class OrderConsumer implements RocketMQListener<String> {
    // ...
}
```

## Pitfalls

1. **Idempotent Consumption**: Messages may be delivered multiple times, implement idempotency
2. **Consume Timeout**: Too short `consume-timeout` causes duplicate consumption
3. **Ordered Messages**: Ordered consumption blocks queue, handle exceptions carefully
4. **Namespace**: Use different namespaces for different environments

## Best Practices

1. Use sync send for producers to ensure message reliability
2. Implement idempotency in consumers using unique IDs for deduplication
3. Set consumer thread count reasonably to avoid message backlog
4. Use namespace to isolate messages across environments

