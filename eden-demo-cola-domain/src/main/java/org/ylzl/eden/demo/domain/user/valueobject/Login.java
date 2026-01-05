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

package org.ylzl.eden.demo.domain.user.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.util.regex.Pattern;

/**
 * 登录账号值对象
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Getter
@EqualsAndHashCode
public class Login {

	private static final Pattern LOGIN_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{3,19}$");

	/**
	 * 账号
	 */
	private final String value;

	private Login(String value) {
		this.value = value;
	}

	/**
	 * 创建登录账号值对象
	 *
	 * @param value 账号
	 * @return 登录账号值对象
	 */
	public static Login of(String value) {
		ClientAssert.notBlank(value, "LOGIN-001", "账号不能为空");
		ClientAssert.isTrue(LOGIN_PATTERN.matcher(value).matches(),
			"LOGIN-002", "账号必须以字母开头，只能包含字母、数字、下划线，长度4-20位");
		return new Login(value.toLowerCase());
	}

	@Override
	public String toString() {
		return value;
	}
}
