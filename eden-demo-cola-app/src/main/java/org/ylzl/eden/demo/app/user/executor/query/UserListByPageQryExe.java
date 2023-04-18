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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.demo.app.user.assembler.UserAssembler;
import org.ylzl.eden.demo.client.user.dto.UserDTO;
import org.ylzl.eden.demo.client.user.dto.query.UserListByPageQry;
import org.ylzl.eden.demo.infrastructure.user.database.dataobject.UserDO;
import org.ylzl.eden.demo.infrastructure.user.database.UserMapper;
import org.ylzl.eden.cola.dto.PageResponse;

/**
 * 根据分页查询获取用户列表指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class UserListByPageQryExe {

	private final UserMapper userMapper;

	private final UserAssembler userAssembler;

	public PageResponse<UserDTO> execute(UserListByPageQry query) {
		Page<UserDO> page = PageHelper.startPage(query.getPageIndex(), query.getPageSize())
			.doSelectPage(() -> userMapper.selectPage(query));

		return PageResponse.of(userAssembler.toDTOList(page.getResult()),
			Integer.parseInt(String.valueOf(page.getTotal())),
			query.getPageSize(), query.getPageIndex());
	}
}
