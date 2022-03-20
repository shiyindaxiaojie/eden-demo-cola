package org.ylzl.eden.demo.adapter.user.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.ylzl.eden.commons.json.JacksonUtils;
import org.ylzl.eden.demo.adapter.constant.ApiConstant;
import org.ylzl.eden.demo.client.user.api.UserService;
import org.ylzl.eden.demo.client.user.dto.UserDTO;
import org.ylzl.eden.demo.client.user.dto.command.UserAddCmd;
import org.ylzl.eden.demo.client.user.dto.command.UserModifyCmd;
import org.ylzl.eden.demo.client.user.dto.command.UserRemoveCmd;
import org.ylzl.eden.demo.client.user.dto.query.UserByIdQry;
import org.ylzl.eden.demo.client.user.dto.query.UserListByPageQry;
import org.ylzl.eden.spring.framework.cola.dto.PageResponse;
import org.ylzl.eden.spring.framework.cola.dto.Response;
import org.ylzl.eden.spring.framework.cola.dto.SingleResponse;
import org.ylzl.eden.spring.integration.messagequeue.producer.Message;
import org.ylzl.eden.spring.integration.messagequeue.producer.MessageQueueProvider;
import org.ylzl.eden.spring.integration.messagequeue.producer.MessageSendCallback;
import org.ylzl.eden.spring.integration.messagequeue.producer.MessageSendResult;

import javax.validation.Valid;

/**
 * 用户领域控制器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@RequiredArgsConstructor
@Slf4j
@RequestMapping(ApiConstant.WEB_API_PATH + "/users")
@RestController
public class UserController {

	private final UserService userService;

	private final MessageQueueProvider messageQueueProvider;

	/**
	 * 创建用户
	 *
	 * @param cmd
	 * @return
	 */
	@PostMapping
	public Response createUser(@Valid @RequestBody UserAddCmd cmd) {
		return userService.createUser(cmd);
	}

	/**
	 * 修改用户
	 *
	 * @param id
	 * @param cmd
	 * @return
	 */
	@PutMapping("/{id}")
	public Response modifyUser(@PathVariable Long id, @Valid @RequestBody UserModifyCmd cmd) {
		cmd.setId(id);
		return userService.modifyUser(cmd);
	}

	/**
	 * 删除用户
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public Response removeUserById(@PathVariable Long id) {
		return userService.removeUser(UserRemoveCmd.builder().id(id).build());
	}

	/**
	 * 根据主键获取用户信息
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public SingleResponse<UserDTO> getUserById(@PathVariable Long id) throws JsonProcessingException {
		SingleResponse<UserDTO> userDTO =
			userService.getUserById(UserByIdQry.builder().id(id).build());
		messageQueueProvider.asyncSend(Message.builder()
				.topic("demo-cola-user")
				.key(String.valueOf(id))
				.tags("demo")
				.delayTimeLevel(2)
				.body(JacksonUtils.toJSONString(userDTO.getData())).build(),
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
		return userDTO;
	}

	/**
	 * 根据分页获取用户列表
	 *
	 * @param query
	 * @return
	 */
	@GetMapping
	public PageResponse<UserDTO> listUserByPage(@Valid @ModelAttribute UserListByPageQry query) {
		return userService.listUserByPage(query);
	}
}
