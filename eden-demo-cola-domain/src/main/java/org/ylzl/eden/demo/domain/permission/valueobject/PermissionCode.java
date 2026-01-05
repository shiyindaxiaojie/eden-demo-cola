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

package org.ylzl.eden.demo.domain.permission.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.util.regex.Pattern;

/**
 * 权限编码值对象
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@Getter
@EqualsAndHashCode
public class PermissionCode {

	private static final Pattern CODE_PATTERN = Pattern.compile("^[a-z][a-z0-9_:]{1,99}$");

	/**
	 * 权限编码
	 */
	private final String value;

	private PermissionCode(String value) {
		this.value = value;
	}

	/**
	 * 创建权限编码值对象
	 *
	 * @param value 权限编码
	 * @return 权限编码值对象
	 */
	public static PermissionCode of(String value) {
		ClientAssert.notBlank(value, "PERM-001", "权限编码不能为空");
		String lowerValue = value.toLowerCase().trim();
		ClientAssert.isTrue(CODE_PATTERN.matcher(lowerValue).matches(),
			"PERM-002", "权限编码格式不正确");
		return new PermissionCode(lowerValue);
	}

	/**
	 * 获取模块名
	 *
	 * @return 模块名
	 */
	public String getModule() {
		int index = value.indexOf(':');
		return index > 0 ? value.substring(0, index) : value;
	}

	/**
	 * 获取操作名
	 *
	 * @return 操作名
	 */
	public String getAction() {
		int index = value.indexOf(':');
		return index > 0 ? value.substring(index + 1) : "";
	}

	@Override
	public String toString() {
		return value;
	}
}
