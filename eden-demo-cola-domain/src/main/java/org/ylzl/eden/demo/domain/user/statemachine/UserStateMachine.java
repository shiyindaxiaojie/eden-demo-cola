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

package org.ylzl.eden.demo.domain.user.statemachine;

import org.ylzl.eden.demo.domain.user.entity.User.UserStatus;

/**
 * 用户状态机
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
public interface UserStateMachine {

	String MACHINE_ID = "userStateMachine";

	/**
	 * 用户状态事件
	 */
	enum UserEvent {
		/** 激活 */
		ACTIVATE,
		/** 锁定 */
		LOCK,
		/** 解锁 */
		UNLOCK,
		/** 禁用 */
		DISABLE
	}

	/**
	 * 触发状态转换
	 *
	 * @param sourceState 源状态
	 * @param event       事件
	 * @param context     上下文
	 * @return 目标状态
	 */
	UserStatus fire(UserStatus sourceState, UserEvent event, Object context);
}
