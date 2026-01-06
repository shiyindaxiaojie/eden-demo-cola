# XXL-Job Distributed Task Scheduling

## What

XXL-Job is a distributed task scheduling platform open-sourced by Dianping. This project integrates XXL-Job for unified management and scheduling of scheduled tasks.

## Why

- Distributed Scheduling: Supports cluster deployment with automatic task sharding
- Visual Management: Web console for task management with CRUD support
- Rich Scheduling Strategies: Supports Cron, fixed frequency, API trigger, etc.
- Execution Logs: Real-time viewing of task execution logs

## How

### 1. Deploy XXL-Job Admin

```bash
# Docker method
docker run -d --name xxl-job-admin \
  -p 8090:8080 \
  -e PARAMS="--spring.datasource.url=jdbc:mysql://host:3306/xxl_job?useUnicode=true&characterEncoding=UTF-8 --spring.datasource.username=root --spring.datasource.password=password" \
  xuxueli/xxl-job-admin:2.3.1
```

### 2. Enable Configuration

Modify `application-dev.yml`:

```yaml
xxl:
  job:
    enable: true # Switch
    access-token:
    admin:
      addresses: http://localhost:8090/xxl-job
    executor:
      app-name: ${spring.application.name}
      ip:
      port:
      log-path: /logs/xxl-job/job-handler/
      log-retention-days: 7
```

### 3. Write JobHandler

```java
@Component
public class DemoJobHandler {

    @XxlJob("demoJobHandler")
    public void demoJob() {
        XxlJobHelper.log("Start executing task");
        
        // Get task parameters
        String param = XxlJobHelper.getJobParam();
        
        // Business logic
        
        XxlJobHelper.log("Task execution completed");
    }
}
```

### 4. Configure Task in Admin Console

1. Login to XXL-Job Admin (default account: admin/123456)
2. Executor Management -> Add Executor (AppName matches configuration)
3. Task Management -> Add Task
   - JobHandler: `demoJobHandler`
   - Cron: `0 0/5 * * * ?` (execute every 5 minutes)

## Cases

### Case 1: Sharding Task

```java
@XxlJob("shardingJobHandler")
public void shardingJob() {
    // Get sharding parameters
    int shardIndex = XxlJobHelper.getShardIndex();
    int shardTotal = XxlJobHelper.getShardTotal();
    
    XxlJobHelper.log("Sharding params: current shard={}, total shards={}", shardIndex, shardTotal);
    
    // Process data by sharding
    List<Long> dataList = getDataBySharding(shardIndex, shardTotal);
    for (Long id : dataList) {
        // Process data
    }
}
```

### Case 2: Task with Parameters

```java
@XxlJob("paramJobHandler")
public void paramJob() {
    String param = XxlJobHelper.getJobParam();
    
    // Parse JSON parameters
    JSONObject json = JSON.parseObject(param);
    String type = json.getString("type");
    Integer count = json.getInteger("count");
    
    // Execute different logic based on parameters
}
```

Configure task parameters in Admin console:
```json
{"type": "order", "count": 100}
```

### Case 3: Task Execution Result

```java
@XxlJob("resultJobHandler")
public void resultJob() {
    try {
        // Business logic
        XxlJobHelper.handleSuccess("Execution successful, processed 100 records");
    } catch (Exception e) {
        XxlJobHelper.handleFail("Execution failed: " + e.getMessage());
    }
}
```

## Pitfalls

1. **Executor Registration**: Ensure executor AppName matches Admin configuration
2. **Network Connectivity**: Executor needs to access Admin, and Admin needs to callback executor
3. **Log Directory**: Ensure `log-path` directory exists and has write permission
4. **Task Timeout**: Set reasonable timeout for long-running tasks

## Best Practices

1. Use sharding tasks to process large data volumes for improved efficiency
2. Log task execution for easier troubleshooting
3. Set up task alerts for failure notifications
4. Set reasonable timeout and retry strategies
