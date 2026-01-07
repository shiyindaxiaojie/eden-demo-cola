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
import org.ylzl.eden.demo.client.permission.dto.command.PermissionModifyCmd;
import org.ylzl.eden.demo.domain.permission.entity.Permission;
import org.ylzl.eden.demo.domain.permission.gateway.PermissionGateway;

/**
 * 修改权限指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class PermissionModifyCmdExe {

	private final PermissionGateway permissionGateway;

	/**
	 * 执行修改权限指令
	 *
	 * @param cmd 修改权限指令
	 * @return 响应结果
	 */
	public Response execute(PermissionModifyCmd cmd) {
		Permission permission = permissionGateway.findById(cmd.getId())
			.orElseThrow(() -> new IllegalArgumentException("权限不存在"));

		permission.updateInfo(cmd.getName(), cmd.getDescription(), cmd.getSort());
		permissionGateway.save(permission);
		return Response.buildSuccess();
	}
}
