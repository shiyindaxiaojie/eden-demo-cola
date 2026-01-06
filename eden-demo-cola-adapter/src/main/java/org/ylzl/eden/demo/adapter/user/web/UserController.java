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

package org.ylzl.eden.demo.adapter.user.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.ylzl.eden.cola.dto.MultiResponse;
import org.ylzl.eden.cola.dto.PageResponse;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.cola.dto.SingleResponse;
import org.ylzl.eden.demo.adapter.constant.API;
import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO;
import org.ylzl.eden.demo.client.role.dto.RoleDTO;
import org.ylzl.eden.demo.client.user.api.UserService;
import org.ylzl.eden.demo.client.user.dto.UserDTO;
import org.ylzl.eden.demo.client.user.dto.command.UserAddCmd;
import org.ylzl.eden.demo.client.user.dto.command.UserAssignRolesCmd;
import org.ylzl.eden.demo.client.user.dto.command.UserModifyCmd;
import org.ylzl.eden.demo.client.user.dto.command.UserRemoveCmd;
import org.ylzl.eden.demo.client.user.dto.query.*;

import javax.validation.Valid;

/**
 * 用户领域控制器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@RequiredArgsConstructor
@Slf4j
@RequestMapping(API.WEB_API_PATH + "/users")
@RestController
public class UserController {

	private final UserService userService;

	/**
	 * 创建用户
	 *
	 * @param cmd 创建用户指令
	 * @return 响应结果
	 */
	@PostMapping
	public Response createUser(@Valid @RequestBody UserAddCmd cmd) {
		return userService.createUser(cmd);
	}

	/**
	 * 修改用户
	 *
	 * @param id  用户主键
	 * @param cmd 修改用户指令
	 * @return 响应结果
	 */
	@PutMapping("/{id}")
	public Response modifyUser(@PathVariable Long id, @Valid @RequestBody UserModifyCmd cmd) {
		cmd.setId(id);
		return userService.modifyUser(cmd);
	}

	/**
	 * 删除用户
	 *
	 * @param id 用户主键
	 * @return 响应结果
	 */
	@DeleteMapping("/{id}")
	public Response removeUserById(@PathVariable Long id) {
		return userService.removeUser(UserRemoveCmd.builder().id(id).build());
	}

	/**
	 * 根据主键获取用户信息
	 *
	 * @param id 用户主键
	 * @return 用户信息
	 */
	@GetMapping("/{id}")
	public SingleResponse<UserDTO> getUserById(@PathVariable Long id) {
		return userService.getUserById(UserByIdQry.builder().id(id).build());
	}

	/**
	 * 分页查询用户列表
	 *
	 * @param query 分页查询条件
	 * @return 用户分页列表
	 */
	@GetMapping
	public PageResponse<UserDTO> listUserByPage(@Valid @ModelAttribute UserListByPageQry query) {
		return userService.listUserByPage(query);
	}

	/**
	 * 为用户分配角色
	 *
	 * @param id  用户主键
	 * @param cmd 分配角色指令
	 * @return 响应结果
	 */
	@PutMapping("/{id}/roles")
	public Response assignRoles(@PathVariable Long id, @RequestBody UserAssignRolesCmd cmd) {
		cmd.setUserId(id);
		return userService.assignRoles(cmd);
	}

	/**
	 * 获取用户角色列表
	 *
	 * @param id 用户主键
	 * @return 角色列表
	 */
	@GetMapping("/{id}/roles")
	public MultiResponse<RoleDTO> getUserRoles(@PathVariable Long id) {
		return userService.getUserRoles(UserRolesQry.builder().userId(id).build());
	}

	/**
	 * 获取用户菜单树
	 *
	 * @param id 用户主键
	 * @return 菜单树列表
	 */
	@GetMapping("/{id}/menus")
	public MultiResponse<MenuTreeDTO> getUserMenus(@PathVariable Long id) {
		return userService.getUserMenus(UserMenusQry.builder().userId(id).build());
	}

	/**
	 * 获取用户权限编码列表
	 *
	 * @param id 用户主键
	 * @return 权限编码列表
	 */
	@GetMapping("/{id}/permissions")
	public MultiResponse<String> getUserPermissions(@PathVariable Long id) {
		return userService.getUserPermissions(UserPermissionsQry.builder().userId(id).build());
	}
}
