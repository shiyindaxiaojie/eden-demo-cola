# XXL-Job 分布式任务调度

## 是什么（What）

XXL-Job 是大众点评开源的分布式任务调度平台，本项目集成 XXL-Job 实现定时任务的统一管理和调度。

## 为什么用（Why）

- 分布式调度：支持集群部署，任务自动分片
- 可视化管理：Web 控制台管理任务，支持 CRUD
- 丰富的调度策略：支持 Cron、固定频率、API 触发等
- 执行日志：任务执行日志实时查看

## 怎么用（How）

### 1. 部署 XXL-Job Admin

```bash
# Docker 方式
docker run -d --name xxl-job-admin \
  -p 8090:8080 \
  -e PARAMS="--spring.datasource.url=jdbc:mysql://host:3306/xxl_job?useUnicode=true&characterEncoding=UTF-8 --spring.datasource.username=root --spring.datasource.password=password" \
  xuxueli/xxl-job-admin:2.3.1
```

### 2. 开启配置

修改 `application-dev.yml`：

```yaml
xxl:
  job:
    enable: true # 开关
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

### 3. 编写 JobHandler

```java
@Component
public class DemoJobHandler {

    @XxlJob("demoJobHandler")
    public void demoJob() {
        XxlJobHelper.log("开始执行任务");
        
        // 获取任务参数
        String param = XxlJobHelper.getJobParam();
        
        // 业务逻辑
        
        XxlJobHelper.log("任务执行完成");
    }
}
```

### 4. 在 Admin 控制台配置任务

1. 登录 XXL-Job Admin（默认账号：admin/123456）
2. 执行器管理 -> 新增执行器（AppName 与配置一致）
3. 任务管理 -> 新增任务
   - JobHandler：`demoJobHandler`
   - Cron：`0 0/5 * * * ?`（每 5 分钟执行）

## 实战案例（Cases）

### 案例一：分片任务

```java
@XxlJob("shardingJobHandler")
public void shardingJob() {
    // 获取分片参数
    int shardIndex = XxlJobHelper.getShardIndex();
    int shardTotal = XxlJobHelper.getShardTotal();
    
    XxlJobHelper.log("分片参数：当前分片={}, 总分片数={}", shardIndex, shardTotal);
    
    // 根据分片处理数据
    List<Long> dataList = getDataBySharding(shardIndex, shardTotal);
    for (Long id : dataList) {
        // 处理数据
    }
}
```

### 案例二：带参数的任务

```java
@XxlJob("paramJobHandler")
public void paramJob() {
    String param = XxlJobHelper.getJobParam();
    
    // 解析 JSON 参数
    JSONObject json = JSON.parseObject(param);
    String type = json.getString("type");
    Integer count = json.getInteger("count");
    
    // 根据参数执行不同逻辑
}
```

在 Admin 控制台配置任务参数：
```json
{"type": "order", "count": 100}
```

### 案例三：任务执行结果

```java
@XxlJob("resultJobHandler")
public void resultJob() {
    try {
        // 业务逻辑
        XxlJobHelper.handleSuccess("执行成功，处理了 100 条数据");
    } catch (Exception e) {
        XxlJobHelper.handleFail("执行失败：" + e.getMessage());
    }
}
```

## 避坑指南（Pitfalls）

1. **执行器注册**：确保执行器 AppName 与 Admin 配置一致
2. **网络连通**：执行器需要能访问 Admin，Admin 也需要能回调执行器
3. **日志目录**：确保 `log-path` 目录存在且有写权限
4. **任务超时**：长时间任务设置合理的超时时间

## 最佳实践（Best Practices）

1. 使用分片任务处理大数据量，提升执行效率
2. 任务执行记录日志，便于问题排查
3. 设置任务告警，执行失败时及时通知
4. 合理设置任务超时和重试策略

