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

package org.ylzl.eden.demo.app.menu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ylzl.eden.cola.dto.MultiResponse;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.cola.dto.SingleResponse;
import org.ylzl.eden.demo.app.menu.executor.command.*;
import org.ylzl.eden.demo.app.menu.executor.query.*;
import org.ylzl.eden.demo.client.menu.api.MenuService;
import org.ylzl.eden.demo.client.menu.dto.MenuDTO;
import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO;
import org.ylzl.eden.demo.client.menu.dto.command.*;
import org.ylzl.eden.demo.client.menu.dto.query.*;

/**
 * 菜单应用服务实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
@Service("menuService")
public class MenuServiceImpl implements MenuService {

	private final MenuAddCmdExe menuAddCmdExe;
	private final MenuModifyCmdExe menuModifyCmdExe;
	private final MenuRemoveCmdExe menuRemoveCmdExe;
	private final MenuByIdQryExe menuByIdQryExe;
	private final MenuTreeQryExe menuTreeQryExe;

	/**
	 * 创建菜单
	 *
	 * @param cmd 创建菜单指令
	 * @return 响应结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response createMenu(MenuAddCmd cmd) {
		return menuAddCmdExe.execute(cmd);
	}

	/**
	 * 修改菜单
	 *
	 * @param cmd 修改菜单指令
	 * @return 响应结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response modifyMenu(MenuModifyCmd cmd) {
		return menuModifyCmdExe.execute(cmd);
	}

	/**
	 * 删除菜单
	 *
	 * @param cmd 删除菜单指令
	 * @return 响应结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response removeMenu(MenuRemoveCmd cmd) {
		return menuRemoveCmdExe.execute(cmd);
	}

	/**
	 * 根据ID获取菜单信息
	 *
	 * @param qry 查询条件
	 * @return 菜单信息
	 */
	@Override
	public SingleResponse<MenuDTO> getMenuById(MenuByIdQry qry) {
		return menuByIdQryExe.execute(qry);
	}

	/**
	 * 获取菜单树列表
	 *
	 * @param qry 查询条件
	 * @return 菜单树列表
	 */
	@Override
	public MultiResponse<MenuTreeDTO> listMenuTree(MenuTreeQry qry) {
		return menuTreeQryExe.execute(qry);
	}
}
