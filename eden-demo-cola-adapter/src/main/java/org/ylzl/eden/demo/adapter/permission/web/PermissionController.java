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
import org.ylzl.eden.cola.dto.ListResponse;
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
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@RequestMapping(API.WEB_API_PATH + "/permissions")
@RestController
public class PermissionController {

	private final PermissionService permissionService;

	@PostMapping
	public Response createPermission(@Valid @RequestBody PermissionAddCmd cmd) {
		return permissionService.createPermission(cmd);
	}

	@PutMapping("/{id}")
	public Response modifyPermission(@PathVariable Long id, @Valid @RequestBody PermissionModifyCmd cmd) {
		cmd.setId(id);
		return permissionService.modifyPermission(cmd);
	}

	@DeleteMapping("/{id}")
	public Response removePermission(@PathVariable Long id) {
		return permissionService.removePermission(PermissionRemoveCmd.builder().id(id).build());
	}

	@GetMapping("/{id}")
	public SingleResponse<PermissionDTO> getPermissionById(@PathVariable Long id) {
		return permissionService.getPermissionById(PermissionByIdQry.builder().id(id).build());
	}

	@GetMapping("/tree")
	public ListResponse<PermissionTreeDTO> listPermissionTree(@ModelAttribute PermissionTreeQry qry) {
		return permissionService.listPermissionTree(qry);
	}
}
