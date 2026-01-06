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
import org.ylzl.eden.demo.client.role.dto.RoleDTO;
import org.ylzl.eden.demo.client.user.dto.query.UserRolesQry;
import org.ylzl.eden.demo.domain.role.entity.Role;
import org.ylzl.eden.demo.domain.user.gateway.UserGateway;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询用户角色执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class UserRolesQryExe {

	private final UserGateway userGateway;

	/**
	 * 执行查询用户角色
	 *
	 * @param qry 查询条件
	 * @return 用户角色列表
	 */
	public MultiResponse<RoleDTO> execute(UserRolesQry qry) {
		ClientAssert.isTrue(userGateway.findById(qry.getUserId()).isPresent(), "USER-404", "用户不存在");

		List<Role> roles = userGateway.findRolesByUserId(qry.getUserId());
		List<RoleDTO> dtos = roles.stream()
			.map(this::toDTO)
			.collect(Collectors.toList());
		return MultiResponse.of(dtos);
	}

	/**
	 * 将角色实体转换为DTO
	 *
	 * @param role 角色实体
	 * @return 角色DTO
	 */
	private RoleDTO toDTO(Role role) {
		return RoleDTO.builder()
			.id(role.getId())
			.code(role.getCode() != null ? role.getCode().getValue() : null)
			.name(role.getName() != null ? role.getName().getValue() : null)
			.description(role.getDescription())
			.status(role.getStatus() != null ? role.getStatus().getValue() : null)
			.sort(role.getSort())
			.createdAt(role.getCreatedAt())
			.build();
	}
}
