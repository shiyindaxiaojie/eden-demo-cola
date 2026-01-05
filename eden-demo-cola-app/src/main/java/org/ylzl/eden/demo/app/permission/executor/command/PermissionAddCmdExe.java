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

package org.ylzl.eden.demo.app.permission.executor.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.demo.client.permission.dto.command.PermissionAddCmd;
import org.ylzl.eden.demo.domain.permission.entity.Permission;
import org.ylzl.eden.demo.domain.permission.entity.PermissionType;
import org.ylzl.eden.demo.domain.permission.gateway.PermissionGateway;
import org.ylzl.eden.demo.domain.permission.valueobject.PermissionCode;
import org.ylzl.eden.spring.framework.error.ClientAssert;

/**
 * 新增权限指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class PermissionAddCmdExe {

	private final PermissionGateway permissionGateway;

	public Response execute(PermissionAddCmd cmd) {
		PermissionCode code = new PermissionCode(cmd.getCode());
		ClientAssert.isFalse(permissionGateway.existsByCode(code), "PERM-001", "权限编码已存在");

		PermissionType type = PermissionType.of(cmd.getType());
		Permission permission = Permission.create(code, cmd.getName(), type);
		permission.setParent(cmd.getParentId());
		permission.updateInfo(null, cmd.getDescription(), cmd.getSort());
		permissionGateway.save(permission);
		return Response.buildSuccess();
	}
}
