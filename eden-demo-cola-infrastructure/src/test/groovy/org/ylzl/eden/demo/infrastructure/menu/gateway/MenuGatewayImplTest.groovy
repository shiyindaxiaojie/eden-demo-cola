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

package org.ylzl.eden.demo.infrastructure.menu.gateway

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.demo.domain.menu.entity.Menu
import org.ylzl.eden.demo.domain.menu.entity.MenuStatus
import org.ylzl.eden.demo.domain.menu.valueobject.MenuPath
import org.ylzl.eden.demo.infrastructure.menu.database.MenuMapper
import org.ylzl.eden.demo.infrastructure.menu.database.convertor.MenuConvertor
import org.ylzl.eden.demo.infrastructure.menu.database.dataobject.MenuDO
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class MenuGatewayImplTest extends Specification {
	@Mock
	MenuMapper menuMapper
	@Mock
	MenuConvertor menuConvertor
	@Mock
	Logger log
	@InjectMocks
	MenuGatewayImpl menuGatewayImpl

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	def "test save new menu"() {
		given:
		def menu = Menu.create("系统管理", new MenuPath("/system"), 0L)
		when(menuConvertor.toDataObject(any())).thenReturn(MenuDO.builder().name("系统管理").path("/system").build())

		when:
		menuGatewayImpl.save(menu)

		then:
		true
	}

	def "test find By Id"() {
		given:
		def menuDO = MenuDO.builder().id(1L).name("系统管理").path("/system").status(1).sort(0).build()
		def menu = Menu.reconstitute(1L, "系统管理", new MenuPath("/system"), "setting", 0L, 0, MenuStatus.VISIBLE, "Layout", null, null)
		when(menuMapper.selectById(any())).thenReturn(menuDO)
		when(menuConvertor.toEntity(any())).thenReturn(menu)

		when:
		def result = menuGatewayImpl.findById(1L)

		then:
		result.isPresent()
		result.get().id == 1L
	}

	def "test exists By Path"() {
		given:
		when(menuMapper.countByPath(any())).thenReturn(1)

		when:
		def result = menuGatewayImpl.existsByPath(new MenuPath("/system"))

		then:
		result == true
	}

	def "test delete By Id"() {
		when:
		menuGatewayImpl.deleteById(1L)

		then:
		true
	}

	def "test find All"() {
		given:
		def menuDO = MenuDO.builder().id(1L).name("系统管理").path("/system").status(1).sort(0).build()
		def menu = Menu.reconstitute(1L, "系统管理", new MenuPath("/system"), "setting", 0L, 0, MenuStatus.VISIBLE, "Layout", null, null)
		when(menuMapper.selectAll()).thenReturn([menuDO])
		when(menuConvertor.toEntity(any())).thenReturn(menu)

		when:
		def result = menuGatewayImpl.findAll()

		then:
		result.size() == 1
	}

	def "test has Children"() {
		given:
		when(menuMapper.countByParentId(any())).thenReturn(2)

		when:
		def result = menuGatewayImpl.hasChildren(1L)

		then:
		result == true
	}

	def "test is Used By Role"() {
		given:
		when(menuMapper.countRoleByMenuId(any())).thenReturn(1)

		when:
		def result = menuGatewayImpl.isUsedByRole(1L)

		then:
		result == true
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
