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
import org.ylzl.eden.demo.client.role.dto.command.RoleAddCmd;
import org.ylzl.eden.demo.domain.role.entity.Role;
import org.ylzl.eden.demo.domain.role.gateway.RoleGateway;
import org.ylzl.eden.demo.domain.role.valueobject.RoleCode;
import org.ylzl.eden.demo.domain.role.valueobject.RoleName;
import org.ylzl.eden.spring.framework.error.ClientAssert;

/**
 * 新增角色指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class RoleAddCmdExe {

	private final RoleGateway roleGateway;

	/**
	 * 执行新增角色指令
	 *
	 * @param cmd 新增角色指令
	 * @return 响应结果
	 */
	public Response execute(RoleAddCmd cmd) {
		RoleCode code = RoleCode.of(cmd.getCode());
		ClientAssert.isTrue(!roleGateway.existsByCode(code), "ROLE-001", "角色编码已存在");

		Role role = Role.create(code, RoleName.of(cmd.getName()));
		role.updateInfo(null, cmd.getDescription(), cmd.getSort());
		roleGateway.save(role);
		return Response.buildSuccess();
	}
}
