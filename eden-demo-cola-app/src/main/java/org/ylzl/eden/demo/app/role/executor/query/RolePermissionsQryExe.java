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

package org.ylzl.eden.demo.app.role.executor.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.cola.dto.MultiResponse;
import org.ylzl.eden.demo.app.permission.assembler.PermissionAssembler;
import org.ylzl.eden.demo.client.permission.dto.PermissionDTO;
import org.ylzl.eden.demo.client.role.dto.query.RolePermissionsQry;
import org.ylzl.eden.demo.domain.permission.entity.Permission;
import org.ylzl.eden.demo.domain.permission.gateway.PermissionGateway;
import org.ylzl.eden.demo.domain.role.gateway.RoleGateway;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.util.Collections;
import java.util.List;

/**
 * 查询角色权限执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class RolePermissionsQryExe {

	private final RoleGateway roleGateway;
	private final PermissionGateway permissionGateway;
	private final PermissionAssembler permissionAssembler;

	/**
	 * 执行查询角色权限
	 *
	 * @param qry 查询条件
	 * @return 角色权限列表
	 */
	public MultiResponse<PermissionDTO> execute(RolePermissionsQry qry) {
		ClientAssert.isTrue(roleGateway.findById(qry.getRoleId()).isPresent(), "ROLE-002", "角色不存在");

		List<Long> permissionIds = roleGateway.findPermissionIdsByRoleId(qry.getRoleId());
		if (permissionIds.isEmpty()) {
			return MultiResponse.of(Collections.emptyList());
		}

		List<Permission> permissions = permissionGateway.findByIds(permissionIds);
		return MultiResponse.of(permissionAssembler.toDTOList(permissions));
	}
}
