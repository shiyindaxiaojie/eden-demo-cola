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

-   **Nacos**: Enable in `bootstrap-dev.yml` for service discovery and config management.
-   **Database**: Default is H2. Modify `application-dev.yml` to use MySQL.
-   **Other Components**: Enable Redis, RocketMQ, etc., via `xxx.enabled` properties.

## 📦 Deployment

### FatJar
```bash
mvn -T 4C clean package
java -Dserver.port=8081 -jar target/eden-demo-cola-start.jar
```

### Docker
```bash
docker build -f docker/Dockerfile -t eden-demo-cola:{tag} .
```

### Helm
```bash
helm install eden-demo-cola ./helm
```

## 📅 Versioning

We follow Semantic Versioning `x.y.z`.

## 📝 Changelog

Please see [CHANGELOG.md](https://github.com/shiyindaxiaojie/eden-demo-cola/blob/main/CHANGELOG.md).

## 📄 License

This project is licensed under the [Apache-2.0 License](https://www.apache.org/licenses/LICENSE-2.0.html).
