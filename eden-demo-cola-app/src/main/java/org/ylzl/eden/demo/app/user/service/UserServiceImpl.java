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

package org.ylzl.eden.demo.app.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ylzl.eden.cola.dto.MultiResponse;
import org.ylzl.eden.cola.dto.PageResponse;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.cola.dto.SingleResponse;
import org.ylzl.eden.demo.app.user.executor.command.UserAddCmdExe;
import org.ylzl.eden.demo.app.user.executor.command.UserAssignRolesCmdExe;
import org.ylzl.eden.demo.app.user.executor.command.UserModifyCmdExe;
import org.ylzl.eden.demo.app.user.executor.command.UserRemoveCmdExe;
import org.ylzl.eden.demo.app.user.executor.query.*;
import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO;
import org.ylzl.eden.demo.client.role.dto.RoleDTO;
import org.ylzl.eden.demo.client.user.api.UserService;
import org.ylzl.eden.demo.client.user.dto.UserDTO;
import org.ylzl.eden.demo.client.user.dto.command.UserAddCmd;
import org.ylzl.eden.demo.client.user.dto.command.UserAssignRolesCmd;
import org.ylzl.eden.demo.client.user.dto.command.UserModifyCmd;
import org.ylzl.eden.demo.client.user.dto.command.UserRemoveCmd;
import org.ylzl.eden.demo.client.user.dto.query.*;

/**
 * 用户应用服务实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

	private final UserAddCmdExe userAddCmdExe;
	private final UserModifyCmdExe userModifyCmdExe;
	private final UserRemoveCmdExe userRemoveCmdExe;
	private final UserAssignRolesCmdExe userAssignRolesCmdExe;
	private final UserByIdQryExe userByIdQryExe;
	private final UserListByPageQryExe userListByPageQryExe;
	private final UserRolesQryExe userRolesQryExe;
	private final UserMenusQryExe userMenusQryExe;
	private final UserPermissionsQryExe userPermissionsQryExe;

	/**
	 * 创建用户
	 *
	 * @param cmd 创建用户指令
	 * @return 响应结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response createUser(UserAddCmd cmd) {
		return userAddCmdExe.execute(cmd);
	}

	/**
	 * 修改用户
	 *
	 * @param cmd 修改用户指令
	 * @return 响应结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response modifyUser(UserModifyCmd cmd) {
		return userModifyCmdExe.execute(cmd);
	}

	/**
	 * 删除用户
	 *
	 * @param cmd 删除用户指令
	 * @return 响应结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response removeUser(UserRemoveCmd cmd) {
		return userRemoveCmdExe.execute(cmd);
	}

	/**
	 * 获取用户信息
	 *
	 * @param query 查询条件
	 * @return 用户信息
	 */
	@Override
	public SingleResponse<UserDTO> getUserById(UserByIdQry query) {
		return userByIdQryExe.execute(query);
	}

	/**
	 * 分页查询用户列表
	 *
	 * @param query 查询条件
	 * @return 分页结果
	 */
	@Override
	public PageResponse<UserDTO> listUserByPage(UserListByPageQry query) {
		return userListByPageQryExe.execute(query);
	}

	/**
	 * 分配角色
	 *
	 * @param cmd 分配角色指令
	 * @return 响应结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response assignRoles(UserAssignRolesCmd cmd) {
		return userAssignRolesCmdExe.execute(cmd);
	}

	/**
	 * 获取用户角色
	 *
	 * @param qry 查询条件
	 * @return 角色列表
	 */
	@Override
	public MultiResponse<RoleDTO> getUserRoles(UserRolesQry qry) {
		return userRolesQryExe.execute(qry);
	}

	/**
	 * 获取用户菜单
	 *
	 * @param qry 查询条件
	 * @return 菜单树
	 */
	@Override
	public MultiResponse<MenuTreeDTO> getUserMenus(UserMenusQry qry) {
		return userMenusQryExe.execute(qry);
	}

	/**
	 * 获取用户权限
	 *
	 * @param qry 查询条件
	 * @return 权限编码列表
	 */
	@Override
	public MultiResponse<String> getUserPermissions(UserPermissionsQry qry) {
		return userPermissionsQryExe.execute(qry);
	}
}
