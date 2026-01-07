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

package org.ylzl.eden.demo.app.role.service

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.app.role.executor.command.*
import org.ylzl.eden.demo.app.role.executor.query.*
import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO
import org.ylzl.eden.demo.client.permission.dto.PermissionDTO
import org.ylzl.eden.demo.client.role.dto.RoleDTO
import org.ylzl.eden.demo.client.role.dto.command.*
import org.ylzl.eden.demo.client.role.dto.query.*
import org.ylzl.eden.cola.dto.MultiResponse
import org.ylzl.eden.cola.dto.PageResponse
import org.ylzl.eden.cola.dto.Response
import org.ylzl.eden.cola.dto.SingleResponse
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class RoleServiceImplTest extends Specification {
	@Mock
	RoleAddCmdExe roleAddCmdExe
	@Mock
	RoleModifyCmdExe roleModifyCmdExe
	@Mock
	RoleRemoveCmdExe roleRemoveCmdExe
	@Mock
	RoleEnableCmdExe roleEnableCmdExe
	@Mock
	RoleDisableCmdExe roleDisableCmdExe
	@Mock
	RoleAssignPermissionsCmdExe roleAssignPermissionsCmdExe
	@Mock
	RoleAssignMenusCmdExe roleAssignMenusCmdExe
	@Mock
	RoleByIdQryExe roleByIdQryExe
	@Mock
	RoleListByPageQryExe roleListByPageQryExe
	@Mock
	RolePermissionsQryExe rolePermissionsQryExe
	@Mock
	RoleMenusQryExe roleMenusQryExe
	@Mock
	Logger log
	@InjectMocks
	RoleServiceImpl roleServiceImpl

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	def "test create Role"() {
		given:
		when(roleAddCmdExe.execute(any())).thenReturn(Response.buildSuccess())

		when:
		Response result = roleServiceImpl.createRole(new RoleAddCmd("ADMIN", "管理员", "描述", 1))

		then:
		result == Response.buildSuccess()
	}

	def "test modify Role"() {
		given:
		when(roleModifyCmdExe.execute(any())).thenReturn(Response.buildSuccess())

		when:
		Response result = roleServiceImpl.modifyRole(new RoleModifyCmd(1L, "管理员", "描述", 1))

		then:
		result == Response.buildSuccess()
	}

	def "test remove Role"() {
		given:
		when(roleRemoveCmdExe.execute(any())).thenReturn(Response.buildSuccess())

		when:
		Response result = roleServiceImpl.removeRole(new RoleRemoveCmd(1L))

		then:
		result == Response.buildSuccess()
	}

	def "test enable Role"() {
		given:
		when(roleEnableCmdExe.execute(any())).thenReturn(Response.buildSuccess())

		when:
		Response result = roleServiceImpl.enableRole(new RoleStatusCmd(1L))

		then:
		result == Response.buildSuccess()
	}

	def "test disable Role"() {
		given:
		when(roleDisableCmdExe.execute(any())).thenReturn(Response.buildSuccess())

		when:
		Response result = roleServiceImpl.disableRole(new RoleStatusCmd(1L))

		then:
		result == Response.buildSuccess()
	}

	def "test get Role By Id"() {
		given:
		when(roleByIdQryExe.execute(any())).thenReturn(SingleResponse.of(new RoleDTO(1L, "ADMIN", "管理员", "描述", 1, 1, null)))

		when:
		SingleResponse<RoleDTO> result = roleServiceImpl.getRoleById(new RoleByIdQry(1L))

		then:
		result == SingleResponse.of(new RoleDTO(1L, "ADMIN", "管理员", "描述", 1, 1, null))
	}

	def "test list Role By Page"() {
		given:
		when(roleListByPageQryExe.execute(any())).thenReturn(PageResponse.of([new RoleDTO(1L, "ADMIN", "管理员", "描述", 1, 1, null)], 1, 1, 1))

		when:
		PageResponse<RoleDTO> result = roleServiceImpl.listRoleByPage(new RoleListByPageQry("ADMIN", "管理员", 1))

		then:
		result == PageResponse.of([new RoleDTO(1L, "ADMIN", "管理员", "描述", 1, 1, null)], 1, 1, 1)
	}

	def "test assign Permissions"() {
		given:
		when(roleAssignPermissionsCmdExe.execute(any())).thenReturn(Response.buildSuccess())

		when:
		Response result = roleServiceImpl.assignPermissions(new RoleAssignPermissionsCmd(1L, [1L, 2L]))

		then:
		result == Response.buildSuccess()
	}

	def "test assign Menus"() {
		given:
		when(roleAssignMenusCmdExe.execute(any())).thenReturn(Response.buildSuccess())

		when:
		Response result = roleServiceImpl.assignMenus(new RoleAssignMenusCmd(1L, [1L, 2L]))

		then:
		result == Response.buildSuccess()
	}

	def "test get Role Permissions"() {
		given:
		when(rolePermissionsQryExe.execute(any())).thenReturn(MultiResponse.of([new PermissionDTO(1L, "user:read", "用户查看", 3, 0L, "描述", 1)]))

		when:
		MultiResponse<PermissionDTO> result = roleServiceImpl.getRolePermissions(new RolePermissionsQry(1L))

		then:
		result == MultiResponse.of([new PermissionDTO(1L, "user:read", "用户查看", 3, 0L, "描述", 1)])
	}

	def "test get Role Menus"() {
		given:
		when(roleMenusQryExe.execute(any())).thenReturn(MultiResponse.of([new MenuTreeDTO(1L, "系统管理", "/system", "setting", 0L, 1, 1, "Layout", [])]))

		when:
		MultiResponse<MenuTreeDTO> result = roleServiceImpl.getRoleMenus(new RoleMenusQry(1L))

		then:
		result == MultiResponse.of([new MenuTreeDTO(1L, "系统管理", "/system", "setting", 0L, 1, 1, "Layout", [])])
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
