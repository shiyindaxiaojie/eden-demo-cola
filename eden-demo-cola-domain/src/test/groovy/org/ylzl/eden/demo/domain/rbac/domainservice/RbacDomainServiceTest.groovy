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

package org.ylzl.eden.demo.domain.rbac.domainservice

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.ylzl.eden.demo.domain.menu.entity.Menu
import org.ylzl.eden.demo.domain.menu.entity.MenuStatus
import org.ylzl.eden.demo.domain.menu.gateway.MenuGateway
import org.ylzl.eden.demo.domain.menu.valueobject.MenuPath
import org.ylzl.eden.demo.domain.permission.entity.Permission
import org.ylzl.eden.demo.domain.permission.entity.PermissionType
import org.ylzl.eden.demo.domain.permission.gateway.PermissionGateway
import org.ylzl.eden.demo.domain.permission.valueobject.PermissionCode
import org.ylzl.eden.demo.domain.role.entity.Role
import org.ylzl.eden.demo.domain.role.entity.RoleStatus
import org.ylzl.eden.demo.domain.role.gateway.RoleGateway
import org.ylzl.eden.demo.domain.role.valueobject.RoleCode
import org.ylzl.eden.demo.domain.role.valueobject.RoleName
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class RbacDomainServiceTest extends Specification {
	@Mock
	RoleGateway roleGateway
	@Mock
	PermissionGateway permissionGateway
	@Mock
	MenuGateway menuGateway
	@InjectMocks
	RbacDomainService rbacDomainService

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	def "test has Permission"() {
		given:
		def role = Role.reconstitute(1L, new RoleCode("ADMIN"), new RoleName("管理员"), "描述", RoleStatus.ENABLED, 0, null, null)
		def permission = Permission.reconstitute(1L, new PermissionCode("user:read"), "用户查看", PermissionType.BUTTON, 0L, "描述", 0, null, null)
		when(roleGateway.findByIds(any())).thenReturn([role])
		when(roleGateway.findPermissionIdsByRoleId(any())).thenReturn([1L])
		when(permissionGateway.findByIds(any())).thenReturn([permission])

		when:
		def result = rbacDomainService.hasPermission([1L], "user:read")

		then:
		result == true
	}

	def "test has Permission with empty roles"() {
		when:
		def result = rbacDomainService.hasPermission([], "user:read")

		then:
		result == false
	}

	def "test get Permission Codes"() {
		given:
		def role = Role.reconstitute(1L, new RoleCode("ADMIN"), new RoleName("管理员"), "描述", RoleStatus.ENABLED, 0, null, null)
		def permission1 = Permission.reconstitute(1L, new PermissionCode("user:read"), "用户查看", PermissionType.BUTTON, 0L, "描述", 0, null, null)
		def permission2 = Permission.reconstitute(2L, new PermissionCode("user:write"), "用户编辑", PermissionType.BUTTON, 0L, "描述", 0, null, null)
		when(roleGateway.findByIds(any())).thenReturn([role])
		when(roleGateway.findPermissionIdsByRoleId(any())).thenReturn([1L, 2L])
		when(permissionGateway.findByIds(any())).thenReturn([permission1, permission2])

		when:
		def result = rbacDomainService.getPermissionCodes([1L])

		then:
		result.size() == 2
		result.contains("user:read")
		result.contains("user:write")
	}

	def "test get User Menus"() {
		given:
		def role = Role.reconstitute(1L, new RoleCode("ADMIN"), new RoleName("管理员"), "描述", RoleStatus.ENABLED, 0, null, null)
		def menu = Menu.reconstitute(1L, "系统管理", new MenuPath("/system"), "setting", 0L, 0, MenuStatus.VISIBLE, "Layout", null, null)
		when(roleGateway.findByIds(any())).thenReturn([role])
		when(roleGateway.findMenuIdsByRoleId(any())).thenReturn([1L])
		when(menuGateway.findByIds(any())).thenReturn([menu])

		when:
		def result = rbacDomainService.getUserMenus([1L])

		then:
		result.size() == 1
		result[0].name == "系统管理"
	}

	def "test build Menu Tree"() {
		given:
		def parentMenu = Menu.reconstitute(1L, "系统管理", new MenuPath("/system"), "setting", 0L, 0, MenuStatus.VISIBLE, "Layout", null, null)
		def childMenu = Menu.reconstitute(2L, "用户管理", new MenuPath("/user"), "user", 1L, 1, MenuStatus.VISIBLE, "User", null, null)

		when:
		def result = rbacDomainService.buildMenuTree([parentMenu, childMenu])

		then:
		result.size() == 1
		result[0].name == "系统管理"
		result[0].children.size() == 1
		result[0].children[0].name == "用户管理"
	}

	def "test is Role In Use"() {
		given:
		when(roleGateway.isUsedByUser(any())).thenReturn(true)

		when:
		def result = rbacDomainService.isRoleInUse(1L)

		then:
		result == true
	}

	def "test has Circular Reference - no circular"() {
		given:
		def parentMenu = Menu.reconstitute(1L, "系统管理", new MenuPath("/system"), "setting", 0L, 0, MenuStatus.VISIBLE, "Layout", null, null)
		when(menuGateway.findById(1L)).thenReturn(Optional.of(parentMenu))

		when:
		def result = rbacDomainService.hasCircularReference(2L, 1L)

		then:
		result == false
	}

	def "test has Circular Reference - self reference"() {
		when:
		def result = rbacDomainService.hasCircularReference(1L, 1L)

		then:
		result == true
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
