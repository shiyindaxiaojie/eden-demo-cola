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
import org.springframework.context.ApplicationEventPublisher
import org.ylzl.eden.demo.client.user.dto.command.UserAddCmd
import org.ylzl.eden.demo.domain.user.domainservice.UserDomainService
import org.ylzl.eden.demo.domain.user.entity.User
import org.ylzl.eden.demo.domain.user.gateway.UserGateway
import org.ylzl.eden.demo.domain.user.valueobject.Email
import org.ylzl.eden.demo.domain.user.valueobject.Login
import org.ylzl.eden.demo.domain.user.valueobject.Password
import org.ylzl.eden.cola.dto.Response
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.Mockito.when

class UserAddCmdExeTest extends Specification {
	@Mock
	UserDomainService userDomainService
	@Mock
	UserGateway userGateway
	@Mock
	ApplicationEventPublisher eventPublisher
	@Mock
	Logger log
	@InjectMocks
	UserAddCmdExe userAddCmdExe

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	def "test execute"() {
		given:
		def user = User.create(new Login("login"), new Email("test@example.com"), new Password("password"))
		when(userDomainService.registerUser(anyString(), anyString(), anyString())).thenReturn(user)

		when:
		Response result = userAddCmdExe.execute(new UserAddCmd("login", "password", "test@example.com"))

		then:
		result == Response.buildSuccess()
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
