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

package org.ylzl.eden.demo.client.permission.dto.command;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 新增权限指令
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class PermissionAddCmd implements Serializable {

	/** 权限编码 */
	@NotBlank(message = "权限编码不能为空")
	private String code;

	/** 权限名称 */
	@NotBlank(message = "权限名称不能为空")
	private String name;

	/** 权限类型：1-目录，2-菜单，3-按钮 */
	@NotNull(message = "权限类型不能为空")
	private Integer type;

	/** 父权限ID */
	private Long parentId;

	/** 权限描述 */
	private String description;

	/** 排序号 */
	private Integer sort;
}
