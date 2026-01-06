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

package org.ylzl.eden.demo.app.user.executor.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.demo.client.user.dto.command.UserAssignRolesCmd;
import org.ylzl.eden.demo.domain.user.gateway.UserGateway;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.util.Collections;

/**
 * 用户分配角色指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class UserAssignRolesCmdExe {

	private final UserGateway userGateway;

	/**
	 * 执行用户分配角色指令
	 *
	 * @param cmd 分配角色指令
	 * @return 响应结果
	 */
	public Response execute(UserAssignRolesCmd cmd) {
		ClientAssert.isTrue(userGateway.findById(cmd.getUserId()).isPresent(), "USER-404", "用户不存在");

		userGateway.saveUserRoles(cmd.getUserId(),
			cmd.getRoleIds() != null ? cmd.getRoleIds() : Collections.emptyList());
		return Response.buildSuccess();
	}
}
