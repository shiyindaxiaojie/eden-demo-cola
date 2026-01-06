# Dynamic-Datasource Multi-datasource

## What

Dynamic-Datasource is a multi-datasource component based on Spring Boot. This project integrates it for dynamic multi-datasource switching.

## Why

- Multi-datasource Support: One application connects to multiple databases
- Dynamic Switching: Flexibly switch datasources using `@DS` annotation
- Read-Write Splitting: Simple master-slave configuration for read-write splitting
- Non-invasive: Annotation-based, no intrusion to business code

## How

### 1. Enable Configuration

Modify `application-dev.yml`:

```yaml
spring:
  datasource:
    dynamic:
      enabled: true # Switch
      strict: true  # Strict mode, throw exception if datasource not found
      primary: ds1  # Default datasource
```

### 2. Multi-datasource Configuration

```yaml
spring:
  datasource:
    dynamic:
      datasource:
        ds1:
          username: root
          password: demo@123
          url: jdbc:mysql://localhost:3306/ds1?rewriteBatchedStatements=true&useSSL=false&useOldAliasMetadataBehavior=true&useUnicode=true&serverTimezone=GMT%2B8
          driver-class-name: com.mysql.cj.jdbc.Driver
          type: com.zaxxer.hikari.HikariDataSource
        ds2:
          username: root
          password: demo@123
          url: jdbc:mysql://localhost:3306/ds2?rewriteBatchedStatements=true&useSSL=false&useOldAliasMetadataBehavior=true&useUnicode=true&serverTimezone=GMT%2B8
          driver-class-name: com.mysql.cj.jdbc.Driver
          type: com.zaxxer.hikari.HikariDataSource
```

### 3. Switch Datasource with @DS Annotation

```java
@Service
public class UserService {

    @DS("ds1")
    public User getUserFromDs1(Long id) {
        return userMapper.selectById(id);
    }

    @DS("ds2")
    public User getUserFromDs2(Long id) {
        return userMapper.selectById(id);
    }
}
```

## Cases

### Case 1: Class-level Switching

```java
@Service
@DS("ds2")  // All methods in this class use ds2 by default
public class OrderService {

    public Order getOrder(Long id) {
        return orderMapper.selectById(id);
    }

    @DS("ds1")  // Method-level overrides class-level
    public Order getOrderFromDs1(Long id) {
        return orderMapper.selectById(id);
    }
}
```

### Case 2: Read-Write Splitting

```yaml
spring:
  datasource:
    dynamic:
      primary: master
      datasource:
        master:
          url: jdbc:mysql://master:3306/demo
          # ... other config
        slave:
          url: jdbc:mysql://slave:3306/demo
          # ... other config
```

```java
@Service
public class UserService {

    @DS("master")
    public void createUser(User user) {
        userMapper.insert(user);
    }

    @DS("slave")
    public User getUser(Long id) {
        return userMapper.selectById(id);
    }
}
```

## Pitfalls

1. **Transaction Issue**: `@DS` annotation must take effect before `@Transactional`
2. **AOP Failure**: Same-class method calls won't trigger AOP, datasource switching fails
3. **Strict Mode**: Enable `strict: true` to fail fast when datasource doesn't exist
4. **Connection Pool Isolation**: Each datasource has independent connection pool

## Best Practices

1. Use meaningful datasource names like `master`, `slave`, `order_db`
2. Enable strict mode to avoid using wrong datasource names
3. In read-write splitting, use master for writes, slave for reads
4. Configure connection pool parameters for each datasource appropriately

