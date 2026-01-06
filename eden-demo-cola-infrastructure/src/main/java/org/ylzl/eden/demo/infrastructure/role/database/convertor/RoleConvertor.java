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

package org.ylzl.eden.demo.infrastructure.role.database.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.ylzl.eden.demo.domain.role.entity.Role;
import org.ylzl.eden.demo.domain.role.entity.RoleStatus;
import org.ylzl.eden.demo.domain.role.valueobject.RoleCode;
import org.ylzl.eden.demo.domain.role.valueobject.RoleName;
import org.ylzl.eden.demo.infrastructure.role.database.dataobject.RoleDO;

/**
 * 角色领域实体转换器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@Mapper(componentModel = "spring",
	nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleConvertor {

	/**
	 * 领域实体转数据对象
	 *
	 * @param role 角色实体
	 * @return 角色数据对象
	 */
	@Mapping(target = "code", expression = "java(role.getCode() != null ? role.getCode().getValue() : null)")
	@Mapping(target = "name", expression = "java(role.getName() != null ? role.getName().getValue() : null)")
	@Mapping(target = "status", expression = "java(role.getStatus() != null ? role.getStatus().getCode() : null)")
	RoleDO toDataObject(Role role);

	/**
	 * 数据对象转领域实体
	 *
	 * @param roleDO 角色数据对象
	 * @return 角色实体
	 */
	default Role toEntity(RoleDO roleDO) {
		if (roleDO == null) {
			return null;
		}
		return Role.reconstitute(
			roleDO.getId(),
			RoleCode.of(roleDO.getCode()),
			RoleName.of(roleDO.getName()),
			roleDO.getDescription(),
			RoleStatus.of(roleDO.getStatus()),
			roleDO.getSort(),
			roleDO.getCreatedAt(),
			roleDO.getUpdatedAt()
		);
	}
}
