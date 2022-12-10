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

package org.ylzl.eden.demo.adapter.user.web

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.client.user.api.UserService
import org.ylzl.eden.demo.client.user.dto.UserDTO
import org.ylzl.eden.demo.client.user.dto.command.UserAddCmd
import org.ylzl.eden.demo.client.user.dto.command.UserModifyCmd
import org.ylzl.eden.demo.client.user.dto.query.UserListByPageQry
import org.ylzl.eden.cola.dto.PageResponse
import org.ylzl.eden.cola.dto.Response
import org.ylzl.eden.cola.dto.SingleResponse
import spock.lang.Specification
import spock.lang.Unroll

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when


class UserControllerTest extends Specification {
	@Mock
	UserService userService
	@Mock
	Logger log
	@InjectMocks
	UserController userController

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	@Unroll
	def "create User where cmd=#cmd then expect: #expectedResult"() {
		given:
		when(userService.createUser(any())).thenReturn(Response.buildSuccess())

		expect:
		userController.createUser(cmd) == expectedResult

		where:
		cmd                                          || expectedResult
		new UserAddCmd("login", "password", "email") || Response.buildSuccess()
	}

	@Unroll
	def "modify User where id=#id and cmd=#cmd then expect: #expectedResult"() {
		given:
		when(userService.modifyUser(any())).thenReturn(Response.buildSuccess())

		expect:
		userController.modifyUser(id, cmd) == expectedResult

		where:
		id | cmd                                                 || expectedResult
		1l | new UserModifyCmd(1l, "login", "password", "email") || Response.buildSuccess()
	}

	@Unroll
	def "remove User By Id where id=#id then expect: #expectedResult"() {
		given:
		when(userService.removeUser(any())).thenReturn(Response.buildSuccess())

		expect:
		userController.removeUserById(id) == expectedResult

		where:
		id || expectedResult
		1l || Response.buildSuccess()
	}

	@Unroll
	def "get User By Id where id=#id then expect: #expectedResult"() {
		given:
		when(userService.getUserById(any())).thenReturn(SingleResponse.of(new UserDTO(1l, "login", "email")))

		expect:
		userController.getUserById(id) == expectedResult

		where:
		id || expectedResult
		1l || SingleResponse.of(new UserDTO(1l, "login", "email"))
	}

	@Unroll
	def "list User By Page where query=#query then expect: #expectedResult"() {
		given:
		when(userService.listUserByPage(any())).thenReturn(PageResponse.of([new UserDTO(1l, "login", "email")], 1, 1, 1))

		expect:
		userController.listUserByPage(query) == expectedResult

		where:
		query                                   || expectedResult
		new UserListByPageQry("login", "email") || PageResponse.of([new UserDTO(1l, "login", "email")], 1, 1, 1)
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
