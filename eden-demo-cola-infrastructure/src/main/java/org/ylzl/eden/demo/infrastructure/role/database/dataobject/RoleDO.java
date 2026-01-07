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

package org.ylzl.eden.demo.infrastructure.role.database.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色表数据库对象
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
@TableName("demo_role")
public class RoleDO implements Serializable {

	/**
	 * 角色ID
	 */
	@TableId("id")
	private Long id;

	/**
	 * 角色编码
	 */
	@TableField("code")
	private String code;

	/**
	 * 角色名称
	 */
	@TableField("name")
	private String name;

	/**
	 * 角色描述
	 */
	@TableField("description")
	private String description;

	/**
	 * 状态：0-禁用,1-启用
	 */
	@TableField("status")
	private Integer status;

	/**
	 * 排序
	 */
	@TableField("sort")
	private Integer sort;

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
