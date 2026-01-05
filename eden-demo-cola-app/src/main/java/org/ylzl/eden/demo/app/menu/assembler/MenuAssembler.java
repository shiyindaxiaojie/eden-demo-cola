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

package org.ylzl.eden.demo.app.menu.assembler;

import org.springframework.stereotype.Component;
import org.ylzl.eden.demo.client.menu.dto.MenuDTO;
import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO;
import org.ylzl.eden.demo.domain.menu.entity.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单领域组装器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@Component
public class MenuAssembler {

	public MenuDTO toDTO(Menu menu) {
		if (menu == null) {
			return null;
		}
		return MenuDTO.builder()
			.id(menu.getId())
			.name(menu.getName())
			.path(menu.getPath() != null ? menu.getPath().getValue() : null)
			.icon(menu.getIcon())
			.parentId(menu.getParentId())
			.sort(menu.getSort())
			.status(menu.getStatus() != null ? menu.getStatus().getValue() : null)
			.component(menu.getComponent())
			.build();
	}

	public List<MenuDTO> toDTOList(List<Menu> menus) {
		if (menus == null) {
			return new ArrayList<>();
		}
		return menus.stream()
			.map(this::toDTO)
			.collect(Collectors.toList());
	}

	public List<MenuTreeDTO> buildTree(List<Menu> menus) {
		if (menus == null || menus.isEmpty()) {
			return new ArrayList<>();
		}

		Map<Long, List<Menu>> childrenMap = menus.stream()
			.filter(m -> m.getParentId() != null && m.getParentId() > 0)
			.collect(Collectors.groupingBy(Menu::getParentId));

		return menus.stream()
			.filter(m -> m.getParentId() == null || m.getParentId() == 0)
			.map(m -> buildTreeNode(m, childrenMap))
			.collect(Collectors.toList());
	}

	private MenuTreeDTO buildTreeNode(Menu menu, Map<Long, List<Menu>> childrenMap) {
		MenuTreeDTO dto = MenuTreeDTO.builder()
			.id(menu.getId())
			.name(menu.getName())
			.path(menu.getPath() != null ? menu.getPath().getValue() : null)
			.icon(menu.getIcon())
			.parentId(menu.getParentId())
			.sort(menu.getSort())
			.status(menu.getStatus() != null ? menu.getStatus().getValue() : null)
			.component(menu.getComponent())
			.build();

		List<Menu> children = childrenMap.get(menu.getId());
		if (children != null && !children.isEmpty()) {
			dto.setChildren(children.stream()
				.map(c -> buildTreeNode(c, childrenMap))
				.collect(Collectors.toList()));
		}
		return dto;
	}
}
