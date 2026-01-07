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

package org.ylzl.eden.demo.domain.user.gateway;

import org.ylzl.eden.demo.domain.role.entity.Role;
import org.ylzl.eden.demo.domain.user.entity.User;
import org.ylzl.eden.demo.domain.user.valueobject.Email;
import org.ylzl.eden.demo.domain.user.valueobject.Login;

import java.util.List;
import java.util.Optional;

/**
 * 用户领域防腐层
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
public interface UserGateway {

	/**
	 * 保存用户
	 *
	 * @param user 用户实体
	 */
	void save(User user);

	/**
	 * 根据ID查询用户
	 *
	 * @param id 用户ID
	 * @return 用户实体
	 */
	Optional<User> findById(Long id);

	/**
	 * 根据登录账号查询用户
	 *
	 * @param login 登录账号
	 * @return 用户实体
	 */
	User findByLogin(Login login);

	/**
	 * 根据邮箱查询用户
	 *
	 * @param email 邮箱
	 * @return 用户实体
	 */
	User findByEmail(Email email);

	/**
	 * 检查账号是否存在
	 *
	 * @param login 登录账号
	 * @return 是否存在
	 */
	boolean existsByLogin(Login login);

	/**
	 * 检查邮箱是否存在
	 *
	 * @param email 邮箱
	 * @return 是否存在
	 */
	boolean existsByEmail(Email email);

	/**
	 * 检查邮箱是否被其他用户使用
	 *
	 * @param email         邮箱
	 * @param excludeUserId 排除的用户ID
	 * @return 是否存在
	 */
	boolean existsByEmailExcludeUser(Email email, Long excludeUserId);

	/**
	 * 删除用户
	 *
	 * @param id 用户ID
	 */
	void deleteById(Long id);

	/**
	 * 保存用户角色关联
	 *
	 * @param userId  用户ID
	 * @param roleIds 角色ID列表
	 */
	void saveUserRoles(Long userId, List<Long> roleIds);

	/**
	 * 查询用户的角色ID列表
	 *
	 * @param userId 用户ID
	 * @return 角色ID列表
	 */
	List<Long> findRoleIdsByUserId(Long userId);

	/**
	 * 查询用户的角色列表
	 *
	 * @param userId 用户ID
	 * @return 角色列表
	 */
	List<Role> findRolesByUserId(Long userId);

	/**
	 * 删除用户的所有角色关联
	 *
	 * @param userId 用户ID
	 */
	void deleteUserRoles(Long userId);
}
