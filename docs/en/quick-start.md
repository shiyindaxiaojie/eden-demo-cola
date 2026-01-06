# Quick Start

## What

COLA (Clean Object-oriented Layered Architecture) is an open-source application architecture from Alibaba. This project is a best practice example based on COLA architecture, integrating commonly used enterprise-level components and configurations.

## Why

- Clean layered architecture reduces system complexity
- Integrates DDD, CQRS, SOLID design principles
- Out-of-the-box component integration solutions
- Complete configuration examples and documentation

## How

### Requirements

- JDK 1.8+ (2.4.x branch) / JDK 11+ (2.7.x branch) / JDK 17+ (3.0.x branch)
- Maven 3.6+
- Git

### Clone Project

```bash
git clone https://github.com/shiyindaxiaojie/eden-demo-cola.git
cd eden-demo-cola
```

### Build Project

```bash
# Install dependency project
git clone https://github.com/shiyindaxiaojie/eden-architect.git
cd eden-architect && mvn install -T 4C -DskipTests

# Build this project
cd eden-demo-cola && mvn install -T 4C -DskipTests
```

### Start Application

```bash
cd eden-demo-cola-start
mvn spring-boot:run
```

Or run `ColaApplication.java` directly.

### Verify Startup

Visit [http://localhost:8081/api/users/1](http://localhost:8081/api/users/1) to verify the API.

## Cases

### Case 1: Local Development Environment

Default configuration uses H2 in-memory database, no additional dependencies required:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:db;DB_CLOSE_ON_EXIT=TRUE
    driver-class-name: org.h2.Driver
```

### Case 2: Connect to MySQL Database

Modify `application-dev.yml`:

```yaml
spring:
  datasource:
    username: root
    password: your_password
    url: jdbc:mysql://localhost:3306/demo?useSSL=false&serverTimezone=GMT%2B8
    driver-class-name: com.mysql.cj.jdbc.Driver
```

## Pitfalls

1. **Dependency Issue**: Must install `eden-architect` project first
2. **Port Conflict**: Default port is 8081, modify `server.port` if conflicted
3. **JDK Version**: Choose the correct JDK version based on branch

## Best Practices

1. Use H2 in-memory database for quick startup in development
2. Enable components on demand to avoid unnecessary dependencies
3. Use Liquibase to manage database changes

