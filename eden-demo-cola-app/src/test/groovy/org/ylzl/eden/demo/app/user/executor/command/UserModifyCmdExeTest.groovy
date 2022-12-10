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

package org.ylzl.eden.demo.app.user.executor.command

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.app.user.assembler.UserAssembler
import org.ylzl.eden.demo.client.user.dto.command.UserModifyCmd
import org.ylzl.eden.demo.domain.user.entity.User
import org.ylzl.eden.demo.domain.user.gateway.UserGateway
import org.ylzl.eden.cola.dto.Response
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class UserModifyCmdExeTest extends Specification {
	@Mock
	UserGateway userGateway
	@Mock
	UserAssembler userAssembler
	@Mock
	Logger log
	@InjectMocks
	UserModifyCmdExe userModifyCmdExe

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	def "test execute"() {
		given:
		when(userAssembler.toEntity(any())).thenReturn(User.builder().id(1L).login("login").email("email").password("password").build())

		when:
		Response result = userModifyCmdExe.execute(new UserModifyCmd(1l, "login", "password", "email"))

		then:
		result == Response.buildSuccess()
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
