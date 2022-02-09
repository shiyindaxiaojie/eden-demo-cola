package org.ylzl.eden.demo.infrastructure.user.gateway;

import org.springframework.stereotype.Repository;
import org.ylzl.eden.demo.domain.user.entity.User;
import org.ylzl.eden.demo.domain.user.gateway.UserGateway;
import org.ylzl.eden.demo.infrastructure.user.database.convertor.UserConvertor;
import org.ylzl.eden.demo.infrastructure.user.database.mapper.UserMapper;

/**
 * 用户领域防腐层实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Repository
public class UserGatewayImpl implements UserGateway {

	private final UserMapper userMapper;

	public UserGatewayImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	/**
	 * 新增用户
	 *
	 * @param user
	 */
	@Override
	public void save(User user) {
		userMapper.insert(UserConvertor.INSTANCE.toDataObject(user));
	}

	/**
	 * 修改用户
	 *
	 * @param user
	 */
	@Override
	public void updateById(User user) {
		userMapper.updateById(UserConvertor.INSTANCE.toDataObject(user));
	}

	/**
	 * 删除用户
	 *
	 * @param user
	 */
	@Override
	public void deleteById(User user) {
		userMapper.deleteById(user.getId());
	}
}
