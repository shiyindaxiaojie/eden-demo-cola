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

package org.ylzl.eden.demo.adapter.user.rpc;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.ylzl.eden.demo.app.user.executor.command.UserAddCmdExe;
import org.ylzl.eden.demo.app.user.executor.command.UserModifyCmdExe;
import org.ylzl.eden.demo.app.user.executor.command.UserRemoveCmdExe;
import org.ylzl.eden.demo.app.user.executor.query.UserByIdQryExe;
import org.ylzl.eden.demo.app.user.executor.query.UserListByPageQryExe;
import org.ylzl.eden.demo.app.user.service.UserServiceImpl;
import org.ylzl.eden.demo.client.user.api.UserService;

/**
 * 用户领域 RPC服务端
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@DubboService
@Slf4j
public class UserProvider extends UserServiceImpl implements UserService {

	public UserProvider(UserAddCmdExe userAddCmdExe, UserModifyCmdExe userModifyCmdExe, UserRemoveCmdExe userRemoveCmdExe, UserByIdQryExe userByIdQryExe, UserListByPageQryExe userListByPageQryExe) {
		super(userAddCmdExe, userModifyCmdExe, userRemoveCmdExe, userByIdQryExe, userListByPageQryExe);
	}
}
