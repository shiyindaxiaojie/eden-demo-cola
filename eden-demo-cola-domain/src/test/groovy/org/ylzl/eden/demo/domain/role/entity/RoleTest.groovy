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

package org.ylzl.eden.demo.domain.role.entity

import org.ylzl.eden.demo.domain.role.valueobject.RoleCode
import org.ylzl.eden.demo.domain.role.valueobject.RoleName
import spock.lang.Specification

class RoleTest extends Specification {

	def "test create role"() {
		when:
		def role = Role.create(new RoleCode("ADMIN"), new RoleName("管理员"))

		then:
		role.code.value == "ADMIN"
		role.name.value == "管理员"
		role.status == RoleStatus.ENABLED
		role.sort == 0
		role.createdAt != null
	}

	def "test reconstitute role"() {
		when:
		def role = Role.reconstitute(1L, new RoleCode("ADMIN"), new RoleName("管理员"),
			"系统管理员", RoleStatus.ENABLED, 1, null, null)

		then:
		role.id == 1L
		role.code.value == "ADMIN"
		role.name.value == "管理员"
		role.description == "系统管理员"
		role.status == RoleStatus.ENABLED
		role.sort == 1
	}

	def "test update info"() {
		given:
		def role = Role.create(new RoleCode("ADMIN"), new RoleName("管理员"))

		when:
		role.updateInfo(new RoleName("超级管理员"), "新描述", 2)

		then:
		role.name.value == "超级管理员"
		role.description == "新描述"
		role.sort == 2
	}

	def "test enable role"() {
		given:
		def role = Role.reconstitute(1L, new RoleCode("ADMIN"), new RoleName("管理员"),
			"描述", RoleStatus.DISABLED, 0, null, null)

		when:
		role.enable()

		then:
		role.status == RoleStatus.ENABLED
		role.isEnabled() == true
	}

	def "test disable role"() {
		given:
		def role = Role.create(new RoleCode("ADMIN"), new RoleName("管理员"))

		when:
		role.disable()

		then:
		role.status == RoleStatus.DISABLED
		role.isEnabled() == false
	}

	def "test is enabled"() {
		given:
		def role = Role.create(new RoleCode("ADMIN"), new RoleName("管理员"))

		expect:
		role.isEnabled() == true
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
