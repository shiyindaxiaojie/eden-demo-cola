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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ylzl.eden.cola.domain.Entity;
import org.ylzl.eden.demo.domain.menu.valueobject.MenuPath;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.time.LocalDateTime;

/**
 * 菜单聚合根
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Menu {

	/**
	 * 菜单ID
	 */
	private Long id;

	/**
	 * 菜单名称
	 */
	private String name;

	/**
	 * 路由路径
	 */
	private MenuPath path;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 父级ID
	 */
	private Long parentId;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 状态
	 */
	private MenuStatus status;

	/**
	 * 组件路径
	 */
	private String component;

	/**
	 * 创建时间
	 */
	private LocalDateTime createdAt;

	/**
	 * 更新时间
	 */
	private LocalDateTime updatedAt;

	/**
	 * 创建菜单
	 *
	 * @param name     菜单名称
	 * @param path     路由路径
	 * @param parentId 父级ID
	 * @return 菜单实体
	 */
	public static Menu create(String name, MenuPath path, Long parentId) {
		ClientAssert.hasText(name, "MENU-001", "菜单名称不能为空");
		Menu menu = new Menu();
		menu.name = name.trim();
		menu.path = path;
		menu.parentId = parentId != null ? parentId : 0L;
		menu.sort = 0;
		menu.status = MenuStatus.VISIBLE;
		menu.createdAt = LocalDateTime.now();
		menu.updatedAt = menu.createdAt;
		return menu;
	}

	/**
	 * 从持久化数据重建菜单
	 *
	 * @param id        菜单ID
	 * @param name      菜单名称
	 * @param path      路由路径
	 * @param icon      图标
	 * @param parentId  父级ID
	 * @param sort      排序
	 * @param status    状态
	 * @param component 组件路径
	 * @param createdAt 创建时间
	 * @param updatedAt 更新时间
	 * @return 菜单实体
	 */
	public static Menu reconstitute(Long id, String name, MenuPath path, String icon,
									Long parentId, Integer sort, MenuStatus status,
									String component, LocalDateTime createdAt, LocalDateTime updatedAt) {
		Menu menu = new Menu();
		menu.id = id;
		menu.name = name;
		menu.path = path;
		menu.icon = icon;
		menu.parentId = parentId;
		menu.sort = sort;
		menu.status = status;
		menu.component = component;
		menu.createdAt = createdAt;
		menu.updatedAt = updatedAt;
		return menu;
	}

	/**
	 * 更新菜单信息
	 *
	 * @param name      菜单名称
	 * @param icon      图标
	 * @param sort      排序
	 * @param component 组件路径
	 */
	public void updateInfo(String name, String icon, Integer sort, String component) {
		if (name != null && !name.trim().isEmpty()) {
			this.name = name.trim();
		}
		this.icon = icon;
		if (sort != null) {
			this.sort = sort;
		}
		this.component = component;
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 更新路径
	 *
	 * @param path 路由路径
	 */
	public void updatePath(MenuPath path) {
		this.path = path;
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 设置父级
	 *
	 * @param parentId 父级ID
	 */
	public void setParent(Long parentId) {
		ClientAssert.isTrue(parentId == null || !parentId.equals(this.id),
			"MENU-008", "不能将菜单设置为自己的子菜单");
		this.parentId = parentId != null ? parentId : 0L;
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 显示菜单
	 */
	public void show() {
		this.status = MenuStatus.VISIBLE;
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 隐藏菜单
	 */
	public void hide() {
		this.status = MenuStatus.HIDDEN;
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 是否可见
	 *
	 * @return 是否可见
	 */
	public boolean isVisible() {
		return this.status == MenuStatus.VISIBLE;
	}

	/**
	 * 是否为根菜单
	 *
	 * @return 是否根菜单
	 */
	public boolean isRootMenu() {
		return this.parentId == null || this.parentId == 0L;
	}
}
