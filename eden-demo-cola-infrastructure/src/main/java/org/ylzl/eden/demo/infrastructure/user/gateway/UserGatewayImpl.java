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

package org.ylzl.eden.demo.infrastructure.user.gateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.ylzl.eden.demo.domain.user.entity.User;
import org.ylzl.eden.demo.domain.user.gateway.UserGateway;
import org.ylzl.eden.demo.infrastructure.user.database.convertor.UserConvertor;
import org.ylzl.eden.demo.infrastructure.user.database.mapper.UserMapper;

/**
 * 用户领域防腐层实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@RequiredArgsConstructor
@Slf4j
@Repository
public class UserGatewayImpl implements UserGateway {

	private final UserMapper userMapper;

	private final UserConvertor userConvertor;

//	private final MessageQueueProvider messageQueueProvider;

	/**
	 * 新增用户
	 *
	 * @param user
	 */
	@Override
	public void save(User user) {
		userMapper.insert(userConvertor.toDataObject(user));

		/*messageQueueProvider.asyncSend(Message.builder()
				.topic("demo-cola-user")
				.key(String.valueOf(user.getId()))
				.tags("demo")
				.delayTimeLevel(2)
				.body(JacksonUtils.toJSONString(user)).build(),
			new MessageSendCallback() {

				@Override
				public void onSuccess(MessageSendResult result) {
					log.info("发送消息成功, topic: {}, offset: {}, queueId: {}",
						result.getTopic(), result.getOffset(), result.getPartition());
				}

				@Override
				public void onFailed(Throwable e) {
					log.info("发送消息失败: {}" , e.getMessage(), e);
				}
			});*/
	}

	/**
	 * 修改用户
	 *
	 * @param user
	 */
	@Override
	public void updateById(User user) {
		userMapper.updateById(userConvertor.toDataObject(user));
	}

	/**
	 * 删除用户
	 *
	 * @param user
	 */
	@Override
	public void deleteById(User user) {
		userMapper.deleteById(user.getId());
	}
}
