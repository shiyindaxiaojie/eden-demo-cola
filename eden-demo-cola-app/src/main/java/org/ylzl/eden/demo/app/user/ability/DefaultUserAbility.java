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

package org.ylzl.eden.demo.app.user.ability;

import lombok.extern.slf4j.Slf4j;
import org.ylzl.eden.cola.extension.Extension;
import org.ylzl.eden.demo.domain.user.ability.UserAbility;
import org.ylzl.eden.demo.domain.user.entity.User;

/**
 * 用户领域能力默认实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Slf4j
@Extension
public class DefaultUserAbility implements UserAbility {

	/**
	 * 校验用户注册
	 *
	 * @param user 用户实体
	 */
	@Override
	public void validateRegister(User user) {
		log.debug("执行默认用户注册校验");
	}

	/**
	 * 注册后处理
	 *
	 * @param user 用户实体
	 */
	@Override
	public void afterRegister(User user) {
		log.debug("执行默认用户注册后处理");
	}

	/**
	 * 校验密码强度
	 *
	 * @param password 密码
	 * @return 是否通过
	 */
	@Override
	public boolean validatePasswordStrength(String password) {
		return password != null && password.length() >= 8;
	}
}
