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

package org.ylzl.eden.demo.domain.role.gateway;

import org.ylzl.eden.demo.domain.role.entity.Role;
import org.ylzl.eden.demo.domain.role.valueobject.RoleCode;

import java.util.List;
import java.util.Optional;

/**
 * 角色领域防腐层
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
public interface RoleGateway {

	/**
	 * 保存角色
	 *
	 * @param role 角色实体
	 */
	void save(Role role);

	/**
	 * 根据ID查询角色
	 *
	 * @param id 角色ID
	 * @return 角色实体
	 */
	Optional<Role> findById(Long id);

	/**
	 * 根据编码查询角色
	 *
	 * @param code 角色编码
	 * @return 角色实体
	 */
	Role findByCode(RoleCode code);

	/**
	 * 检查编码是否存在
	 *
	 * @param code 角色编码
	 * @return 是否存在
	 */
	boolean existsByCode(RoleCode code);

	/**
	 * 删除角色
	 *
	 * @param id 角色ID
	 */
	void deleteById(Long id);

	/**
	 * 根据ID列表查询角色
	 *
	 * @param ids 角色ID列表
	 * @return 角色列表
	 */
	List<Role> findByIds(List<Long> ids);

	/**
	 * 查询所有启用的角色
	 *
	 * @return 角色列表
	 */
	List<Role> findAllEnabled();

	/**
	 * 检查角色是否被用户使用
	 *
	 * @param roleId 角色ID
	 * @return 是否被使用
	 */
	boolean isUsedByUser(Long roleId);

	/**
	 * 保存角色权限关联
	 *
	 * @param roleId        角色ID
	 * @param permissionIds 权限ID列表
	 */
	void saveRolePermissions(Long roleId, List<Long> permissionIds);

	/**
	 * 保存角色菜单关联
	 *
	 * @param roleId  角色ID
	 * @param menuIds 菜单ID列表
	 */
	void saveRoleMenus(Long roleId, List<Long> menuIds);

	/**
	 * 查询角色的权限ID列表
	 *
	 * @param roleId 角色ID
	 * @return 权限ID列表
	 */
	List<Long> findPermissionIdsByRoleId(Long roleId);

	/**
	 * 查询角色的菜单ID列表
	 *
	 * @param roleId 角色ID
	 * @return 菜单ID列表
	 */
	List<Long> findMenuIdsByRoleId(Long roleId);

	/**
	 * 删除角色的所有权限关联
	 *
	 * @param roleId 角色ID
	 */
	void deleteRolePermissions(Long roleId);

	/**
	 * 删除角色的所有菜单关联
	 *
	 * @param roleId 角色ID
	 */
	void deleteRoleMenus(Long roleId);
}
