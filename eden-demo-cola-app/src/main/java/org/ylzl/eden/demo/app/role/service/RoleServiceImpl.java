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

package org.ylzl.eden.demo.app.role.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ylzl.eden.cola.dto.ListResponse;
import org.ylzl.eden.cola.dto.PageResponse;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.cola.dto.SingleResponse;
import org.ylzl.eden.demo.app.role.executor.command.*;
import org.ylzl.eden.demo.app.role.executor.query.*;
import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO;
import org.ylzl.eden.demo.client.permission.dto.PermissionDTO;
import org.ylzl.eden.demo.client.role.api.RoleService;
import org.ylzl.eden.demo.client.role.dto.RoleDTO;
import org.ylzl.eden.demo.client.role.dto.command.*;
import org.ylzl.eden.demo.client.role.dto.query.*;

/**
 * 角色应用服务实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Service("roleService")
public class RoleServiceImpl implements RoleService {

	private final RoleAddCmdExe roleAddCmdExe;
	private final RoleModifyCmdExe roleModifyCmdExe;
	private final RoleRemoveCmdExe roleRemoveCmdExe;
	private final RoleEnableCmdExe roleEnableCmdExe;
	private final RoleDisableCmdExe roleDisableCmdExe;
	private final RoleAssignPermissionsCmdExe roleAssignPermissionsCmdExe;
	private final RoleAssignMenusCmdExe roleAssignMenusCmdExe;
	private final RoleByIdQryExe roleByIdQryExe;
	private final RoleListByPageQryExe roleListByPageQryExe;
	private final RolePermissionsQryExe rolePermissionsQryExe;
	private final RoleMenusQryExe roleMenusQryExe;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response createRole(RoleAddCmd cmd) {
		return roleAddCmdExe.execute(cmd);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response modifyRole(RoleModifyCmd cmd) {
		return roleModifyCmdExe.execute(cmd);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response removeRole(RoleRemoveCmd cmd) {
		return roleRemoveCmdExe.execute(cmd);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response enableRole(RoleStatusCmd cmd) {
		return roleEnableCmdExe.execute(cmd);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response disableRole(RoleStatusCmd cmd) {
		return roleDisableCmdExe.execute(cmd);
	}

	@Override
	public SingleResponse<RoleDTO> getRoleById(RoleByIdQry qry) {
		return roleByIdQryExe.execute(qry);
	}

	@Override
	public PageResponse<RoleDTO> listRoleByPage(RoleListByPageQry qry) {
		return roleListByPageQryExe.execute(qry);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response assignPermissions(RoleAssignPermissionsCmd cmd) {
		return roleAssignPermissionsCmdExe.execute(cmd);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response assignMenus(RoleAssignMenusCmd cmd) {
		return roleAssignMenusCmdExe.execute(cmd);
	}

	@Override
	public ListResponse<PermissionDTO> getRolePermissions(RolePermissionsQry qry) {
		return rolePermissionsQryExe.execute(qry);
	}

	@Override
	public ListResponse<MenuTreeDTO> getRoleMenus(RoleMenusQry qry) {
		return roleMenusQryExe.execute(qry);
	}
}
