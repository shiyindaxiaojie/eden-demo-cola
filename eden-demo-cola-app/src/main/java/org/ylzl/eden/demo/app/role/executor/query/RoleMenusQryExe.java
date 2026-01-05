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

package org.ylzl.eden.demo.app.role.executor.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.cola.dto.ListResponse;
import org.ylzl.eden.demo.app.menu.assembler.MenuAssembler;
import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO;
import org.ylzl.eden.demo.client.role.dto.query.RoleMenusQry;
import org.ylzl.eden.demo.domain.menu.entity.Menu;
import org.ylzl.eden.demo.domain.menu.gateway.MenuGateway;
import org.ylzl.eden.demo.domain.role.gateway.RoleGateway;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.util.Collections;
import java.util.List;

/**
 * 查询角色菜单执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class RoleMenusQryExe {

	private final RoleGateway roleGateway;
	private final MenuGateway menuGateway;
	private final MenuAssembler menuAssembler;

	public ListResponse<MenuTreeDTO> execute(RoleMenusQry qry) {
		ClientAssert.isTrue(roleGateway.findById(qry.getRoleId()).isPresent(), "ROLE-002", "角色不存在");

		List<Long> menuIds = roleGateway.findMenuIdsByRoleId(qry.getRoleId());
		if (menuIds.isEmpty()) {
			return ListResponse.of(Collections.emptyList());
		}

		List<Menu> menus = menuGateway.findByIds(menuIds);
		return ListResponse.of(menuAssembler.buildTree(menus));
	}
}
