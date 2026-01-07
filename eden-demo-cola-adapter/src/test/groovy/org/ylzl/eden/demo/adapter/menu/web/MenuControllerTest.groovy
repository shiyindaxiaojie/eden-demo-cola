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

package org.ylzl.eden.demo.adapter.menu.web

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.client.menu.api.MenuService
import org.ylzl.eden.demo.client.menu.dto.MenuDTO
import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO
import org.ylzl.eden.demo.client.menu.dto.command.*
import org.ylzl.eden.demo.client.menu.dto.query.*
import org.ylzl.eden.cola.dto.MultiResponse
import org.ylzl.eden.cola.dto.Response
import org.ylzl.eden.cola.dto.SingleResponse
import spock.lang.Specification
import spock.lang.Unroll

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class MenuControllerTest extends Specification {
	@Mock
	MenuService menuService
	@Mock
	Logger log
	@InjectMocks
	MenuController menuController

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	@Unroll
	def "create Menu where cmd=#cmd then expect: #expectedResult"() {
		given:
		when(menuService.createMenu(any())).thenReturn(Response.buildSuccess())

		expect:
		menuController.createMenu(cmd) == expectedResult

		where:
		cmd                                                              || expectedResult
		new MenuAddCmd("系统管理", "/system", "setting", 0L, 1, "Layout") || Response.buildSuccess()
	}

	@Unroll
	def "modify Menu where id=#id and cmd=#cmd then expect: #expectedResult"() {
		given:
		when(menuService.modifyMenu(any())).thenReturn(Response.buildSuccess())

		expect:
		menuController.modifyMenu(id, cmd) == expectedResult

		where:
		id | cmd                                                              || expectedResult
		1L | new MenuModifyCmd(1L, "系统管理", "/system", "setting", 0L, 1, "Layout") || Response.buildSuccess()
	}

	@Unroll
	def "remove Menu where id=#id then expect: #expectedResult"() {
		given:
		when(menuService.removeMenu(any())).thenReturn(Response.buildSuccess())

		expect:
		menuController.removeMenu(id) == expectedResult

		where:
		id || expectedResult
		1L || Response.buildSuccess()
	}

	@Unroll
	def "get Menu By Id where id=#id then expect: #expectedResult"() {
		given:
		when(menuService.getMenuById(any())).thenReturn(SingleResponse.of(new MenuDTO(1L, "系统管理", "/system", "setting", 0L, 1, 1, "Layout")))

		expect:
		menuController.getMenuById(id) == expectedResult

		where:
		id || expectedResult
		1L || SingleResponse.of(new MenuDTO(1L, "系统管理", "/system", "setting", 0L, 1, 1, "Layout"))
	}

	@Unroll
	def "list Menu Tree where query=#query then expect: #expectedResult"() {
		given:
		when(menuService.listMenuTree(any())).thenReturn(MultiResponse.of([new MenuTreeDTO(1L, "系统管理", "/system", "setting", 0L, 1, 1, "Layout", [])]))

		expect:
		menuController.listMenuTree(query) == expectedResult

		where:
		query                 || expectedResult
		new MenuTreeQry(1) || MultiResponse.of([new MenuTreeDTO(1L, "系统管理", "/system", "setting", 0L, 1, 1, "Layout", [])])
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
