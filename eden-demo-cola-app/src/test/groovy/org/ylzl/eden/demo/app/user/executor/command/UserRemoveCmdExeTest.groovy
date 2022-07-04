package org.ylzl.eden.demo.app.user.executor.command

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.app.user.assembler.UserAssembler
import org.ylzl.eden.demo.client.user.dto.command.UserRemoveCmd
import org.ylzl.eden.demo.domain.user.entity.User
import org.ylzl.eden.demo.domain.user.gateway.UserGateway
import org.ylzl.eden.spring.framework.cola.dto.Response
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class UserRemoveCmdExeTest extends Specification {
	@Mock
	UserGateway userGateway
	@Mock
	UserAssembler userAssembler
	@Mock
	Logger log
	@InjectMocks
	UserRemoveCmdExe userRemoveCmdExe

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	def "test execute"() {
		given:
		when(userAssembler.toEntity(any())).thenReturn(User.builder().id(1L).login("login").email("email").password("password").build())

		when:
		Response result = userRemoveCmdExe.execute(new UserRemoveCmd(1l))

		then:
		result == Response.buildSuccess()
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
