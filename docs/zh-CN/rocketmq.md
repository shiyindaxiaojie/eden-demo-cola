# RocketMQ 消息队列

## 是什么（What）

Apache RocketMQ 是阿里巴巴开源的分布式消息中间件，本项目集成 RocketMQ 实现异步消息、削峰填谷等功能。

## 为什么用（Why）

- 高性能：单机支持百万级消息堆积
- 高可用：支持主从同步、多副本机制
- 丰富特性：支持顺序消息、延迟消息、事务消息
- 阿里生态：与 Dubbo、Nacos 等组件无缝集成

## 怎么用（How）

### 1. 安装 RocketMQ

```bash
# Docker 方式
docker run -d --name rmqnamesrv -p 9876:9876 apache/rocketmq:4.9.4 sh mqnamesrv
docker run -d --name rmqbroker -p 10911:10911 -p 10909:10909 \
  -e "NAMESRV_ADDR=host.docker.internal:9876" \
  apache/rocketmq:4.9.4 sh mqbroker
```

### 2. 开启配置

修改 `application-dev.yml`：

```yaml
rocketmq:
  enabled: true # 开关
  name-server: localhost:9876
  producer:
    namespace: ${spring.profiles.active}
    group: ${spring.application.name}
    send-message-timeout: 3000           # 生产消息超时（毫秒）
    retry-times-when-send-failed: 2      # 同步模式失败重试次数
    retry-times-when-send-async-failed: 2 # 异步模式失败重试次数
  consumer:
    namespace: ${spring.profiles.active}
    group: ${spring.application.name}
    pull-batch-size: 500                 # 单次拉取消息条数
    consume-message-batch-max-size: 100  # 单次消费消息条数
    consume-mode: CONCURRENTLY           # 并发模式
    consume-thread-min: 8                # 消费最小线程数
    consume-thread-max: 64               # 消费最大线程数
    consume-timeout: 15                  # 消费超时（分钟）
```

### 3. 发送消息

```java
@Autowired
private RocketMQTemplate rocketMQTemplate;

// 同步发送
public void sendSync(String topic, String message) {
    rocketMQTemplate.syncSend(topic, message);
}

// 异步发送
public void sendAsync(String topic, String message) {
    rocketMQTemplate.asyncSend(topic, message, new SendCallback() {
        @Override
        public void onSuccess(SendResult sendResult) {
            log.info("发送成功: {}", sendResult);
        }
        @Override
        public void onException(Throwable e) {
            log.error("发送失败", e);
        }
    });
}
```

### 4. 消费消息

```java
@Component
@RocketMQMessageListener(
    topic = "demo-topic",
    consumerGroup = "demo-consumer-group"
)
public class DemoConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("收到消息: {}", message);
        // 业务处理
    }
}
```

## 实战案例（Cases）

### 案例一：延迟消息

```java
// 延迟级别：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
rocketMQTemplate.syncSend("demo-topic", 
    MessageBuilder.withPayload("延迟消息").build(), 
    3000,  // 超时时间
    3);    // 延迟级别 3 = 10s
```

### 案例二：顺序消息

```java
// 生产者：相同 hashKey 的消息发送到同一队列
rocketMQTemplate.syncSendOrderly("order-topic", message, orderId);

// 消费者：使用顺序消费模式
@RocketMQMessageListener(
    topic = "order-topic",
    consumerGroup = "order-consumer",
    consumeMode = ConsumeMode.ORDERLY
)
public class OrderConsumer implements RocketMQListener<String> {
    // ...
}
```

## 避坑指南（Pitfalls）

1. **消费幂等**：消息可能重复投递，消费端必须实现幂等
2. **消费超时**：`consume-timeout` 设置过短会导致消息重复消费
3. **顺序消息**：顺序消费会阻塞队列，注意处理异常
4. **命名空间**：不同环境使用不同 namespace 隔离消息

## 最佳实践（Best Practices）

1. 生产者使用同步发送确保消息可靠性
2. 消费者实现幂等，使用唯一 ID 去重
3. 合理设置消费线程数，避免消费积压
4. 使用 namespace 隔离不同环境的消息

