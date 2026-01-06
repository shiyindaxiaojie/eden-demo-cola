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

package org.ylzl.eden.demo.app.user.executor.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.cola.dto.MultiResponse;
import org.ylzl.eden.demo.client.user.dto.query.UserPermissionsQry;
import org.ylzl.eden.demo.domain.permission.entity.Permission;
import org.ylzl.eden.demo.domain.permission.gateway.PermissionGateway;
import org.ylzl.eden.demo.domain.role.entity.Role;
import org.ylzl.eden.demo.domain.role.gateway.RoleGateway;
import org.ylzl.eden.demo.domain.user.gateway.UserGateway;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 查询用户权限执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class UserPermissionsQryExe {

	private final UserGateway userGateway;
	private final RoleGateway roleGateway;
	private final PermissionGateway permissionGateway;

	/**
	 * 执行查询用户权限
	 *
	 * @param qry 查询条件
	 * @return 用户权限编码列表
	 */
	public MultiResponse<String> execute(UserPermissionsQry qry) {
		ClientAssert.isTrue(userGateway.findById(qry.getUserId()).isPresent(), "USER-404", "用户不存在");

		// 获取用户的所有角色
		List<Role> roles = userGateway.findRolesByUserId(qry.getUserId());
		if (roles.isEmpty()) {
			return MultiResponse.of(Collections.emptyList());
		}

		// 获取所有角色的权限ID（去重）
		Set<Long> permissionIds = new HashSet<>();
		for (Role role : roles) {
			if (role.isEnabled()) {
				List<Long> rolePermissionIds = roleGateway.findPermissionIdsByRoleId(role.getId());
				permissionIds.addAll(rolePermissionIds);
			}
		}

		if (permissionIds.isEmpty()) {
			return MultiResponse.of(Collections.emptyList());
		}

		// 获取权限编码列表
		List<Permission> permissions = permissionGateway.findByIds(new ArrayList<>(permissionIds));
		List<String> permissionCodes = permissions.stream()
			.map(p -> p.getCode() != null ? p.getCode().getValue() : null)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());

		return MultiResponse.of(permissionCodes);
	}
}
