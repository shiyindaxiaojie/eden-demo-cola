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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ylzl.eden.cola.domain.Entity;
import org.ylzl.eden.demo.domain.role.valueobject.RoleCode;
import org.ylzl.eden.demo.domain.role.valueobject.RoleName;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.time.LocalDateTime;

/**
 * 角色聚合根
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Role {

	/**
	 * 角色ID
	 */
	private Long id;

	/**
	 * 角色编码
	 */
	private RoleCode code;

	/**
	 * 角色名称
	 */
	private RoleName name;

	/**
	 * 角色描述
	 */
	private String description;

	/**
	 * 角色状态
	 */
	private RoleStatus status;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 创建时间
	 */
	private LocalDateTime createdAt;

	/**
	 * 更新时间
	 */
	private LocalDateTime updatedAt;

	/**
	 * 创建角色
	 *
	 * @param code 角色编码
	 * @param name 角色名称
	 * @return 角色实体
	 */
	public static Role create(RoleCode code, RoleName name) {
		Role role = new Role();
		role.code = code;
		role.name = name;
		role.status = RoleStatus.ENABLED;
		role.sort = 0;
		role.createdAt = LocalDateTime.now();
		role.updatedAt = role.createdAt;
		return role;
	}

	/**
	 * 从持久化数据重建角色
	 *
	 * @param id          角色ID
	 * @param code        角色编码
	 * @param name        角色名称
	 * @param description 描述
	 * @param status      状态
	 * @param sort        排序
	 * @param createdAt   创建时间
	 * @param updatedAt   更新时间
	 * @return 角色实体
	 */
	public static Role reconstitute(Long id, RoleCode code, RoleName name,
									String description, RoleStatus status, Integer sort,
									LocalDateTime createdAt, LocalDateTime updatedAt) {
		Role role = new Role();
		role.id = id;
		role.code = code;
		role.name = name;
		role.description = description;
		role.status = status;
		role.sort = sort;
		role.createdAt = createdAt;
		role.updatedAt = updatedAt;
		return role;
	}

	/**
	 * 更新角色信息
	 *
	 * @param name        角色名称
	 * @param description 描述
	 * @param sort        排序
	 */
	public void updateInfo(RoleName name, String description, Integer sort) {
		if (name != null) {
			this.name = name;
		}
		this.description = description;
		if (sort != null) {
			this.sort = sort;
		}
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 启用角色
	 */
	public void enable() {
		ClientAssert.isTrue(this.status == RoleStatus.DISABLED, "ROLE-008", "角色已启用");
		this.status = RoleStatus.ENABLED;
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 禁用角色
	 */
	public void disable() {
		ClientAssert.isTrue(this.status == RoleStatus.ENABLED, "ROLE-009", "角色已禁用");
		this.status = RoleStatus.DISABLED;
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 是否启用
	 *
	 * @return 是否启用
	 */
	public boolean isEnabled() {
		return this.status == RoleStatus.ENABLED;
	}
}
