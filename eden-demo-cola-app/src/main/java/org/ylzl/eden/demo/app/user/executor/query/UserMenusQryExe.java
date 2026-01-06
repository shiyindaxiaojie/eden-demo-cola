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

package org.ylzl.eden.demo.app.user.executor.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.cola.dto.MultiResponse;
import org.ylzl.eden.demo.app.menu.assembler.MenuAssembler;
import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO;
import org.ylzl.eden.demo.client.user.dto.query.UserMenusQry;
import org.ylzl.eden.demo.domain.menu.entity.Menu;
import org.ylzl.eden.demo.domain.menu.gateway.MenuGateway;
import org.ylzl.eden.demo.domain.role.entity.Role;
import org.ylzl.eden.demo.domain.role.gateway.RoleGateway;
import org.ylzl.eden.demo.domain.user.gateway.UserGateway;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 查询用户菜单执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class UserMenusQryExe {

	private final UserGateway userGateway;
	private final RoleGateway roleGateway;
	private final MenuGateway menuGateway;
	private final MenuAssembler menuAssembler;

	/**
	 * 执行查询用户菜单
	 *
	 * @param qry 查询条件
	 * @return 用户菜单树列表
	 */
	public MultiResponse<MenuTreeDTO> execute(UserMenusQry qry) {
		ClientAssert.isTrue(userGateway.findById(qry.getUserId()).isPresent(), "USER-404", "用户不存在");

		// 获取用户的所有角色
		List<Role> roles = userGateway.findRolesByUserId(qry.getUserId());
		if (roles.isEmpty()) {
			return MultiResponse.of(Collections.emptyList());
		}

		// 获取所有角色的菜单ID（去重）
		Set<Long> menuIds = new HashSet<>();
		for (Role role : roles) {
			if (role.isEnabled()) {
				List<Long> roleMenuIds = roleGateway.findMenuIdsByRoleId(role.getId());
				menuIds.addAll(roleMenuIds);
			}
		}

		if (menuIds.isEmpty()) {
			return MultiResponse.of(Collections.emptyList());
		}

		// 获取菜单并构建树
		List<Menu> menus = menuGateway.findByIds(new ArrayList<>(menuIds));
		// 只返回可见的菜单
		menus = menus.stream()
			.filter(Menu::isVisible)
			.collect(Collectors.toList());

		return MultiResponse.of(menuAssembler.buildTree(menus));
	}
}
