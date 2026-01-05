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

package org.ylzl.eden.demo.app.menu.executor.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.demo.client.menu.dto.command.MenuRemoveCmd;
import org.ylzl.eden.demo.domain.menu.gateway.MenuGateway;
import org.ylzl.eden.spring.framework.error.ClientAssert;

/**
 * 删除菜单指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class MenuRemoveCmdExe {

	private final MenuGateway menuGateway;

	public Response execute(MenuRemoveCmd cmd) {
		ClientAssert.isTrue(menuGateway.findById(cmd.getId()).isPresent(), "MENU-003", "菜单不存在");
		ClientAssert.isFalse(menuGateway.hasChildren(cmd.getId()), "MENU-004", "存在子菜单，无法删除");
		ClientAssert.isFalse(menuGateway.isUsedByRole(cmd.getId()), "MENU-005", "菜单正在被角色使用，无法删除");

		menuGateway.deleteById(cmd.getId());
		return Response.buildSuccess();
	}
}
