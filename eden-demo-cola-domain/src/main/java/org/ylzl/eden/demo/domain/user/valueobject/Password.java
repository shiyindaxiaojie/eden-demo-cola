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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.ylzl.eden.spring.framework.error.ClientAssert;

/**
 * 密码值对象
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Getter
@EqualsAndHashCode
public class Password {

	private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private static final int MIN_LENGTH = 8;
	private static final int MAX_LENGTH = 32;

	/**
	 * 加密后的密码
	 */
	private final String hashedValue;

	private Password(String hashedValue) {
		this.hashedValue = hashedValue;
	}

	/**
	 * 从明文密码创建
	 *
	 * @param plainText 明文密码
	 * @return 密码值对象
	 */
	public static Password fromPlainText(String plainText) {
		validate(plainText);
		return new Password(ENCODER.encode(plainText));
	}

	/**
	 * 从加密密码创建（用于数据库读取）
	 *
	 * @param encrypted 加密后的密码
	 * @return 密码值对象
	 */
	public static Password fromEncrypted(String encrypted) {
		return new Password(encrypted);
	}

	/**
	 * 校验密码强度
	 *
	 * @param plainText 明文密码
	 */
	private static void validate(String plainText) {
		ClientAssert.hasText(plainText, "PWD-001", "密码不能为空");
		ClientAssert.isTrue(plainText.length() >= MIN_LENGTH,
			"PWD-002", "密码长度不能少于" + MIN_LENGTH + "位");
		ClientAssert.isTrue(plainText.length() <= MAX_LENGTH,
			"PWD-003", "密码长度不能超过" + MAX_LENGTH + "位");
		ClientAssert.isTrue(containsDigit(plainText),
			"PWD-004", "密码必须包含数字");
		ClientAssert.isTrue(containsLetter(plainText),
			"PWD-005", "密码必须包含字母");
	}

	/**
	 * 验证密码是否匹配
	 *
	 * @param plainText 明文密码
	 * @return 是否匹配
	 */
	public boolean matches(String plainText) {
		return ENCODER.matches(plainText, hashedValue);
	}

	private static boolean containsDigit(String s) {
		return s.chars().anyMatch(Character::isDigit);
	}

	private static boolean containsLetter(String s) {
		return s.chars().anyMatch(Character::isLetter);
	}

	@Override
	public String toString() {
		return "******";
	}
}
