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

package org.ylzl.eden.demo.infrastructure.permission.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ylzl.eden.demo.infrastructure.permission.database.dataobject.PermissionDO;

import java.util.List;

/**
 * 权限表映射器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Mapper
public interface PermissionMapper extends BaseMapper<PermissionDO> {

	/**
	 * 根据编码查询权限
	 *
	 * @param code 权限编码
	 * @return 权限数据对象
	 */
	PermissionDO selectByCode(@Param("code") String code);

	/**
	 * 统计编码数量
	 *
	 * @param code 权限编码
	 * @return 数量
	 */
	int countByCode(@Param("code") String code);

	/**
	 * 根据ID列表查询权限
	 *
	 * @param ids 权限ID列表
	 * @return 权限列表
	 */
	List<PermissionDO> selectByIds(@Param("ids") List<Long> ids);

	/**
	 * 查询所有权限
	 *
	 * @return 权限列表
	 */
	List<PermissionDO> selectAll();

	/**
	 * 检查权限是否被角色使用
	 *
	 * @param permissionId 权限ID
	 * @return 使用数量
	 */
	int countRoleByPermissionId(@Param("permissionId") Long permissionId);

	/**
	 * 根据父级ID查询权限
	 *
	 * @param parentId 父级ID
	 * @return 权限列表
	 */
	List<PermissionDO> selectByParentId(@Param("parentId") Long parentId);
}
