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

package org.ylzl.eden.demo.infrastructure.role.database;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色权限关联表映射器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@Mapper
public interface RolePermissionMapper {

	/**
	 * 批量插入角色权限关联
	 *
	 * @param roleId        角色ID
	 * @param permissionIds 权限ID列表
	 */
	void batchInsert(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);

	/**
	 * 删除角色的所有权限关联
	 *
	 * @param roleId 角色ID
	 */
	void deleteByRoleId(@Param("roleId") Long roleId);

	/**
	 * 查询角色的权限ID列表
	 *
	 * @param roleId 角色ID
	 * @return 权限ID列表
	 */
	List<Long> selectPermissionIdsByRoleId(@Param("roleId") Long roleId);

	/**
	 * 检查权限是否被角色使用
	 *
	 * @param permissionId 权限ID
	 * @return 使用数量
	 */
	int countByPermissionId(@Param("permissionId") Long permissionId);
}
