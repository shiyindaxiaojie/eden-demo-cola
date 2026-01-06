# Sentinel Traffic Governance

## What

Sentinel is a traffic governance component open-sourced by Alibaba. This project integrates [Sentinel](https://github.com/shiyindaxiaojie/Sentinel) for rate limiting, circuit breaking, and system protection.

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/sentinel/sentinel-dashboard-overview-custom.png)

## Why

- Flow Control: Rate limiting based on QPS or concurrency to protect system stability
- Circuit Breaking: Automatic circuit breaking when service anomalies occur to prevent avalanche
- System Protection: Adaptive protection based on system load
- Real-time Monitoring: Dashboard for real-time traffic and rule viewing

## How

### 1. Deploy Sentinel Dashboard

```bash
# Download and start
java -Dserver.port=8090 -jar sentinel-dashboard-1.8.6.jar
```

### 2. Enable Configuration

Modify `application-dev.yml`:

```yaml
spring:
  cloud:
    sentinel:
      enabled: true              # Switch
      http-method-specify: true  # RESTful compatible
      eager: true                # Immediately refresh to Dashboard
      transport:
        dashboard: localhost:8090
```

### 3. Nacos Rule Persistence

```yaml
spring:
  cloud:
    sentinel:
      datasource:
        flow:
          nacos:
            server-addr: ${spring.cloud.nacos.config.server-addr}
            namespace: ${spring.cloud.nacos.config.namespace}
            groupId: sentinel
            dataId: ${spring.application.name}-flow-rule
            rule-type: flow
            data-type: json
```

### 4. Use Annotations

```java
@SentinelResource(
    value = "getUserById",
    blockHandler = "getUserByIdBlockHandler",
    fallback = "getUserByIdFallback"
)
public User getUserById(Long id) {
    return userRepository.findById(id);
}

// Rate limiting handler
public User getUserByIdBlockHandler(Long id, BlockException ex) {
    return new User("Rate Limited Default User");
}

// Fallback handler
public User getUserByIdFallback(Long id, Throwable ex) {
    return new User("Fallback Default User");
}
```

## Cases

### Case 1: QPS Flow Control Rule

Configure `{app-name}-flow-rule` in Nacos:

```json
[
  {
    "resource": "getUserById",
    "limitApp": "default",
    "grade": 1,
    "count": 100,
    "strategy": 0,
    "controlBehavior": 0
  }
]
```

Parameter description:
- `grade`: 1=QPS, 0=Concurrency
- `count`: Threshold
- `controlBehavior`: 0=Fast Fail, 1=Warm Up, 2=Queue

### Case 2: Circuit Breaking Rule

```json
[
  {
    "resource": "getUserById",
    "grade": 0,
    "count": 5,
    "timeWindow": 10,
    "minRequestAmount": 5,
    "statIntervalMs": 1000,
    "slowRatioThreshold": 0.5
  }
]
```

Parameter description:
- `grade`: 0=Slow Call Ratio, 1=Exception Ratio, 2=Exception Count
- `timeWindow`: Circuit breaking duration (seconds)
- `slowRatioThreshold`: Slow call ratio threshold

### Case 3: Global Exception Handler

```java
@Component
public class SentinelBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, 
                       BlockException e) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("{\"code\":429,\"message\":\"Too many requests\"}");
    }
}
```

## Pitfalls

1. **Rule Persistence**: Rules configured in Dashboard are lost after restart, use Nacos for persistence
2. **RESTful Compatible**: Enable `http-method-specify` to distinguish different HTTP methods
3. **Resource Name**: The value of `@SentinelResource` must match the resource in the rule
4. **Exception Handling**: `blockHandler` and `fallback` method signatures must match

## Best Practices

1. Use Nacos to persist rules to avoid rule loss
2. Configure rate limiting and circuit breaking rules for core interfaces
3. Set reasonable circuit breaking thresholds to avoid false positives
4. Regularly check Dashboard monitoring data and adjust rules
