# Arthas Online Diagnostics

## What

Arthas is an open-source Java diagnostic tool from Alibaba. This project integrates [Arthas](https://github.com/shiyindaxiaojie/arthas) for runtime diagnostics, performance analysis, and troubleshooting.

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/arthas/arthas-dashboard-overview.png)

## Why

- Online Diagnostics: Diagnose issues without restarting the application
- Dynamic Tracing: Real-time view of method calls, parameters, and return values
- Performance Analysis: Analyze method execution time and flame graphs
- Hot Fix: Dynamically modify code without redeployment

## How

### 1. Deploy Arthas Tunnel Server

```bash
# Download and start Tunnel Server
java -jar arthas-tunnel-server.jar --server.port=7777
```

### 2. Enable Configuration

Modify `application-dev.yml`:

```yaml
spring:
  arthas:
    enabled: true # Switch

arthas:
  agent-id: ${spring.application.name}@${random.value}
  tunnel-server: ws://localhost:7777/ws
  session-timeout: 1800
  telnet-port: 0 # Random port
  http-port: 0   # Random port
```

### 3. Connect to Arthas

Connect via Tunnel Server Web Console:

```
http://localhost:7777/
```

Or attach directly to the process:

```bash
java -jar arthas-boot.jar
```

## Cases

### Case 1: Monitor Method Calls

```bash
# Watch method calls
watch com.example.UserService getUserById '{params, returnObj}' -x 3

# Trace method call chain
trace com.example.UserService getUserById

# Monitor method execution time
monitor -c 5 com.example.UserService getUserById
```

### Case 2: View JVM Information

```bash
# View JVM overview
dashboard

# View thread information
thread

# View memory information
memory

# View GC information
jvm
```

### Case 3: Decompile and Hot Fix

```bash
# Decompile class
jad com.example.UserService

# Search class
sc -d com.example.UserService

# Hot fix (use with caution)
redefine /tmp/UserService.class
```

### Case 4: Flame Graph Analysis

```bash
# Generate CPU flame graph
profiler start
# Wait for a while
profiler stop --format html
```

## Pitfalls

1. **Port Conflict**: Use random ports (set to 0) to avoid multi-instance conflicts
2. **Security Risk**: Restrict Arthas access in production environments
3. **Performance Impact**: Commands like trace and watch have performance overhead, close them after use
4. **Hot Fix Risk**: Use redefine command with caution, may cause unexpected issues

## Best Practices

1. Use Tunnel Server to manage multiple application instances uniformly
2. Configure access authentication in production, restrict operation permissions
3. Exit promptly after diagnostics to avoid continuous performance overhead
4. Combine with logs and monitoring for comprehensive problem analysis
