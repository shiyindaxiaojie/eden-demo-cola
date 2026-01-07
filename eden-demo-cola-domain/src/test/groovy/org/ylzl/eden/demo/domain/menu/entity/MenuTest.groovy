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

package org.ylzl.eden.demo.domain.menu.entity

import org.ylzl.eden.demo.domain.menu.valueobject.MenuPath
import spock.lang.Specification

class MenuTest extends Specification {

	def "test create menu"() {
		when:
		def menu = Menu.create("系统管理", new MenuPath("/system"), 0L)

		then:
		menu.name == "系统管理"
		menu.path.value == "/system"
		menu.parentId == 0L
		menu.status == MenuStatus.VISIBLE
		menu.sort == 0
		menu.createdAt != null
	}

	def "test reconstitute menu"() {
		when:
		def menu = Menu.reconstitute(1L, "系统管理", new MenuPath("/system"), "setting",
			0L, 1, MenuStatus.VISIBLE, "Layout", null, null)

		then:
		menu.id == 1L
		menu.name == "系统管理"
		menu.path.value == "/system"
		menu.icon == "setting"
		menu.parentId == 0L
		menu.sort == 1
		menu.status == MenuStatus.VISIBLE
		menu.component == "Layout"
	}

	def "test update info"() {
		given:
		def menu = Menu.create("系统管理", new MenuPath("/system"), 0L)

		when:
		menu.updateInfo("系统设置", "config", 2, "SystemLayout")

		then:
		menu.name == "系统设置"
		menu.icon == "config"
		menu.sort == 2
		menu.component == "SystemLayout"
	}

	def "test update path"() {
		given:
		def menu = Menu.create("系统管理", new MenuPath("/system"), 0L)

		when:
		menu.updatePath(new MenuPath("/admin"))

		then:
		menu.path.value == "/admin"
	}

	def "test set parent"() {
		given:
		def menu = Menu.create("用户管理", new MenuPath("/user"), 0L)

		when:
		menu.setParent(1L)

		then:
		menu.parentId == 1L
	}

	def "test show menu"() {
		given:
		def menu = Menu.reconstitute(1L, "系统管理", new MenuPath("/system"), "setting",
			0L, 0, MenuStatus.HIDDEN, "Layout", null, null)

		when:
		menu.show()

		then:
		menu.status == MenuStatus.VISIBLE
		menu.isVisible() == true
	}

	def "test hide menu"() {
		given:
		def menu = Menu.create("系统管理", new MenuPath("/system"), 0L)

		when:
		menu.hide()

		then:
		menu.status == MenuStatus.HIDDEN
		menu.isVisible() == false
	}

	def "test is root menu"() {
		given:
		def rootMenu = Menu.create("系统管理", new MenuPath("/system"), 0L)
		def childMenu = Menu.create("用户管理", new MenuPath("/user"), 1L)

		expect:
		rootMenu.isRootMenu() == true
		childMenu.isRootMenu() == false
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
