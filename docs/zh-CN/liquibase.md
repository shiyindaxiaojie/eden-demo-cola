# Liquibase 数据库版本管理

## 是什么（What）

Liquibase 是开源的数据库变更管理工具，本项目集成 Liquibase 实现数据库版本控制和自动迁移。

## 为什么用（Why）

- 版本控制：像管理代码一样管理数据库变更
- 自动迁移：应用启动时自动执行未执行的变更脚本
- 多环境支持：同一套脚本适用于开发、测试、生产环境
- 回滚能力：支持变更回滚，降低发布风险

## 怎么用（How）

### 1. 开启配置

修改 `application-dev.yml`：

```yaml
spring:
  liquibase:
    enabled: true # 开关
```

### 2. 创建 Changelog 文件

在 `src/main/resources/db/changelog` 目录下创建变更文件：

```
db/
└── changelog/
    ├── db.changelog-master.yaml    # 主文件
    ├── db.changelog-1.0.yaml       # 版本 1.0 变更
    └── db.changelog-1.1.yaml       # 版本 1.1 变更
```

### 3. 主文件配置

`db.changelog-master.yaml`：

```yaml
databaseChangeLog:
  - include:
      file: db/changelog/db.changelog-1.0.yaml
  - include:
      file: db/changelog/db.changelog-1.1.yaml
```

### 4. 变更脚本示例

`db.changelog-1.0.yaml`：

```yaml
databaseChangeLog:
  - changeSet:
      id: 1
      author: developer
      changes:
        - createTable:
            tableName: sys_user
            columns:
              - column:
                  name: id
                  type: bigint
                  autoIncrement: true
                  constraints:
                    primaryKey: true
              - column:
                  name: login
                  type: varchar(50)
                  constraints:
                    nullable: false
              - column:
                  name: email
                  type: varchar(100)
              - column:
                  name: created_date
                  type: datetime
```

## 实战案例（Cases）

### 案例一：添加新表

```yaml
databaseChangeLog:
  - changeSet:
      id: 2
      author: developer
      changes:
        - createTable:
            tableName: sys_role
            columns:
              - column:
                  name: id
                  type: bigint
                  autoIncrement: true
                  constraints:
                    primaryKey: true
              - column:
                  name: code
                  type: varchar(50)
              - column:
                  name: name
                  type: varchar(100)
```

### 案例二：添加索引

```yaml
databaseChangeLog:
  - changeSet:
      id: 3
      author: developer
      changes:
        - createIndex:
            indexName: idx_user_login
            tableName: sys_user
            columns:
              - column:
                  name: login
```

### 案例三：使用 SQL 脚本

```yaml
databaseChangeLog:
  - changeSet:
      id: 4
      author: developer
      changes:
        - sqlFile:
            path: db/sql/init-data.sql
            relativeToChangelogFile: false
```

## 避坑指南（Pitfalls）

1. **changeSet ID 唯一**：同一作者的 changeSet ID 必须唯一，否则会报错
2. **已执行的变更不可修改**：已执行的 changeSet 不要修改，否则校验失败
3. **ShardingSphere 兼容性**：ShardingSphere 5.x 与 Liquibase 存在兼容性问题
4. **生产环境谨慎**：生产环境建议关闭自动执行，使用手动迁移

## 最佳实践（Best Practices）

1. 每个版本使用独立的 changelog 文件，便于管理
2. changeSet 粒度要小，一个 changeSet 只做一件事
3. 为 changeSet 添加有意义的注释
4. 使用 `runOnChange: true` 标记可重复执行的脚本（如视图、存储过程）

