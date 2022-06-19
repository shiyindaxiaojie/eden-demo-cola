package org.ylzl.eden.demo.app.user.executor.query

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.app.user.assembler.UserAssembler
import org.ylzl.eden.demo.client.user.dto.UserDTO
import org.ylzl.eden.demo.client.user.dto.query.UserListByPageQry
import org.ylzl.eden.demo.infrastructure.user.database.dataobject.UserDO
import org.ylzl.eden.demo.infrastructure.user.database.mapper.UserMapper
import org.ylzl.eden.spring.framework.cola.dto.PageResponse
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class UserListByPageQryExeTest extends Specification {
	@Mock
	UserMapper userMapper
	@Mock
	UserAssembler userAssembler
	@Mock
	Logger log
	@InjectMocks
	UserListByPageQryExe userListByPageQryExe

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	def "test execute"() {
		given:
		when(userMapper.selectPage(any())).thenReturn([UserDO.builder().id(1L).login("login").email ("email").build()])
		when(userAssembler.toDTOList(any())).thenReturn([UserDTO.builder().id(1L).login("login").email("email").build()])

		when:
		PageResponse<UserDTO> result = userListByPageQryExe.execute(new UserListByPageQry("login", "email"))

		then:
		result.getData()[0].getId() == 1L
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
