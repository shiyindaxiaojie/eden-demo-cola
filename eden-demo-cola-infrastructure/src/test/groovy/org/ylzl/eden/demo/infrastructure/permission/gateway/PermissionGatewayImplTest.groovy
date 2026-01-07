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

package org.ylzl.eden.demo.infrastructure.permission.gateway

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.domain.permission.entity.Permission
import org.ylzl.eden.demo.domain.permission.entity.PermissionType
import org.ylzl.eden.demo.domain.permission.valueobject.PermissionCode
import org.ylzl.eden.demo.infrastructure.permission.database.PermissionMapper
import org.ylzl.eden.demo.infrastructure.permission.database.convertor.PermissionConvertor
import org.ylzl.eden.demo.infrastructure.permission.database.dataobject.PermissionDO
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class PermissionGatewayImplTest extends Specification {
	@Mock
	PermissionMapper permissionMapper
	@Mock
	PermissionConvertor permissionConvertor
	@Mock
	Logger log
	@InjectMocks
	PermissionGatewayImpl permissionGatewayImpl

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	def "test save new permission"() {
		given:
		def permission = Permission.create(new PermissionCode("user:read"), "用户查看", PermissionType.BUTTON)
		when(permissionConvertor.toDataObject(any())).thenReturn(PermissionDO.builder().code("user:read").name("用户查看").type(3).build())

		when:
		permissionGatewayImpl.save(permission)

		then:
		true
	}

	def "test find By Id"() {
		given:
		def permissionDO = PermissionDO.builder().id(1L).code("user:read").name("用户查看").type(3).sort(0).build()
		def permission = Permission.reconstitute(1L, new PermissionCode("user:read"), "用户查看", PermissionType.BUTTON, 0L, "描述", 0, null, null)
		when(permissionMapper.selectById(any())).thenReturn(permissionDO)
		when(permissionConvertor.toEntity(any())).thenReturn(permission)

		when:
		def result = permissionGatewayImpl.findById(1L)

		then:
		result.isPresent()
		result.get().id == 1L
	}

	def "test exists By Code"() {
		given:
		when(permissionMapper.countByCode(any())).thenReturn(1)

		when:
		def result = permissionGatewayImpl.existsByCode(new PermissionCode("user:read"))

		then:
		result == true
	}

	def "test delete By Id"() {
		when:
		permissionGatewayImpl.deleteById(1L)

		then:
		true
	}

	def "test find All"() {
		given:
		def permissionDO = PermissionDO.builder().id(1L).code("user:read").name("用户查看").type(3).sort(0).build()
		def permission = Permission.reconstitute(1L, new PermissionCode("user:read"), "用户查看", PermissionType.BUTTON, 0L, "描述", 0, null, null)
		when(permissionMapper.selectAll()).thenReturn([permissionDO])
		when(permissionConvertor.toEntity(any())).thenReturn(permission)

		when:
		def result = permissionGatewayImpl.findAll()

		then:
		result.size() == 1
	}

	def "test is Used By Role"() {
		given:
		when(permissionMapper.countRoleByPermissionId(any())).thenReturn(1)

		when:
		def result = permissionGatewayImpl.isUsedByRole(1L)

		then:
		result == true
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
