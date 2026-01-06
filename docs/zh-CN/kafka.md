# Kafka 消息引擎

## 是什么（What）

Apache Kafka 是高吞吐量的分布式消息系统，本项目集成 Spring Kafka 实现消息的生产和消费。

## 为什么用（Why）

- 高吞吐量：单机每秒可处理百万级消息
- 持久化：消息持久化到磁盘，支持消息回溯
- 分布式：天然支持分区和副本机制
- 生态丰富：与大数据生态（Spark、Flink）无缝集成

## 怎么用（How）

### 1. 安装 Kafka

```bash
# Docker 方式
docker run -d --name zookeeper -p 2181:2181 zookeeper:3.7
docker run -d --name kafka -p 9092:9092 \
  -e KAFKA_ZOOKEEPER_CONNECT=host.docker.internal:2181 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
  wurstmeister/kafka:2.13-2.8.1
```

### 2. 开启配置

修改 `application-dev.yml`：

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    client-id: ${spring.application.name}
    producer:
      acks: all                    # 发送确认机制
      batch-size: 4KB              # 批处理大小
      buffer-memory: 40960         # 发送缓冲大小
      retries: 3                   # 失败重试次数
      compression-type: lz4        # 压缩类型
    consumer:
      group-id: ${spring.application.name}
      enable-auto-commit: false    # 关闭自动提交
      auto-offset-reset: earliest  # 重连时重置到最早偏移量
      heartbeat-interval: 5000     # 心跳频率
      max-poll-records: 100        # 单次拉取最大记录数
      fetch-max-wait: 3000         # 拉取等待时间
      fetch-min-size: 4096         # 触发拉取的最小值
      isolation-level: READ_COMMITTED
    listener:
      type: BATCH                  # 批量监听
      ack-mode: MANUAL_IMMEDIATE   # 手动提交
      concurrency: 5               # 消费线程数
      poll-timeout: 5000           # 拉取超时时间
```

### 3. 发送消息

```java
@Autowired
private KafkaTemplate<String, String> kafkaTemplate;

// 同步发送
public void sendSync(String topic, String message) {
    kafkaTemplate.send(topic, message).get();
}

// 异步发送
public void sendAsync(String topic, String key, String message) {
    kafkaTemplate.send(topic, key, message)
        .addCallback(
            result -> log.info("发送成功: {}", result),
            ex -> log.error("发送失败", ex)
        );
}
```

### 4. 消费消息

```java
@Component
public class DemoConsumer {

    @KafkaListener(topics = "demo-topic", groupId = "demo-group")
    public void consume(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try {
            log.info("收到消息: key={}, value={}", record.key(), record.value());
            // 业务处理
            ack.acknowledge(); // 手动提交
        } catch (Exception e) {
            log.error("消费失败", e);
            // 不提交，消息会重新消费
        }
    }
}
```

## 实战案例（Cases）

### 案例一：批量消费

```java
@KafkaListener(topics = "batch-topic", groupId = "batch-group")
public void batchConsume(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
    log.info("批量收到 {} 条消息", records.size());
    for (ConsumerRecord<String, String> record : records) {
        // 处理每条消息
    }
    ack.acknowledge(); // 批量提交
}
```

### 案例二：指定分区消费

```java
@KafkaListener(
    topicPartitions = @TopicPartition(
        topic = "demo-topic",
        partitions = {"0", "1"}
    ),
    groupId = "partition-group"
)
public void consumePartition(ConsumerRecord<String, String> record) {
    log.info("分区 {} 消息: {}", record.partition(), record.value());
}
```

## 避坑指南（Pitfalls）

1. **自动提交**：关闭 `enable-auto-commit`，使用手动提交避免消息丢失
2. **消费组实例数**：消费组实例数不能超过分区数，否则有实例空闲
3. **重平衡**：消费者加入/退出会触发重平衡，注意处理
4. **消息顺序**：只有同一分区内的消息有序

## 最佳实践（Best Practices）

1. 使用手动提交模式，确保消息处理完成后再提交
2. 合理设置分区数，分区数 >= 消费者实例数
3. 使用 key 保证相同 key 的消息发送到同一分区
4. 批量消费提升吞吐量，但注意单批处理时间

