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

package org.ylzl.eden.demo.domain.user.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ylzl.eden.cola.domain.Entity;
import org.ylzl.eden.demo.domain.user.event.UserCreatedEvent;
import org.ylzl.eden.demo.domain.user.event.UserEmailChangedEvent;
import org.ylzl.eden.demo.domain.user.event.UserPasswordChangedEvent;
import org.ylzl.eden.demo.domain.user.valueobject.Email;
import org.ylzl.eden.demo.domain.user.valueobject.Login;
import org.ylzl.eden.demo.domain.user.valueobject.Password;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户聚合根
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

	/**
	 * 用户ID
	 */
	private Long id;

	/**
	 * 登录账号
	 */
	private Login login;

	/**
	 * 邮箱
	 */
	private Email email;

	/**
	 * 密码
	 */
	private Password password;

	/**
	 * 用户状态
	 */
	private UserStatus status;

	/**
	 * 创建时间
	 */
	private LocalDateTime createdAt;

	/**
	 * 更新时间
	 */
	private LocalDateTime updatedAt;

	/**
	 * 领域事件集合
	 */
	@Getter(AccessLevel.NONE)
	private final List<Object> domainEvents = new ArrayList<>();

	/**
	 * 用户状态枚举
	 */
	public enum UserStatus {
		/** 待激活 */
		PENDING,
		/** 已激活 */
		ACTIVE,
		/** 已锁定 */
		LOCKED,
		/** 已禁用 */
		DISABLED
	}

	/**
	 * 创建用户
	 *
	 * @param login    登录账号
	 * @param email    邮箱
	 * @param password 密码
	 * @return 用户实体
	 */
	public static User create(Login login, Email email, Password password) {
		User user = new User();
		user.login = login;
		user.email = email;
		user.password = password;
		user.status = UserStatus.PENDING;
		user.createdAt = LocalDateTime.now();
		user.updatedAt = user.createdAt;
		user.registerEvent(new UserCreatedEvent(user.id, login.getValue(), email.getValue()));
		return user;
	}

	/**
	 * 从持久化数据重建用户
	 *
	 * @param id        用户ID
	 * @param login     登录账号
	 * @param email     邮箱
	 * @param password  密码
	 * @param status    状态
	 * @param createdAt 创建时间
	 * @param updatedAt 更新时间
	 * @return 用户实体
	 */
	public static User reconstitute(Long id, Login login, Email email,
									Password password, UserStatus status,
									LocalDateTime createdAt, LocalDateTime updatedAt) {
		User user = new User();
		user.id = id;
		user.login = login;
		user.email = email;
		user.password = password;
		user.status = status;
		user.createdAt = createdAt;
		user.updatedAt = updatedAt;
		return user;
	}

	/**
	 * 修改邮箱
	 *
	 * @param newEmail 新邮箱
	 */
	public void changeEmail(Email newEmail) {
		ClientAssert.notNull(newEmail, "USER-001", "新邮箱不能为空");
		ClientAssert.isTrue(!newEmail.equals(this.email), "USER-002", "新邮箱不能与原邮箱相同");
		Email oldEmail = this.email;
		this.email = newEmail;
		this.updatedAt = LocalDateTime.now();
		registerEvent(new UserEmailChangedEvent(this.id, oldEmail.getValue(), newEmail.getValue()));
	}

	/**
	 * 修改密码
	 *
	 * @param currentPassword 当前密码
	 * @param newPassword     新密码
	 */
	public void changePassword(Password currentPassword, Password newPassword) {
		ClientAssert.notNull(newPassword, "USER-003", "新密码不能为空");
		if (currentPassword != null) {
			ClientAssert.isTrue(this.password.equals(currentPassword), "USER-004", "当前密码不正确");
		}
		this.password = newPassword;
		this.updatedAt = LocalDateTime.now();
		registerEvent(new UserPasswordChangedEvent(this.id, this.login.getValue()));
	}

	/**
	 * 激活用户
	 */
	public void activate() {
		ClientAssert.isTrue(this.status == UserStatus.PENDING, "USER-005", "只有待激活状态的用户才能激活");
		this.status = UserStatus.ACTIVE;
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 锁定用户
	 */
	public void lock() {
		ClientAssert.isTrue(this.status == UserStatus.ACTIVE, "USER-006", "只有激活状态的用户才能锁定");
		this.status = UserStatus.LOCKED;
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 解锁用户
	 */
	public void unlock() {
		ClientAssert.isTrue(this.status == UserStatus.LOCKED, "USER-007", "只有锁定状态的用户才能解锁");
		this.status = UserStatus.ACTIVE;
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 禁用用户
	 */
	public void disable() {
		ClientAssert.isTrue(this.status != UserStatus.DISABLED, "USER-008", "用户已经是禁用状态");
		this.status = UserStatus.DISABLED;
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 验证密码
	 *
	 * @param plainPassword 明文密码
	 * @return 是否匹配
	 */
	public boolean verifyPassword(String plainPassword) {
		return this.password.matches(plainPassword);
	}

	/**
	 * 是否可登录
	 *
	 * @return 是否可登录
	 */
	public boolean canLogin() {
		return this.status == UserStatus.ACTIVE;
	}

	/**
	 * 注册领域事件
	 *
	 * @param event 领域事件
	 */
	private void registerEvent(Object event) {
		domainEvents.add(event);
	}

	/**
	 * 获取领域事件
	 *
	 * @return 领域事件列表
	 */
	public List<Object> getDomainEvents() {
		return new ArrayList<>(domainEvents);
	}

	/**
	 * 清除领域事件
	 */
	public void clearDomainEvents() {
		domainEvents.clear();
	}
}
