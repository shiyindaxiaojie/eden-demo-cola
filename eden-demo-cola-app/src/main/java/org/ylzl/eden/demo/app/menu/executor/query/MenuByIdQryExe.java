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
import org.ylzl.eden.cola.dto.SingleResponse;
import org.ylzl.eden.demo.app.menu.assembler.MenuAssembler;
import org.ylzl.eden.demo.client.menu.dto.MenuDTO;
import org.ylzl.eden.demo.client.menu.dto.query.MenuByIdQry;
import org.ylzl.eden.demo.domain.menu.entity.Menu;
import org.ylzl.eden.demo.domain.menu.gateway.MenuGateway;
import org.ylzl.eden.spring.framework.error.ClientAssert;

/**
 * 根据ID查询菜单执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class MenuByIdQryExe {

	private final MenuGateway menuGateway;
	private final MenuAssembler menuAssembler;

	/**
	 * 执行根据ID查询菜单
	 *
	 * @param qry 查询条件
	 * @return 菜单信息
	 */
	public SingleResponse<MenuDTO> execute(MenuByIdQry qry) {
		Menu menu = menuGateway.findById(qry.getId())
			.orElse(null);
		ClientAssert.notNull(menu, "MENU-003", "菜单不存在");
		return SingleResponse.of(menuAssembler.toDTO(menu));
	}
}
