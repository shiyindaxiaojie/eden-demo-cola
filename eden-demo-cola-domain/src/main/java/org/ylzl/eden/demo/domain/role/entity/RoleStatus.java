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

package org.ylzl.eden.demo.domain.role.entity;

import lombok.Getter;

/**
 * 角色状态枚举
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Getter
public enum RoleStatus {

	/**
	 * 禁用
	 */
	DISABLED(0, "禁用"),

	/**
	 * 启用
	 */
	ENABLED(1, "启用");

	private final int code;
	private final String desc;

	RoleStatus(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 获取值
	 *
	 * @return 值
	 */
	public Integer getValue() {
		return this.code;
	}

	/**
	 * 根据编码获取枚举
	 *
	 * @param code 编码
	 * @return 枚举值
	 */
	public static RoleStatus of(int code) {
		for (RoleStatus status : values()) {
			if (status.code == code) {
				return status;
			}
		}
		return DISABLED;
	}
}
