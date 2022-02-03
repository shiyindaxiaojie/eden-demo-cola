package org.ylzl.eden.demo.app.user.executor.command;

import com.alibaba.cola.dto.Response;
import org.ylzl.eden.demo.app.user.assembler.UserAssembler;
import org.ylzl.eden.demo.client.user.dto.command.UserModifyCmd;
import org.ylzl.eden.demo.domain.user.entity.User;
import org.ylzl.eden.demo.domain.user.gateway.UserGateway;
import org.springframework.stereotype.Component;

/**
 * 修改用户指令执行器
 *
 * @author gyl
 * @since 2.4.x
 */
@Component
public class UserModifyCmdExe {

	private final UserGateway userGateway;

	public UserModifyCmdExe(UserGateway userGateway) {
		this.userGateway = userGateway;
	}

	public Response execute(UserModifyCmd cmd) {
		User user = UserAssembler.INSTANCE.toEntity(cmd.getUserDTO());
		user.setId(cmd.getId());
		userGateway.updateById(user);
		return Response.buildSuccess();
	}
}
