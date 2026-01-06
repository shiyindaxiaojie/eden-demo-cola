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

package org.ylzl.eden.demo.app.role.executor.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.cola.dto.SingleResponse;
import org.ylzl.eden.demo.app.role.assembler.RoleAssembler;
import org.ylzl.eden.demo.client.role.dto.RoleDTO;
import org.ylzl.eden.demo.client.role.dto.query.RoleByIdQry;
import org.ylzl.eden.demo.infrastructure.role.database.RoleMapper;
import org.ylzl.eden.demo.infrastructure.role.database.dataobject.RoleDO;
import org.ylzl.eden.spring.framework.error.ClientAssert;

/**
 * 根据ID查询角色执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class RoleByIdQryExe {

	private final RoleMapper roleMapper;
	private final RoleAssembler roleAssembler;

	/**
	 * 执行根据ID查询角色
	 *
	 * @param qry 查询条件
	 * @return 角色信息
	 */
	public SingleResponse<RoleDTO> execute(RoleByIdQry qry) {
		RoleDO roleDO = roleMapper.selectById(qry.getId());
		ClientAssert.notNull(roleDO, "ROLE-002", "角色不存在");
		return SingleResponse.of(roleAssembler.toDTO(roleDO));
	}
}
