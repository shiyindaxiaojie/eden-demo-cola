package org.ylzl.eden.demo.app.user.executor.command;

import com.alibaba.cola.dto.Response;
import org.ylzl.eden.demo.app.user.assembler.UserAssembler;
import org.ylzl.eden.demo.client.user.dto.command.UserAddCmd;
import org.ylzl.eden.demo.domain.user.entity.User;
import org.ylzl.eden.demo.domain.user.gateway.UserGateway;
import org.springframework.stereotype.Component;

/**
 * 新增用户指令执行器
 *
 * @author gyl
 * @since 2.4.x
 */
@Component
public class UserAddCmdExe {

	private final UserGateway userGateway;

	public UserAddCmdExe(UserGateway userGateway) {
		this.userGateway = userGateway;
	}

	public Response execute(UserAddCmd cmd) {
		User user = UserAssembler.INSTANCE.toEntity(cmd.getUserDTO());
		userGateway.save(user);
		return Response.buildSuccess();
	}
}
