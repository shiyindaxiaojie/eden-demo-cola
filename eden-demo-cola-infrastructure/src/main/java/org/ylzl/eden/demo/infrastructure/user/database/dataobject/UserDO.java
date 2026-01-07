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

package org.ylzl.eden.demo.infrastructure.user.database.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户信息表数据库对象
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder
@ToString
@TableName("demo_user")
public class UserDO implements Serializable {

	/**
	 * 用户ID
	 */
	@TableId("id")
	private Long id;

	/**
	 * 登录账号
	 */
	@TableField("login")
	private String login;

	/**
	 * 邮箱
	 */
	@TableField("email")
	private String email;

	/**
	 * 密码
	 */
	@TableField("password")
	private String password;

	/**
	 * 状态：0-待激活,1-已激活,2-已锁定,3-已禁用
	 */
	@TableField("status")
	private Integer status;

	/**
	 * 创建时间
	 */
	@TableField("created_at")
	private LocalDateTime createdAt;

	/**
	 * 更新时间
	 */
	@TableField("updated_at")
	private LocalDateTime updatedAt;
}
