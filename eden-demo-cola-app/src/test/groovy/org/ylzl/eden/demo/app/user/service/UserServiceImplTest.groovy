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

package org.ylzl.eden.demo.app.user.service

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.app.user.executor.command.UserAddCmdExe
import org.ylzl.eden.demo.app.user.executor.command.UserModifyCmdExe
import org.ylzl.eden.demo.app.user.executor.command.UserRemoveCmdExe
import org.ylzl.eden.demo.app.user.executor.query.UserByIdQryExe
import org.ylzl.eden.demo.app.user.executor.query.UserListByPageQryExe
import org.ylzl.eden.demo.client.user.dto.UserDTO
import org.ylzl.eden.demo.client.user.dto.command.UserAddCmd
import org.ylzl.eden.demo.client.user.dto.command.UserModifyCmd
import org.ylzl.eden.demo.client.user.dto.command.UserRemoveCmd
import org.ylzl.eden.demo.client.user.dto.query.UserByIdQry
import org.ylzl.eden.demo.client.user.dto.query.UserListByPageQry
import org.ylzl.eden.cola.dto.PageResponse
import org.ylzl.eden.cola.dto.Response
import org.ylzl.eden.cola.dto.SingleResponse
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class UserServiceImplTest extends Specification {
	@Mock
	UserAddCmdExe userAddCmdExe
	@Mock
	UserModifyCmdExe userModifyCmdExe
	@Mock
	UserRemoveCmdExe userRemoveCmdExe
	@Mock
	UserByIdQryExe userByIdQryExe
	@Mock
	UserListByPageQryExe userListByPageQryExe
	@Mock
	Logger log
	@InjectMocks
	UserServiceImpl userServiceImpl

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	def "test create User"() {
		given:
		when(userAddCmdExe.execute(any())).thenReturn(Response.buildSuccess())

		when:
		Response result = userServiceImpl.createUser(new UserAddCmd("login", "password", "email"))

		then:
		result == Response.buildSuccess()
	}

	def "test modify User"() {
		given:
		when(userModifyCmdExe.execute(any())).thenReturn(Response.buildSuccess())

		when:
		Response result = userServiceImpl.modifyUser(new UserModifyCmd(1l, "login", "password", "email"))

		then:
		result == Response.buildSuccess()
	}

	def "test remove User"() {
		given:
		when(userRemoveCmdExe.execute(any())).thenReturn(Response.buildSuccess())
		when(userByIdQryExe.execute(any())).thenReturn(SingleResponse.of(UserDTO.builder().id(1L).build()))

		when:
		Response result = userServiceImpl.removeUser(new UserRemoveCmd(1l))

		then:
		result == Response.buildSuccess()
	}

	def "test get User By Id"() {
		given:
		when(userByIdQryExe.execute(any())).thenReturn(SingleResponse.of(UserDTO.builder().id(1L).build()))

		when:
		SingleResponse<UserDTO> result = userServiceImpl.getUserById(new UserByIdQry(1l))

		then:
		result == SingleResponse.of(UserDTO.builder().id(1L).build())
	}

	def "test list User By Page"() {
		given:
		when(userListByPageQryExe.execute(any())).thenReturn(PageResponse.of([new UserDTO(1l, "login", "email")], 1, 1, 1))

		when:
		PageResponse<UserDTO> result = userServiceImpl.listUserByPage(new UserListByPageQry("login", "email"))

		then:
		result == PageResponse.of([new UserDTO(1l, "login", "email")], 1, 1, 1)
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
