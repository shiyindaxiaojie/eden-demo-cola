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

package org.ylzl.eden.demo.domain.user.gateway;

import org.ylzl.eden.demo.domain.user.entity.User;

/**
 * 用户领域防腐层
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
public interface UserGateway {

	/**
	 * 新增用户
	 *
	 * @param user
	 */
	void save(User user);

	/**
	 * 修改用户
	 *
	 * @param user
	 */
	void updateById(User user);

	/**
	 * 删除用户
	 *
	 * @param user
	 */
	void deleteById(User user);
}
