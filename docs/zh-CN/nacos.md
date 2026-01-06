# Nacos 注册中心与配置中心

## 是什么（What）

Nacos 是阿里巴巴开源的服务发现、配置管理和服务管理平台，本项目集成 Nacos 实现服务注册发现和动态配置管理。

## 为什么用（Why）

- 服务注册与发现：微服务架构下的服务治理
- 动态配置管理：配置热更新，无需重启应用
- 支持 Log4j2 配置动态刷新

## 怎么用（How）

### 1. 安装 Nacos

参考 [Nacos Quick Start](https://nacos.io/zh-cn/docs/quick-start.html) 快速搭建。

### 2. 开启配置

修改 `bootstrap-dev.yml`：

```yaml
spring:
  cloud:
    nacos:
      discovery: # 注册中心
        enabled: true
        server-addr: localhost:8848
        namespace: demo
        group: eden
        username: nacos
        password: nacos
      config: # 配置中心
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

### 3. Log4j2 动态刷新（可选）

```yaml
log4j2:
  nacos:
    enabled: true
    group: eden
    data-id: log4j2.yml
```

## 实战案例（Cases）

### 案例一：多环境配置隔离

通过 `namespace` 隔离不同环境：

```yaml
spring:
  cloud:
    nacos:
      discovery:
        namespace: dev  # 开发环境
        # namespace: test  # 测试环境
        # namespace: prod  # 生产环境
```

### 案例二：配置分组管理

通过 `group` 区分不同应用组：

```yaml
spring:
  cloud:
    nacos:
      config:
        group: eden  # 应用组
```

## 避坑指南（Pitfalls）

1. **命名空间**：确保 namespace 在 Nacos 控制台已创建
2. **网络连通**：确保应用能访问 Nacos 服务地址
3. **配置格式**：`file-extension` 需与 Nacos 中的配置格式一致

## 最佳实践（Best Practices）

1. 使用 namespace 隔离环境，group 隔离应用组
2. 敏感配置（密码等）使用 Nacos 加密功能
3. 配置变更前先在测试环境验证
