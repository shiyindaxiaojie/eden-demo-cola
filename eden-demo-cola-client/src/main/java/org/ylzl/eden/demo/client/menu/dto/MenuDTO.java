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

package org.ylzl.eden.demo.client.menu.dto;

import lombok.*;

import java.io.Serializable;

/**
 * 菜单（数据传输对象）
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
public class MenuDTO implements Serializable {

	/** 菜单ID */
	private Long id;

	/** 菜单名称 */
	private String name;

	/** 路由路径 */
	private String path;

	/** 菜单图标 */
	private String icon;

	/** 父菜单ID */
	private Long parentId;

	/** 排序号 */
	private Integer sort;

	/** 状态：0-禁用，1-启用 */
	private Integer status;

	/** 组件路径 */
	private String component;
}
