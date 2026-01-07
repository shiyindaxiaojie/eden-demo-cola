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

package org.ylzl.eden.demo.app.permission.service

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.app.permission.executor.command.*
import org.ylzl.eden.demo.app.permission.executor.query.*
import org.ylzl.eden.demo.client.permission.dto.PermissionDTO
import org.ylzl.eden.demo.client.permission.dto.PermissionTreeDTO
import org.ylzl.eden.demo.client.permission.dto.command.*
import org.ylzl.eden.demo.client.permission.dto.query.*
import org.ylzl.eden.cola.dto.MultiResponse
import org.ylzl.eden.cola.dto.Response
import org.ylzl.eden.cola.dto.SingleResponse
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class PermissionServiceImplTest extends Specification {
	@Mock
	PermissionAddCmdExe permissionAddCmdExe
	@Mock
	PermissionModifyCmdExe permissionModifyCmdExe
	@Mock
	PermissionRemoveCmdExe permissionRemoveCmdExe
	@Mock
	PermissionByIdQryExe permissionByIdQryExe
	@Mock
	PermissionTreeQryExe permissionTreeQryExe
	@Mock
	Logger log
	@InjectMocks
	PermissionServiceImpl permissionServiceImpl

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	def "test create Permission"() {
		given:
		when(permissionAddCmdExe.execute(any())).thenReturn(Response.buildSuccess())

		when:
		Response result = permissionServiceImpl.createPermission(new PermissionAddCmd("user:read", "用户查看", 3, 0L, "描述", 1))

		then:
		result == Response.buildSuccess()
	}

	def "test modify Permission"() {
		given:
		when(permissionModifyCmdExe.execute(any())).thenReturn(Response.buildSuccess())

		when:
		Response result = permissionServiceImpl.modifyPermission(new PermissionModifyCmd(1L, "用户查看", "描述", 1))

		then:
		result == Response.buildSuccess()
	}

	def "test remove Permission"() {
		given:
		when(permissionRemoveCmdExe.execute(any())).thenReturn(Response.buildSuccess())

		when:
		Response result = permissionServiceImpl.removePermission(new PermissionRemoveCmd(1L))

		then:
		result == Response.buildSuccess()
	}

	def "test get Permission By Id"() {
		given:
		when(permissionByIdQryExe.execute(any())).thenReturn(SingleResponse.of(new PermissionDTO(1L, "user:read", "用户查看", 3, 0L, "描述", 1)))

		when:
		SingleResponse<PermissionDTO> result = permissionServiceImpl.getPermissionById(new PermissionByIdQry(1L))

		then:
		result == SingleResponse.of(new PermissionDTO(1L, "user:read", "用户查看", 3, 0L, "描述", 1))
	}

	def "test list Permission Tree"() {
		given:
		when(permissionTreeQryExe.execute(any())).thenReturn(MultiResponse.of([new PermissionTreeDTO(1L, "user:read", "用户查看", 3, 0L, "描述", 1, [])]))

		when:
		MultiResponse<PermissionTreeDTO> result = permissionServiceImpl.listPermissionTree(new PermissionTreeQry(3))

		then:
		result == MultiResponse.of([new PermissionTreeDTO(1L, "user:read", "用户查看", 3, 0L, "描述", 1, [])])
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
