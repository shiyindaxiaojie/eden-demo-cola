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

package org.ylzl.eden.demo.adapter.permission.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.ylzl.eden.cola.dto.MultiResponse;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.cola.dto.SingleResponse;
import org.ylzl.eden.demo.adapter.constant.API;
import org.ylzl.eden.demo.client.permission.api.PermissionService;
import org.ylzl.eden.demo.client.permission.dto.PermissionDTO;
import org.ylzl.eden.demo.client.permission.dto.PermissionTreeDTO;
import org.ylzl.eden.demo.client.permission.dto.command.*;
import org.ylzl.eden.demo.client.permission.dto.query.*;

import javax.validation.Valid;

/**
 * 权限控制器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
@RequestMapping(API.WEB_API_PATH + "/permissions")
@RestController
public class PermissionController {

	private final PermissionService permissionService;

	/**
	 * 创建权限
	 *
	 * @param cmd 创建权限指令
	 * @return 响应结果
	 */
	@PostMapping
	public Response createPermission(@Valid @RequestBody PermissionAddCmd cmd) {
		return permissionService.createPermission(cmd);
	}

	/**
	 * 修改权限
	 *
	 * @param id  权限主键
	 * @param cmd 修改权限指令
	 * @return 响应结果
	 */
	@PutMapping("/{id}")
	public Response modifyPermission(@PathVariable Long id, @Valid @RequestBody PermissionModifyCmd cmd) {
		cmd.setId(id);
		return permissionService.modifyPermission(cmd);
	}

	/**
	 * 删除权限
	 *
	 * @param id 权限主键
	 * @return 响应结果
	 */
	@DeleteMapping("/{id}")
	public Response removePermission(@PathVariable Long id) {
		return permissionService.removePermission(PermissionRemoveCmd.builder().id(id).build());
	}

	/**
	 * 根据主键获取权限信息
	 *
	 * @param id 权限主键
	 * @return 权限信息
	 */
	@GetMapping("/{id}")
	public SingleResponse<PermissionDTO> getPermissionById(@PathVariable Long id) {
		return permissionService.getPermissionById(PermissionByIdQry.builder().id(id).build());
	}

	/**
	 * 获取权限树列表
	 *
	 * @param qry 查询条件
	 * @return 权限树列表
	 */
	@GetMapping("/tree")
	public MultiResponse<PermissionTreeDTO> listPermissionTree(@ModelAttribute PermissionTreeQry qry) {
		return permissionService.listPermissionTree(qry);
	}
}
