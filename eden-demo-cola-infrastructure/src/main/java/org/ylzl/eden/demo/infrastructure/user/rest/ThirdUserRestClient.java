package org.ylzl.eden.demo.infrastructure.user.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.ylzl.eden.demo.api.dto.UserResponseDTO;
import org.ylzl.eden.spring.framework.dto.SingleResult;
import org.ylzl.eden.spring.framework.json.support.JSONHelper;

/**
 *  第三方用户 REST 调用
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class ThirdUserRestClient {

	private final StringRedisTemplate redisTemplate;

	private final RestTemplate restTemplate;

	public SingleResult<UserResponseDTO> getUser(long id) {
		log.info("根据用户ID [{}] 发起 REST 调用第三方", id);
		SingleResult<UserResponseDTO> singleResult =
			restTemplate.getForObject("http://localhost:8082/api/users/" + id,
			SingleResult.class);

		redisTemplate.opsForValue().set("test", JSONHelper.json().toJSONString(singleResult));
		return singleResult;
	}
}
