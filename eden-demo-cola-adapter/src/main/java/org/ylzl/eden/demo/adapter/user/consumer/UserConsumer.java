package org.ylzl.eden.demo.adapter.user.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ylzl.eden.common.mq.core.Message;
import org.ylzl.eden.common.mq.core.MessageQueueConsumer;
import org.ylzl.eden.common.mq.core.MessageQueueListener;
import org.ylzl.eden.common.mq.core.consumer.Acknowledgement;

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
