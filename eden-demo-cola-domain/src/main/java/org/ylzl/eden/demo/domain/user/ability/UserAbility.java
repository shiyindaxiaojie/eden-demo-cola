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

package org.ylzl.eden.demo.domain.user.ability;

import org.ylzl.eden.demo.domain.user.entity.User;

/**
 * 用户领域能力扩展点
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
public interface UserAbility {

	/**
	 * 校验用户注册
	 *
	 * @param user 用户实体
	 */
	void validateRegister(User user);

	/**
	 * 注册后处理
	 *
	 * @param user 用户实体
	 */
	void afterRegister(User user);

	/**
	 * 校验密码强度
	 *
	 * @param password 密码
	 * @return 是否通过
	 */
	boolean validatePasswordStrength(String password);
}
