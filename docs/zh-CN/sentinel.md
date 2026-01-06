# Sentinel 流量治理

## 是什么（What）

Sentinel 是阿里巴巴开源的流量治理组件，本项目集成 [Sentinel](https://github.com/shiyindaxiaojie/Sentinel) 实现限流、熔断、系统保护等功能。

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/sentinel/sentinel-dashboard-overview-custom.png)

## 为什么用（Why）

- 流量控制：基于 QPS 或并发数限流，保护系统稳定
- 熔断降级：服务异常时自动熔断，防止雪崩
- 系统保护：基于系统负载自适应保护
- 实时监控：Dashboard 实时查看流量和规则

## 怎么用（How）

### 1. 部署 Sentinel Dashboard

```bash
# 下载并启动
java -Dserver.port=8090 -jar sentinel-dashboard-1.8.6.jar
```

### 2. 开启配置

修改 `application-dev.yml`：

```yaml
spring:
  cloud:
    sentinel:
      enabled: true              # 开关
      http-method-specify: true  # 兼容 RESTful
      eager: true                # 立刻刷新到 Dashboard
      transport:
        dashboard: localhost:8090
```

### 3. Nacos 规则持久化

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

### 4. 使用注解

```java
@SentinelResource(
    value = "getUserById",
    blockHandler = "getUserByIdBlockHandler",
    fallback = "getUserByIdFallback"
)
public User getUserById(Long id) {
    return userRepository.findById(id);
}

// 限流处理
public User getUserByIdBlockHandler(Long id, BlockException ex) {
    return new User("限流默认用户");
}

// 降级处理
public User getUserByIdFallback(Long id, Throwable ex) {
    return new User("降级默认用户");
}
```

## 实战案例（Cases）

### 案例一：QPS 限流规则

在 Nacos 中配置 `{app-name}-flow-rule`：

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

参数说明：
- `grade`: 1=QPS, 0=并发数
- `count`: 阈值
- `controlBehavior`: 0=快速失败, 1=Warm Up, 2=排队等待

### 案例二：熔断降级规则

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

参数说明：
- `grade`: 0=慢调用比例, 1=异常比例, 2=异常数
- `timeWindow`: 熔断时长（秒）
- `slowRatioThreshold`: 慢调用比例阈值

### 案例三：全局异常处理

```java
@Component
public class SentinelBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, 
                       BlockException e) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("{\"code\":429,\"message\":\"请求过于频繁\"}");
    }
}
```

## 避坑指南（Pitfalls）

1. **规则持久化**：Dashboard 配置的规则重启后丢失，需使用 Nacos 持久化
2. **RESTful 兼容**：开启 `http-method-specify` 区分不同 HTTP 方法
3. **资源名称**：`@SentinelResource` 的 value 需与规则中的 resource 一致
4. **异常处理**：`blockHandler` 和 `fallback` 方法签名需匹配

## 最佳实践（Best Practices）

1. 使用 Nacos 持久化规则，避免规则丢失
2. 为核心接口配置限流和熔断规则
3. 合理设置熔断阈值，避免误熔断
4. 定期查看 Dashboard 监控数据，调整规则

