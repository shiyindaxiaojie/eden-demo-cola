# Nacos Registry & Configuration Center

## What

Nacos is Alibaba's open-source service discovery, configuration management, and service management platform. This project integrates Nacos for service registration/discovery and dynamic configuration management.

## Why

- Service Registration & Discovery: Service governance in microservices architecture
- Dynamic Configuration Management: Hot configuration updates without restarting
- Supports Log4j2 dynamic configuration refresh

## How

### 1. Install Nacos

Refer to [Nacos Quick Start](https://nacos.io/en-us/docs/quick-start.html) for setup.

### 2. Enable Configuration

Modify `bootstrap-dev.yml`:

```yaml
spring:
  cloud:
    nacos:
      discovery: # Registry
        enabled: true
        server-addr: localhost:8848
        namespace: demo
        group: eden
        username: nacos
        password: nacos
      config: # Configuration Center
        name: ${spring.application.name}
        file-extension: yaml
        enabled: true
        server-addr: localhost:8848
        namespace: demo
        group: eden
        username: nacos
        password: nacos
        extension-configs:
          - group: eden
            data-id: log4j2.yml
            refresh: true
```

### 3. Log4j2 Dynamic Refresh (Optional)

```yaml
log4j2:
  nacos:
    enabled: true
    group: eden
    data-id: log4j2.yml
```

## Cases

### Case 1: Multi-environment Configuration Isolation

Isolate environments using `namespace`:

```yaml
spring:
  cloud:
    nacos:
      discovery:
        namespace: dev  # Development
        # namespace: test  # Testing
        # namespace: prod  # Production
```

### Case 2: Configuration Group Management

Distinguish application groups using `group`:

```yaml
spring:
  cloud:
    nacos:
      config:
        group: eden  # Application group
```

## Pitfalls

1. **Namespace**: Ensure namespace is created in Nacos console
2. **Network Connectivity**: Ensure application can access Nacos server
3. **Configuration Format**: `file-extension` must match the format in Nacos

## Best Practices

1. Use namespace to isolate environments, group to isolate application groups
2. Use Nacos encryption for sensitive configurations (passwords, etc.)
3. Verify configuration changes in test environment first

