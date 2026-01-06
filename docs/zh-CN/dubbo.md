# Dubbo RPC 框架

## 是什么（What）

Apache Dubbo 是阿里巴巴开源的高性能 RPC 框架，本项目集成 Dubbo 实现微服务间的远程调用。

## 为什么用（Why）

- 高性能：基于 Netty 的高性能网络通信
- 服务治理：负载均衡、服务降级、集群容错
- 多协议支持：支持 Dubbo、REST、gRPC 等协议
- 生态丰富：与 Nacos、Sentinel 等组件无缝集成

## 怎么用（How）

### 1. 开启配置

修改 `application-dev.yml`：

```yaml
dubbo:
  enabled: true # 开关，请不要使用 @EnabledDubbo 原生注解，否则无法关闭
  scan:
    base-packages: org.ylzl.eden.demo.adapter # Dubbo 服务扫描
  application:
    id: ${spring.application.name}
    name: ${spring.application.name}
  protocol:
    name: dubbo
    port: -1 # 随机端口
```

### 2. Nacos 注册中心配置

```yaml
dubbo:
  registry:
    id: ${spring.application.name}
    address: nacos://${spring.cloud.nacos.discovery.server-addr}?namespace=${spring.cloud.nacos.discovery.namespace}&group=dubbo
    protocol: nacos
    check: false  # 是否开启服务注册的启动检查
    register: true # 是否开启服务注册
  config-center:
    address: nacos://${spring.cloud.nacos.discovery.server-addr}?namespace=${spring.cloud.nacos.discovery.namespace}&group=config
  metadata-report:
    address: nacos://${spring.cloud.nacos.discovery.server-addr}?namespace=${spring.cloud.nacos.discovery.namespace}&group=metadata
```

### 3. 服务提供者和消费者配置

```yaml
dubbo:
  provider:
    retries: 0      # 重试次数
    timeout: 3000   # 超时时间
    check: false    # 是否开启检查
    filter: cat-tracing
  consumer:
    retries: 0      # 重试次数
    timeout: 3000   # 超时时间
    check: false    # 是否开启检查
    filter: cat-tracing,cat-consumer
```

### 4. 定义服务接口

```java
// 在 client 模块定义接口
public interface UserService {
    User getUserById(Long id);
    void createUser(User user);
}
```

### 5. 实现服务提供者

```java
@DubboService
public class UserServiceImpl implements UserService {

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }
}
```

### 6. 调用服务消费者

```java
@Service
public class OrderService {

    @DubboReference
    private UserService userService;

    public void createOrder(Long userId) {
        User user = userService.getUserById(userId);
        // 业务逻辑
    }
}
```

## 实战案例（Cases）

### 案例一：负载均衡配置

```java
@DubboReference(loadbalance = "roundrobin")
private UserService userService;
```

负载均衡策略：
- `random`：随机（默认）
- `roundrobin`：轮询
- `leastactive`：最少活跃调用
- `consistenthash`：一致性哈希

### 案例二：服务降级

```java
@DubboReference(mock = "com.example.UserServiceMock")
private UserService userService;

public class UserServiceMock implements UserService {
    @Override
    public User getUserById(Long id) {
        return new User("降级默认用户");
    }
}
```

### 案例三：集成 CAT 链路追踪

```yaml
dubbo:
  provider:
    filter: cat-tracing
  consumer:
    filter: cat-tracing,cat-consumer
```

## 避坑指南（Pitfalls）

1. **启动检查**：开发环境建议关闭 `check: false`，避免依赖服务未启动导致启动失败
2. **超时配置**：消费者超时应小于提供者超时，避免重试导致重复调用
3. **序列化**：接口参数和返回值必须实现 Serializable
4. **注解冲突**：不要使用 `@EnableDubbo`，使用 `dubbo.enabled` 控制开关

## 最佳实践（Best Practices）

1. 接口定义在独立的 client 模块，便于服务消费者引用
2. 合理设置超时和重试次数，避免雪崩
3. 使用 Nacos 作为注册中心，实现服务发现
4. 集成 CAT 或 Sentinel 实现链路追踪和流量治理

