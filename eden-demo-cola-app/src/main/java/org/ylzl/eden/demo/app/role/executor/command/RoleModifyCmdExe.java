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
import org.ylzl.eden.demo.client.role.dto.command.RoleModifyCmd;
import org.ylzl.eden.demo.domain.role.entity.Role;
import org.ylzl.eden.demo.domain.role.gateway.RoleGateway;
import org.ylzl.eden.demo.domain.role.valueobject.RoleName;
import org.ylzl.eden.spring.framework.error.ClientAssert;

/**
 * 修改角色指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class RoleModifyCmdExe {

	private final RoleGateway roleGateway;

	public Response execute(RoleModifyCmd cmd) {
		Role role = roleGateway.findById(cmd.getId())
			.orElseThrow(() -> new IllegalArgumentException("角色不存在"));

		RoleName name = cmd.getName() != null ? new RoleName(cmd.getName()) : null;
		role.updateInfo(name, cmd.getDescription(), cmd.getSort());
		roleGateway.save(role);
		return Response.buildSuccess();
	}
}
