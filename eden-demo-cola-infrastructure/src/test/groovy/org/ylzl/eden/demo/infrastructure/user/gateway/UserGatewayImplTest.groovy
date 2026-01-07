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
import org.ylzl.eden.demo.domain.user.valueobject.Email
import org.ylzl.eden.demo.domain.user.valueobject.Login
import org.ylzl.eden.demo.domain.user.valueobject.Password
import org.ylzl.eden.demo.infrastructure.role.database.RoleMapper
import org.ylzl.eden.demo.infrastructure.role.database.convertor.RoleConvertor
import org.ylzl.eden.demo.infrastructure.user.database.UserMapper
import org.ylzl.eden.demo.infrastructure.user.database.UserRoleMapper
import org.ylzl.eden.demo.infrastructure.user.database.convertor.UserConvertor
import org.ylzl.eden.demo.infrastructure.user.database.dataobject.UserDO
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class UserGatewayImplTest extends Specification {
	@Mock
	UserMapper userMapper
	@Mock
	UserRoleMapper userRoleMapper
	@Mock
	RoleMapper roleMapper
	@Mock
	UserConvertor userConvertor
	@Mock
	RoleConvertor roleConvertor
	@Mock
	Logger log
	@InjectMocks
	UserGatewayImpl userGatewayImpl

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	def "test save new user"() {
		given:
		def user = User.create(new Login("login"), new Email("test@example.com"), new Password("password"))
		def userDO = UserDO.builder().login("login").email("test@example.com").password("password").build()
		when(userConvertor.toDataObject(any())).thenReturn(userDO)

		when:
		userGatewayImpl.save(user)

		then:
		true
	}

	def "test find By Id"() {
		given:
		def userDO = UserDO.builder().id(1L).login("login").email("test@example.com").password("password").status(1).build()
		def user = User.reconstitute(1L, new Login("login"), new Email("test@example.com"),
			new Password("password"), User.UserStatus.ACTIVE, null, null)
		when(userMapper.selectById(any())).thenReturn(userDO)
		when(userConvertor.toEntity(any())).thenReturn(user)

		when:
		def result = userGatewayImpl.findById(1L)

		then:
		result.isPresent()
		result.get().id == 1L
	}

	def "test delete By Id"() {
		when:
		userGatewayImpl.deleteById(1L)

		then:
		true
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
