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

package org.ylzl.eden.demo.infrastructure.menu.gateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.ylzl.eden.demo.domain.menu.entity.Menu;
import org.ylzl.eden.demo.domain.menu.gateway.MenuGateway;
import org.ylzl.eden.demo.domain.menu.valueobject.MenuPath;
import org.ylzl.eden.demo.infrastructure.menu.database.MenuMapper;
import org.ylzl.eden.demo.infrastructure.menu.database.convertor.MenuConvertor;
import org.ylzl.eden.demo.infrastructure.menu.database.dataobject.MenuDO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 菜单领域防腐层实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
@Repository
public class MenuGatewayImpl implements MenuGateway {

	private final MenuMapper menuMapper;
	private final MenuConvertor menuConvertor;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Menu menu) {
		MenuDO menuDO = menuConvertor.toDataObject(menu);
		if (menu.getId() == null) {
			menuMapper.insert(menuDO);
		} else {
			menuMapper.updateById(menuDO);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Menu> findById(Long id) {
		MenuDO menuDO = menuMapper.selectById(id);
		return Optional.ofNullable(menuConvertor.toEntity(menuDO));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existsByPath(MenuPath path) {
		return menuMapper.countByPath(path.getValue()) > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existsByPathExcludeId(MenuPath path, Long excludeId) {
		return menuMapper.countByPathExcludeId(path.getValue(), excludeId) > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteById(Long id) {
		menuMapper.deleteById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Menu> findAll() {
		List<MenuDO> menuDOs = menuMapper.selectAll();
		return menuDOs.stream()
			.map(menuConvertor::toEntity)
			.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Menu> findAllVisible() {
		List<MenuDO> menuDOs = menuMapper.selectAllVisible();
		return menuDOs.stream()
			.map(menuConvertor::toEntity)
			.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Menu> findByIds(List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		List<MenuDO> menuDOs = menuMapper.selectByIds(ids);
		return menuDOs.stream()
			.map(menuConvertor::toEntity)
			.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Menu> findByParentId(Long parentId) {
		List<MenuDO> menuDOs = menuMapper.selectByParentId(parentId);
		return menuDOs.stream()
			.map(menuConvertor::toEntity)
			.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasChildren(Long menuId) {
		return menuMapper.countByParentId(menuId) > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isUsedByRole(Long menuId) {
		return menuMapper.countRoleByMenuId(menuId) > 0;
	}
}
