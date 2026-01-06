# Redis Cache

## What

Redis is a high-performance key-value store database. This project integrates Redis for data caching, distributed locks, and more.

## Why

- Performance Improvement: Reduce database access pressure
- Distributed Cache: Share cache data across multiple instances
- Rich Data Structures: Supports String, Hash, List, Set, ZSet, etc.

## How

### 1. Install Redis

```bash
# Docker method
docker run -d --name redis -p 6379:6379 redis:latest

# Or use official Redis installation package
```

### 2. Enable Configuration

Modify `application-dev.yml`:

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

### 3. Usage Example

```java
@Autowired
private StringRedisTemplate redisTemplate;

// Set cache
redisTemplate.opsForValue().set("key", "value", 30, TimeUnit.MINUTES);

// Get cache
String value = redisTemplate.opsForValue().get("key");
```

## Cases

### Case 1: Cache User Information

```java
@Cacheable(value = "user", key = "#id")
public User getUserById(Long id) {
    return userRepository.findById(id);
}
```

### Case 2: Distributed Lock

```java
Boolean locked = redisTemplate.opsForValue()
    .setIfAbsent("lock:order:" + orderId, "1", 30, TimeUnit.SECONDS);
if (Boolean.TRUE.equals(locked)) {
    try {
        // Business logic
    } finally {
        redisTemplate.delete("lock:order:" + orderId);
    }
}
```

## Pitfalls

1. **Connection Pool Configuration**: Configure pool size based on concurrency
2. **Timeout Settings**: Set reasonable timeout to avoid blocking
3. **Password Security**: Always set password in production

## Best Practices

1. Use Lettuce connection pool (default), better performance than Jedis
2. Set reasonable cache expiration time to avoid memory overflow
3. Use Pipeline for batch operations to improve performance

