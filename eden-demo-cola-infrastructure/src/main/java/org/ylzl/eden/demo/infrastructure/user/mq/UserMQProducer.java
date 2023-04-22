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

package org.ylzl.eden.demo.infrastructure.user.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ylzl.eden.common.mq.MessageQueueProvider;
import org.ylzl.eden.common.mq.model.Message;
import org.ylzl.eden.common.mq.producer.MessageSendCallback;
import org.ylzl.eden.common.mq.producer.MessageSendResult;
import org.ylzl.eden.demo.domain.user.entity.User;
import org.ylzl.eden.spring.framework.json.support.JSONHelper;

/**
 * 用户消息生产者
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
//@Component
public class UserMQProducer {

	private final MessageQueueProvider messageQueueProvider;

	public void send(User user) {
		MessageSendResult result =
			messageQueueProvider.syncSend(Message.builder()
				.topic("demo-cola-user")
				.key(String.valueOf(user.getId()))
				.tags("demo")
				.delayTimeLevel(2)
				.body(JSONHelper.json().toJSONString(user)).build());

		log.info("发送消息成功, topic: {}, offset: {}, queueId: {}",
			result.getTopic(), result.getOffset(), result.getPartition());

		messageQueueProvider.asyncSend(Message.builder()
				.topic("demo-cola-user")
				.key(String.valueOf(user.getId()))
				.tags("demo")
				.delayTimeLevel(2)
				.body(JSONHelper.json().toJSONString(user)).build(),
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
			});
	}
}
