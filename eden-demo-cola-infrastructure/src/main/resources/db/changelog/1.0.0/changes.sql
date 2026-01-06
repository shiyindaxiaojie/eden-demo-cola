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
CREATE TABLE IF NOT EXISTS demo_user
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    login      VARCHAR(20)  NOT NULL,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(100) NOT NULL,
    status     TINYINT      NOT NULL DEFAULT 0,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uk_user_login UNIQUE (login),
    CONSTRAINT uk_user_email UNIQUE (email)
);
CREATE INDEX IF NOT EXISTS idx_user_status ON demo_user(status);

-- 角色表
CREATE TABLE IF NOT EXISTS demo_role
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    code        VARCHAR(30)  NOT NULL,
    name        VARCHAR(50)  NOT NULL,
    description VARCHAR(200) DEFAULT NULL,
    status      TINYINT      NOT NULL DEFAULT 1,
    sort        INT          NOT NULL DEFAULT 0,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uk_role_code UNIQUE (code)
);
CREATE INDEX IF NOT EXISTS idx_role_status ON demo_role(status);

-- 权限表
CREATE TABLE IF NOT EXISTS demo_permission
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    code        VARCHAR(100) NOT NULL,
    name        VARCHAR(50)  NOT NULL,
    type        TINYINT      NOT NULL,
    parent_id   BIGINT       DEFAULT 0,
    description VARCHAR(200) DEFAULT NULL,
    sort        INT          NOT NULL DEFAULT 0,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uk_permission_code UNIQUE (code)
);
CREATE INDEX IF NOT EXISTS idx_permission_parent_id ON demo_permission(parent_id);
CREATE INDEX IF NOT EXISTS idx_permission_type ON demo_permission(type);

-- 菜单表
CREATE TABLE IF NOT EXISTS demo_menu
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    name       VARCHAR(50)  NOT NULL,
    path       VARCHAR(200) NOT NULL,
    icon       VARCHAR(50)  DEFAULT NULL,
    parent_id  BIGINT       DEFAULT 0,
    sort       INT          NOT NULL DEFAULT 0,
    status     TINYINT      NOT NULL DEFAULT 1,
    component  VARCHAR(200) DEFAULT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uk_menu_path UNIQUE (path)
);
CREATE INDEX IF NOT EXISTS idx_menu_parent_id ON demo_menu(parent_id);
CREATE INDEX IF NOT EXISTS idx_menu_status ON demo_menu(status);

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS demo_user_role
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id)
);
CREATE INDEX IF NOT EXISTS idx_user_role_role_id ON demo_user_role(role_id);

-- 角色权限关联表
CREATE TABLE IF NOT EXISTS demo_role_permission
(
    role_id       BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id)
);
CREATE INDEX IF NOT EXISTS idx_role_permission_permission_id ON demo_role_permission(permission_id);

-- 角色菜单关联表
CREATE TABLE IF NOT EXISTS demo_role_menu
(
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, menu_id)
);
CREATE INDEX IF NOT EXISTS idx_role_menu_menu_id ON demo_role_menu(menu_id);
