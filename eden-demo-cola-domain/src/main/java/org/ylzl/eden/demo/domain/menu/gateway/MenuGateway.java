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

package org.ylzl.eden.demo.domain.menu.gateway;

import org.ylzl.eden.demo.domain.menu.entity.Menu;
import org.ylzl.eden.demo.domain.menu.valueobject.MenuPath;

import java.util.List;
import java.util.Optional;

/**
 * 菜单领域防腐层
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
public interface MenuGateway {

	/**
	 * 保存菜单
	 *
	 * @param menu 菜单实体
	 */
	void save(Menu menu);

	/**
	 * 根据ID查询菜单
	 *
	 * @param id 菜单ID
	 * @return 菜单实体
	 */
	Optional<Menu> findById(Long id);

	/**
	 * 检查路径是否存在
	 *
	 * @param path 菜单路径
	 * @return 是否存在
	 */
	boolean existsByPath(MenuPath path);

	/**
	 * 检查路径是否存在（排除指定ID）
	 *
	 * @param path      菜单路径
	 * @param excludeId 排除的ID
	 * @return 是否存在
	 */
	boolean existsByPathExcludeId(MenuPath path, Long excludeId);

	/**
	 * 删除菜单
	 *
	 * @param id 菜单ID
	 */
	void deleteById(Long id);

	/**
	 * 查询所有菜单
	 *
	 * @return 菜单列表
	 */
	List<Menu> findAll();

	/**
	 * 查询所有可见菜单
	 *
	 * @return 菜单列表
	 */
	List<Menu> findAllVisible();

	/**
	 * 根据ID列表查询菜单
	 *
	 * @param ids 菜单ID列表
	 * @return 菜单列表
	 */
	List<Menu> findByIds(List<Long> ids);

	/**
	 * 根据父级ID查询菜单
	 *
	 * @param parentId 父级ID
	 * @return 菜单列表
	 */
	List<Menu> findByParentId(Long parentId);

	/**
	 * 检查是否有子菜单
	 *
	 * @param menuId 菜单ID
	 * @return 是否有子菜单
	 */
	boolean hasChildren(Long menuId);

	/**
	 * 检查菜单是否被角色使用
	 *
	 * @param menuId 菜单ID
	 * @return 是否被使用
	 */
	boolean isUsedByRole(Long menuId);
}
