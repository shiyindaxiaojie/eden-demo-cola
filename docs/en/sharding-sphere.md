# ShardingSphere Sharding

## What

Apache ShardingSphere is a distributed database middleware. This project integrates ShardingSphere-JDBC for database sharding, read-write splitting, and more.

## Why

- Database Sharding: Solve performance bottlenecks of single database with large data
- Read-Write Splitting: Write to master, read from slaves, improve query performance
- Distributed Transactions: Support XA, BASE distributed transaction modes
- Transparent Integration: No code intrusion, compatible with JDBC specification

## How

### 1. Enable Configuration

Modify `application-dev.yml`:

```yaml
spring:
  shardingsphere:
    enabled: true # Switch
```

### 2. Multi-datasource Configuration

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

### 3. Sharding Rules Configuration

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

## Cases

### Case 1: Read-Write Splitting Configuration

```yaml
spring:
  shardingsphere:
    datasource:
      names: master,slave1,slave2
      master:
        url: jdbc:mysql://master:3306/demo
        # ... other config
      slave1:
        url: jdbc:mysql://slave1:3306/demo
        # ... other config
      slave2:
        url: jdbc:mysql://slave2:3306/demo
        # ... other config
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

### Case 2: Sharding + Read-Write Splitting

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

## Pitfalls

1. **Liquibase Compatibility**: ShardingSphere 5.x has compatibility issues with Liquibase
2. **Sharding Key Selection**: Choose frequently queried and evenly distributed fields
3. **Cross-database Queries**: Avoid cross-database JOIN queries, poor performance
4. **Transaction Boundaries**: Distributed transactions have performance overhead

## Best Practices

1. Choose high-frequency query fields as sharding keys to reduce cross-shard queries
2. Estimate data growth and plan shard count reasonably
3. Use snowflake algorithm for distributed ID generation
4. Be aware of master-slave delay in read-write splitting scenarios

