# Zipkin Distributed Tracing

## What

Zipkin is a distributed tracing system open-sourced by Twitter. This project integrates Spring Cloud Sleuth + Zipkin for microservice call chain tracing.

## Why

- Spring Ecosystem: Seamless integration with Spring Cloud
- Auto Instrumentation: Sleuth automatically adds tracing for HTTP, RPC, MQ, etc.
- Multiple Reporting Methods: Supports HTTP, Kafka, RabbitMQ reporting
- Lightweight: Simple deployment with low resource consumption

## How

### 1. Deploy Zipkin

```bash
# Docker method
docker run -d --name zipkin -p 9411:9411 openzipkin/zipkin:2.23
```

### 2. Enable Configuration

Modify `application-dev.yml`:

```yaml
spring:
  sleuth:
    sampler:
      probability: 1           # Sampling rate 100%
    async:
      configurer:
        enabled: false         # Conflicts with higher Spring versions, disable
  zipkin:
    enabled: true              # Switch
    discovery-client-enabled: true
    locator:
      discovery:
        enabled: true
    sender:
      type: kafka              # Reporting method: web/kafka/rabbit
    kafka:
      topic: zipkin            # Kafka Topic
```

### 3. Kafka Reporting Configuration

```yaml
spring:
  kafka:
    producer:
      batch-size: 4096
      buffer-memory: 40960
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```

### 4. Custom Span

```java
@Autowired
private Tracer tracer;

public void doSomething() {
    Span span = tracer.nextSpan().name("doSomething").start();
    try (Tracer.SpanInScope ws = tracer.withSpan(span)) {
        // Add tags
        span.tag("user.id", userId);
        
        // Business logic
        
    } catch (Exception e) {
        span.error(e);
        throw e;
    } finally {
        span.end();
    }
}
```

## Cases

### Case 1: HTTP Reporting

```yaml
spring:
  zipkin:
    enabled: true
    base-url: http://localhost:9411
    sender:
      type: web
```

### Case 2: Kafka Reporting (Recommended)

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

Zipkin server configured with Kafka consumer:

```bash
docker run -d --name zipkin \
  -e KAFKA_BOOTSTRAP_SERVERS=localhost:9092 \
  -p 9411:9411 \
  openzipkin/zipkin:2.23
```

### Case 3: Log Correlation with TraceId

```xml
<!-- logback-spring.xml -->
<pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{traceId}] [%thread] %-5level %logger{36} - %msg%n</pattern>
```

## Pitfalls

1. **Sampling Rate**: Do not use 100% sampling rate in production
2. **Async Configuration**: `spring.sleuth.async.configurer.enabled` conflicts with higher Spring versions
3. **Kafka Configuration**: When using Kafka reporting, ensure Zipkin server is also configured with Kafka
4. **Storage Backend**: Use Elasticsearch or MySQL for storage in production

## Best Practices

1. Use Kafka reporting to avoid HTTP reporting performance overhead
2. Set sampling rate to 0.1 (10%) or lower in production
3. Configure log output with TraceId for log correlation
4. Use Elasticsearch for storage to support large-scale data queries
