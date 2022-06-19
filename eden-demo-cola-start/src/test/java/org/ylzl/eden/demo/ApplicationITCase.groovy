package org.ylzl.eden.demo

import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.ylzl.eden.demo.adapter.user.web.UserController
import org.ylzl.eden.demo.client.user.dto.UserDTO
import org.ylzl.eden.spring.framework.cola.dto.SingleResponse
import org.ylzl.eden.spring.test.redis.EmbeddedRedis
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
/**
 * 应用启动集成测试
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationITCase extends Specification {

	@Autowired
	private UserController userController;

	@Shared
	private EmbeddedRedis embeddedRedis;

	def setupSpec() {
		MockitoAnnotations.openMocks(this)

		embeddedRedis = new EmbeddedRedis()
		embeddedRedis.before()
	}

	def cleanupSpec() {
		embeddedRedis.after()
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
