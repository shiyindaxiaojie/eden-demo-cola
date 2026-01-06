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

package org.ylzl.eden.demo.app.role.executor.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.demo.client.role.dto.command.RoleAssignMenusCmd;
import org.ylzl.eden.demo.domain.role.gateway.RoleGateway;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.util.Collections;

/**
 * 角色分配菜单指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class RoleAssignMenusCmdExe {

	private final RoleGateway roleGateway;

	/**
	 * 执行角色分配菜单指令
	 *
	 * @param cmd 分配菜单指令
	 * @return 响应结果
	 */
	public Response execute(RoleAssignMenusCmd cmd) {
		ClientAssert.isTrue(roleGateway.findById(cmd.getRoleId()).isPresent(), "ROLE-002", "角色不存在");

		roleGateway.saveRoleMenus(cmd.getRoleId(),
			cmd.getMenuIds() != null ? cmd.getMenuIds() : Collections.emptyList());
		return Response.buildSuccess();
	}
}
