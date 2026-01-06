# 数据源配置

## 是什么（What）

数据源（DataSource）是 Java 应用连接数据库的标准接口，本项目集成 HikariCP 连接池，支持 H2 内存数据库和 MySQL 数据库。

## 为什么用（Why）

- 连接池管理：复用数据库连接，减少连接创建开销
- 高性能：HikariCP 是目前性能最好的 JDBC 连接池
- 灵活配置：支持多种数据库，开发环境使用 H2，生产环境使用 MySQL

## 怎么用（How）

### 1. H2 内存数据库（开发环境）

默认配置使用 H2 内存数据库，无需额外安装：

```yaml
spring:
  h2:
    console:
      enabled: true # 开启 H2 控制台
      path: /h2-console
      settings:
        trace: false
        web-allow-others: false
  datasource:
    username: sa
    password: demo
    url: jdbc:h2:mem:db;DB_CLOSE_ON_EXIT=TRUE
    driver-class-name: org.h2.Driver
    type: com.zaxxer.hikari.HikariDataSource
```

### 2. MySQL 数据库（生产环境）

修改 `application-dev.yml`：

```yaml
spring:
  datasource:
    username: root
    password: demo@123
    url: jdbc:mysql://localhost:3306/demo?rewriteBatchedStatements=true&useSSL=false&useOldAliasMetadataBehavior=true&useUnicode=true&serverTimezone=GMT%2B8
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.zaxxer.hikari.HikariDataSource
```

### 3. HikariCP 连接池配置

```yaml
spring:
  datasource:
    hikari:
      connectionTimeout: 5000      # 连接超时时间（毫秒）
      minimumIdle: 2               # 最小空闲连接数
      maximumPoolSize: 10          # 最大连接数
      idleTimeout: 300000          # 空闲连接超时时间（毫秒）
      maxLifetime: 1200000         # 连接最大生命周期（毫秒）
      # connection-init-sql: SET NAMES utf8mb4  # 云数据库乱码解决方案
```

## 实战案例（Cases）

### 案例一：开发环境快速启动

使用 H2 内存数据库，配合 Liquibase 自动初始化表结构：

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:db;DB_CLOSE_ON_EXIT=TRUE
    driver-class-name: org.h2.Driver
  liquibase:
    enabled: true
```

启动后访问 `http://localhost:8081/h2-console` 可查看数据库。

### 案例二：生产环境 MySQL 配置

```yaml
spring:
  datasource:
    username: ${DB_USERNAME:root}
    password: ${DB_PASSWORD:}
    url: jdbc:mysql://${DB_HOST:localhost}:${DB_PORT:3306}/${DB_NAME:demo}?rewriteBatchedStatements=true&useSSL=false&serverTimezone=GMT%2B8
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      minimumIdle: 5
      maximumPoolSize: 20
```

## 避坑指南（Pitfalls）

1. **连接泄漏**：确保使用 try-with-resources 或框架管理连接，避免连接泄漏
2. **连接池大小**：`maximumPoolSize` 不宜过大，一般为 CPU 核心数 * 2 + 磁盘数
3. **超时配置**：`maxLifetime` 应小于数据库的 `wait_timeout`，避免使用已关闭的连接
4. **字符编码**：云数据库可能出现乱码，使用 `connection-init-sql: SET NAMES utf8mb4`

## 最佳实践（Best Practices）

1. 开发环境使用 H2 内存数据库，快速启动无需依赖
2. 使用环境变量管理敏感配置（用户名、密码）
3. 根据业务负载合理配置连接池参数
4. 开启连接池监控，及时发现连接泄漏问题

