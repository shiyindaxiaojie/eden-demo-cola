package org.ylzl.eden.demo.app.user.executor.query;

import com.alibaba.cola.dto.SingleResponse;
import org.springframework.stereotype.Component;
import org.ylzl.eden.demo.app.user.assembler.UserAssembler;
import org.ylzl.eden.demo.client.user.dto.UserVO;
import org.ylzl.eden.demo.client.user.dto.query.UserByIdQry;
import org.ylzl.eden.demo.infrastructure.user.database.dataobject.UserDO;
import org.ylzl.eden.demo.infrastructure.user.database.mapper.UserMapper;
import org.ylzl.eden.spring.framework.web.errors.ErrorEnum;

/**
 * 根据主键获取用户信息指令执行器
 *
 * @author gyl
 * @since 2.4.x
 */
@Component
public class UserByIdQryExe {

	private final UserMapper userMapper;

	public UserByIdQryExe(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public SingleResponse<UserVO> execute(UserByIdQry query) {
		UserDO userDO = userMapper.selectById(query.getId());
		ErrorEnum.A0201.notNull(userDO);
		return SingleResponse.of(UserAssembler.INSTANCE.toVO(userDO));
	}
}
