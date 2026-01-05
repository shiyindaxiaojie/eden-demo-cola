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

package org.ylzl.eden.demo.infrastructure.user.statemachine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.cola.statemachine.StateMachine;
import org.ylzl.eden.cola.statemachine.factory.StateMachineFactory;
import org.ylzl.eden.demo.domain.user.entity.User.UserStatus;
import org.ylzl.eden.demo.domain.user.statemachine.UserStateMachine;

import javax.annotation.PostConstruct;

/**
 * 用户状态机实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Slf4j
@Component
public class UserStateMachineImpl implements UserStateMachine {

	private StateMachine<UserStatus, UserEvent, Object> stateMachine;

	/**
	 * 初始化状态机
	 */
	@PostConstruct
	public void init() {
		stateMachine = StateMachineFactory
			.<UserStatus, UserEvent, Object>create(MACHINE_ID)
			.externalTransition()
				.from(UserStatus.PENDING)
				.to(UserStatus.ACTIVE)
				.on(UserEvent.ACTIVATE)
				.perform((from, to, event, ctx) -> log.info("用户激活: {} -> {}", from, to))
			.and()
			.externalTransition()
				.from(UserStatus.ACTIVE)
				.to(UserStatus.LOCKED)
				.on(UserEvent.LOCK)
				.perform((from, to, event, ctx) -> log.info("用户锁定: {} -> {}", from, to))
			.and()
			.externalTransition()
				.from(UserStatus.LOCKED)
				.to(UserStatus.ACTIVE)
				.on(UserEvent.UNLOCK)
				.perform((from, to, event, ctx) -> log.info("用户解锁: {} -> {}", from, to))
			.and()
			.externalTransitions()
				.fromAmong(UserStatus.PENDING, UserStatus.ACTIVE, UserStatus.LOCKED)
				.to(UserStatus.DISABLED)
				.on(UserEvent.DISABLE)
				.perform((from, to, event, ctx) -> log.info("用户禁用: {} -> {}", from, to))
			.build();
	}

	/**
	 * 触发状态转换
	 *
	 * @param sourceState 源状态
	 * @param event       事件
	 * @param context     上下文
	 * @return 目标状态
	 */
	@Override
	public UserStatus fire(UserStatus sourceState, UserEvent event, Object context) {
		return stateMachine.fireEvent(sourceState, event, context);
	}
}
