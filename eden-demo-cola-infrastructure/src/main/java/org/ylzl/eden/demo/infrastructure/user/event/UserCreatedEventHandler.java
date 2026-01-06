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

package org.ylzl.eden.demo.infrastructure.user.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.ylzl.eden.demo.domain.user.event.UserCreatedEvent;

/**
 * 用户创建事件处理器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class UserCreatedEventHandler {

	/**
	 * 处理用户创建事件
	 *
	 * @param event 用户创建事件
	 */
	@Async
	@EventListener
	public void handle(UserCreatedEvent event) {
		log.info("处理用户创建事件: userId={}, login={}, email={}",
			event.getUserId(), event.getLogin(), event.getEmail());
		// 发送欢迎邮件
		// 初始化用户配置
	}
}
