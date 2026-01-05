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
import org.ylzl.eden.cola.dto.ListResponse;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.cola.dto.SingleResponse;

/**
 * 菜单服务接口
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
public interface MenuService {

	Response createMenu(MenuAddCmd cmd);

	Response modifyMenu(MenuModifyCmd cmd);

	Response removeMenu(MenuRemoveCmd cmd);

	SingleResponse<MenuDTO> getMenuById(MenuByIdQry qry);

	ListResponse<MenuTreeDTO> listMenuTree(MenuTreeQry qry);
}
