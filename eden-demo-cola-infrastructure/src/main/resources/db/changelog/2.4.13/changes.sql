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

CREATE TABLE `demo_user`
(
	`id`                 bigint(20)  NOT NULL COMMENT '主键',
	`login`              varchar(20) NOT NULL COMMENT '登录账户',
	`password_hash`      varchar(70)          DEFAULT NULL COMMENT '密码',
	`email`              varchar(100)         DEFAULT NULL COMMENT '邮箱',
	`activated`          bit(1)      NOT NULL COMMENT '是否激活账户',
	`locked`             bit(1)      NOT NULL COMMENT '是否锁定账户',
	`lang_key`           varchar(6)           DEFAULT NULL COMMENT '语言',
	`activation_key`     varchar(20)          DEFAULT NULL COMMENT '激活账户的代码',
	`reset_key`          varchar(20)          DEFAULT NULL COMMENT '重置密码的代码',
	`reset_date`         timestamp   NULL     DEFAULT NULL COMMENT '重置密码的时间',
	`created_by`         varchar(20) NOT NULL COMMENT '创建的账户',
	`created_date`       timestamp   NOT NULL DEFAULT current_timestamp() COMMENT '创建的时间',
	`last_modified_by`   varchar(20)          DEFAULT NULL COMMENT '最后修改的账户',
	`last_modified_date` timestamp   NULL     DEFAULT NULL COMMENT '最后修改的时间',
	PRIMARY KEY (`id`),
	UNIQUE KEY `ux_user_login` (`login`),
	UNIQUE KEY `ux_user_email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户表';

CREATE TABLE `demo_authority`
(
	`id`   bigint(20)  NOT NULL COMMENT '主键',
	`name` varchar(60) NOT NULL COMMENT '权限名称',
	`code` varchar(20) NOT NULL COMMENT '权限代码',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='权限表';

CREATE TABLE `demo_user_authority`
(
	`user_id`      bigint(20) NOT NULL COMMENT '用户主键',
	`authority_id` bigint(20) NOT NULL COMMENT '权限主键',
	PRIMARY KEY (`user_id`, `authority_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户权限关联表';

