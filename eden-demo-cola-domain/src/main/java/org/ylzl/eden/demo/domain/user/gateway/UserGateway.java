package org.ylzl.eden.demo.domain.user.gateway;

import org.ylzl.eden.demo.domain.user.entity.User;

/**
 * 用户领域防腐层
 *
 * @author gyl
 * @since 2.4.x
 */
public interface UserGateway {

	/**
	 * 新增用户
	 *
	 * @param user
	 */
	void save(User user);

	/**
	 * 修改用户
	 *
	 * @param user
	 */
	void updateById(User user);

	/**
	 * 删除用户
	 *
	 * @param user
	 */
	void deleteById(User user);
}
