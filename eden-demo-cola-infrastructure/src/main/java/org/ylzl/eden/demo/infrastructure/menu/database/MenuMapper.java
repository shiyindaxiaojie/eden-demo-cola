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

package org.ylzl.eden.demo.infrastructure.menu.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ylzl.eden.demo.infrastructure.menu.database.dataobject.MenuDO;

import java.util.List;

/**
 * 菜单表映射器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuDO> {

	/**
	 * 统计路径数量
	 *
	 * @param path 菜单路径
	 * @return 数量
	 */
	int countByPath(@Param("path") String path);

	/**
	 * 统计路径数量（排除指定ID）
	 *
	 * @param path      菜单路径
	 * @param excludeId 排除的ID
	 * @return 数量
	 */
	int countByPathExcludeId(@Param("path") String path, @Param("excludeId") Long excludeId);

	/**
	 * 根据ID列表查询菜单
	 *
	 * @param ids 菜单ID列表
	 * @return 菜单列表
	 */
	List<MenuDO> selectByIds(@Param("ids") List<Long> ids);

	/**
	 * 查询所有菜单
	 *
	 * @return 菜单列表
	 */
	List<MenuDO> selectAll();

	/**
	 * 查询所有可见菜单
	 *
	 * @return 菜单列表
	 */
	List<MenuDO> selectAllVisible();

	/**
	 * 根据父级ID查询菜单
	 *
	 * @param parentId 父级ID
	 * @return 菜单列表
	 */
	List<MenuDO> selectByParentId(@Param("parentId") Long parentId);

	/**
	 * 统计子菜单数量
	 *
	 * @param parentId 父级ID
	 * @return 数量
	 */
	int countByParentId(@Param("parentId") Long parentId);

	/**
	 * 检查菜单是否被角色使用
	 *
	 * @param menuId 菜单ID
	 * @return 使用数量
	 */
	int countRoleByMenuId(@Param("menuId") Long menuId);
}
