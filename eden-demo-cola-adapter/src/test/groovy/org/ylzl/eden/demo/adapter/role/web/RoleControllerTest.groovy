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

package org.ylzl.eden.demo.adapter.role.web

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO
import org.ylzl.eden.demo.client.permission.dto.PermissionDTO
import org.ylzl.eden.demo.client.role.api.RoleService
import org.ylzl.eden.demo.client.role.dto.RoleDTO
import org.ylzl.eden.demo.client.role.dto.command.*
import org.ylzl.eden.demo.client.role.dto.query.*
import org.ylzl.eden.cola.dto.MultiResponse
import org.ylzl.eden.cola.dto.PageResponse
import org.ylzl.eden.cola.dto.Response
import org.ylzl.eden.cola.dto.SingleResponse
import spock.lang.Specification
import spock.lang.Unroll

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class RoleControllerTest extends Specification {
	@Mock
	RoleService roleService
	@Mock
	Logger log
	@InjectMocks
	RoleController roleController

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	@Unroll
	def "create Role where cmd=#cmd then expect: #expectedResult"() {
		given:
		when(roleService.createRole(any())).thenReturn(Response.buildSuccess())

		expect:
		roleController.createRole(cmd) == expectedResult

		where:
		cmd                                              || expectedResult
		new RoleAddCmd("ADMIN", "管理员", "系统管理员", 1) || Response.buildSuccess()
	}

	@Unroll
	def "modify Role where id=#id and cmd=#cmd then expect: #expectedResult"() {
		given:
		when(roleService.modifyRole(any())).thenReturn(Response.buildSuccess())

		expect:
		roleController.modifyRole(id, cmd) == expectedResult

		where:
		id | cmd                                        || expectedResult
		1L | new RoleModifyCmd(1L, "管理员", "描述", 1) || Response.buildSuccess()
	}

	@Unroll
	def "remove Role where id=#id then expect: #expectedResult"() {
		given:
		when(roleService.removeRole(any())).thenReturn(Response.buildSuccess())

		expect:
		roleController.removeRole(id) == expectedResult

		where:
		id || expectedResult
		1L || Response.buildSuccess()
	}

	@Unroll
	def "enable Role where id=#id then expect: #expectedResult"() {
		given:
		when(roleService.enableRole(any())).thenReturn(Response.buildSuccess())

		expect:
		roleController.enableRole(id) == expectedResult

		where:
		id || expectedResult
		1L || Response.buildSuccess()
	}

	@Unroll
	def "disable Role where id=#id then expect: #expectedResult"() {
		given:
		when(roleService.disableRole(any())).thenReturn(Response.buildSuccess())

		expect:
		roleController.disableRole(id) == expectedResult

		where:
		id || expectedResult
		1L || Response.buildSuccess()
	}

	@Unroll
	def "get Role By Id where id=#id then expect: #expectedResult"() {
		given:
		when(roleService.getRoleById(any())).thenReturn(SingleResponse.of(new RoleDTO(1L, "ADMIN", "管理员", "描述", 1, 1, null)))

		expect:
		roleController.getRoleById(id) == expectedResult

		where:
		id || expectedResult
		1L || SingleResponse.of(new RoleDTO(1L, "ADMIN", "管理员", "描述", 1, 1, null))
	}

	@Unroll
	def "list Role By Page where query=#query then expect: #expectedResult"() {
		given:
		when(roleService.listRoleByPage(any())).thenReturn(PageResponse.of([new RoleDTO(1L, "ADMIN", "管理员", "描述", 1, 1, null)], 1, 1, 1))

		expect:
		roleController.listRoleByPage(query) == expectedResult

		where:
		query                                      || expectedResult
		new RoleListByPageQry("ADMIN", "管理员", 1) || PageResponse.of([new RoleDTO(1L, "ADMIN", "管理员", "描述", 1, 1, null)], 1, 1, 1)
	}

	@Unroll
	def "assign Permissions where id=#id and cmd=#cmd then expect: #expectedResult"() {
		given:
		when(roleService.assignPermissions(any())).thenReturn(Response.buildSuccess())

		expect:
		roleController.assignPermissions(id, cmd) == expectedResult

		where:
		id | cmd                                              || expectedResult
		1L | new RoleAssignPermissionsCmd(1L, [1L, 2L, 3L]) || Response.buildSuccess()
	}

	@Unroll
	def "assign Menus where id=#id and cmd=#cmd then expect: #expectedResult"() {
		given:
		when(roleService.assignMenus(any())).thenReturn(Response.buildSuccess())

		expect:
		roleController.assignMenus(id, cmd) == expectedResult

		where:
		id | cmd                                      || expectedResult
		1L | new RoleAssignMenusCmd(1L, [1L, 2L, 3L]) || Response.buildSuccess()
	}

	@Unroll
	def "get Role Permissions where id=#id then expect: #expectedResult"() {
		given:
		when(roleService.getRolePermissions(any())).thenReturn(MultiResponse.of([new PermissionDTO(1L, "user:read", "用户查看", 3, 0L, "描述", 1)]))

		expect:
		roleController.getRolePermissions(id) == expectedResult

		where:
		id || expectedResult
		1L || MultiResponse.of([new PermissionDTO(1L, "user:read", "用户查看", 3, 0L, "描述", 1)])
	}

	@Unroll
	def "get Role Menus where id=#id then expect: #expectedResult"() {
		given:
		when(roleService.getRoleMenus(any())).thenReturn(MultiResponse.of([new MenuTreeDTO(1L, "系统管理", "/system", "setting", 0L, 1, 1, "Layout", [])]))

		expect:
		roleController.getRoleMenus(id) == expectedResult

		where:
		id || expectedResult
		1L || MultiResponse.of([new MenuTreeDTO(1L, "系统管理", "/system", "setting", 0L, 1, 1, "Layout", [])])
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
