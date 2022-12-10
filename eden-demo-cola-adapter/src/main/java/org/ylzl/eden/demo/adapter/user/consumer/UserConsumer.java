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

package org.ylzl.eden.demo.adapter.user.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ylzl.eden.dynamic.mq.MessageQueueConsumer;
import org.ylzl.eden.dynamic.mq.MessageQueueListener;
import org.ylzl.eden.dynamic.mq.consumer.Acknowledgement;
import org.ylzl.eden.dynamic.mq.model.Message;

import java.util.List;

/**
 * 用户消息消费
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@RequiredArgsConstructor
@Slf4j
// 该注解会触发消息消费
@MessageQueueListener(topic = "demo-cola-user")
public class UserConsumer implements MessageQueueConsumer {

	/**
	 * 消费消息
	 *
	 * @param messages
	 * @param ack
	 */
	@Override
	public void consume(List<Message> messages, Acknowledgement ack) {
		log.info("消费消息: {}", messages);
		ack.acknowledge();
	}
}
