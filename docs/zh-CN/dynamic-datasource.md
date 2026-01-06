# Dynamic-Datasource 多数据源

## 是什么（What）

Dynamic-Datasource 是基于 Spring Boot 的多数据源组件，本项目集成该组件实现多数据源动态切换。

## 为什么用（Why）

- 多数据源支持：一个应用连接多个数据库
- 动态切换：通过 `@DS` 注解灵活切换数据源
- 读写分离：简单的主从配置实现读写分离
- 无侵入：基于注解，对业务代码无侵入

## 怎么用（How）

### 1. 开启配置

修改 `application-dev.yml`：

```yaml
spring:
  datasource:
    dynamic:
      enabled: true # 开关
      strict: true  # 严格模式，未匹配到数据源时抛异常
      primary: ds1  # 默认数据源
```

### 2. 多数据源配置

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

### 3. 使用 @DS 注解切换数据源

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

## 实战案例（Cases）

### 案例一：类级别切换

```java
@Service
@DS("ds2")  // 该类所有方法默认使用 ds2
public class OrderService {

    public Order getOrder(Long id) {
        return orderMapper.selectById(id);
    }

    @DS("ds1")  // 方法级别覆盖类级别
    public Order getOrderFromDs1(Long id) {
        return orderMapper.selectById(id);
    }
}
```

### 案例二：读写分离

```yaml
spring:
  datasource:
    dynamic:
      primary: master
      datasource:
        master:
          url: jdbc:mysql://master:3306/demo
          # ... 其他配置
        slave:
          url: jdbc:mysql://slave:3306/demo
          # ... 其他配置
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

## 避坑指南（Pitfalls）

1. **事务问题**：`@DS` 注解需要在 `@Transactional` 之前生效，注意注解顺序
2. **AOP 失效**：同类方法调用不会触发 AOP，数据源切换失效
3. **严格模式**：开启 `strict: true` 可在数据源不存在时快速失败
4. **连接池隔离**：每个数据源独立连接池，注意总连接数

## 最佳实践（Best Practices）

1. 使用有意义的数据源名称，如 `master`、`slave`、`order_db`
2. 开启严格模式，避免使用错误的数据源名称
3. 读写分离场景，写操作使用主库，读操作使用从库
4. 合理配置每个数据源的连接池参数

