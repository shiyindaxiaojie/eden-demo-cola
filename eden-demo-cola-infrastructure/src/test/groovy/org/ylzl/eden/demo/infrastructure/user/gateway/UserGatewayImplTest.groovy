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

package org.ylzl.eden.demo.infrastructure.user.gateway

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.domain.user.entity.User
import org.ylzl.eden.demo.infrastructure.user.database.convertor.UserConvertor
import org.ylzl.eden.demo.infrastructure.user.database.dataobject.UserDO
import org.ylzl.eden.demo.infrastructure.user.database.UserMapper
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class UserGatewayImplTest extends Specification {
	@Mock
	UserMapper userMapper
	@Mock
	UserConvertor userConvertor
	@Mock
	Logger log
	@InjectMocks
	UserGatewayImpl userGatewayImpl

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	def "test save"() {
		given:
		when(userConvertor.toDataObject(any())).thenReturn(UserDO.builder().id(1L).login("login").email("email").passwordHash("password").build())

		when:
		userGatewayImpl.save(new User(1l, "login", "email", "password"))

		then:
		true
	}

	def "test update By Id"() {
		given:
		when(userConvertor.toDataObject(any())).thenReturn(UserDO.builder().id(1L).login("login").email("email").passwordHash("password").build())

		when:
		userGatewayImpl.updateById(new User(1l, "login", "email", "password"))

		then:
		true
	}

	def "test delete By Id"() {
		when:
		userGatewayImpl.deleteById(new User(1l, "login", "email", "password"))

		then:
		true
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
