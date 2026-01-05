/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

-- =============================================
-- RBAC 权限管理系统初始化脚本
-- 版本: 1.0.0
-- =============================================

-- 用户表
CREATE TABLE `demo_user`
(
	`id`         BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
	`login`      VARCHAR(20)  NOT NULL COMMENT '登录账号',
	`email`      VARCHAR(100) NOT NULL COMMENT '邮箱',
	`password`   VARCHAR(100) NOT NULL COMMENT '密码',
	`status`     TINYINT      NOT NULL DEFAULT 0 COMMENT '状态：0-待激活,1-已激活,2-已锁定,3-已禁用',
	`created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`updated_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`),
	UNIQUE KEY `uk_login` (`login`),
	UNIQUE KEY `uk_email` (`email`),
	KEY `idx_status` (`status`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- 角色表
CREATE TABLE `demo_role`
(
	`id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '角色ID',
	`code`        VARCHAR(30)  NOT NULL COMMENT '角色编码',
	`name`        VARCHAR(50)  NOT NULL COMMENT '角色名称',
	`description` VARCHAR(200) DEFAULT NULL COMMENT '角色描述',
	`status`      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0-禁用,1-启用',
	`sort`        INT          NOT NULL DEFAULT 0 COMMENT '排序',
	`created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`updated_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`),
	UNIQUE KEY `uk_code` (`code`),
	KEY `idx_status` (`status`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';

-- 权限表
CREATE TABLE `demo_permission`
(
	`id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '权限ID',
	`code`        VARCHAR(100) NOT NULL COMMENT '权限编码',
	`name`        VARCHAR(50)  NOT NULL COMMENT '权限名称',
	`type`        TINYINT      NOT NULL COMMENT '类型：1-菜单,2-按钮,3-接口',
	`parent_id`   BIGINT       DEFAULT 0 COMMENT '父级ID',
	`description` VARCHAR(200) DEFAULT NULL COMMENT '权限描述',
	`sort`        INT          NOT NULL DEFAULT 0 COMMENT '排序',
	`created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`updated_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`),
	UNIQUE KEY `uk_code` (`code`),
	KEY `idx_parent_id` (`parent_id`),
	KEY `idx_type` (`type`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='权限表';

-- 菜单表
CREATE TABLE `demo_menu`
(
	`id`         BIGINT       NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
	`name`       VARCHAR(50)  NOT NULL COMMENT '菜单名称',
	`path`       VARCHAR(200) NOT NULL COMMENT '路由路径',
	`icon`       VARCHAR(50)  DEFAULT NULL COMMENT '图标',
	`parent_id`  BIGINT       DEFAULT 0 COMMENT '父级ID',
	`sort`       INT          NOT NULL DEFAULT 0 COMMENT '排序',
	`status`     TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0-隐藏,1-显示',
	`component`  VARCHAR(200) DEFAULT NULL COMMENT '组件路径',
	`created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`updated_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`),
	UNIQUE KEY `uk_path` (`path`),
	KEY `idx_parent_id` (`parent_id`),
	KEY `idx_status` (`status`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='菜单表';

-- 用户角色关联表
CREATE TABLE `demo_user_role`
(
	`user_id` BIGINT NOT NULL COMMENT '用户ID',
	`role_id` BIGINT NOT NULL COMMENT '角色ID',
	PRIMARY KEY (`user_id`, `role_id`),
	KEY `idx_role_id` (`role_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='用户角色关联表';

-- 角色权限关联表
CREATE TABLE `demo_role_permission`
(
	`role_id`       BIGINT NOT NULL COMMENT '角色ID',
	`permission_id` BIGINT NOT NULL COMMENT '权限ID',
	PRIMARY KEY (`role_id`, `permission_id`),
	KEY `idx_permission_id` (`permission_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='角色权限关联表';

-- 角色菜单关联表
CREATE TABLE `demo_role_menu`
(
	`role_id` BIGINT NOT NULL COMMENT '角色ID',
	`menu_id` BIGINT NOT NULL COMMENT '菜单ID',
	PRIMARY KEY (`role_id`, `menu_id`),
	KEY `idx_menu_id` (`menu_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='角色菜单关联表';

