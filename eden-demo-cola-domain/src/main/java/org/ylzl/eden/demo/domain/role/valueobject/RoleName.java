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

package org.ylzl.eden.demo.domain.role.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ylzl.eden.spring.framework.error.ClientAssert;

/**
 * 角色名称值对象
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@Getter
@EqualsAndHashCode
public class RoleName {

	private static final int MIN_LENGTH = 2;
	private static final int MAX_LENGTH = 50;

	/**
	 * 角色名称
	 */
	private final String value;

	private RoleName(String value) {
		this.value = value;
	}

	/**
	 * 创建角色名称值对象
	 *
	 * @param value 角色名称
	 * @return 角色名称值对象
	 */
	public static RoleName of(String value) {
		ClientAssert.hasText(value, "ROLE-003", "角色名称不能为空");
		String trimmedValue = value.trim();
		ClientAssert.isTrue(trimmedValue.length() >= MIN_LENGTH && trimmedValue.length() <= MAX_LENGTH,
			"ROLE-004", "角色名称长度必须在" + MIN_LENGTH + "-" + MAX_LENGTH + "个字符之间");
		return new RoleName(trimmedValue);
	}

	@Override
	public String toString() {
		return value;
	}
}
