# Jaeger 分布式追踪

## 是什么（What）

Jaeger 是 CNCF 开源的分布式追踪系统，本项目集成 OpenTracing + Jaeger 实现微服务调用链追踪。

## 为什么用（Why）

- 标准化：基于 OpenTracing 标准，可替换追踪后端
- 可视化：提供直观的调用链可视化界面
- 性能分析：分析服务间调用延迟和瓶颈
- 云原生：CNCF 毕业项目，与 Kubernetes 生态集成良好

## 怎么用（How）

### 1. 部署 Jaeger

```bash
# Docker 方式（All-in-One）
docker run -d --name jaeger \
  -p 5775:5775/udp \
  -p 6831:6831/udp \
  -p 6832:6832/udp \
  -p 5778:5778 \
  -p 16686:16686 \
  -p 14268:14268 \
  -p 14250:14250 \
  jaegertracing/all-in-one:1.35
```

### 2. 开启配置

修改 `application-dev.yml`：

```yaml
opentracing:
  jaeger:
    service-name: ${spring.application.name}
    enabled: true              # 开关
    log-spans: true            # 日志输出 Span
    enable-b3-propagation: true  # 支持 B3 格式透传
    enable-w3c-propagation: true # 支持 W3C 格式透传
    const-sampler:
      decision: true           # 全量采样
    probabilistic-sampler:
      sampling-rate: 1         # 采样率 100%
  spring:
    cloud:
      async:
        enabled: false         # 关闭异步配置（与高版本 Spring 冲突）
```

### 3. 自定义 Span

```java
@Autowired
private Tracer tracer;

public void doSomething() {
    Span span = tracer.buildSpan("doSomething").start();
    try (Scope scope = tracer.activateSpan(span)) {
        // 添加标签
        span.setTag("user.id", userId);
        span.setTag("order.id", orderId);
        
        // 业务逻辑
        
    } catch (Exception e) {
        span.setTag("error", true);
        span.log(Map.of("error.message", e.getMessage()));
        throw e;
    } finally {
        span.finish();
    }
}
```

## 实战案例（Cases）

### 案例一：HTTP 请求自动追踪

框架自动埋点，无需手动编码。访问 `http://localhost:16686` 查看 Jaeger UI。

### 案例二：跨服务追踪

```java
// 服务 A 调用服务 B
@Autowired
private RestTemplate restTemplate;

public String callServiceB() {
    // RestTemplate 自动透传 TraceId
    return restTemplate.getForObject("http://service-b/api/data", String.class);
}
```

### 案例三：异步任务追踪

```java
@Autowired
private Tracer tracer;

public void asyncTask() {
    Span span = tracer.activeSpan();
    CompletableFuture.runAsync(() -> {
        try (Scope scope = tracer.activateSpan(span)) {
            // 异步任务中继续追踪
        }
    });
}
```

## 避坑指南（Pitfalls）

1. **采样率**：生产环境不要使用 100% 采样率，会影响性能
2. **异步配置**：`opentracing.spring.cloud.async.enabled` 与高版本 Spring 冲突
3. **Span 关闭**：确保 Span 正确关闭，否则数据不完整
4. **存储后端**：生产环境使用 Elasticsearch 或 Cassandra 存储

## 最佳实践（Best Practices）

1. 生产环境使用概率采样，采样率根据流量调整
2. 关键业务添加自定义标签，便于问题定位
3. 使用 B3 或 W3C 格式实现跨系统追踪
4. 配合日志系统，通过 TraceId 关联日志

