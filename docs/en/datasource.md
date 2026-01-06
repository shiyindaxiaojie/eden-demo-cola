# Data Source Configuration

## What

DataSource is the standard Java interface for database connections. This project integrates HikariCP connection pool, supporting H2 in-memory database and MySQL database.

## Why

- Connection Pool Management: Reuse database connections, reduce connection creation overhead
- High Performance: HikariCP is currently the best performing JDBC connection pool
- Flexible Configuration: Supports multiple databases, H2 for development, MySQL for production

## How

### 1. H2 In-memory Database (Development)

Default configuration uses H2 in-memory database, no additional installation required:

```yaml
spring:
  h2:
    console:
      enabled: true # Enable H2 console
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

### 2. MySQL Database (Production)

Modify `application-dev.yml`:

```yaml
spring:
  datasource:
    username: root
    password: demo@123
    url: jdbc:mysql://localhost:3306/demo?rewriteBatchedStatements=true&useSSL=false&useOldAliasMetadataBehavior=true&useUnicode=true&serverTimezone=GMT%2B8
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.zaxxer.hikari.HikariDataSource
```

### 3. HikariCP Connection Pool Configuration

```yaml
spring:
  datasource:
    hikari:
      connectionTimeout: 5000      # Connection timeout (ms)
      minimumIdle: 2               # Minimum idle connections
      maximumPoolSize: 10          # Maximum connections
      idleTimeout: 300000          # Idle connection timeout (ms)
      maxLifetime: 1200000         # Maximum connection lifetime (ms)
      # connection-init-sql: SET NAMES utf8mb4  # Cloud database encoding fix
```

## Cases

### Case 1: Quick Start in Development

Use H2 in-memory database with Liquibase auto-initialization:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:db;DB_CLOSE_ON_EXIT=TRUE
    driver-class-name: org.h2.Driver
  liquibase:
    enabled: true
```

Access `http://localhost:8081/h2-console` to view the database after startup.

### Case 2: Production MySQL Configuration

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

## Pitfalls

1. **Connection Leak**: Use try-with-resources or framework-managed connections
2. **Pool Size**: `maximumPoolSize` should not be too large, typically CPU cores * 2 + disk count
3. **Timeout Configuration**: `maxLifetime` should be less than database `wait_timeout`
4. **Character Encoding**: Use `connection-init-sql: SET NAMES utf8mb4` for cloud databases

## Best Practices

1. Use H2 in-memory database for quick startup in development
2. Use environment variables for sensitive configurations (username, password)
3. Configure connection pool parameters based on business load
4. Enable connection pool monitoring to detect connection leaks

