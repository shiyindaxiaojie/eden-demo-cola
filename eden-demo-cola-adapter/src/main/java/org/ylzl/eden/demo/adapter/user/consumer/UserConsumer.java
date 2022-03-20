package org.ylzl.eden.demo.adapter.user.consumer;

import lombok.extern.slf4j.Slf4j;
import org.ylzl.eden.spring.integration.messagequeue.annotation.MessageQueueListener;
import org.ylzl.eden.spring.integration.messagequeue.consumer.Acknowledgement;
import org.ylzl.eden.spring.integration.messagequeue.consumer.MessageListener;

import java.util.List;

/**
 * 用户消息消费
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Slf4j
@MessageQueueListener(topic = "demo-cola-user", consumerGroup = "eden-demo-cola")
public class UserConsumer implements MessageListener {

	/**
	 * 消费消息
	 *
	 * @param messages
	 * @param ack
	 */
	@Override
	public void consume(List<String> messages, Acknowledgement ack) {
		log.info("消费消息: {}", messages);
		ack.acknowledge();
	}
}
