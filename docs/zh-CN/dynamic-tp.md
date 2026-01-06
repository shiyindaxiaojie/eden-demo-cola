# Dynamic-TP 动态线程池

## 是什么（What）

Dynamic-TP 是基于配置中心的动态线程池组件，本项目集成 Dynamic-TP 实现线程池参数动态调整和监控告警。

## 为什么用（Why）

- 动态调参：无需重启应用即可调整线程池参数
- 实时监控：线程池运行状态实时监控
- 告警通知：线程池异常时及时告警
- 多框架支持：支持 Dubbo、RocketMQ、Undertow 等线程池

## 怎么用（How）

### 1. 开启配置

修改 `application-dev.yml`：

```yaml
spring:
  dynamic:
    tp:
      enabled: true
      enabledBanner: false
      enabledCollect: true        # 开启指标采集
      collectorTypes: micrometer  # 采集类型
      logPath: ${user.home}/logs
      monitorInterval: 5          # 监控间隔（秒）
```

### 2. Web 容器线程池配置

```yaml
spring:
  dynamic:
    tp:
      undertowTp:
        corePoolSize: 16
        maximumPoolSize: 256
        keepAliveTime: 60
```

### 3. Dubbo 线程池配置

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

### 4. RocketMQ 线程池配置

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

### 5. 自定义线程池配置

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

### 6. 使用自定义线程池

```java
@Resource(name = "dtpExecutor")
private ThreadPoolExecutor dtpExecutor;

public void asyncTask() {
    dtpExecutor.execute(() -> {
        // 异步任务
    });
}
```

## 实战案例（Cases）

### 案例一：动态调整线程池参数

通过 Nacos 配置中心动态修改：

```yaml
spring:
  dynamic:
    tp:
      executors:
        - threadPoolName: myApp#dtpExecutor
          corePoolSize: 32        # 动态调整
          maximumPoolSize: 512    # 动态调整
          queueCapacity: 1000     # 动态调整
```

修改配置后，线程池参数自动生效，无需重启。

### 案例二：配置告警通知

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
          threshold: 80           # 队列容量告警阈值
        - type: liveness
          threshold: 80           # 线程活跃度告警阈值
        - type: reject
          threshold: 1            # 拒绝任务告警阈值
```

### 案例三：监控指标采集

```yaml
spring:
  dynamic:
    tp:
      enabledCollect: true
      collectorTypes: micrometer
```

配合 Prometheus + Grafana 实现可视化监控。

## 避坑指南（Pitfalls）

1. **线程池命名**：使用 `应用名#线程池名` 格式，便于区分
2. **队列类型**：`VariableLinkedBlockingQueue` 支持动态调整容量
3. **拒绝策略**：根据业务选择合适的拒绝策略
4. **监控间隔**：`monitorInterval` 不宜过小，避免性能开销

## 最佳实践（Best Practices）

1. 为不同业务场景创建独立的线程池
2. 配置告警通知，及时发现线程池异常
3. 结合监控系统，分析线程池运行状态
4. 根据业务负载动态调整线程池参数

