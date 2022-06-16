package org.ylzl.eden.demo

import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.ylzl.eden.demo.adapter.user.web.UserController
import org.ylzl.eden.demo.client.user.dto.UserDTO
import org.ylzl.eden.spring.framework.cola.dto.SingleResponse
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationIT extends Specification {

	@Autowired
	private UserController userController;

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	@Unroll
	def "get User By Id where id=#id then expect: #expectedResult"() {
		expect:
		userController.getUserById(id) == expectedResult

		where:
		id || expectedResult
		1l || SingleResponse.of(new UserDTO(1l, "admin", "1813986321@qq.com"))
	}
}
