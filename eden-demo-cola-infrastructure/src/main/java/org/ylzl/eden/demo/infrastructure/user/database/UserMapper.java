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

package org.ylzl.eden.demo.infrastructure.user.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.ylzl.eden.demo.client.user.dto.query.UserListByPageQry;
import org.ylzl.eden.demo.infrastructure.user.database.dataobject.UserDO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户记录表映射器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

	/**
	 * 根据查询条件分页查询用户列表
	 *
	 * @param query 查询条件
	 * @return 用户列表
	 */
	List<UserDO> selectPage(UserListByPageQry query);

	/**
	 * 根据登录账号查询用户
	 *
	 * @param login 登录账号
	 * @return 用户数据对象
	 */
	UserDO selectByLogin(@Param("login") String login);

	/**
	 * 根据邮箱查询用户
	 *
	 * @param email 邮箱
	 * @return 用户数据对象
	 */
	UserDO selectByEmail(@Param("email") String email);

	/**
	 * 统计登录账号数量
	 *
	 * @param login 登录账号
	 * @return 数量
	 */
	int countByLogin(@Param("login") String login);

	/**
	 * 统计邮箱数量
	 *
	 * @param email 邮箱
	 * @return 数量
	 */
	int countByEmail(@Param("email") String email);

	/**
	 * 统计邮箱数量（排除指定用户）
	 *
	 * @param email         邮箱
	 * @param excludeUserId 排除的用户ID
	 * @return 数量
	 */
	int countByEmailExcludeUser(@Param("email") String email, @Param("excludeUserId") Long excludeUserId);
}
