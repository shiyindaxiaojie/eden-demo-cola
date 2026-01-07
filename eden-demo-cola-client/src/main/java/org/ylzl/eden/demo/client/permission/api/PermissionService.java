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

package org.ylzl.eden.demo.client.permission.api;

import org.ylzl.eden.demo.client.permission.dto.PermissionDTO;
import org.ylzl.eden.demo.client.permission.dto.PermissionTreeDTO;
import org.ylzl.eden.demo.client.permission.dto.command.*;
import org.ylzl.eden.demo.client.permission.dto.query.*;
import org.ylzl.eden.cola.dto.MultiResponse;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.cola.dto.SingleResponse;

/**
 * 权限服务接口
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
public interface PermissionService {

	/**
	 * 创建权限
	 *
	 * @param cmd 创建权限指令
	 * @return 响应结果
	 */
	Response createPermission(PermissionAddCmd cmd);

	/**
	 * 修改权限
	 *
	 * @param cmd 修改权限指令
	 * @return 响应结果
	 */
	Response modifyPermission(PermissionModifyCmd cmd);

	/**
	 * 删除权限
	 *
	 * @param cmd 删除权限指令
	 * @return 响应结果
	 */
	Response removePermission(PermissionRemoveCmd cmd);

	/**
	 * 根据主键获取权限信息
	 *
	 * @param qry 查询条件
	 * @return 权限信息
	 */
	SingleResponse<PermissionDTO> getPermissionById(PermissionByIdQry qry);

	/**
	 * 获取权限树列表
	 *
	 * @param qry 查询条件
	 * @return 权限树列表
	 */
	MultiResponse<PermissionTreeDTO> listPermissionTree(PermissionTreeQry qry);
}
