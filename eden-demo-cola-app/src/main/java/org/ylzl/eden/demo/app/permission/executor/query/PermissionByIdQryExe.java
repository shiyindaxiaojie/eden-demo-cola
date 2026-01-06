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
import org.ylzl.eden.cola.dto.SingleResponse;
import org.ylzl.eden.demo.app.permission.assembler.PermissionAssembler;
import org.ylzl.eden.demo.client.permission.dto.PermissionDTO;
import org.ylzl.eden.demo.client.permission.dto.query.PermissionByIdQry;
import org.ylzl.eden.demo.domain.permission.entity.Permission;
import org.ylzl.eden.demo.domain.permission.gateway.PermissionGateway;
import org.ylzl.eden.spring.framework.error.ClientAssert;

/**
 * 根据ID查询权限执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class PermissionByIdQryExe {

	private final PermissionGateway permissionGateway;
	private final PermissionAssembler permissionAssembler;

	/**
	 * 执行根据ID查询权限
	 *
	 * @param qry 查询条件
	 * @return 权限信息
	 */
	public SingleResponse<PermissionDTO> execute(PermissionByIdQry qry) {
		Permission permission = permissionGateway.findById(qry.getId())
			.orElse(null);
		ClientAssert.notNull(permission, "PERM-002", "权限不存在");
		return SingleResponse.of(permissionAssembler.toDTO(permission));
	}
}
