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
import org.ylzl.eden.cola.dto.ListResponse;
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
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@RequestMapping(API.WEB_API_PATH + "/menus")
@RestController
public class MenuController {

	private final MenuService menuService;

	@PostMapping
	public Response createMenu(@Valid @RequestBody MenuAddCmd cmd) {
		return menuService.createMenu(cmd);
	}

	@PutMapping("/{id}")
	public Response modifyMenu(@PathVariable Long id, @Valid @RequestBody MenuModifyCmd cmd) {
		cmd.setId(id);
		return menuService.modifyMenu(cmd);
	}

	@DeleteMapping("/{id}")
	public Response removeMenu(@PathVariable Long id) {
		return menuService.removeMenu(MenuRemoveCmd.builder().id(id).build());
	}

	@GetMapping("/{id}")
	public SingleResponse<MenuDTO> getMenuById(@PathVariable Long id) {
		return menuService.getMenuById(MenuByIdQry.builder().id(id).build());
	}

	@GetMapping("/tree")
	public ListResponse<MenuTreeDTO> listMenuTree(@ModelAttribute MenuTreeQry qry) {
		return menuService.listMenuTree(qry);
	}
}
