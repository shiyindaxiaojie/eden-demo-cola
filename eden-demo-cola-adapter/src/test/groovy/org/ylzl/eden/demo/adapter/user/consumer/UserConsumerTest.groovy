package org.ylzl.eden.demo.adapter.user.consumer

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.ylzl.eden.common.mq.consumer.Acknowledgement
import org.ylzl.eden.common.mq.model.Message
import spock.lang.Specification

class UserConsumerTest extends Specification {
	@Mock
	Logger log
	@InjectMocks
	UserConsumer userConsumer
	@Mock
	Acknowledgement acknowledgement

	def setup() {
		MockitoAnnotations.openMocks(this)
	}

	def "test consume"() {
		when:
		userConsumer.consume([new Message("namespace", "topic", 0, "key", "tags", "body", 0)], acknowledgement)

		then:
		true
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
