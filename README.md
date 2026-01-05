<img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/readme/icon.png" align="right" />

# COLA Architecture

[![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/readme/language-java-blue.svg)](https://github.com/shiyindaxiaojie/eden-demo-cola)
[![Build Status](https://github.com/shiyindaxiaojie/eden-demo-cola/actions/workflows/maven-ci.yml/badge.svg?branch=main)](https://github.com/shiyindaxiaojie/eden-demo-cola/actions)
[![License](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/readme/license-apache2.0-red.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=shiyindaxiaojie_eden-demo-cola&metric=alert_status)](https://sonarcloud.io/dashboard?id=shiyindaxiaojie_eden-demo-cola)

<p>
  <strong>A Clean, Object-Oriented, Layered, and Extensible Application Architecture</strong>
</p>

[简体中文](./README-zh-CN.md) | English

---

## 📖 Introduction

**COLA (Clean Object-Oriented and Layered Architecture)** is designed to reduce the entropy of complex application scenarios and improve development and operation efficiency. Whether it is traditional Layered Architecture, Hexagonal Architecture, or Onion Architecture, they all advocate regarding business as the core, decoupling external dependencies, and separating business complexity from technical complexity. COLA integrates design thoughts from **CQRS**, **DDD**, and **SOLID** to form a practical application architecture.

> For detailed documentation, please visit [WIKI](https://github.com/shiyindaxiaojie/eden-demo-cola/wiki).

## 🏗️ Architecture

<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/eden-demo-cola/component.png" alt="Architecture Diagram" width="100%" />
</div>

### Component Overview

| Component | Description |
|-----------|-------------|
| **eden-demo-cola-adapter** | Adapter Layer. Functions as the Inbound Adapter in **Hexagonal Architecture**. |
| **eden-demo-cola-app** | Application Layer. Handles **CQRS** commands, updates commands, calls the Domain Layer, queries views, and can bypass the Domain Layer to call Infrastructure. |
| **eden-demo-cola-client** | API Layer. Provides interfaces externally as a jar package. |
| **eden-demo-cola-domain** | Domain Layer. Core business implementation. Unlike traditional layered architectures, it provides Anti-Corruption Layer (ACL) interfaces and does not depend on Infrastructure implementation. |
| **eden-demo-cola-infrastructure** | Infrastructure Layer. Outbound Adapter in **Hexagonal Architecture**. Encapsulates technical details and uses **Dependency Inversion** to implement ACL interfaces exposed by the Domain. |
| **eden-demo-cola-start** | Startup Module. Manages application configuration and delivery. |

## � GExecution Flow

<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/eden-demo-cola/sequence.png" alt="Sequence Diagram" width="100%" />
</div>

## 🚀 Getting Started

### Prerequisites

Since `Spring Boot 2.4.x` and `3.0.x` vary significantly, we maintain matching branches:

- **Branch 2.4.x**: For `Spring Boot 2.4.x` (Min JDK 1.8)
- **Branch 2.7.x**: For `Spring Boot 2.7.x` (Min JDK 11)
- **Branch 3.0.x**: For `Spring Boot 3.0.x` (Min JDK 17)

### Installation

Clone the repository and install it to your local Maven repository. This project depends on [eden-architect](https://github.com/shiyindaxiaojie/eden-architect).

```bash
git clone https://github.com/shiyindaxiaojie/eden-demo-cola.git
cd eden-demo-cola
mvn install -T 4C
```

### Usage

#### Quick Start

The project defaults to the `dev` environment. All external component dependencies are disabled by default for easy startup.

1.  Run `mvn install` (add `-DskipTests` to skip tests).
2.  Enter `eden-demo-cola-start` directory and run `mvn spring-boot:run` or start the `ColaApplication` class.
3.  Test the demo API: [http://localhost:8081/api/users/1](http://localhost:8081/api/users/1).
4.  Accessing the root [http://localhost:8081](http://localhost:8081) will show a 404 page (frontend not included).

<img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/common/404.png" alt="404 Page" width="500" />

#### Configuration

**Enable Service Discovery and Configuration Management**: We recommend using `Nacos`. You can refer to [Nacos Quick Start](https://nacos.io/zh-cn/docs/quick-start.html) to set it up quickly. Modify the configuration file: [bootstrap-dev.yml](https://github.com/shiyindaxiaojie/eden-demo-cola/blob/main/eden-demo-cola-start/src/main/resources/config/bootstrap-dev.yml), adjust the following:

```yaml
spring:
  cloud:
    nacos:
      discovery: # Service Discovery
        enabled: true # Disabled by default, enable as needed
      config: # Configuration Management
        enabled: true # Disabled by default, enable as needed
```

**Modify Default DataSource**: This project uses `H2` in-memory database by default, with `Liquibase` automatically initializing SQL scripts on startup. If you're using an external MySQL database, adjust the database connection in: [application-dev.yml](https://github.com/shiyindaxiaojie/eden-demo-cola/blob/main/eden-demo-cola-start/src/main/resources/config/application-dev.yml), and remove any `H2` related configurations.

```yaml
spring:
#  h2: # In-memory database
#    console:
#      enabled: true # Do not enable in production
#      path: /h2-console
#      settings:
#        trace: false
#        web-allow-others: false
  datasource: # DataSource Management
    username: 
    password: 
    url: jdbc:mysql://host:port/schema?rewriteBatchedStatements=true&useSSL=false&useOldAliasMetadataBehavior=true&useUnicode=true&serverTimezone=GMT%2B8
    driver-class-name: com.mysql.cj.jdbc.Driver
```

Additionally, this project includes usage examples for common components like `Redis` cache, `RocketMQ` message queue, and `ShardingSphere` sharding. They are disabled by default via `xxx.enabled`. You can enable them based on your actual needs to complete component integration.

## 📦 Deployment

### FatJar Deployment

Execute `mvn -T 4C clean package` to package into a runnable fat jar, then start the application with the following command:

```bash
java -Dserver.port=8081 -jar target/eden-demo-cola-start.jar
```

### Assembly Packaging

Execute `mvn -P assembly -T 4C clean package` to package into a compressed archive. Choose one of the following archives and copy it to your desired deployment directory:

* target/eden-demo-cola-start-assembly.zip
* target/eden-demo-cola-start-assembly.tar.gz

After extracting the files, you can find `startup.sh` or `startup.bat` scripts in the `bin` directory. Run them directly to start the application.

### Jib Image Build

Google Jib plugin allows you to build images without Docker installed.

```bash
mvn -T 4C -U package
mvn -pl eden-demo-cola-start jib:build -Djib.disableUpdateChecks=true -DskipTests
```

### Docker Deployment

Build images based on Spring Boot's layering feature. Ensure Docker is properly installed, then execute:

```bash
docker build -f docker/Dockerfile -t eden-demo-cola:{tag} .
```

### Helm Deployment

Application-centric approach. We recommend using Helm to manage K8s resource descriptors. Use the following commands to install and uninstall:

```bash
helm install eden-demo-cola ./helm # Deploy resources
helm uninstall eden-demo-cola # Uninstall resources
```

## 📋 Versioning

The project version format is `x.y.z`, where x is a number starting from 0 with no upper limit. During the incubation stage, the first digit is fixed at 0, i.e., version format `0.x.x`.

* Incubation version: 0.0.1-SNAPSHOT
* Development version: 1.0.0-SNAPSHOT
* Release version: 1.0.0

Version iteration rules:

* 1.0.0 <> 1.0.1: Compatible
* 1.0.0 <> 1.1.0: Mostly compatible
* 1.0.0 <> 2.0.0: Incompatible

## 🔧 Continuous Integration

> CI/CD Tool Options: Jenkins, Zadig, CODING, Codeup

### CODING CI/CD

The following demonstrates continuous build and deployment using CODING. [Portal](https://www.yuque.com/mengxiangge/action/coding)

<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/common/coding-cicd.png" alt="CODING CI/CD" width="100%" />
</div>

<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/common/coding-test-report.png" alt="CODING Test Report" width="100%" />
</div>

### Codeup CI/CD

> TODO, Coming soon

## 💡 Best Practices

### DDD - Domain-Driven Design

> TODO, Coming soon

### Git Multi-User Collaboration Branch Management

In the era of agile development, `GitFlow` seems inadequate. The author has developed a simple and practical workflow for the team. [Portal](https://www.processon.com/view/63d5d1fc56e18032d4a00998)

<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/processon/git-action.png" alt="Git Action" width="100%" />
</div>

### CAT - Observability Solution

Analyze the entire chain through `TraceId`: `HTTP` request latency, `RPC` calls, `Log` business logs, `SQL` and `Cache` execution time. [Portal](https://github.com/shiyindaxiaojie/cat)

<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/cat/tracing.png" alt="CAT Tracing" width="100%" />
</div>

### Sentinel - Traffic Governance Solution

Configure flow control rules based on business load and view interface QPS and throttling status at any time. [Portal](https://github.com/shiyindaxiaojie/Sentinel)

<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/sentinel/sentinel-dashboard-overview-custom.png" alt="Sentinel Dashboard" width="100%" />
</div>

### Arthas - Online Diagnostic Tool

Use dynamic runtime probes to automatically discover services, out-of-the-box, allowing you to diagnose your application in low-load environments. [Portal](https://github.com/shiyindaxiaojie/arthas)

<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/arthas/arthas-dashboard-overview.png" alt="Arthas Dashboard" width="100%" />
</div>

## 📝 Changelog

Please see [CHANGELOG.md](https://github.com/shiyindaxiaojie/eden-demo-cola/blob/main/CHANGELOG.md).
