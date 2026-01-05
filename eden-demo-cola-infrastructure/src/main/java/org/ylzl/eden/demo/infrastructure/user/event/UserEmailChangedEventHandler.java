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
import org.ylzl.eden.demo.domain.user.event.UserEmailChangedEvent;

/**
 * 用户邮箱变更事件处理器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class UserEmailChangedEventHandler {

	/**
	 * 处理邮箱变更事件
	 *
	 * @param event 邮箱变更事件
	 */
	@Async
	@EventListener
	public void handle(UserEmailChangedEvent event) {
		log.info("处理邮箱变更事件: userId={}, oldEmail={}, newEmail={}",
			event.getUserId(), event.getOldEmail(), event.getNewEmail());
		// 发送验证邮件到新邮箱
		// 发送通知到旧邮箱
		// 记录审计日志
	}
}
