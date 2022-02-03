package org.ylzl.eden.demo.app.user.executor.command;

import com.alibaba.cola.dto.Response;
import org.ylzl.eden.demo.client.user.dto.command.UserRemoveCmd;
import org.ylzl.eden.demo.domain.user.entity.User;
import org.ylzl.eden.demo.domain.user.gateway.UserGateway;
import org.springframework.stereotype.Component;

/**
 * 删除用户指令执行器
 *
 * @author gyl
 * @since 2.4.x
 */
@Component
public class UserRemoveCmdExe {

	private final UserGateway userGateway;

	public UserRemoveCmdExe(UserGateway userGateway) {
		this.userGateway = userGateway;
	}

	public Response execute(UserRemoveCmd cmd) {
		userGateway.deleteById(User.builder().id(cmd.getId()).build());
		return Response.buildSuccess();
	}
}
