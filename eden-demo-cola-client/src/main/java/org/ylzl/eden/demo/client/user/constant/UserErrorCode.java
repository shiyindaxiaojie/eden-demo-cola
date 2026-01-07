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

package org.ylzl.eden.demo.client.user.constant;

/**
 * 用户错误码
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
public interface UserErrorCode {

	/** 用户不存在 */
	String USER_NOT_FOUND = "USER-404";

	/** 登录账号已存在 */
	String USER_LOGIN_EXISTS = "USER-REG-001";

	/** 邮箱已被注册 */
	String USER_EMAIL_EXISTS = "USER-REG-002";

	/** 邮箱格式无效 */
	String USER_EMAIL_INVALID = "USER-EMAIL-001";

	/** 密码格式无效 */
	String USER_PASSWORD_INVALID = "USER-PWD-001";

	/** 用户状态无效 */
	String USER_STATUS_INVALID = "USER-STATUS-001";

	/** 用户认证失败 */
	String USER_AUTH_FAILED = "USER-AUTH-001";
}
