package org.ylzl.eden.demo.app.user.executor.command;

import org.springframework.stereotype.Component;
import org.ylzl.eden.demo.app.user.assembler.UserAssembler;
import org.ylzl.eden.demo.client.user.dto.command.UserModifyCmd;
import org.ylzl.eden.demo.domain.user.entity.User;
import org.ylzl.eden.demo.domain.user.gateway.UserGateway;
import org.ylzl.eden.spring.framework.cola.dto.Response;

/**
 * 修改用户指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
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
