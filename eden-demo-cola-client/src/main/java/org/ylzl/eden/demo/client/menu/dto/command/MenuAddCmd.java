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

package org.ylzl.eden.demo.client.menu.dto.command;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 新增菜单指令
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
public class MenuAddCmd implements Serializable {

	/** 菜单名称 */
	@NotBlank(message = "菜单名称不能为空")
	private String name;

	/** 路由路径 */
	@NotBlank(message = "菜单路径不能为空")
	private String path;

	/** 菜单图标 */
	private String icon;

	/** 父菜单ID */
	private Long parentId;

	/** 排序号 */
	private Integer sort;

	/** 组件路径 */
	private String component;
}
