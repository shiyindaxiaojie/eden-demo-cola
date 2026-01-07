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

package org.ylzl.eden.demo.infrastructure.role.gateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.ylzl.eden.demo.domain.role.entity.Role;
import org.ylzl.eden.demo.domain.role.gateway.RoleGateway;
import org.ylzl.eden.demo.domain.role.valueobject.RoleCode;
import org.ylzl.eden.demo.infrastructure.role.database.RoleMapper;
import org.ylzl.eden.demo.infrastructure.role.database.RoleMenuMapper;
import org.ylzl.eden.demo.infrastructure.role.database.RolePermissionMapper;
import org.ylzl.eden.demo.infrastructure.role.database.convertor.RoleConvertor;
import org.ylzl.eden.demo.infrastructure.role.database.dataobject.RoleDO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 角色领域防腐层实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
@Repository
public class RoleGatewayImpl implements RoleGateway {

	private final RoleMapper roleMapper;
	private final RolePermissionMapper rolePermissionMapper;
	private final RoleMenuMapper roleMenuMapper;
	private final RoleConvertor roleConvertor;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Role role) {
		RoleDO roleDO = roleConvertor.toDataObject(role);
		if (role.getId() == null) {
			roleMapper.insert(roleDO);
		} else {
			roleMapper.updateById(roleDO);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Role> findById(Long id) {
		RoleDO roleDO = roleMapper.selectById(id);
		return Optional.ofNullable(roleConvertor.toEntity(roleDO));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role findByCode(RoleCode code) {
		RoleDO roleDO = roleMapper.selectByCode(code.getValue());
		return roleConvertor.toEntity(roleDO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existsByCode(RoleCode code) {
		return roleMapper.countByCode(code.getValue()) > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteById(Long id) {
		// 删除角色权限关联
		rolePermissionMapper.deleteByRoleId(id);
		// 删除角色菜单关联
		roleMenuMapper.deleteByRoleId(id);
		// 删除角色
		roleMapper.deleteById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> findByIds(List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		List<RoleDO> roleDOs = roleMapper.selectByIds(ids);
		return roleDOs.stream()
			.map(roleConvertor::toEntity)
			.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> findAllEnabled() {
		List<RoleDO> roleDOs = roleMapper.selectAllEnabled();
		return roleDOs.stream()
			.map(roleConvertor::toEntity)
			.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isUsedByUser(Long roleId) {
		return roleMapper.countUserByRoleId(roleId) > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveRolePermissions(Long roleId, List<Long> permissionIds) {
		// 先删除原有关联
		rolePermissionMapper.deleteByRoleId(roleId);
		// 再批量插入新关联
		if (permissionIds != null && !permissionIds.isEmpty()) {
			rolePermissionMapper.batchInsert(roleId, permissionIds);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveRoleMenus(Long roleId, List<Long> menuIds) {
		// 先删除原有关联
		roleMenuMapper.deleteByRoleId(roleId);
		// 再批量插入新关联
		if (menuIds != null && !menuIds.isEmpty()) {
			roleMenuMapper.batchInsert(roleId, menuIds);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Long> findPermissionIdsByRoleId(Long roleId) {
		return rolePermissionMapper.selectPermissionIdsByRoleId(roleId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Long> findMenuIdsByRoleId(Long roleId) {
		return roleMenuMapper.selectMenuIdsByRoleId(roleId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteRolePermissions(Long roleId) {
		rolePermissionMapper.deleteByRoleId(roleId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteRoleMenus(Long roleId) {
		roleMenuMapper.deleteByRoleId(roleId);
	}
}
