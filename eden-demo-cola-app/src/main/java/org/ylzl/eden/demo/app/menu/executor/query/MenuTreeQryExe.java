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

package org.ylzl.eden.demo.app.menu.executor.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.cola.dto.MultiResponse;
import org.ylzl.eden.demo.app.menu.assembler.MenuAssembler;
import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO;
import org.ylzl.eden.demo.client.menu.dto.query.MenuTreeQry;
import org.ylzl.eden.demo.domain.menu.entity.Menu;
import org.ylzl.eden.demo.domain.menu.gateway.MenuGateway;

import java.util.List;

/**
 * 查询菜单树执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class MenuTreeQryExe {

	private final MenuGateway menuGateway;
	private final MenuAssembler menuAssembler;

	/**
	 * 执行查询菜单树
	 *
	 * @param qry 查询条件
	 * @return 菜单树列表
	 */
	public MultiResponse<MenuTreeDTO> execute(MenuTreeQry qry) {
		List<Menu> menus;
		if (qry.getStatus() != null && qry.getStatus() == 1) {
			menus = menuGateway.findAllVisible();
		} else {
			menus = menuGateway.findAll();
		}
		return MultiResponse.of(menuAssembler.buildTree(menus));
	}
}
