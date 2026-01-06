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

package org.ylzl.eden.demo.client.role.api;

import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO;
import org.ylzl.eden.demo.client.permission.dto.PermissionDTO;
import org.ylzl.eden.demo.client.role.dto.RoleDTO;
import org.ylzl.eden.demo.client.role.dto.command.*;
import org.ylzl.eden.demo.client.role.dto.query.*;
import org.ylzl.eden.cola.dto.MultiResponse;
import org.ylzl.eden.cola.dto.PageResponse;
import org.ylzl.eden.cola.dto.Response;
import org.ylzl.eden.cola.dto.SingleResponse;

/**
 * 角色服务接口
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
public interface RoleService {

	Response createRole(RoleAddCmd cmd);

	Response modifyRole(RoleModifyCmd cmd);

	Response removeRole(RoleRemoveCmd cmd);

	Response enableRole(RoleStatusCmd cmd);

	Response disableRole(RoleStatusCmd cmd);

	SingleResponse<RoleDTO> getRoleById(RoleByIdQry qry);

	PageResponse<RoleDTO> listRoleByPage(RoleListByPageQry qry);

	Response assignPermissions(RoleAssignPermissionsCmd cmd);

	Response assignMenus(RoleAssignMenusCmd cmd);

	MultiResponse<PermissionDTO> getRolePermissions(RolePermissionsQry qry);

	MultiResponse<MenuTreeDTO> getRoleMenus(RoleMenusQry qry);
}
