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

package org.ylzl.eden.demo.domain.permission.entity;

import lombok.Getter;

/**
 * 权限类型枚举
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@Getter
public enum PermissionType {

	/**
	 * 菜单权限
	 */
	MENU(1, "菜单"),

	/**
	 * 按钮权限
	 */
	BUTTON(2, "按钮"),

	/**
	 * 接口权限
	 */
	API(3, "接口");

	private final int value;
	private final String desc;

	PermissionType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	/**
	 * 获取值
	 *
	 * @return 值
	 */
	public Integer getValue() {
		return this.value;
	}

	/**
	 * 获取编码（兼容 MapStruct）
	 *
	 * @return 编码
	 */
	public Integer getCode() {
		return this.value;
	}

	/**
	 * 根据编码获取枚举
	 *
	 * @param code 编码
	 * @return 枚举值
	 */
	public static PermissionType of(int code) {
		for (PermissionType type : values()) {
			if (type.value == code) {
				return type;
			}
		}
		return MENU;
	}
}
