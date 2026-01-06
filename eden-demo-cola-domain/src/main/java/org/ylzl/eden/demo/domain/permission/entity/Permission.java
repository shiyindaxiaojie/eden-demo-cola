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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ylzl.eden.cola.domain.Entity;
import org.ylzl.eden.demo.domain.permission.valueobject.PermissionCode;

import java.time.LocalDateTime;

/**
 * 权限聚合根
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Permission {

	/**
	 * 权限ID
	 */
	private Long id;

	/**
	 * 权限编码
	 */
	private PermissionCode code;

	/**
	 * 权限名称
	 */
	private String name;

	/**
	 * 权限类型
	 */
	private PermissionType type;

	/**
	 * 父级ID
	 */
	private Long parentId;

	/**
	 * 权限描述
	 */
	private String description;

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
	 * 创建权限
	 *
	 * @param code 权限编码
	 * @param name 权限名称
	 * @param type 权限类型
	 * @return 权限实体
	 */
	public static Permission create(PermissionCode code, String name, PermissionType type) {
		Permission permission = new Permission();
		permission.code = code;
		permission.name = name;
		permission.type = type;
		permission.parentId = 0L;
		permission.sort = 0;
		permission.createdAt = LocalDateTime.now();
		permission.updatedAt = permission.createdAt;
		return permission;
	}

	/**
	 * 从持久化数据重建权限
	 *
	 * @param id          权限ID
	 * @param code        权限编码
	 * @param name        权限名称
	 * @param type        权限类型
	 * @param parentId    父级ID
	 * @param description 描述
	 * @param sort        排序
	 * @param createdAt   创建时间
	 * @param updatedAt   更新时间
	 * @return 权限实体
	 */
	public static Permission reconstitute(Long id, PermissionCode code, String name,
										  PermissionType type, Long parentId, String description,
										  Integer sort, LocalDateTime createdAt, LocalDateTime updatedAt) {
		Permission permission = new Permission();
		permission.id = id;
		permission.code = code;
		permission.name = name;
		permission.type = type;
		permission.parentId = parentId;
		permission.description = description;
		permission.sort = sort;
		permission.createdAt = createdAt;
		permission.updatedAt = updatedAt;
		return permission;
	}

	/**
	 * 更新权限信息
	 *
	 * @param name        权限名称
	 * @param description 描述
	 * @param sort        排序
	 */
	public void updateInfo(String name, String description, Integer sort) {
		if (name != null && !name.trim().isEmpty()) {
			this.name = name.trim();
		}
		this.description = description;
		if (sort != null) {
			this.sort = sort;
		}
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 设置父级
	 *
	 * @param parentId 父级ID
	 */
	public void setParent(Long parentId) {
		this.parentId = parentId != null ? parentId : 0L;
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 是否为菜单权限
	 *
	 * @return 是否菜单权限
	 */
	public boolean isMenuPermission() {
		return this.type == PermissionType.MENU;
	}

	/**
	 * 是否为按钮权限
	 *
	 * @return 是否按钮权限
	 */
	public boolean isButtonPermission() {
		return this.type == PermissionType.BUTTON;
	}

	/**
	 * 是否为根权限
	 *
	 * @return 是否根权限
	 */
	public boolean isRoot() {
		return this.parentId == null || this.parentId == 0L;
	}
}
