# Dubbo RPC Framework

## What

Apache Dubbo is a high-performance RPC framework open-sourced by Alibaba. This project integrates Dubbo for remote service invocation between microservices.

## Why

- High Performance: High-performance network communication based on Netty
- Service Governance: Load balancing, service degradation, cluster fault tolerance
- Multi-Protocol Support: Supports Dubbo, REST, gRPC and other protocols
- Rich Ecosystem: Seamless integration with Nacos, Sentinel and other components

## How

### 1. Enable Configuration

Modify `application-dev.yml`:

```yaml
dubbo:
  enabled: true # Switch, do not use @EnabledDubbo annotation, otherwise cannot be disabled
  scan:
    base-packages: org.ylzl.eden.demo.adapter # Dubbo service scan
  application:
    id: ${spring.application.name}
    name: ${spring.application.name}
  protocol:
    name: dubbo
    port: -1 # Random port
```

### 2. Nacos Registry Configuration

```yaml
dubbo:
  registry:
    id: ${spring.application.name}
    address: nacos://${spring.cloud.nacos.discovery.server-addr}?namespace=${spring.cloud.nacos.discovery.namespace}&group=dubbo
    protocol: nacos
    check: false  # Whether to enable startup check for service registration
    register: true # Whether to enable service registration
  config-center:
    address: nacos://${spring.cloud.nacos.discovery.server-addr}?namespace=${spring.cloud.nacos.discovery.namespace}&group=config
  metadata-report:
    address: nacos://${spring.cloud.nacos.discovery.server-addr}?namespace=${spring.cloud.nacos.discovery.namespace}&group=metadata
```

### 3. Provider and Consumer Configuration

```yaml
dubbo:
  provider:
    retries: 0      # Retry count
    timeout: 3000   # Timeout
    check: false    # Whether to enable check
    filter: cat-tracing
  consumer:
    retries: 0      # Retry count
    timeout: 3000   # Timeout
    check: false    # Whether to enable check
    filter: cat-tracing,cat-consumer
```

### 4. Define Service Interface

```java
// Define interface in client module
public interface UserService {
    User getUserById(Long id);
    void createUser(User user);
}
```

### 5. Implement Service Provider

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

### 6. Call Service Consumer

```java
@Service
public class OrderService {

    @DubboReference
    private UserService userService;

    public void createOrder(Long userId) {
        User user = userService.getUserById(userId);
        // Business logic
    }
}
```

## Cases

### Case 1: Load Balancing Configuration

```java
@DubboReference(loadbalance = "roundrobin")
private UserService userService;
```

Load balancing strategies:
- `random`: Random (default)
- `roundrobin`: Round Robin
- `leastactive`: Least Active
- `consistenthash`: Consistent Hash

### Case 2: Service Degradation

```java
@DubboReference(mock = "com.example.UserServiceMock")
private UserService userService;

public class UserServiceMock implements UserService {
    @Override
    public User getUserById(Long id) {
        return new User("Fallback Default User");
    }
}
```

### Case 3: CAT Tracing Integration

```yaml
dubbo:
  provider:
    filter: cat-tracing
  consumer:
    filter: cat-tracing,cat-consumer
```

## Pitfalls

1. **Startup Check**: Disable `check: false` in development to avoid startup failure when dependent services are not running
2. **Timeout Configuration**: Consumer timeout should be less than provider timeout to avoid duplicate calls due to retries
3. **Serialization**: Interface parameters and return values must implement Serializable
4. **Annotation Conflict**: Do not use `@EnableDubbo`, use `dubbo.enabled` to control the switch

## Best Practices

1. Define interfaces in a separate client module for easy reference by service consumers
2. Set reasonable timeout and retry counts to avoid avalanche
3. Use Nacos as registry for service discovery
4. Integrate CAT or Sentinel for tracing and traffic governance
