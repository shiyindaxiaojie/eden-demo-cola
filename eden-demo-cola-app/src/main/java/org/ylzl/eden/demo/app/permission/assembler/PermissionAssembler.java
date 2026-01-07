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

package org.ylzl.eden.demo.app.permission.assembler;

import org.springframework.stereotype.Component;
import org.ylzl.eden.demo.client.permission.dto.PermissionDTO;
import org.ylzl.eden.demo.client.permission.dto.PermissionTreeDTO;
import org.ylzl.eden.demo.domain.permission.entity.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限领域组装器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Component
public class PermissionAssembler {

	public PermissionDTO toDTO(Permission permission) {
		if (permission == null) {
			return null;
		}
		return PermissionDTO.builder()
			.id(permission.getId())
			.code(permission.getCode() != null ? permission.getCode().getValue() : null)
			.name(permission.getName())
			.type(permission.getType() != null ? permission.getType().getValue() : null)
			.parentId(permission.getParentId())
			.description(permission.getDescription())
			.sort(permission.getSort())
			.build();
	}

	public List<PermissionDTO> toDTOList(List<Permission> permissions) {
		if (permissions == null) {
			return new ArrayList<>();
		}
		return permissions.stream()
			.map(this::toDTO)
			.collect(Collectors.toList());
	}

	public List<PermissionTreeDTO> buildTree(List<Permission> permissions) {
		if (permissions == null || permissions.isEmpty()) {
			return new ArrayList<>();
		}

		Map<Long, List<Permission>> childrenMap = permissions.stream()
			.filter(p -> p.getParentId() != null && p.getParentId() > 0)
			.collect(Collectors.groupingBy(Permission::getParentId));

		return permissions.stream()
			.filter(p -> p.getParentId() == null || p.getParentId() == 0)
			.map(p -> buildTreeNode(p, childrenMap))
			.collect(Collectors.toList());
	}

	private PermissionTreeDTO buildTreeNode(Permission permission, Map<Long, List<Permission>> childrenMap) {
		PermissionTreeDTO dto = PermissionTreeDTO.builder()
			.id(permission.getId())
			.code(permission.getCode() != null ? permission.getCode().getValue() : null)
			.name(permission.getName())
			.type(permission.getType() != null ? permission.getType().getValue() : null)
			.parentId(permission.getParentId())
			.description(permission.getDescription())
			.sort(permission.getSort())
			.build();

		List<Permission> children = childrenMap.get(permission.getId());
		if (children != null && !children.isEmpty()) {
			dto.setChildren(children.stream()
				.map(c -> buildTreeNode(c, childrenMap))
				.collect(Collectors.toList()));
		}
		return dto;
	}
}
