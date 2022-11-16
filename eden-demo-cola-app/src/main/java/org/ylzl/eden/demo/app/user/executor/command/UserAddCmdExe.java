package org.ylzl.eden.demo.app.user.executor.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ylzl.eden.demo.app.user.assembler.UserAssembler;
import org.ylzl.eden.demo.client.user.dto.command.UserAddCmd;
import org.ylzl.eden.demo.domain.user.entity.User;
import org.ylzl.eden.demo.domain.user.gateway.UserGateway;
import org.ylzl.eden.cola.dto.Response;

/**
 * 新增用户指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class UserAddCmdExe {

	private final UserGateway userGateway;

	private final UserAssembler userAssembler;

	public Response execute(UserAddCmd cmd) {
		User user = userAssembler.toEntity(cmd);
		userGateway.save(user);
		return Response.buildSuccess();
	}
}
