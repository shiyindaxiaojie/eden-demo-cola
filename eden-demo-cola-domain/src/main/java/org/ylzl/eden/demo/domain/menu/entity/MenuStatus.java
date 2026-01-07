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

package org.ylzl.eden.demo.domain.menu.entity;

import lombok.Getter;

/**
 * 菜单状态枚举
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Getter
public enum MenuStatus {

	/**
	 * 隐藏
	 */
	HIDDEN(0, "隐藏"),

	/**
	 * 显示
	 */
	VISIBLE(1, "显示");

	private final int value;
	private final String desc;

	MenuStatus(int value, String desc) {
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
	public static MenuStatus of(int code) {
		for (MenuStatus status : values()) {
			if (status.value == code) {
				return status;
			}
		}
		return HIDDEN;
	}
}
