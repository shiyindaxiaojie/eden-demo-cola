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
 * 邮箱值对象
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Getter
@EqualsAndHashCode
public class Email {

	private static final Pattern EMAIL_PATTERN =
		Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

	/**
	 * 邮箱地址
	 */
	private final String value;

	private Email(String value) {
		this.value = value;
	}

	/**
	 * 创建邮箱值对象
	 *
	 * @param value 邮箱地址
	 * @return 邮箱值对象
	 */
	public static Email of(String value) {
		ClientAssert.hasText(value, "EMAIL-001", "邮箱不能为空");
		ClientAssert.isTrue(EMAIL_PATTERN.matcher(value).matches(),
			"EMAIL-002", "邮箱格式不正确");
		ClientAssert.isTrue(value.length() <= 100,
			"EMAIL-003", "邮箱长度不能超过100个字符");
		return new Email(value.toLowerCase().trim());
	}

	/**
	 * 获取邮箱域名
	 *
	 * @return 域名
	 */
	public String getDomain() {
		return value.substring(value.indexOf("@") + 1);
	}

	/**
	 * 是否为企业邮箱
	 *
	 * @return 是否企业邮箱
	 */
	public boolean isEnterprise() {
		String domain = getDomain();
		return !domain.endsWith("gmail.com")
			&& !domain.endsWith("qq.com")
			&& !domain.endsWith("163.com");
	}

	@Override
	public String toString() {
		return value;
	}
}
