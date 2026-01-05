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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.demo.client.menu.dto.command.MenuModifyCmd;
import org.ylzl.eden.demo.domain.menu.entity.Menu;
import org.ylzl.eden.demo.domain.menu.gateway.MenuGateway;
import org.ylzl.eden.demo.domain.menu.valueobject.MenuPath;
import org.ylzl.eden.spring.framework.error.ClientAssert;

/**
 * 修改菜单指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class MenuModifyCmdExe {

	private final MenuGateway menuGateway;

	public Response execute(MenuModifyCmd cmd) {
		Menu menu = menuGateway.findById(cmd.getId())
			.orElseThrow(() -> new IllegalArgumentException("菜单不存在"));

		if (StringUtils.isNotBlank(cmd.getPath())) {
			MenuPath newPath = new MenuPath(cmd.getPath());
			ClientAssert.isFalse(menuGateway.existsByPathExcludeId(newPath, cmd.getId()),
				"MENU-002", "菜单路径已存在");
			menu.updatePath(newPath);
		}

		if (cmd.getParentId() != null) {
			menu.setParent(cmd.getParentId());
		}

		menu.updateInfo(cmd.getName(), cmd.getIcon(), cmd.getSort(), cmd.getComponent());
		menuGateway.save(menu);
		return Response.buildSuccess();
	}
}
