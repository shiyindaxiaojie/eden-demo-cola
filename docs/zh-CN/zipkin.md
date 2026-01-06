# Zipkin 链路追踪

## 是什么（What）

Zipkin 是 Twitter 开源的分布式追踪系统，本项目集成 Spring Cloud Sleuth + Zipkin 实现微服务调用链追踪。

## 为什么用（Why）

- Spring 生态：与 Spring Cloud 无缝集成
- 自动埋点：Sleuth 自动为 HTTP、RPC、MQ 等添加追踪
- 多种上报方式：支持 HTTP、Kafka、RabbitMQ 上报
- 轻量级：部署简单，资源占用少

## 怎么用（How）

### 1. 部署 Zipkin

```bash
# Docker 方式
docker run -d --name zipkin -p 9411:9411 openzipkin/zipkin:2.23
```

### 2. 开启配置

修改 `application-dev.yml`：

```yaml
spring:
  sleuth:
    sampler:
      probability: 1           # 采样率 100%
    async:
      configurer:
        enabled: false         # 与 Spring 高版本冲突，关闭
  zipkin:
    enabled: true              # 开关
    discovery-client-enabled: true
    locator:
      discovery:
        enabled: true
    sender:
      type: kafka              # 上报方式：web/kafka/rabbit
    kafka:
      topic: zipkin            # Kafka Topic
```

### 3. Kafka 上报配置

```yaml
spring:
  kafka:
    producer:
      batch-size: 4096
      buffer-memory: 40960
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```

### 4. 自定义 Span

```java
@Autowired
private Tracer tracer;

public void doSomething() {
    Span span = tracer.nextSpan().name("doSomething").start();
    try (Tracer.SpanInScope ws = tracer.withSpan(span)) {
        // 添加标签
        span.tag("user.id", userId);
        
        // 业务逻辑
        
    } catch (Exception e) {
        span.error(e);
        throw e;
    } finally {
        span.end();
    }
}
```

## 实战案例（Cases）

### 案例一：HTTP 上报

```yaml
spring:
  zipkin:
    enabled: true
    base-url: http://localhost:9411
    sender:
      type: web
```

### 案例二：Kafka 上报（推荐）

```yaml
spring:
  zipkin:
    enabled: true
    sender:
      type: kafka
    kafka:
      topic: zipkin
  kafka:
    bootstrap-servers: localhost:9092
```

Zipkin 服务端配置 Kafka 消费：

```bash
docker run -d --name zipkin \
  -e KAFKA_BOOTSTRAP_SERVERS=localhost:9092 \
  -p 9411:9411 \
  openzipkin/zipkin:2.23
```

### 案例三：日志关联 TraceId

```xml
<!-- logback-spring.xml -->
<pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{traceId}] [%thread] %-5level %logger{36} - %msg%n</pattern>
```

## 避坑指南（Pitfalls）

1. **采样率**：生产环境不要使用 100% 采样率
2. **异步配置**：`spring.sleuth.async.configurer.enabled` 与高版本 Spring 冲突
3. **Kafka 配置**：使用 Kafka 上报时，确保 Zipkin 服务端也配置了 Kafka
4. **存储后端**：生产环境使用 Elasticsearch 或 MySQL 存储

## 最佳实践（Best Practices）

1. 使用 Kafka 上报，避免 HTTP 上报的性能开销
2. 生产环境采样率设置为 0.1（10%）或更低
3. 配置日志输出 TraceId，便于日志关联
4. 使用 Elasticsearch 存储，支持大规模数据查询

