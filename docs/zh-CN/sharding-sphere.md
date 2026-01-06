# ShardingSphere 分库分表

## 是什么（What）

Apache ShardingSphere 是一款分布式数据库中间件，本项目集成 ShardingSphere-JDBC 实现分库分表、读写分离等功能。

## 为什么用（Why）

- 分库分表：解决单库数据量过大的性能瓶颈
- 读写分离：主库写、从库读，提升查询性能
- 分布式事务：支持 XA、BASE 等分布式事务模式
- 透明化接入：对应用代码无侵入，兼容 JDBC 规范

## 怎么用（How）

### 1. 开启配置

修改 `application-dev.yml`：

```yaml
spring:
  shardingsphere:
    enabled: true # 开关
```

### 2. 多数据源配置

```yaml
spring:
  shardingsphere:
    datasource:
      names: ds1,ds2
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

### 3. 分片规则配置

```yaml
spring:
  shardingsphere:
    rules:
      sharding:
        tables:
          t_order:
            actual-data-nodes: ds$->{0..1}.t_order_$->{0..1}
            table-strategy:
              standard:
                sharding-column: order_id
                sharding-algorithm-name: t-order-inline
            database-strategy:
              standard:
                sharding-column: user_id
                sharding-algorithm-name: database-inline
        sharding-algorithms:
          database-inline:
            type: INLINE
            props:
              algorithm-expression: ds$->{user_id % 2}
          t-order-inline:
            type: INLINE
            props:
              algorithm-expression: t_order_$->{order_id % 2}
```

## 实战案例（Cases）

### 案例一：读写分离配置

```yaml
spring:
  shardingsphere:
    datasource:
      names: master,slave1,slave2
      master:
        url: jdbc:mysql://master:3306/demo
        # ... 其他配置
      slave1:
        url: jdbc:mysql://slave1:3306/demo
        # ... 其他配置
      slave2:
        url: jdbc:mysql://slave2:3306/demo
        # ... 其他配置
    rules:
      readwrite-splitting:
        data-sources:
          readwrite_ds:
            static-strategy:
              write-data-source-name: master
              read-data-source-names:
                - slave1
                - slave2
            load-balancer-name: round_robin
        load-balancers:
          round_robin:
            type: ROUND_ROBIN
```

### 案例二：分库分表 + 读写分离

```yaml
spring:
  shardingsphere:
    rules:
      sharding:
        tables:
          t_order:
            actual-data-nodes: readwrite_ds.t_order_$->{0..1}
      readwrite-splitting:
        data-sources:
          readwrite_ds:
            static-strategy:
              write-data-source-name: master
              read-data-source-names: slave1,slave2
```

## 避坑指南（Pitfalls）

1. **Liquibase 兼容性**：ShardingSphere 5.x 与 Liquibase 存在兼容性问题，建议分开使用
2. **分片键选择**：分片键应选择查询频繁且分布均匀的字段
3. **跨库查询**：避免跨库 JOIN 查询，性能较差
4. **事务边界**：分布式事务有性能开销，合理设计事务边界

## 最佳实践（Best Practices）

1. 分片键选择高频查询字段，减少跨分片查询
2. 预估数据增长，合理规划分片数量
3. 使用雪花算法生成分布式 ID
4. 读写分离场景注意主从延迟问题

