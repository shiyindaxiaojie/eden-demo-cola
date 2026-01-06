# Dynamic-MQ 动态消息队列

## 是什么（What）

Dynamic-MQ 是本项目封装的动态消息队列组件，支持在 RocketMQ 和 Kafka 之间动态切换，实现消息中间件的统一抽象。

## 为什么用（Why）

- 统一抽象：屏蔽不同消息中间件的差异
- 动态切换：通过配置切换消息中间件，无需修改代码
- 平滑迁移：从 RocketMQ 迁移到 Kafka 或反向迁移更简单
- 多环境适配：不同环境可使用不同的消息中间件

## 怎么用（How）

### 1. 开启配置

修改 `application-dev.yml`：

```yaml
spring:
  message-queue:
    dynamic:
      primary: RocketMQ # 默认消息队列：RocketMQ 或 Kafka
```

### 2. RocketMQ 配置

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

### 3. Kafka 配置

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

### 4. 使用统一 API

```java
@Autowired
private DynamicMessageQueueTemplate mqTemplate;

// 发送消息
public void send(String topic, String message) {
    mqTemplate.send(topic, message);
}

// 发送带 Key 的消息
public void sendWithKey(String topic, String key, String message) {
    mqTemplate.send(topic, key, message);
}
```

## 实战案例（Cases）

### 案例一：开发环境使用 RocketMQ

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

### 案例二：生产环境使用 Kafka

```yaml
# application-prod.yml
spring:
  message-queue:
    dynamic:
      primary: Kafka
  kafka:
    bootstrap-servers: kafka-cluster:9092
```

### 案例三：同时使用两种消息队列

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

## 避坑指南（Pitfalls）

1. **特性差异**：RocketMQ 和 Kafka 特性不完全相同，如延迟消息、事务消息
2. **消费模式**：切换消息队列后，消费者需要重新配置
3. **消息格式**：确保消息序列化方式一致
4. **Topic 命名**：不同消息队列的 Topic 命名规范可能不同

## 最佳实践（Best Practices）

1. 使用统一的消息格式，便于切换消息队列
2. 避免使用特定消息队列的专有特性
3. 消费者实现幂等，应对消息重复
4. 通过配置文件区分不同环境的消息队列

