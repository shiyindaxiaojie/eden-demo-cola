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

package org.ylzl.eden.demo.client.menu.api;

import org.ylzl.eden.demo.client.menu.dto.MenuDTO;
import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO;
import org.ylzl.eden.demo.client.menu.dto.command.*;
import org.ylzl.eden.demo.client.menu.dto.query.*;
import org.ylzl.eden.cola.dto.MultiResponse;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.cola.dto.SingleResponse;

/**
 * 菜单服务接口
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
public interface MenuService {

	/**
	 * 创建菜单
	 *
	 * @param cmd 创建菜单指令
	 * @return 响应结果
	 */
	Response createMenu(MenuAddCmd cmd);

	/**
	 * 修改菜单
	 *
	 * @param cmd 修改菜单指令
	 * @return 响应结果
	 */
	Response modifyMenu(MenuModifyCmd cmd);

	/**
	 * 删除菜单
	 *
	 * @param cmd 删除菜单指令
	 * @return 响应结果
	 */
	Response removeMenu(MenuRemoveCmd cmd);

	/**
	 * 根据主键获取菜单信息
	 *
	 * @param qry 查询条件
	 * @return 菜单信息
	 */
	SingleResponse<MenuDTO> getMenuById(MenuByIdQry qry);

	/**
	 * 获取菜单树列表
	 *
	 * @param qry 查询条件
	 * @return 菜单树列表
	 */
	MultiResponse<MenuTreeDTO> listMenuTree(MenuTreeQry qry);
}
