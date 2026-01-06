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
import org.ylzl.eden.demo.client.menu.dto.command.MenuAddCmd;
import org.ylzl.eden.demo.domain.menu.entity.Menu;
import org.ylzl.eden.demo.domain.menu.gateway.MenuGateway;
import org.ylzl.eden.demo.domain.menu.valueobject.MenuPath;
import org.ylzl.eden.spring.framework.error.ClientAssert;

/**
 * 新增菜单指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class MenuAddCmdExe {

	private final MenuGateway menuGateway;

	/**
	 * 执行新增菜单指令
	 *
	 * @param cmd 新增菜单指令
	 * @return 响应结果
	 */
	public Response execute(MenuAddCmd cmd) {
		MenuPath path = MenuPath.of(cmd.getPath());
		ClientAssert.isTrue(!menuGateway.existsByPath(path), "MENU-002", "菜单路径已存在");

		Menu menu = Menu.create(cmd.getName(), path, cmd.getParentId());
		menu.updateInfo(null, cmd.getIcon(), cmd.getSort(), cmd.getComponent());
		menuGateway.save(menu);
		return Response.buildSuccess();
	}
}
