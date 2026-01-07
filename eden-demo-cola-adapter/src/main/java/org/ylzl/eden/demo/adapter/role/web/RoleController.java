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

package org.ylzl.eden.demo.adapter.role.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.ylzl.eden.cola.dto.MultiResponse;
import org.ylzl.eden.cola.dto.PageResponse;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.cola.dto.SingleResponse;
import org.ylzl.eden.demo.adapter.constant.API;
import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO;
import org.ylzl.eden.demo.client.permission.dto.PermissionDTO;
import org.ylzl.eden.demo.client.role.api.RoleService;
import org.ylzl.eden.demo.client.role.dto.RoleDTO;
import org.ylzl.eden.demo.client.role.dto.command.*;
import org.ylzl.eden.demo.client.role.dto.query.*;

import javax.validation.Valid;

/**
 * 角色控制器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
@RequestMapping(API.WEB_API_PATH + "/roles")
@RestController
public class RoleController {

	private final RoleService roleService;

	/**
	 * 创建角色
	 *
	 * @param cmd 创建角色指令
	 * @return 响应结果
	 */
	@PostMapping
	public Response createRole(@Valid @RequestBody RoleAddCmd cmd) {
		return roleService.createRole(cmd);
	}

	/**
	 * 修改角色
	 *
	 * @param id  角色主键
	 * @param cmd 修改角色指令
	 * @return 响应结果
	 */
	@PutMapping("/{id}")
	public Response modifyRole(@PathVariable Long id, @Valid @RequestBody RoleModifyCmd cmd) {
		cmd.setId(id);
		return roleService.modifyRole(cmd);
	}

	/**
	 * 删除角色
	 *
	 * @param id 角色主键
	 * @return 响应结果
	 */
	@DeleteMapping("/{id}")
	public Response removeRole(@PathVariable Long id) {
		return roleService.removeRole(RoleRemoveCmd.builder().id(id).build());
	}

	/**
	 * 启用角色
	 *
	 * @param id 角色主键
	 * @return 响应结果
	 */
	@PutMapping("/{id}/enable")
	public Response enableRole(@PathVariable Long id) {
		return roleService.enableRole(RoleStatusCmd.builder().id(id).build());
	}

	/**
	 * 禁用角色
	 *
	 * @param id 角色主键
	 * @return 响应结果
	 */
	@PutMapping("/{id}/disable")
	public Response disableRole(@PathVariable Long id) {
		return roleService.disableRole(RoleStatusCmd.builder().id(id).build());
	}

	/**
	 * 根据主键获取角色信息
	 *
	 * @param id 角色主键
	 * @return 角色信息
	 */
	@GetMapping("/{id}")
	public SingleResponse<RoleDTO> getRoleById(@PathVariable Long id) {
		return roleService.getRoleById(RoleByIdQry.builder().id(id).build());
	}

	/**
	 * 分页查询角色列表
	 *
	 * @param qry 分页查询条件
	 * @return 角色分页列表
	 */
	@GetMapping
	public PageResponse<RoleDTO> listRoleByPage(@Valid @ModelAttribute RoleListByPageQry qry) {
		return roleService.listRoleByPage(qry);
	}

	/**
	 * 为角色分配权限
	 *
	 * @param id  角色主键
	 * @param cmd 分配权限指令
	 * @return 响应结果
	 */
	@PutMapping("/{id}/permissions")
	public Response assignPermissions(@PathVariable Long id, @RequestBody RoleAssignPermissionsCmd cmd) {
		cmd.setRoleId(id);
		return roleService.assignPermissions(cmd);
	}

	/**
	 * 为角色分配菜单
	 *
	 * @param id  角色主键
	 * @param cmd 分配菜单指令
	 * @return 响应结果
	 */
	@PutMapping("/{id}/menus")
	public Response assignMenus(@PathVariable Long id, @RequestBody RoleAssignMenusCmd cmd) {
		cmd.setRoleId(id);
		return roleService.assignMenus(cmd);
	}

	/**
	 * 获取角色权限列表
	 *
	 * @param id 角色主键
	 * @return 权限列表
	 */
	@GetMapping("/{id}/permissions")
	public MultiResponse<PermissionDTO> getRolePermissions(@PathVariable Long id) {
		return roleService.getRolePermissions(RolePermissionsQry.builder().roleId(id).build());
	}

	/**
	 * 获取角色菜单树
	 *
	 * @param id 角色主键
	 * @return 菜单树列表
	 */
	@GetMapping("/{id}/menus")
	public MultiResponse<MenuTreeDTO> getRoleMenus(@PathVariable Long id) {
		return roleService.getRoleMenus(RoleMenusQry.builder().roleId(id).build());
	}
}
