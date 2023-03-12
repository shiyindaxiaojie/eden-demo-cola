# Changelog

## 0.0.1-SNAPSHOT(Dec 31, 2022)

### Features

- 整合 `Spring Cloud Alibaba` 组件，例如 `Nacos`、`Sentinel`、`Dubbo`，接入二次开发的 `Sentinel Dashboard` 可实现流控规则和监控数据的持久化
- 数据库组件集成，例如 `Mybatis Plus`、`Dynamic DataSource`、`HikariCP`、`ShardingSphere`
- 分布式缓存组件集成，使用自研 Cache 组件支持 `Redis`、`Caffeine`、`Guava`，支持京东`HotKey` 或者 `Sentinel` 动态切换多级缓存
- 分布式消息队列组件集成，使用自研 MQ 组件支持 `RocketMQ` 和 `Kafka` 动态切换
- 分布式调度平台组件集成，接入二次开发的 `Xxl-Job` 可实现服务的动态注册
- 在线诊断组件集成，接入二次开发的 `Arthas Tunnel Proxy` 可实现服务的自动发现
- 实时监控组件集成，接入二次开发的 `CAT` 可实现链路的可视化跟踪
- 动态线程池集成，支持服务、 `Dubbo`、`RocketMQ` 的线程调整
