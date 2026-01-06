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

package org.ylzl.eden.demo.infrastructure.role.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ylzl.eden.demo.infrastructure.role.database.dataobject.RoleDO;

import java.util.List;

/**
 * 角色表映射器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleDO> {

	/**
	 * 根据编码查询角色
	 *
	 * @param code 角色编码
	 * @return 角色数据对象
	 */
	RoleDO selectByCode(@Param("code") String code);

	/**
	 * 统计编码数量
	 *
	 * @param code 角色编码
	 * @return 数量
	 */
	int countByCode(@Param("code") String code);

	/**
	 * 根据ID列表查询角色
	 *
	 * @param ids 角色ID列表
	 * @return 角色列表
	 */
	List<RoleDO> selectByIds(@Param("ids") List<Long> ids);

	/**
	 * 查询所有启用的角色
	 *
	 * @return 角色列表
	 */
	List<RoleDO> selectAllEnabled();

	/**
	 * 检查角色是否被用户使用
	 *
	 * @param roleId 角色ID
	 * @return 使用数量
	 */
	int countUserByRoleId(@Param("roleId") Long roleId);
}
