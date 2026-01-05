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

package org.ylzl.eden.demo.infrastructure.permission.database.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.ylzl.eden.demo.domain.permission.entity.Permission;
import org.ylzl.eden.demo.domain.permission.entity.PermissionType;
import org.ylzl.eden.demo.domain.permission.valueobject.PermissionCode;
import org.ylzl.eden.demo.infrastructure.permission.database.dataobject.PermissionDO;

/**
 * 权限领域实体转换器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@Mapper(componentModel = "spring",
	nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PermissionConvertor {

	/**
	 * 领域实体转数据对象
	 *
	 * @param permission 权限实体
	 * @return 权限数据对象
	 */
	@Mapping(target = "code", expression = "java(permission.getCode() != null ? permission.getCode().getValue() : null)")
	@Mapping(target = "type", expression = "java(permission.getType() != null ? permission.getType().getCode() : null)")
	PermissionDO toDataObject(Permission permission);

	/**
	 * 数据对象转领域实体
	 *
	 * @param permissionDO 权限数据对象
	 * @return 权限实体
	 */
	default Permission toEntity(PermissionDO permissionDO) {
		if (permissionDO == null) {
			return null;
		}
		return Permission.reconstitute(
			permissionDO.getId(),
			PermissionCode.of(permissionDO.getCode()),
			permissionDO.getName(),
			PermissionType.of(permissionDO.getType()),
			permissionDO.getParentId(),
			permissionDO.getDescription(),
			permissionDO.getSort(),
			permissionDO.getCreatedAt(),
			permissionDO.getUpdatedAt()
		);
	}
}
