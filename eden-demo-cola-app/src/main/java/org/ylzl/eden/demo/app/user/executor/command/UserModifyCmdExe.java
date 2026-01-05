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

package org.ylzl.eden.demo.app.user.executor.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.demo.client.user.dto.command.UserModifyCmd;
import org.ylzl.eden.demo.domain.user.domainservice.UserDomainService;
import org.ylzl.eden.demo.domain.user.entity.User;
import org.ylzl.eden.demo.domain.user.gateway.UserGateway;
import org.ylzl.eden.spring.framework.error.ClientAssert;

/**
 * 修改用户指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class UserModifyCmdExe {

	private final UserDomainService userDomainService;

	private final UserGateway userGateway;

	private final ApplicationEventPublisher eventPublisher;

	/**
	 * 执行修改用户指令
	 *
	 * @param cmd 修改用户指令
	 * @return 响应结果
	 */
	public Response execute(UserModifyCmd cmd) {
		User user = userGateway.findById(cmd.getId()).orElse(null);
		ClientAssert.notNull(user, "USER-404", "用户不存在");

		if (cmd.getEmail() != null) {
			userDomainService.changeEmail(user, cmd.getEmail());
		}

		userGateway.save(user);
		user.getDomainEvents().forEach(eventPublisher::publishEvent);
		user.clearDomainEvents();
		return Response.buildSuccess();
	}
}
