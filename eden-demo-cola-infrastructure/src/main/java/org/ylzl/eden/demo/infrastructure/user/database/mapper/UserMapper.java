package org.ylzl.eden.demo.infrastructure.user.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.ylzl.eden.demo.client.user.dto.query.UserListByPageQry;
import org.ylzl.eden.demo.infrastructure.user.database.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户记录表映射器
 *
 * @author gyl
 * @since 2.4.x
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

	/**
	 * 根据手机号码查询用户列表
	 *
	 * @param query 查询条件
	 * @return 用户列表
	 */
	List<UserDO> selectPage(UserListByPageQry query);
}
