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

package org.ylzl.eden.demo.domain.user.domainservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ylzl.eden.demo.domain.user.entity.User;
import org.ylzl.eden.demo.domain.user.gateway.UserGateway;
import org.ylzl.eden.demo.domain.user.valueobject.Email;
import org.ylzl.eden.demo.domain.user.valueobject.Login;
import org.ylzl.eden.demo.domain.user.valueobject.Password;
import org.ylzl.eden.spring.framework.error.ClientAssert;

/**
 * 用户领域服务
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Service
public class UserDomainService {

	private final UserGateway userGateway;

	/**
	 * 注册用户
	 *
	 * @param loginStr    账号
	 * @param emailStr    邮箱
	 * @param passwordStr 密码
	 * @return 用户实体
	 */
	public User registerUser(String loginStr, String emailStr, String passwordStr) {
		Login login = Login.of(loginStr);
		Email email = Email.of(emailStr);
		Password password = Password.fromPlainText(passwordStr);

		ClientAssert.isTrue(!userGateway.existsByLogin(login),
			"USER-REG-001", "账号已存在");
		ClientAssert.isTrue(!userGateway.existsByEmail(email),
			"USER-REG-002", "邮箱已被注册");

		return User.create(login, email, password);
	}

	/**
	 * 用户登录验证
	 *
	 * @param loginStr      账号
	 * @param plainPassword 明文密码
	 * @return 用户实体
	 */
	public User authenticate(String loginStr, String plainPassword) {
		Login login = Login.of(loginStr);

		User user = userGateway.findByLogin(login);
		ClientAssert.notNull(user, "AUTH-001", "用户不存在");
		ClientAssert.isTrue(user.canLogin(), "AUTH-002", "用户状态异常，无法登录");
		ClientAssert.isTrue(user.verifyPassword(plainPassword), "AUTH-003", "密码错误");

		return user;
	}

	/**
	 * 修改邮箱
	 *
	 * @param user        用户实体
	 * @param newEmailStr 新邮箱
	 */
	public void changeEmail(User user, String newEmailStr) {
		Email newEmail = Email.of(newEmailStr);

		ClientAssert.isTrue(!userGateway.existsByEmailExcludeUser(newEmail, user.getId()),
			"USER-EMAIL-001", "该邮箱已被其他用户使用");

		user.changeEmail(newEmail);
	}
}
