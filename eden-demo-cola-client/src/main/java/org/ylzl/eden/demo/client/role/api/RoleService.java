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

package org.ylzl.eden.demo.client.role.api;

import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO;
import org.ylzl.eden.demo.client.permission.dto.PermissionDTO;
import org.ylzl.eden.demo.client.role.dto.RoleDTO;
import org.ylzl.eden.demo.client.role.dto.command.*;
import org.ylzl.eden.demo.client.role.dto.query.*;
import org.ylzl.eden.cola.dto.MultiResponse;
import org.ylzl.eden.cola.dto.PageResponse;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.cola.dto.SingleResponse;

/**
 * 角色服务接口
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
public interface RoleService {

	/**
	 * 创建角色
	 *
	 * @param cmd 创建角色指令
	 * @return 响应结果
	 */
	Response createRole(RoleAddCmd cmd);

	/**
	 * 修改角色
	 *
	 * @param cmd 修改角色指令
	 * @return 响应结果
	 */
	Response modifyRole(RoleModifyCmd cmd);

	/**
	 * 删除角色
	 *
	 * @param cmd 删除角色指令
	 * @return 响应结果
	 */
	Response removeRole(RoleRemoveCmd cmd);

	/**
	 * 启用角色
	 *
	 * @param cmd 角色状态指令
	 * @return 响应结果
	 */
	Response enableRole(RoleStatusCmd cmd);

	/**
	 * 禁用角色
	 *
	 * @param cmd 角色状态指令
	 * @return 响应结果
	 */
	Response disableRole(RoleStatusCmd cmd);

	/**
	 * 根据主键获取角色信息
	 *
	 * @param qry 查询条件
	 * @return 角色信息
	 */
	SingleResponse<RoleDTO> getRoleById(RoleByIdQry qry);

	/**
	 * 分页查询角色列表
	 *
	 * @param qry 分页查询条件
	 * @return 角色分页列表
	 */
	PageResponse<RoleDTO> listRoleByPage(RoleListByPageQry qry);

	/**
	 * 为角色分配权限
	 *
	 * @param cmd 分配权限指令
	 * @return 响应结果
	 */
	Response assignPermissions(RoleAssignPermissionsCmd cmd);

	/**
	 * 为角色分配菜单
	 *
	 * @param cmd 分配菜单指令
	 * @return 响应结果
	 */
	Response assignMenus(RoleAssignMenusCmd cmd);

	/**
	 * 获取角色权限列表
	 *
	 * @param qry 查询条件
	 * @return 权限列表
	 */
	MultiResponse<PermissionDTO> getRolePermissions(RolePermissionsQry qry);

	/**
	 * 获取角色菜单树
	 *
	 * @param qry 查询条件
	 * @return 菜单树列表
	 */
	MultiResponse<MenuTreeDTO> getRoleMenus(RoleMenusQry qry);
}
