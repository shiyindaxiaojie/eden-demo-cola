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

package org.ylzl.eden.demo.client.role.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色（数据传输对象）
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
public class RoleDTO implements Serializable {

	/** 角色ID */
	private Long id;

	/** 角色编码 */
	private String code;

	/** 角色名称 */
	private String name;

	/** 角色描述 */
	private String description;

	/** 状态：0-禁用，1-启用 */
	private Integer status;

	/** 排序号 */
	private Integer sort;

	/** 创建时间 */
	private LocalDateTime createdAt;
}
