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

package org.ylzl.eden.demo.infrastructure.menu.database.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.ylzl.eden.demo.domain.menu.entity.Menu;
import org.ylzl.eden.demo.domain.menu.entity.MenuStatus;
import org.ylzl.eden.demo.domain.menu.valueobject.MenuPath;
import org.ylzl.eden.demo.infrastructure.menu.database.dataobject.MenuDO;

/**
 * 菜单领域实体转换器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Mapper(componentModel = "spring",
	nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MenuConvertor {

	/**
	 * 领域实体转数据对象
	 *
	 * @param menu 菜单实体
	 * @return 菜单数据对象
	 */
	@Mapping(target = "path", expression = "java(menu.getPath() != null ? menu.getPath().getValue() : null)")
	@Mapping(target = "status", expression = "java(menu.getStatus() != null ? menu.getStatus().getCode() : null)")
	MenuDO toDataObject(Menu menu);

	/**
	 * 数据对象转领域实体
	 *
	 * @param menuDO 菜单数据对象
	 * @return 菜单实体
	 */
	default Menu toEntity(MenuDO menuDO) {
		if (menuDO == null) {
			return null;
		}
		return Menu.reconstitute(
			menuDO.getId(),
			menuDO.getName(),
			MenuPath.of(menuDO.getPath()),
			menuDO.getIcon(),
			menuDO.getParentId(),
			menuDO.getSort(),
			MenuStatus.of(menuDO.getStatus()),
			menuDO.getComponent(),
			menuDO.getCreatedAt(),
			menuDO.getUpdatedAt()
		);
	}
}
