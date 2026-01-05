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

package org.ylzl.eden.demo.domain.menu.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.util.regex.Pattern;

/**
 * 菜单路径值对象
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@Getter
@EqualsAndHashCode
public class MenuPath {

	private static final Pattern PATH_PATTERN = Pattern.compile("^/[a-zA-Z0-9/_-]*$");

	/**
	 * 菜单路径
	 */
	private final String value;

	private MenuPath(String value) {
		this.value = value;
	}

	/**
	 * 创建菜单路径值对象
	 *
	 * @param value 菜单路径
	 * @return 菜单路径值对象
	 */
	public static MenuPath of(String value) {
		ClientAssert.notBlank(value, "MENU-002", "菜单路径不能为空");
		String trimmedValue = value.trim();
		ClientAssert.isTrue(PATH_PATTERN.matcher(trimmedValue).matches(),
			"MENU-003", "菜单路径格式不正确，必须以/开头");
		return new MenuPath(trimmedValue);
	}

	/**
	 * 获取路径层级
	 *
	 * @return 层级数
	 */
	public int getLevel() {
		return (int) value.chars().filter(ch -> ch == '/').count();
	}

	/**
	 * 获取父路径
	 *
	 * @return 父路径
	 */
	public String getParentPath() {
		int lastIndex = value.lastIndexOf('/');
		return lastIndex > 0 ? value.substring(0, lastIndex) : "/";
	}

	@Override
	public String toString() {
		return value;
	}
}
