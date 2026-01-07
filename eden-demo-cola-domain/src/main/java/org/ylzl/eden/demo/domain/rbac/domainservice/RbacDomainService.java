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

package org.ylzl.eden.demo.domain.rbac.domainservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ylzl.eden.demo.domain.menu.entity.Menu;
import org.ylzl.eden.demo.domain.menu.gateway.MenuGateway;
import org.ylzl.eden.demo.domain.permission.entity.Permission;
import org.ylzl.eden.demo.domain.permission.gateway.PermissionGateway;
import org.ylzl.eden.demo.domain.role.entity.Role;
import org.ylzl.eden.demo.domain.role.gateway.RoleGateway;

import java.util.*;
import java.util.stream.Collectors;

/**
 * RBAC 领域服务
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Service
public class RbacDomainService {

	private final RoleGateway roleGateway;
	private final PermissionGateway permissionGateway;
	private final MenuGateway menuGateway;

	/**
	 * 检查用户是否有权限
	 *
	 * @param roleIds        用户角色ID列表
	 * @param permissionCode 权限编码
	 * @return 是否有权限
	 */
	public boolean hasPermission(List<Long> roleIds, String permissionCode) {
		if (roleIds == null || roleIds.isEmpty()) {
			return false;
		}
		Set<String> permissionCodes = getPermissionCodes(roleIds);
		return permissionCodes.contains(permissionCode);
	}

	/**
	 * 获取用户所有权限编码
	 *
	 * @param roleIds 用户角色ID列表
	 * @return 权限编码集合
	 */
	public Set<String> getPermissionCodes(List<Long> roleIds) {
		if (roleIds == null || roleIds.isEmpty()) {
			return Collections.emptySet();
		}

		// 获取启用的角色
		List<Role> roles = roleGateway.findByIds(roleIds);
		List<Role> enabledRoles = roles.stream()
			.filter(Role::isEnabled)
			.collect(Collectors.toList());

		if (enabledRoles.isEmpty()) {
			return Collections.emptySet();
		}

		// 获取所有角色的权限ID
		Set<Long> permissionIds = new HashSet<>();
		for (Role role : enabledRoles) {
			List<Long> rolePermissionIds = roleGateway.findPermissionIdsByRoleId(role.getId());
			permissionIds.addAll(rolePermissionIds);
		}

		if (permissionIds.isEmpty()) {
			return Collections.emptySet();
		}

		// 获取权限编码
		List<Permission> permissions = permissionGateway.findByIds(new ArrayList<>(permissionIds));
		return permissions.stream()
			.map(p -> p.getCode().getValue())
			.collect(Collectors.toSet());
	}

	/**
	 * 获取用户菜单列表
	 *
	 * @param roleIds 用户角色ID列表
	 * @return 菜单列表
	 */
	public List<Menu> getUserMenus(List<Long> roleIds) {
		if (roleIds == null || roleIds.isEmpty()) {
			return Collections.emptyList();
		}

		// 获取启用的角色
		List<Role> roles = roleGateway.findByIds(roleIds);
		List<Role> enabledRoles = roles.stream()
			.filter(Role::isEnabled)
			.collect(Collectors.toList());

		if (enabledRoles.isEmpty()) {
			return Collections.emptyList();
		}

		// 获取所有角色的菜单ID（去重）
		Set<Long> menuIds = new HashSet<>();
		for (Role role : enabledRoles) {
			List<Long> roleMenuIds = roleGateway.findMenuIdsByRoleId(role.getId());
			menuIds.addAll(roleMenuIds);
		}

		if (menuIds.isEmpty()) {
			return Collections.emptyList();
		}

		// 获取可见的菜单
		List<Menu> menus = menuGateway.findByIds(new ArrayList<>(menuIds));
		return menus.stream()
			.filter(Menu::isVisible)
			.sorted(Comparator.comparing(Menu::getSort))
			.collect(Collectors.toList());
	}

	/**
	 * 构建菜单树
	 *
	 * @param menus 菜单列表
	 * @return 菜单树
	 */
	public List<MenuTreeNode> buildMenuTree(List<Menu> menus) {
		if (menus == null || menus.isEmpty()) {
			return Collections.emptyList();
		}

		// 按父级ID分组
		Map<Long, List<Menu>> menuMap = menus.stream()
			.collect(Collectors.groupingBy(m -> m.getParentId() != null ? m.getParentId() : 0L));

		// 构建树
		return buildTree(menuMap, 0L);
	}

	private List<MenuTreeNode> buildTree(Map<Long, List<Menu>> menuMap, Long parentId) {
		List<Menu> children = menuMap.get(parentId);
		if (children == null || children.isEmpty()) {
			return Collections.emptyList();
		}

		return children.stream()
			.sorted(Comparator.comparing(Menu::getSort))
			.map(menu -> {
				MenuTreeNode node = new MenuTreeNode();
				node.setId(menu.getId());
				node.setName(menu.getName());
				node.setPath(menu.getPath().getValue());
				node.setIcon(menu.getIcon());
				node.setComponent(menu.getComponent());
				node.setSort(menu.getSort());
				node.setChildren(buildTree(menuMap, menu.getId()));
				return node;
			})
			.collect(Collectors.toList());
	}

	/**
	 * 检查角色是否被使用
	 *
	 * @param roleId 角色ID
	 * @return 是否被使用
	 */
	public boolean isRoleInUse(Long roleId) {
		return roleGateway.isUsedByUser(roleId);
	}

	/**
	 * 检查菜单循环引用
	 *
	 * @param menuId   菜单ID
	 * @param parentId 父级ID
	 * @return 是否存在循环引用
	 */
	public boolean hasCircularReference(Long menuId, Long parentId) {
		if (parentId == null || parentId == 0L) {
			return false;
		}
		if (menuId.equals(parentId)) {
			return true;
		}

		// 向上遍历检查
		Set<Long> visited = new HashSet<>();
		visited.add(menuId);
		Long currentParentId = parentId;

		while (currentParentId != null && currentParentId != 0L) {
			if (visited.contains(currentParentId)) {
				return true;
			}
			visited.add(currentParentId);

			Optional<Menu> parentMenu = menuGateway.findById(currentParentId);
			if (!parentMenu.isPresent()) {
				break;
			}
			currentParentId = parentMenu.get().getParentId();
		}

		return false;
	}

	/**
	 * 菜单树节点
	 */
	@lombok.Data
	public static class MenuTreeNode {
		private Long id;
		private String name;
		private String path;
		private String icon;
		private String component;
		private Integer sort;
		private List<MenuTreeNode> children;
	}
}
