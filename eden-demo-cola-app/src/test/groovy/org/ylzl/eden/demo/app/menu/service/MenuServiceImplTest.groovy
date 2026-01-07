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

package org.ylzl.eden.demo.app.menu.service

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.app.menu.executor.command.*
import org.ylzl.eden.demo.app.menu.executor.query.*
import org.ylzl.eden.demo.client.menu.dto.MenuDTO
import org.ylzl.eden.demo.client.menu.dto.MenuTreeDTO
import org.ylzl.eden.demo.client.menu.dto.command.*
import org.ylzl.eden.demo.client.menu.dto.query.*
import org.ylzl.eden.cola.dto.MultiResponse
import org.ylzl.eden.cola.dto.Response
import org.ylzl.eden.cola.dto.SingleResponse
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class MenuServiceImplTest extends Specification {
	@Mock
	MenuAddCmdExe menuAddCmdExe
	@Mock
	MenuModifyCmdExe menuModifyCmdExe
	@Mock
	MenuRemoveCmdExe menuRemoveCmdExe
	@Mock
	MenuByIdQryExe menuByIdQryExe
	@Mock
	MenuTreeQryExe menuTreeQryExe
	@Mock
	Logger log
	@InjectMocks
	MenuServiceImpl menuServiceImpl

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	def "test create Menu"() {
		given:
		when(menuAddCmdExe.execute(any())).thenReturn(Response.buildSuccess())

		when:
		Response result = menuServiceImpl.createMenu(new MenuAddCmd("系统管理", "/system", "setting", 0L, 1, "Layout"))

		then:
		result == Response.buildSuccess()
	}

	def "test modify Menu"() {
		given:
		when(menuModifyCmdExe.execute(any())).thenReturn(Response.buildSuccess())

		when:
		Response result = menuServiceImpl.modifyMenu(new MenuModifyCmd(1L, "系统管理", "/system", "setting", 0L, 1, "Layout"))

		then:
		result == Response.buildSuccess()
	}

	def "test remove Menu"() {
		given:
		when(menuRemoveCmdExe.execute(any())).thenReturn(Response.buildSuccess())

		when:
		Response result = menuServiceImpl.removeMenu(new MenuRemoveCmd(1L))

		then:
		result == Response.buildSuccess()
	}

	def "test get Menu By Id"() {
		given:
		when(menuByIdQryExe.execute(any())).thenReturn(SingleResponse.of(new MenuDTO(1L, "系统管理", "/system", "setting", 0L, 1, 1, "Layout")))

		when:
		SingleResponse<MenuDTO> result = menuServiceImpl.getMenuById(new MenuByIdQry(1L))

		then:
		result == SingleResponse.of(new MenuDTO(1L, "系统管理", "/system", "setting", 0L, 1, 1, "Layout"))
	}

	def "test list Menu Tree"() {
		given:
		when(menuTreeQryExe.execute(any())).thenReturn(MultiResponse.of([new MenuTreeDTO(1L, "系统管理", "/system", "setting", 0L, 1, 1, "Layout", [])]))

		when:
		MultiResponse<MenuTreeDTO> result = menuServiceImpl.listMenuTree(new MenuTreeQry(1))

		then:
		result == MultiResponse.of([new MenuTreeDTO(1L, "系统管理", "/system", "setting", 0L, 1, 1, "Layout", [])])
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
