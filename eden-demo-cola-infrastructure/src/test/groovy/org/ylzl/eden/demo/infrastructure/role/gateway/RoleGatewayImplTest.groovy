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

package org.ylzl.eden.demo.infrastructure.role.gateway

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.domain.role.entity.Role
import org.ylzl.eden.demo.domain.role.entity.RoleStatus
import org.ylzl.eden.demo.domain.role.valueobject.RoleCode
import org.ylzl.eden.demo.domain.role.valueobject.RoleName
import org.ylzl.eden.demo.infrastructure.role.database.RoleMapper
import org.ylzl.eden.demo.infrastructure.role.database.RoleMenuMapper
import org.ylzl.eden.demo.infrastructure.role.database.RolePermissionMapper
import org.ylzl.eden.demo.infrastructure.role.database.convertor.RoleConvertor
import org.ylzl.eden.demo.infrastructure.role.database.dataobject.RoleDO
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class RoleGatewayImplTest extends Specification {
	@Mock
	RoleMapper roleMapper
	@Mock
	RolePermissionMapper rolePermissionMapper
	@Mock
	RoleMenuMapper roleMenuMapper
	@Mock
	RoleConvertor roleConvertor
	@Mock
	Logger log
	@InjectMocks
	RoleGatewayImpl roleGatewayImpl

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	def "test save new role"() {
		given:
		def role = Role.create(new RoleCode("ADMIN"), new RoleName("管理员"))
		when(roleConvertor.toDataObject(any())).thenReturn(RoleDO.builder().code("ADMIN").name("管理员").build())

		when:
		roleGatewayImpl.save(role)

		then:
		true
	}

	def "test find By Id"() {
		given:
		def roleDO = RoleDO.builder().id(1L).code("ADMIN").name("管理员").status(1).sort(0).build()
		def role = Role.reconstitute(1L, new RoleCode("ADMIN"), new RoleName("管理员"), "描述", RoleStatus.ENABLED, 0, null, null)
		when(roleMapper.selectById(any())).thenReturn(roleDO)
		when(roleConvertor.toEntity(any())).thenReturn(role)

		when:
		def result = roleGatewayImpl.findById(1L)

		then:
		result.isPresent()
		result.get().id == 1L
	}

	def "test exists By Code"() {
		given:
		when(roleMapper.countByCode(any())).thenReturn(1)

		when:
		def result = roleGatewayImpl.existsByCode(new RoleCode("ADMIN"))

		then:
		result == true
	}

	def "test delete By Id"() {
		when:
		roleGatewayImpl.deleteById(1L)

		then:
		true
	}

	def "test save Role Permissions"() {
		when:
		roleGatewayImpl.saveRolePermissions(1L, [1L, 2L, 3L])

		then:
		true
	}

	def "test save Role Menus"() {
		when:
		roleGatewayImpl.saveRoleMenus(1L, [1L, 2L, 3L])

		then:
		true
	}

	def "test find Permission Ids By Role Id"() {
		given:
		when(rolePermissionMapper.selectPermissionIdsByRoleId(any())).thenReturn([1L, 2L, 3L])

		when:
		def result = roleGatewayImpl.findPermissionIdsByRoleId(1L)

		then:
		result == [1L, 2L, 3L]
	}

	def "test find Menu Ids By Role Id"() {
		given:
		when(roleMenuMapper.selectMenuIdsByRoleId(any())).thenReturn([1L, 2L, 3L])

		when:
		def result = roleGatewayImpl.findMenuIdsByRoleId(1L)

		then:
		result == [1L, 2L, 3L]
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
