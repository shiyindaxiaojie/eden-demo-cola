# CAT 应用监控

## 是什么（What）

CAT（Central Application Tracking）是大众点评开源的实时应用监控平台，本项目集成 [CAT](https://github.com/shiyindaxiaojie/cat) 实现事务追踪、性能分析和问题诊断。

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/cat/tracing.png)

## 为什么用（Why）

- 实时监控：秒级监控数据展示
- 全链路追踪：通过 TraceId 串联完整调用链
- 性能分析：HTTP、RPC、SQL、Cache 执行时间分析
- 问题诊断：快速定位性能瓶颈和异常

## 怎么用（How）

### 1. 部署 CAT Server

参考 [CAT 项目文档](https://github.com/shiyindaxiaojie/cat) 部署 CAT 服务端。

### 2. 开启配置

修改 `application-dev.yml`：

```yaml
cat:
  enabled: true              # 开关
  trace-mode: true           # 开启访问观测
  support-out-trace-id: true # 支持外部 HTTP 链路透传
  home: /tmp                 # CAT 客户端配置目录
  servers: localhost         # CAT 服务端地址
  tcp-port: 2280             # TCP 端口
  http-port: 8080            # HTTP 端口
```

### 3. 创建客户端配置

在 `/tmp` 目录下创建 `client.xml`：

```xml
<?xml version="1.0" encoding="utf-8"?>
<config mode="client">
    <servers>
        <server ip="localhost" port="2280" http-port="8080"/>
    </servers>
</config>
```

### 4. 使用示例

```java
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;

public void doSomething() {
    Transaction t = Cat.newTransaction("Service", "doSomething");
    try {
        // 业务逻辑
        t.setStatus(Transaction.SUCCESS);
    } catch (Exception e) {
        t.setStatus(e);
        Cat.logError(e);
        throw e;
    } finally {
        t.complete();
    }
}
```

## 实战案例（Cases）

### 案例一：HTTP 请求追踪

框架自动埋点，无需手动编码：

```yaml
cat:
  trace-mode: true # 开启后自动追踪 HTTP 请求
```

访问 CAT 控制台查看 Transaction 报表。

### 案例二：自定义业务埋点

```java
// 记录事件
Cat.logEvent("Order", "Create", Event.SUCCESS, "orderId=" + orderId);

// 记录指标
Cat.logMetricForCount("order.create.count");
Cat.logMetricForDuration("order.create.time", duration);

// 记录错误
Cat.logError("订单创建失败", exception);
```

### 案例三：链路透传

```java
// 获取当前 TraceId
String traceId = Cat.getCurrentMessageId();

// 设置远程调用的 TraceId
Cat.logRemoteCallClient(context);

// 服务端接收 TraceId
Cat.logRemoteCallServer(context);
```

## 避坑指南（Pitfalls）

1. **配置目录**：确保 `cat.home` 目录存在且有写权限
2. **服务端连接**：确保应用能访问 CAT 服务端的 TCP 和 HTTP 端口
3. **事务完成**：Transaction 必须调用 `complete()`，否则数据不完整
4. **性能影响**：高并发场景注意 CAT 客户端的性能开销

## 最佳实践（Best Practices）

1. 使用 `trace-mode` 自动埋点，减少手动编码
2. 关键业务添加自定义埋点，便于问题定位
3. 通过 TraceId 串联完整调用链
4. 定期查看 Problem 报表，及时发现异常

