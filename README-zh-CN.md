<img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/eden-images/readme/icon.png" align="right" />

[license-apache2.0]:https://www.apache.org/licenses/LICENSE-2.0.html

[github-action]:https://github.com/shiyindaxiaojie/eden-demo-cola/actions

[sonarcloud-dashboard]:https://sonarcloud.io/dashboard?id=shiyindaxiaojie_eden-demo-cola

# COLA 架构

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/eden-images/readme/language-java-blue.svg) [![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/eden-images/readme/license-apache2.0-red.svg)][license-apache2.0] [![](https://github.com/shiyindaxiaojie/eden-demo-cola/workflows/build/badge.svg)][github-action] [![](https://sonarcloud.io/api/project_badges/measure?project=shiyindaxiaojie_eden-demo-cola&metric=alert_status)][sonarcloud-dashboard]

本项目使用 COLA 架构构建，COLA 架构是一个整洁的，面向对象的，分层的，可扩展的应用架构，可以帮助降低复杂应用场景的系统熵值，提升系统开发和运维效率。不管是传统的分层架构、六边形架构、还是洋葱架构，都提倡以业务为核心，解耦外部依赖，分离业务复杂度和技术复杂度等，COLA
架构在此基础上融合了 CQRS、DDD、SOLID 等设计思想，形成一套可落地的应用架构。

> 参考文档请查看 [WIKI](https://github.com/shiyindaxiaojie/eden-demo-cola/wiki) 。

## 组件构成

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/eden-images/eden-demo-cola/component.png)

* **eden-demo-cola-adapter**：适配层，六边形架构中的入站适配器
* **eden-demo-cola-app**：应用层，负责 CQRS 的处理工作，接收更新指令并调用领域层，对于查询视图操作直接绕过领域层调用基础设施层
* **eden-demo-cola-client**：API层，对外以 jar 包的形式提供接口
* **eden-demo-cola-domain**：领域层，业务核心实现，不同于传统的分层架构，提供防腐层接口，不依赖基础设施层的技术实现
* **eden-demo-cola-infrastructure**：基础设施层，六边形架构中的出站适配器，封装技术细节，使用依赖倒置实现 Domain 暴露的防腐层接口
* **eden-demo-cola-start**：程序启动入口

## 运行流程

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/eden-images/eden-demo-cola/sequence.png)

## 如何构建

* master 分支对应的是 `eden-demo-cola 2.4.x`，最低支持 JDK 1.8。
* 1.5.x 分支对应的是 `eden-demo-cola 1.5.x`，最低支持 JDK 1.8。
* 2.4.x 分支对应的是 `eden-demo-cola 2.4.x`，最低支持 JDK 1.8。

为了简化不必要的技术细节，本项目依赖 [eden-architect](https://github.com/shiyindaxiaojie/eden-architect)，本项目涉及的依赖项暂时没有发布到 Maven 中央仓库。请按下列步骤完成构建。

1. 克隆 [eden-architect](https://github.com/shiyindaxiaojie/eden-architect) 到本地，执行 `./mvnw install` 安装到本地 Maven 仓库，确保接下来的构建不会出现依赖错误。
2. 构建本项目，执行 `./mvnw install` 即可。
3. 我们提供了一些 CI/CD 平台的构建模板，例如 [CODING](https://coding.net/)，您可以在 `.coding`找到相关示例。

## 如何使用

### 微调默认配置

> 假定您使用的运行环境 `spring.profiles.active` 为 local。

* 修改 Nacos 客户端配置，您可以查阅 [Nacos Quick Start](https://nacos.io/zh-cn/docs/quick-start.html) 快速搭建，请根据您的 Nacos 地址修改配置文件：[bootstrap-local.yml](https://github.com/shiyindaxiaojie/eden-demo-cola/blob/main/eden-demo-cola-start/src/main/resources/config/bootstrap-local.yml)
* 修改数据库的配置，本项目默认使用 H2 内存数据库启动，基于 Liquibase 在项目启动时自动初始化 SQL 脚本。如果您使用的是外部的 MySQL 数据库，可以从此处调整下数据库的连接信息：[application-local.yml](https://github.com/shiyindaxiaojie/eden-demo-cola/blob/main/eden-demo-cola-start/src/main/resources/config/application-local.yml)
* 本项目罗列了 `Redis` 缓存、`RocketMQ` 消息队列、`Dynamic Source` 动态数据源、`ShardingSphere`
  分库分表等常用组件的使用方案，默认通过 `xxx.enabled` 关闭自动配置。您可以根据实际情况开启配置，直接完成组件的集成。

### 运行您的应用

- 在项目目录下运行 `mvn install`（如果不想运行测试，可以加上 `-DskipTests` 参数）。
- 进入 `eden-demo-cola-start` 目录，执行 `mvn spring-boot:run` 或者启动 `Application` 类。运行成功的话，可以看到 `Spring Boot` 启动成功的界面。
- 本应用中已经实现了一个简单的 `RestController` 接口，可以点击 [演示接口](http://localhost:8080/api/users/1) 进行测试。

## 版本规范

项目的版本号格式为 x.x.x 的形式，其中 x 的数值类型为数字，从 0 开始取值，且不限于 0~9 这个范围。项目处于孵化器阶段时，第一位版本号固定使用
0，即版本号为 0.x.x 的格式。

由于 `Spring Boot 1.5.x` 和 `Spring Boot 2.4.x` 在架构层面有很大的变更，因此我们采取跟 Spring Boot
版本号一致的版本:

* 1.5.x 版本适用于 `Spring Boot 1.5.x`
* 2.4.x 版本适用于 `Spring Boot 2.4.x`
