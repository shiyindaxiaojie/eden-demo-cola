# Dynamic-TP Dynamic Thread Pool

## What

Dynamic-TP is a dynamic thread pool component based on configuration center. This project integrates Dynamic-TP for dynamic thread pool parameter adjustment and monitoring alerts.

## Why

- Dynamic Tuning: Adjust thread pool parameters without restarting the application
- Real-time Monitoring: Real-time monitoring of thread pool status
- Alert Notification: Timely alerts when thread pool anomalies occur
- Multi-framework Support: Supports Dubbo, RocketMQ, Undertow and other thread pools

## How

### 1. Enable Configuration

Modify `application-dev.yml`:

```yaml
spring:
  dynamic:
    tp:
      enabled: true
      enabledBanner: false
      enabledCollect: true        # Enable metrics collection
      collectorTypes: micrometer  # Collection type
      logPath: ${user.home}/logs
      monitorInterval: 5          # Monitor interval (seconds)
```

### 2. Web Container Thread Pool Configuration

```yaml
spring:
  dynamic:
    tp:
      undertowTp:
        corePoolSize: 16
        maximumPoolSize: 256
        keepAliveTime: 60
```

### 3. Dubbo Thread Pool Configuration

```yaml
spring:
  dynamic:
    tp:
      dubboTp:
        - threadPoolName: ${spring.application.name}#dubboTp
          corePoolSize: 16
          maximumPoolSize: 256
          keepAliveTime: 60
```

### 4. RocketMQ Thread Pool Configuration

```yaml
spring:
  dynamic:
    tp:
      rocketMqTp:
        - threadPoolName: ${spring.application.name}#rocketMqTp
          corePoolSize: 16
          maximumPoolSize: 256
          keepAliveTime: 60
```

### 5. Custom Thread Pool Configuration

```yaml
spring:
  dynamic:
    tp:
      executors:
        - threadPoolName: ${spring.application.name}#dtpExecutor
          executorType: common
          corePoolSize: 16
          maximumPoolSize: 256
          queueCapacity: 500
          queueType: VariableLinkedBlockingQueue
          rejectedHandlerType: CallerRunsPolicy
```

### 6. Use Custom Thread Pool

```java
@Resource(name = "dtpExecutor")
private ThreadPoolExecutor dtpExecutor;

public void asyncTask() {
    dtpExecutor.execute(() -> {
        // Async task
    });
}
```

## Cases

### Case 1: Dynamically Adjust Thread Pool Parameters

Dynamically modify via Nacos configuration center:

```yaml
spring:
  dynamic:
    tp:
      executors:
        - threadPoolName: myApp#dtpExecutor
          corePoolSize: 32        # Dynamic adjustment
          maximumPoolSize: 512    # Dynamic adjustment
          queueCapacity: 1000     # Dynamic adjustment
```

After modifying the configuration, thread pool parameters take effect automatically without restart.

### Case 2: Configure Alert Notification

```yaml
spring:
  dynamic:
    tp:
      platforms:
        - platform: ding
          urlKey: your-ding-webhook-key
          secret: your-secret
      notifyItems:
        - type: capacity
          threshold: 80           # Queue capacity alert threshold
        - type: liveness
          threshold: 80           # Thread liveness alert threshold
        - type: reject
          threshold: 1            # Rejected task alert threshold
```

### Case 3: Metrics Collection

```yaml
spring:
  dynamic:
    tp:
      enabledCollect: true
      collectorTypes: micrometer
```

Combined with Prometheus + Grafana for visual monitoring.

## Pitfalls

1. **Thread Pool Naming**: Use `appName#poolName` format for easy identification
2. **Queue Type**: `VariableLinkedBlockingQueue` supports dynamic capacity adjustment
3. **Rejection Policy**: Choose appropriate rejection policy based on business needs
4. **Monitor Interval**: `monitorInterval` should not be too small to avoid performance overhead

## Best Practices

1. Create separate thread pools for different business scenarios
2. Configure alert notifications to detect thread pool anomalies promptly
3. Combine with monitoring systems to analyze thread pool status
4. Dynamically adjust thread pool parameters based on business load
