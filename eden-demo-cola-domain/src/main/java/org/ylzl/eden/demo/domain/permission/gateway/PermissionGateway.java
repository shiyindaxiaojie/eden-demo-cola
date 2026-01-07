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

package org.ylzl.eden.demo.domain.permission.gateway;

import org.ylzl.eden.demo.domain.permission.entity.Permission;
import org.ylzl.eden.demo.domain.permission.valueobject.PermissionCode;

import java.util.List;
import java.util.Optional;

/**
 * 权限领域防腐层
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
public interface PermissionGateway {

	/**
	 * 保存权限
	 *
	 * @param permission 权限实体
	 */
	void save(Permission permission);

	/**
	 * 根据ID查询权限
	 *
	 * @param id 权限ID
	 * @return 权限实体
	 */
	Optional<Permission> findById(Long id);

	/**
	 * 根据编码查询权限
	 *
	 * @param code 权限编码
	 * @return 权限实体
	 */
	Permission findByCode(PermissionCode code);

	/**
	 * 检查编码是否存在
	 *
	 * @param code 权限编码
	 * @return 是否存在
	 */
	boolean existsByCode(PermissionCode code);

	/**
	 * 删除权限
	 *
	 * @param id 权限ID
	 */
	void deleteById(Long id);

	/**
	 * 查询所有权限
	 *
	 * @return 权限列表
	 */
	List<Permission> findAll();

	/**
	 * 根据ID列表查询权限
	 *
	 * @param ids 权限ID列表
	 * @return 权限列表
	 */
	List<Permission> findByIds(List<Long> ids);

	/**
	 * 检查权限是否被角色使用
	 *
	 * @param permissionId 权限ID
	 * @return 是否被使用
	 */
	boolean isUsedByRole(Long permissionId);

	/**
	 * 根据父级ID查询权限
	 *
	 * @param parentId 父级ID
	 * @return 权限列表
	 */
	List<Permission> findByParentId(Long parentId);
}
