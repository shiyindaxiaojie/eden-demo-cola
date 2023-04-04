package org.ylzl.eden.demo.infrastructure.user.rpc;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;
import org.ylzl.eden.demo.api.UserService;
import org.ylzl.eden.demo.api.dto.UserResponseDTO;
import org.ylzl.eden.spring.framework.dto.SingleResult;

/**
 * 第三方用户 RPC 调用
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@Slf4j
@Component
public class ThirdUserRpcClient {

	@DubboReference
	private UserService userService;

	public SingleResult<UserResponseDTO> getUser(long id) {
		log.info("根据用户ID [{}] 发起 RPC 调用第三方", id);
		return userService.getUserById(id);
	}
}
