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
import org.ylzl.eden.demo.client.user.dto.command.UserAddCmd;
import org.ylzl.eden.demo.domain.user.domainservice.UserDomainService;
import org.ylzl.eden.demo.domain.user.entity.User;
import org.ylzl.eden.demo.domain.user.gateway.UserGateway;

/**
 * 新增用户指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class UserAddCmdExe {

	private final UserDomainService userDomainService;

	private final UserGateway userGateway;

	private final ApplicationEventPublisher eventPublisher;

	/**
	 * 执行新增用户指令
	 *
	 * @param cmd 新增用户指令
	 * @return 响应结果
	 */
	public Response execute(UserAddCmd cmd) {
		User user = userDomainService.registerUser(
			cmd.getLogin(),
			cmd.getEmail(),
			cmd.getPassword()
		);
		userGateway.save(user);
		user.getDomainEvents().forEach(eventPublisher::publishEvent);
		user.clearDomainEvents();
		return Response.buildSuccess();
	}
}
