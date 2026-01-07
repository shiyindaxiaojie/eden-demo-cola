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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.ylzl.eden.cola.dto.PageResponse;
import org.ylzl.eden.demo.app.role.assembler.RoleAssembler;
import org.ylzl.eden.demo.client.role.dto.RoleDTO;
import org.ylzl.eden.demo.client.role.dto.query.RoleListByPageQry;
import org.ylzl.eden.demo.infrastructure.role.database.RoleMapper;
import org.ylzl.eden.demo.infrastructure.role.database.dataobject.RoleDO;

/**
 * 分页查询角色列表执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class RoleListByPageQryExe {

	private final RoleMapper roleMapper;
	private final RoleAssembler roleAssembler;

	/**
	 * 执行分页查询角色列表
	 *
	 * @param qry 分页查询条件
	 * @return 角色分页列表
	 */
	public PageResponse<RoleDTO> execute(RoleListByPageQry qry) {
		LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(qry.getCode())) {
			wrapper.like(RoleDO::getCode, qry.getCode());
		}
		if (StringUtils.isNotBlank(qry.getName())) {
			wrapper.like(RoleDO::getName, qry.getName());
		}
		if (qry.getStatus() != null) {
			wrapper.eq(RoleDO::getStatus, qry.getStatus());
		}
		wrapper.orderByAsc(RoleDO::getSort);

		IPage<RoleDO> page = roleMapper.selectPage(
			new Page<>(qry.getPageIndex(), qry.getPageSize()), wrapper);

		return PageResponse.of(
			roleAssembler.toDTOList(page.getRecords()),
			(int) page.getTotal(),
			qry.getPageSize(),
			qry.getPageIndex()
		);
	}
}
