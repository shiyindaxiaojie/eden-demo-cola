package org.ylzl.eden.demo.app.user.executor.query;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;
import org.ylzl.eden.demo.app.user.assembler.UserAssembler;
import org.ylzl.eden.demo.client.user.dto.UserVO;
import org.ylzl.eden.demo.client.user.dto.query.UserListByPageQry;
import org.ylzl.eden.demo.infrastructure.user.database.dataobject.UserDO;
import org.ylzl.eden.demo.infrastructure.user.database.mapper.UserMapper;
import org.ylzl.eden.spring.framework.cola.dto.PageResponse;

/**
 * 根据分页查询获取用户列表指令执行器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Component
public class UserListByPageQryExe {

	private final UserMapper userMapper;

	public UserListByPageQryExe(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public PageResponse<UserVO> execute(UserListByPageQry query) {
		Page<UserDO> page = PageHelper.startPage(query.getPageIndex(), query.getPageSize())
			.doSelectPage(() -> userMapper.selectPage(query));

		return PageResponse.of(UserAssembler.INSTANCE.toVOList(page.getResult()),
			Integer.parseInt(String.valueOf(page.getTotal())),
			query.getPageSize(), query.getPageIndex());
	}
}
