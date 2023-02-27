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

package org.ylzl.eden.demo.adapter.user.mq

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
