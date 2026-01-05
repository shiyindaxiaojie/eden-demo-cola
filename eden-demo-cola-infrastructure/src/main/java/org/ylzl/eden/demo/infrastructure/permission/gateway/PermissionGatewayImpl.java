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

package org.ylzl.eden.demo.infrastructure.permission.gateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.ylzl.eden.demo.domain.permission.entity.Permission;
import org.ylzl.eden.demo.domain.permission.gateway.PermissionGateway;
import org.ylzl.eden.demo.domain.permission.valueobject.PermissionCode;
import org.ylzl.eden.demo.infrastructure.permission.database.PermissionMapper;
import org.ylzl.eden.demo.infrastructure.permission.database.convertor.PermissionConvertor;
import org.ylzl.eden.demo.infrastructure.permission.database.dataobject.PermissionDO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 权限领域防腐层实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Repository
public class PermissionGatewayImpl implements PermissionGateway {

	private final PermissionMapper permissionMapper;
	private final PermissionConvertor permissionConvertor;

	@Override
	public void save(Permission permission) {
		PermissionDO permissionDO = permissionConvertor.toDataObject(permission);
		if (permission.getId() == null) {
			permissionMapper.insert(permissionDO);
		} else {
			permissionMapper.updateById(permissionDO);
		}
	}

	@Override
	public Optional<Permission> findById(Long id) {
		PermissionDO permissionDO = permissionMapper.selectById(id);
		return Optional.ofNullable(permissionConvertor.toEntity(permissionDO));
	}

	@Override
	public Permission findByCode(PermissionCode code) {
		PermissionDO permissionDO = permissionMapper.selectByCode(code.getValue());
		return permissionConvertor.toEntity(permissionDO);
	}

	@Override
	public boolean existsByCode(PermissionCode code) {
		return permissionMapper.countByCode(code.getValue()) > 0;
	}

	@Override
	public void deleteById(Long id) {
		permissionMapper.deleteById(id);
	}

	@Override
	public List<Permission> findAll() {
		List<PermissionDO> permissionDOs = permissionMapper.selectAll();
		return permissionDOs.stream()
			.map(permissionConvertor::toEntity)
			.collect(Collectors.toList());
	}

	@Override
	public List<Permission> findByIds(List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		List<PermissionDO> permissionDOs = permissionMapper.selectByIds(ids);
		return permissionDOs.stream()
			.map(permissionConvertor::toEntity)
			.collect(Collectors.toList());
	}

	@Override
	public boolean isUsedByRole(Long permissionId) {
		return permissionMapper.countRoleByPermissionId(permissionId) > 0;
	}
}
