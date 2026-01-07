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

package org.ylzl.eden.demo.domain.permission.entity

import org.ylzl.eden.demo.domain.permission.valueobject.PermissionCode
import spock.lang.Specification

class PermissionTest extends Specification {

	def "test create permission"() {
		when:
		def permission = Permission.create(new PermissionCode("user:read"), "用户查看", PermissionType.BUTTON)

		then:
		permission.code.value == "user:read"
		permission.name == "用户查看"
		permission.type == PermissionType.BUTTON
		permission.parentId == 0L
		permission.sort == 0
		permission.createdAt != null
	}

	def "test reconstitute permission"() {
		when:
		def permission = Permission.reconstitute(1L, new PermissionCode("user:read"), "用户查看",
			PermissionType.BUTTON, 0L, "查看用户信息", 1, null, null)

		then:
		permission.id == 1L
		permission.code.value == "user:read"
		permission.name == "用户查看"
		permission.type == PermissionType.BUTTON
		permission.parentId == 0L
		permission.description == "查看用户信息"
		permission.sort == 1
	}

	def "test update info"() {
		given:
		def permission = Permission.create(new PermissionCode("user:read"), "用户查看", PermissionType.BUTTON)

		when:
		permission.updateInfo("用户详情查看", "查看用户详细信息", 2)

		then:
		permission.name == "用户详情查看"
		permission.description == "查看用户详细信息"
		permission.sort == 2
	}

	def "test set parent"() {
		given:
		def permission = Permission.create(new PermissionCode("user:read"), "用户查看", PermissionType.BUTTON)

		when:
		permission.setParent(1L)

		then:
		permission.parentId == 1L
	}

	def "test is menu permission"() {
		given:
		def menuPermission = Permission.create(new PermissionCode("system:menu"), "系统菜单", PermissionType.MENU)
		def buttonPermission = Permission.create(new PermissionCode("user:read"), "用户查看", PermissionType.BUTTON)

		expect:
		menuPermission.isMenuPermission() == true
		buttonPermission.isMenuPermission() == false
	}

	def "test is button permission"() {
		given:
		def menuPermission = Permission.create(new PermissionCode("system:menu"), "系统菜单", PermissionType.MENU)
		def buttonPermission = Permission.create(new PermissionCode("user:read"), "用户查看", PermissionType.BUTTON)

		expect:
		menuPermission.isButtonPermission() == false
		buttonPermission.isButtonPermission() == true
	}

	def "test is root"() {
		given:
		def rootPermission = Permission.create(new PermissionCode("system"), "系统", PermissionType.MENU)
		def childPermission = Permission.reconstitute(2L, new PermissionCode("user:read"), "用户查看",
			PermissionType.BUTTON, 1L, "描述", 0, null, null)

		expect:
		rootPermission.isRoot() == true
		childPermission.isRoot() == false
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
