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

package org.ylzl.eden.demo.adapter.menu.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.ylzl.eden.cola.dto.MultiResponse;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.cola.dto.SingleResponse;
import org.ylzl.eden.demo.adapter.constant.API;
import org.ylzl.eden.demo.client.menu.api.MenuService;
import org.ylzl.eden.demo.client.menu.dto.MenuDTO;
import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO;
import org.ylzl.eden.demo.client.menu.dto.command.*;
import org.ylzl.eden.demo.client.menu.dto.query.*;

import javax.validation.Valid;

/**
 * 菜单控制器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
@RequestMapping(API.WEB_API_PATH + "/menus")
@RestController
public class MenuController {

	private final MenuService menuService;

	/**
	 * 创建菜单
	 *
	 * @param cmd 创建菜单指令
	 * @return 响应结果
	 */
	@PostMapping
	public Response createMenu(@Valid @RequestBody MenuAddCmd cmd) {
		return menuService.createMenu(cmd);
	}

	/**
	 * 修改菜单
	 *
	 * @param id  菜单主键
	 * @param cmd 修改菜单指令
	 * @return 响应结果
	 */
	@PutMapping("/{id}")
	public Response modifyMenu(@PathVariable Long id, @Valid @RequestBody MenuModifyCmd cmd) {
		cmd.setId(id);
		return menuService.modifyMenu(cmd);
	}

	/**
	 * 删除菜单
	 *
	 * @param id 菜单主键
	 * @return 响应结果
	 */
	@DeleteMapping("/{id}")
	public Response removeMenu(@PathVariable Long id) {
		return menuService.removeMenu(MenuRemoveCmd.builder().id(id).build());
	}

	/**
	 * 根据主键获取菜单信息
	 *
	 * @param id 菜单主键
	 * @return 菜单信息
	 */
	@GetMapping("/{id}")
	public SingleResponse<MenuDTO> getMenuById(@PathVariable Long id) {
		return menuService.getMenuById(MenuByIdQry.builder().id(id).build());
	}

	/**
	 * 获取菜单树列表
	 *
	 * @param qry 查询条件
	 * @return 菜单树列表
	 */
	@GetMapping("/tree")
	public MultiResponse<MenuTreeDTO> listMenuTree(@ModelAttribute MenuTreeQry qry) {
		return menuService.listMenuTree(qry);
	}
}
