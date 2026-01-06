# CAT Application Monitoring

## What

CAT (Central Application Tracking) is Dianping's open-source real-time application monitoring platform. This project integrates [CAT](https://github.com/shiyindaxiaojie/cat) for transaction tracing, performance analysis, and problem diagnosis.

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/cat/tracing.png)

## Why

- Real-time Monitoring: Second-level monitoring data display
- Full-link Tracing: Connect complete call chain through TraceId
- Performance Analysis: HTTP, RPC, SQL, Cache execution time analysis
- Problem Diagnosis: Quickly locate performance bottlenecks and exceptions

## How

### 1. Deploy CAT Server

Refer to [CAT Project Documentation](https://github.com/shiyindaxiaojie/cat) to deploy CAT server.

### 2. Enable Configuration

Modify `application-dev.yml`:

```yaml
cat:
  enabled: true              # Switch
  trace-mode: true           # Enable access observation
  support-out-trace-id: true # Support external HTTP trace propagation
  home: /tmp                 # CAT client config directory
  servers: localhost         # CAT server address
  tcp-port: 2280             # TCP port
  http-port: 8080            # HTTP port
```

### 3. Create Client Configuration

Create `client.xml` in `/tmp` directory:

```xml
<?xml version="1.0" encoding="utf-8"?>
<config mode="client">
    <servers>
        <server ip="localhost" port="2280" http-port="8080"/>
    </servers>
</config>
```

### 4. Usage Example

```java
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;

public void doSomething() {
    Transaction t = Cat.newTransaction("Service", "doSomething");
    try {
        // Business logic
        t.setStatus(Transaction.SUCCESS);
    } catch (Exception e) {
        t.setStatus(e);
        Cat.logError(e);
        throw e;
    } finally {
        t.complete();
    }
}
```

## Cases

### Case 1: HTTP Request Tracing

Framework auto-instruments, no manual coding required:

```yaml
cat:
  trace-mode: true # Auto-trace HTTP requests when enabled
```

Access CAT console to view Transaction reports.

### Case 2: Custom Business Instrumentation

```java
// Log event
Cat.logEvent("Order", "Create", Event.SUCCESS, "orderId=" + orderId);

// Log metrics
Cat.logMetricForCount("order.create.count");
Cat.logMetricForDuration("order.create.time", duration);

// Log error
Cat.logError("Order creation failed", exception);
```

### Case 3: Trace Propagation

```java
// Get current TraceId
String traceId = Cat.getCurrentMessageId();

// Set TraceId for remote call
Cat.logRemoteCallClient(context);

// Server receives TraceId
Cat.logRemoteCallServer(context);
```

## Pitfalls

1. **Config Directory**: Ensure `cat.home` directory exists and is writable
2. **Server Connection**: Ensure application can access CAT server TCP and HTTP ports
3. **Transaction Completion**: Transaction must call `complete()`, otherwise data is incomplete
4. **Performance Impact**: Watch CAT client performance overhead in high-concurrency scenarios

## Best Practices

1. Use `trace-mode` for auto-instrumentation to reduce manual coding
2. Add custom instrumentation for critical business for easier problem location
3. Connect complete call chain through TraceId
4. Regularly check Problem reports to discover exceptions early

