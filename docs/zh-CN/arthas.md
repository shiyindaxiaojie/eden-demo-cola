# Arthas 在线诊断

## 是什么（What）

Arthas 是阿里巴巴开源的 Java 诊断工具，本项目集成 [Arthas](https://github.com/shiyindaxiaojie/arthas) 实现运行时诊断、性能分析和问题排查。

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/arthas/arthas-dashboard-overview.png)

## 为什么用（Why）

- 在线诊断：无需重启应用即可诊断问题
- 动态追踪：实时查看方法调用、参数、返回值
- 性能分析：分析方法耗时、火焰图
- 热修复：动态修改代码，无需重新部署

## 怎么用（How）

### 1. 部署 Arthas Tunnel Server

```bash
# 下载并启动 Tunnel Server
java -jar arthas-tunnel-server.jar --server.port=7777
```

### 2. 开启配置

修改 `application-dev.yml`：

```yaml
spring:
  arthas:
    enabled: true # 开关

arthas:
  agent-id: ${spring.application.name}@${random.value}
  tunnel-server: ws://localhost:7777/ws
  session-timeout: 1800
  telnet-port: 0 # 随机端口
  http-port: 0   # 随机端口
```

### 3. 连接 Arthas

通过 Tunnel Server Web 控制台连接：

```
http://localhost:7777/
```

或直接 attach 到进程：

```bash
java -jar arthas-boot.jar
```

## 实战案例（Cases）

### 案例一：查看方法调用

```bash
# 监控方法调用
watch com.example.UserService getUserById '{params, returnObj}' -x 3

# 追踪方法调用链
trace com.example.UserService getUserById

# 查看方法耗时
monitor -c 5 com.example.UserService getUserById
```

### 案例二：查看 JVM 信息

```bash
# 查看 JVM 概览
dashboard

# 查看线程信息
thread

# 查看内存信息
memory

# 查看 GC 信息
jvm
```

### 案例三：反编译和热修复

```bash
# 反编译类
jad com.example.UserService

# 搜索类
sc -d com.example.UserService

# 热修复（谨慎使用）
redefine /tmp/UserService.class
```

### 案例四：火焰图分析

```bash
# 生成 CPU 火焰图
profiler start
# 等待一段时间
profiler stop --format html
```

## 避坑指南（Pitfalls）

1. **端口冲突**：使用随机端口（设置为 0）避免多实例冲突
2. **安全风险**：生产环境限制 Arthas 访问权限
3. **性能影响**：trace、watch 等命令有性能开销，使用后及时关闭
4. **热修复风险**：redefine 命令谨慎使用，可能导致不可预期问题

## 最佳实践（Best Practices）

1. 使用 Tunnel Server 统一管理多个应用实例
2. 生产环境配置访问认证，限制操作权限
3. 诊断完成后及时退出，避免持续性能开销
4. 结合日志和监控，综合分析问题

