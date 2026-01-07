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

package org.ylzl.eden.demo.infrastructure.permission.database.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限表数据库对象
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
@TableName("demo_permission")
public class PermissionDO implements Serializable {

	/**
	 * 权限ID
	 */
	@TableId("id")
	private Long id;

	/**
	 * 权限编码
	 */
	@TableField("code")
	private String code;

	/**
	 * 权限名称
	 */
	@TableField("name")
	private String name;

	/**
	 * 类型：1-菜单,2-按钮,3-接口
	 */
	@TableField("type")
	private Integer type;

	/**
	 * 父级ID
	 */
	@TableField("parent_id")
	private Long parentId;

	/**
	 * 权限描述
	 */
	@TableField("description")
	private String description;

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
