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
-- RBAC 权限管理系统回滚脚本
-- 版本: 1.0.0
-- =============================================

DROP TABLE IF EXISTS `demo_role_menu`;
DROP TABLE IF EXISTS `demo_role_permission`;
DROP TABLE IF EXISTS `demo_user_role`;
DROP TABLE IF EXISTS `demo_menu`;
DROP TABLE IF EXISTS `demo_permission`;
DROP TABLE IF EXISTS `demo_role`;
DROP TABLE IF EXISTS `demo_user`;

