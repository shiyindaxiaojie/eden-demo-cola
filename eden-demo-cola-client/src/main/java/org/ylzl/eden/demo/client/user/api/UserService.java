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

package org.ylzl.eden.demo.client.user.api;

import org.ylzl.eden.demo.client.user.dto.UserDTO;
import org.ylzl.eden.demo.client.user.dto.command.UserAddCmd;
import org.ylzl.eden.demo.client.user.dto.command.UserModifyCmd;
import org.ylzl.eden.demo.client.user.dto.command.UserRemoveCmd;
import org.ylzl.eden.demo.client.user.dto.query.UserByIdQry;
import org.ylzl.eden.demo.client.user.dto.query.UserListByPageQry;
import org.ylzl.eden.cola.dto.PageResponse;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.cola.dto.SingleResponse;

/**
 * 用户领域业务接口
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
public interface UserService {

	/**
	 * 创建用户
	 *
	 * @param cmd
	 */
	Response createUser(UserAddCmd cmd);

	/**
	 * 修改用户
	 *
	 * @param cmd
	 */
	Response modifyUser(UserModifyCmd cmd);

	/**
	 * 删除用户
	 *
	 * @param cmd
	 */
	Response removeUser(UserRemoveCmd cmd);

	/**
	 * 获取用户信息
	 *
	 * @param query
	 * @return
	 */
	SingleResponse<UserDTO> getUserById(UserByIdQry query);

	/**
	 * 获取用户分页
	 *
	 * @param query
	 * @return
	 */
	PageResponse<UserDTO> listUserByPage(UserListByPageQry query);
}
