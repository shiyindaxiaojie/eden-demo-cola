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

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色关联表映射器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Mapper
public interface UserRoleMapper {

	/**
	 * 批量插入用户角色关联
	 *
	 * @param userId  用户ID
	 * @param roleIds 角色ID列表
	 */
	void batchInsert(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

	/**
	 * 删除用户的所有角色关联
	 *
	 * @param userId 用户ID
	 */
	void deleteByUserId(@Param("userId") Long userId);

	/**
	 * 查询用户的角色ID列表
	 *
	 * @param userId 用户ID
	 * @return 角色ID列表
	 */
	List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);
}
