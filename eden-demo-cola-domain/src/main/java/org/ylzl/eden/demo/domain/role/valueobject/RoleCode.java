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

import java.util.regex.Pattern;

/**
 * 角色编码值对象
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@Getter
@EqualsAndHashCode
public class RoleCode {

	private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z][A-Z0-9_]{1,29}$");

	/**
	 * 角色编码
	 */
	private final String value;

	private RoleCode(String value) {
		this.value = value;
	}

	/**
	 * 创建角色编码值对象
	 *
	 * @param value 角色编码
	 * @return 角色编码值对象
	 */
	public static RoleCode of(String value) {
		ClientAssert.hasText(value, "ROLE-001", "角色编码不能为空");
		String upperValue = value.toUpperCase().trim();
		ClientAssert.isTrue(CODE_PATTERN.matcher(upperValue).matches(),
			"ROLE-002", "角色编码格式不正确，只能包含大写字母和下划线，长度2-30位");
		return new RoleCode(upperValue);
	}

	@Override
	public String toString() {
		return value;
	}
}
