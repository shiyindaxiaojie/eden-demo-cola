package org.ylzl.eden.demo.app.user.executor.query

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.app.user.assembler.UserAssembler
import org.ylzl.eden.demo.client.user.dto.UserDTO
import org.ylzl.eden.demo.client.user.dto.query.UserByIdQry
import org.ylzl.eden.demo.infrastructure.user.database.dataobject.UserDO
import org.ylzl.eden.demo.infrastructure.user.database.mapper.UserMapper
import org.ylzl.eden.spring.framework.cola.dto.SingleResponse
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
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
		UserDO userDO = UserDO.builder().id(1L).login("login").email("email").build()
		UserDTO userDTO = UserDTO.builder().id(1L).login("login").email("email").build()
		when(userMapper.selectById(any())).thenReturn(userDO)
		when(userAssembler.toDTO(userDO)).thenReturn(userDTO)

		when:
		SingleResponse<UserDTO> result = userByIdQryExe.execute(new UserByIdQry(1L))

		then:
		result == SingleResponse.of(userDTO)
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
