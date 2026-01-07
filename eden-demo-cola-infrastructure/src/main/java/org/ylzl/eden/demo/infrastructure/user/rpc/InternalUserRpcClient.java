package org.ylzl.eden.demo.infrastructure.user.rpc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.demo.domain.user.entity.User;

/**
 * 基于 RPC 同步到内部用户系统
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class InternalUserRpcClient {

//	@DubboReference
//	private UserSyncService userSyncService;

	/**
	 * 同步用户信息到内部系统
	 *
	 * @param user 用户实体
	 */
	public void syncUser(User user) {
		log.info("基于 RPC 同步到内部用户系统");
	}
}
