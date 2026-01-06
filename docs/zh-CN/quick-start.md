# 快速开始

## 是什么（What）

COLA（Clean Object-oriented Layered Architecture）是阿里巴巴开源的应用架构，本项目是基于 COLA 架构的最佳实践示例，集成了企业级开发常用的组件和配置。

## 为什么用（Why）

- 整洁的分层架构，降低系统复杂度
- 融合 DDD、CQRS、SOLID 等设计思想
- 开箱即用的组件集成方案
- 完善的配置示例和文档

## 怎么用（How）

### 环境要求

- JDK 1.8+（2.4.x 分支）/ JDK 11+（2.7.x 分支）/ JDK 17+（3.0.x 分支）
- Maven 3.6+
- Git

### 克隆项目

```bash
git clone https://github.com/shiyindaxiaojie/eden-demo-cola.git
cd eden-demo-cola
```

### 构建项目

```bash
# 安装依赖项目
git clone https://github.com/shiyindaxiaojie/eden-architect.git
cd eden-architect && mvn install -T 4C -DskipTests

# 构建本项目
cd eden-demo-cola && mvn install -T 4C -DskipTests
```

### 启动应用

```bash
cd eden-demo-cola-start
mvn spring-boot:run
```

或者直接运行 `ColaApplication.java` 启动类。

### 验证启动

访问 [http://localhost:8081/api/users/1](http://localhost:8081/api/users/1) 验证接口是否正常。

## 实战案例（Cases）

### 案例一：本地开发环境

默认配置使用 H2 内存数据库，无需额外依赖即可启动：

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:db;DB_CLOSE_ON_EXIT=TRUE
    driver-class-name: org.h2.Driver
```

### 案例二：连接 MySQL 数据库

修改 `application-dev.yml`：

```yaml
spring:
  datasource:
    username: root
    password: your_password
    url: jdbc:mysql://localhost:3306/demo?useSSL=false&serverTimezone=GMT%2B8
    driver-class-name: com.mysql.cj.jdbc.Driver
```

## 避坑指南（Pitfalls）

1. **依赖问题**：必须先安装 `eden-architect` 项目
2. **端口冲突**：默认端口 8081，如有冲突请修改 `server.port`
3. **JDK 版本**：请根据分支选择对应的 JDK 版本

## 最佳实践（Best Practices）

1. 开发环境使用 H2 内存数据库快速启动
2. 按需开启组件，避免不必要的依赖
3. 使用 Liquibase 管理数据库变更
