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

package org.ylzl.eden.demo.app.user.executor.query

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.app.user.assembler.UserAssembler
import org.ylzl.eden.demo.client.user.dto.UserDTO
import org.ylzl.eden.demo.client.user.dto.query.UserByIdQry
import org.ylzl.eden.demo.infrastructure.user.database.dataobject.UserDO
import org.ylzl.eden.demo.infrastructure.user.database.UserMapper
import org.ylzl.eden.cola.dto.SingleResponse
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.anyLong
import static org.mockito.Mockito.when

class UserByIdQryExeTest extends Specification {
	@Mock
	UserMapper userMapper
	@Mock
	UserAssembler userAssembler
	@Mock
	Logger log
	@InjectMocks
	UserByIdQryExe userByIdQryExe

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	def "test execute"() {
		given:
		def userDO = UserDO.builder().id(1L).login("login").email("test@example.com").build()
		def userDTO = UserDTO.builder().id(1L).login("login").email("test@example.com").build()
		when(userMapper.selectById(anyLong())).thenReturn(userDO)
		when(userAssembler.toDTO(userDO)).thenReturn(userDTO)

		when:
		SingleResponse<UserDTO> result = userByIdQryExe.execute(new UserByIdQry(1L))

		then:
		result.data.id == 1L
		result.data.login == "login"
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
