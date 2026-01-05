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

package org.ylzl.eden.demo.app.permission.executor.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.cola.dto.ListResponse;
import org.ylzl.eden.demo.app.permission.assembler.PermissionAssembler;
import org.ylzl.eden.demo.client.permission.dto.PermissionTreeDTO;
import org.ylzl.eden.demo.client.permission.dto.query.PermissionTreeQry;
import org.ylzl.eden.demo.domain.permission.entity.Permission;
import org.ylzl.eden.demo.domain.permission.gateway.PermissionGateway;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询权限树执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class PermissionTreeQryExe {

	private final PermissionGateway permissionGateway;
	private final PermissionAssembler permissionAssembler;

	public ListResponse<PermissionTreeDTO> execute(PermissionTreeQry qry) {
		List<Permission> permissions = permissionGateway.findAll();

		if (qry.getType() != null) {
			permissions = permissions.stream()
				.filter(p -> p.getType() != null && p.getType().getValue().equals(qry.getType()))
				.collect(Collectors.toList());
		}

		return ListResponse.of(permissionAssembler.buildTree(permissions));
	}
}
