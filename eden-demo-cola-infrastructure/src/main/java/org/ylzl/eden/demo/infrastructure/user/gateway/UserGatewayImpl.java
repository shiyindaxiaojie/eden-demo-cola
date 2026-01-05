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

package org.ylzl.eden.demo.infrastructure.user.gateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.ylzl.eden.demo.domain.role.entity.Role;
import org.ylzl.eden.demo.domain.user.entity.User;
import org.ylzl.eden.demo.domain.user.gateway.UserGateway;
import org.ylzl.eden.demo.domain.user.valueobject.Email;
import org.ylzl.eden.demo.domain.user.valueobject.Login;
import org.ylzl.eden.demo.infrastructure.role.database.RoleMapper;
import org.ylzl.eden.demo.infrastructure.role.database.convertor.RoleConvertor;
import org.ylzl.eden.demo.infrastructure.role.database.dataobject.RoleDO;
import org.ylzl.eden.demo.infrastructure.user.database.UserMapper;
import org.ylzl.eden.demo.infrastructure.user.database.UserRoleMapper;
import org.ylzl.eden.demo.infrastructure.user.database.convertor.UserConvertor;
import org.ylzl.eden.demo.infrastructure.user.database.dataobject.UserDO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户领域防腐层实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@RequiredArgsConstructor
@Slf4j
@Repository
public class UserGatewayImpl implements UserGateway {

	private final UserMapper userMapper;
	private final UserRoleMapper userRoleMapper;
	private final RoleMapper roleMapper;
	private final UserConvertor userConvertor;
	private final RoleConvertor roleConvertor;

	/**
	 * 保存用户
	 *
	 * @param user 用户实体
	 */
	@Override
	public void save(User user) {
		UserDO userDO = userConvertor.toDataObject(user);
		if (user.getId() == null) {
			userMapper.insert(userDO);
		} else {
			userMapper.updateById(userDO);
		}
	}

	/**
	 * 根据ID查询用户
	 *
	 * @param id 用户ID
	 * @return 用户实体
	 */
	@Override
	public Optional<User> findById(Long id) {
		UserDO userDO = userMapper.selectById(id);
		return Optional.ofNullable(userConvertor.toEntity(userDO));
	}

	/**
	 * 根据登录账号查询用户
	 *
	 * @param login 登录账号
	 * @return 用户实体
	 */
	@Override
	public User findByLogin(Login login) {
		UserDO userDO = userMapper.selectByLogin(login.getValue());
		return userConvertor.toEntity(userDO);
	}

	/**
	 * 根据邮箱查询用户
	 *
	 * @param email 邮箱
	 * @return 用户实体
	 */
	@Override
	public User findByEmail(Email email) {
		UserDO userDO = userMapper.selectByEmail(email.getValue());
		return userConvertor.toEntity(userDO);
	}

	/**
	 * 检查账号是否存在
	 *
	 * @param login 登录账号
	 * @return 是否存在
	 */
	@Override
	public boolean existsByLogin(Login login) {
		return userMapper.countByLogin(login.getValue()) > 0;
	}

	/**
	 * 检查邮箱是否存在
	 *
	 * @param email 邮箱
	 * @return 是否存在
	 */
	@Override
	public boolean existsByEmail(Email email) {
		return userMapper.countByEmail(email.getValue()) > 0;
	}

	/**
	 * 检查邮箱是否被其他用户使用
	 *
	 * @param email         邮箱
	 * @param excludeUserId 排除的用户ID
	 * @return 是否存在
	 */
	@Override
	public boolean existsByEmailExcludeUser(Email email, Long excludeUserId) {
		return userMapper.countByEmailExcludeUser(email.getValue(), excludeUserId) > 0;
	}

	/**
	 * 删除用户
	 *
	 * @param id 用户ID
	 */
	@Override
	public void deleteById(Long id) {
		// 删除用户角色关联
		userRoleMapper.deleteByUserId(id);
		// 删除用户
		userMapper.deleteById(id);
	}

	/**
	 * 保存用户角色关联
	 *
	 * @param userId  用户ID
	 * @param roleIds 角色ID列表
	 */
	@Override
	public void saveUserRoles(Long userId, List<Long> roleIds) {
		// 先删除原有关联
		userRoleMapper.deleteByUserId(userId);
		// 再批量插入新关联
		if (roleIds != null && !roleIds.isEmpty()) {
			userRoleMapper.batchInsert(userId, roleIds);
		}
	}

	/**
	 * 查询用户的角色ID列表
	 *
	 * @param userId 用户ID
	 * @return 角色ID列表
	 */
	@Override
	public List<Long> findRoleIdsByUserId(Long userId) {
		return userRoleMapper.selectRoleIdsByUserId(userId);
	}

	/**
	 * 查询用户的角色列表
	 *
	 * @param userId 用户ID
	 * @return 角色列表
	 */
	@Override
	public List<Role> findRolesByUserId(Long userId) {
		List<Long> roleIds = userRoleMapper.selectRoleIdsByUserId(userId);
		if (roleIds == null || roleIds.isEmpty()) {
			return Collections.emptyList();
		}
		List<RoleDO> roleDOs = roleMapper.selectByIds(roleIds);
		return roleDOs.stream()
			.map(roleConvertor::toEntity)
			.collect(Collectors.toList());
	}

	/**
	 * 删除用户的所有角色关联
	 *
	 * @param userId 用户ID
	 */
	@Override
	public void deleteUserRoles(Long userId) {
		userRoleMapper.deleteByUserId(userId);
	}
}
