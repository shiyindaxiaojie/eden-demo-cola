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

package org.ylzl.eden.demo.app.user.executor.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.cola.dto.SingleResponse;
import org.ylzl.eden.demo.app.user.assembler.UserAssembler;
import org.ylzl.eden.demo.client.user.dto.UserDTO;
import org.ylzl.eden.demo.client.user.dto.query.UserByIdQry;
import org.ylzl.eden.demo.infrastructure.user.database.dataobject.UserDO;
import org.ylzl.eden.demo.infrastructure.user.database.UserMapper;
import org.ylzl.eden.spring.framework.error.ClientAssert;

/**
 * 根据主键获取用户信息指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class UserByIdQryExe {

	private final UserMapper userMapper;

	private final UserAssembler userAssembler;

	public SingleResponse<UserDTO> execute(UserByIdQry query) {
		UserDO userDO = userMapper.selectById(query.getId());
		ClientAssert.notNull(userDO, "USER-FOUND-404", query.getId());
		return SingleResponse.of(userAssembler.toDTO(userDO));
	}
}
