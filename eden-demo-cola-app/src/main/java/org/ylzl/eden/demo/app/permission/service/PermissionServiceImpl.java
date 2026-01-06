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

package org.ylzl.eden.demo.app.permission.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ylzl.eden.cola.dto.MultiResponse;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.cola.dto.SingleResponse;
import org.ylzl.eden.demo.app.permission.executor.command.*;
import org.ylzl.eden.demo.app.permission.executor.query.*;
import org.ylzl.eden.demo.client.permission.api.PermissionService;
import org.ylzl.eden.demo.client.permission.dto.PermissionDTO;
import org.ylzl.eden.demo.client.permission.dto.PermissionTreeDTO;
import org.ylzl.eden.demo.client.permission.dto.command.*;
import org.ylzl.eden.demo.client.permission.dto.query.*;

/**
 * 权限应用服务实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	private final PermissionAddCmdExe permissionAddCmdExe;
	private final PermissionModifyCmdExe permissionModifyCmdExe;
	private final PermissionRemoveCmdExe permissionRemoveCmdExe;
	private final PermissionByIdQryExe permissionByIdQryExe;
	private final PermissionTreeQryExe permissionTreeQryExe;

	/**
	 * 创建权限
	 *
	 * @param cmd 创建权限指令
	 * @return 响应结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response createPermission(PermissionAddCmd cmd) {
		return permissionAddCmdExe.execute(cmd);
	}

	/**
	 * 修改权限
	 *
	 * @param cmd 修改权限指令
	 * @return 响应结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response modifyPermission(PermissionModifyCmd cmd) {
		return permissionModifyCmdExe.execute(cmd);
	}

	/**
	 * 删除权限
	 *
	 * @param cmd 删除权限指令
	 * @return 响应结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response removePermission(PermissionRemoveCmd cmd) {
		return permissionRemoveCmdExe.execute(cmd);
	}

	/**
	 * 根据ID获取权限信息
	 *
	 * @param qry 查询条件
	 * @return 权限信息
	 */
	@Override
	public SingleResponse<PermissionDTO> getPermissionById(PermissionByIdQry qry) {
		return permissionByIdQryExe.execute(qry);
	}

	/**
	 * 获取权限树列表
	 *
	 * @param qry 查询条件
	 * @return 权限树列表
	 */
	@Override
	public MultiResponse<PermissionTreeDTO> listPermissionTree(PermissionTreeQry qry) {
		return permissionTreeQryExe.execute(qry);
	}
}
