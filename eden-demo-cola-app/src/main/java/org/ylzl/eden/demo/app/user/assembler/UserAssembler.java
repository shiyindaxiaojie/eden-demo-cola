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

package org.ylzl.eden.demo.app.user.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.ylzl.eden.demo.client.user.dto.UserDTO;
import org.ylzl.eden.demo.infrastructure.user.database.dataobject.UserDO;

import java.util.List;

/**
 * 用户领域组装器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Mapper(componentModel = "spring",
	nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserAssembler {

	/**
	 * 数据对象转DTO
	 *
	 * @param dataObject 数据对象
	 * @return DTO
	 */
	UserDTO toDTO(UserDO dataObject);

	/**
	 * 数据对象列表转DTO列表
	 *
	 * @param dataObjectList 数据对象列表
	 * @return DTO列表
	 */
	List<UserDTO> toDTOList(List<UserDO> dataObjectList);
}
