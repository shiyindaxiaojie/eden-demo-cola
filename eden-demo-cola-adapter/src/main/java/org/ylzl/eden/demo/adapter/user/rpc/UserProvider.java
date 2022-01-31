package org.ylzl.eden.demo.adapter.user.rpc;

import org.ylzl.eden.demo.adapter.constant.ApiConstant;
import org.ylzl.eden.demo.app.user.executor.command.UserAddCmdExe;
import org.ylzl.eden.demo.app.user.executor.command.UserModifyCmdExe;
import org.ylzl.eden.demo.app.user.executor.command.UserRemoveCmdExe;
import org.ylzl.eden.demo.app.user.executor.query.UserByIdQryExe;
import org.ylzl.eden.demo.app.user.executor.query.UserListByPageQryExe;
import org.ylzl.eden.demo.app.user.service.UserServiceImpl;
import org.ylzl.eden.demo.client.user.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 用户领域 RPC服务端
 *
 * @author gyl
 * @since 2.4.x
 */
@DubboService(timeout = ApiConstant.DEFAULT_TIMEOUT)
@Slf4j
public class UserProvider extends UserServiceImpl implements UserService {

	public UserProvider(UserAddCmdExe userAddCmdExe, UserModifyCmdExe userModifyCmdExe, UserRemoveCmdExe userRemoveCmdExe, UserByIdQryExe userByIdQryExe, UserListByPageQryExe userListByPageQryExe) {
		super(userAddCmdExe, userModifyCmdExe, userRemoveCmdExe, userByIdQryExe, userListByPageQryExe);
	}
}
