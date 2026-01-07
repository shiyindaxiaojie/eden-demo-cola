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

package org.ylzl.eden.demo.infrastructure.user.database.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.ylzl.eden.demo.domain.user.entity.User;
import org.ylzl.eden.demo.domain.user.valueobject.Email;
import org.ylzl.eden.demo.domain.user.valueobject.Login;
import org.ylzl.eden.demo.domain.user.valueobject.Password;
import org.ylzl.eden.demo.infrastructure.user.database.dataobject.UserDO;

/**
 * 用户领域实体转换器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Mapper(componentModel = "spring",
	nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserConvertor {

	/**
	 * 领域实体转数据对象
	 *
	 * @param user 用户实体
	 * @return 用户数据对象
	 */
	@Mapping(target = "login", expression = "java(user.getLogin() != null ? user.getLogin().getValue() : null)")
	@Mapping(target = "email", expression = "java(user.getEmail() != null ? user.getEmail().getValue() : null)")
	@Mapping(target = "password", expression = "java(user.getPassword() != null ? user.getPassword().getHashedValue() : null)")
	@Mapping(target = "status", expression = "java(user.getStatus() != null ? user.getStatus().ordinal() : null)")
	UserDO toDataObject(User user);

	/**
	 * 数据对象转领域实体
	 *
	 * @param userDO 用户数据对象
	 * @return 用户实体
	 */
	default User toEntity(UserDO userDO) {
		if (userDO == null) {
			return null;
		}
		return User.reconstitute(
			userDO.getId(),
			Login.of(userDO.getLogin()),
			Email.of(userDO.getEmail()),
			Password.fromEncrypted(userDO.getPassword()),
			User.UserStatus.values()[userDO.getStatus()],
			userDO.getCreatedAt(),
			userDO.getUpdatedAt()
		);
	}
}
