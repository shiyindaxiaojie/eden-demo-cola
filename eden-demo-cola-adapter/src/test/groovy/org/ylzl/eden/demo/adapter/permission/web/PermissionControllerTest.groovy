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

package org.ylzl.eden.demo.adapter.permission.web

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.client.permission.api.PermissionService
import org.ylzl.eden.demo.client.permission.dto.PermissionDTO
import org.ylzl.eden.demo.client.permission.dto.PermissionTreeDTO
import org.ylzl.eden.demo.client.permission.dto.command.*
import org.ylzl.eden.demo.client.permission.dto.query.*
import org.ylzl.eden.cola.dto.MultiResponse
import org.ylzl.eden.cola.dto.Response
import org.ylzl.eden.cola.dto.SingleResponse
import spock.lang.Specification
import spock.lang.Unroll

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class PermissionControllerTest extends Specification {
	@Mock
	PermissionService permissionService
	@Mock
	Logger log
	@InjectMocks
	PermissionController permissionController

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	@Unroll
	def "create Permission where cmd=#cmd then expect: #expectedResult"() {
		given:
		when(permissionService.createPermission(any())).thenReturn(Response.buildSuccess())

		expect:
		permissionController.createPermission(cmd) == expectedResult

		where:
		cmd                                                              || expectedResult
		new PermissionAddCmd("user:read", "用户查看", 3, 0L, "描述", 1) || Response.buildSuccess()
	}

	@Unroll
	def "modify Permission where id=#id and cmd=#cmd then expect: #expectedResult"() {
		given:
		when(permissionService.modifyPermission(any())).thenReturn(Response.buildSuccess())

		expect:
		permissionController.modifyPermission(id, cmd) == expectedResult

		where:
		id | cmd                                          || expectedResult
		1L | new PermissionModifyCmd(1L, "用户查看", "描述", 1) || Response.buildSuccess()
	}

	@Unroll
	def "remove Permission where id=#id then expect: #expectedResult"() {
		given:
		when(permissionService.removePermission(any())).thenReturn(Response.buildSuccess())

		expect:
		permissionController.removePermission(id) == expectedResult

		where:
		id || expectedResult
		1L || Response.buildSuccess()
	}

	@Unroll
	def "get Permission By Id where id=#id then expect: #expectedResult"() {
		given:
		when(permissionService.getPermissionById(any())).thenReturn(SingleResponse.of(new PermissionDTO(1L, "user:read", "用户查看", 3, 0L, "描述", 1)))

		expect:
		permissionController.getPermissionById(id) == expectedResult

		where:
		id || expectedResult
		1L || SingleResponse.of(new PermissionDTO(1L, "user:read", "用户查看", 3, 0L, "描述", 1))
	}

	@Unroll
	def "list Permission Tree where query=#query then expect: #expectedResult"() {
		given:
		when(permissionService.listPermissionTree(any())).thenReturn(MultiResponse.of([new PermissionTreeDTO(1L, "user:read", "用户查看", 3, 0L, "描述", 1, [])]))

		expect:
		permissionController.listPermissionTree(query) == expectedResult

		where:
		query                     || expectedResult
		new PermissionTreeQry(3) || MultiResponse.of([new PermissionTreeDTO(1L, "user:read", "用户查看", 3, 0L, "描述", 1, [])])
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
