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
import org.ylzl.eden.demo.client.permission.dto.command.PermissionRemoveCmd;
import org.ylzl.eden.demo.domain.permission.gateway.PermissionGateway;
import org.ylzl.eden.spring.framework.error.ClientAssert;

/**
 * 删除权限指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class PermissionRemoveCmdExe {

	private final PermissionGateway permissionGateway;

	/**
	 * 执行删除权限指令
	 *
	 * @param cmd 删除权限指令
	 * @return 响应结果
	 */
	public Response execute(PermissionRemoveCmd cmd) {
		ClientAssert.isTrue(permissionGateway.findById(cmd.getId()).isPresent(), "PERM-002", "权限不存在");
		ClientAssert.isTrue(!permissionGateway.isUsedByRole(cmd.getId()), "PERM-003", "权限正在被角色使用，无法删除");
		ClientAssert.isTrue(permissionGateway.findByParentId(cmd.getId()).isEmpty(), "PERM-004", "存在子权限，无法删除");

		permissionGateway.deleteById(cmd.getId());
		return Response.buildSuccess();
	}
}
