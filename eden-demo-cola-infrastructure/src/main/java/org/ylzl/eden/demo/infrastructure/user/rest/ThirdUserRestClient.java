package org.ylzl.eden.demo.infrastructure.user.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.demo.domain.user.entity.User;

/**
 * 基于 REST 同步到第三方用户系统
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class ThirdUserRestClient {

//	private final RestTemplate restTemplate;

	public void syncUser(User user) {
		log.info("基于 REST 同步到第三方用户系统");
	}
}
