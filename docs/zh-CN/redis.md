# Redis 缓存

## 是什么（What）

Redis 是高性能的键值存储数据库，本项目集成 Redis 实现数据缓存、分布式锁等功能。

## 为什么用（Why）

- 提升系统性能：减少数据库访问压力
- 分布式缓存：多实例共享缓存数据
- 丰富的数据结构：支持 String、Hash、List、Set、ZSet 等

## 怎么用（How）

### 1. 安装 Redis

```bash
# Docker 方式
docker run -d --name redis -p 6379:6379 redis:latest

# 或使用 Redis 官方安装包
```

### 2. 开启配置

修改 `application-dev.yml`：

```yaml
spring:
  redis:
    enabled: true
    host: localhost
    port: 6379
    password: demo@123
    database: 1
    timeout: 5000
    lettuce:
      pool:
        min-idle: 1
        max-idle: 64
        max-active: 64
        max-wait: -1
```

### 3. 使用示例

```java
@Autowired
private StringRedisTemplate redisTemplate;

// 设置缓存
redisTemplate.opsForValue().set("key", "value", 30, TimeUnit.MINUTES);

// 获取缓存
String value = redisTemplate.opsForValue().get("key");
```

## 实战案例（Cases）

### 案例一：缓存用户信息

```java
@Cacheable(value = "user", key = "#id")
public User getUserById(Long id) {
    return userRepository.findById(id);
}
```

### 案例二：分布式锁

```java
Boolean locked = redisTemplate.opsForValue()
    .setIfAbsent("lock:order:" + orderId, "1", 30, TimeUnit.SECONDS);
if (Boolean.TRUE.equals(locked)) {
    try {
        // 业务逻辑
    } finally {
        redisTemplate.delete("lock:order:" + orderId);
    }
}
```

## 避坑指南（Pitfalls）

1. **连接池配置**：根据并发量合理配置连接池大小
2. **超时设置**：设置合理的超时时间，避免阻塞
3. **密码安全**：生产环境务必设置密码

## 最佳实践（Best Practices）

1. 使用 Lettuce 连接池（默认），性能优于 Jedis
2. 设置合理的缓存过期时间，避免内存溢出
3. 使用 Pipeline 批量操作提升性能
