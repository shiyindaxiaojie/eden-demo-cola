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
 * 角色菜单关联表映射器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Mapper
public interface RoleMenuMapper {

	/**
	 * 批量插入角色菜单关联
	 *
	 * @param roleId  角色ID
	 * @param menuIds 菜单ID列表
	 */
	void batchInsert(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);

	/**
	 * 删除角色的所有菜单关联
	 *
	 * @param roleId 角色ID
	 */
	void deleteByRoleId(@Param("roleId") Long roleId);

	/**
	 * 查询角色的菜单ID列表
	 *
	 * @param roleId 角色ID
	 * @return 菜单ID列表
	 */
	List<Long> selectMenuIdsByRoleId(@Param("roleId") Long roleId);

	/**
	 * 检查菜单是否被角色使用
	 *
	 * @param menuId 菜单ID
	 * @return 使用数量
	 */
	int countByMenuId(@Param("menuId") Long menuId);
}
