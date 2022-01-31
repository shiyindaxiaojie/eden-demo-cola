package org.ylzl.eden.demo.client.user.dto.query;

import com.alibaba.cola.dto.PageQuery;
import lombok.*;

import java.io.Serializable;

/**
 * 根据分页查询获取用户列表指令
 *
 * @author gyl
 * @since 2.4.x
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Data
public class UserListByPageQry extends PageQuery implements Serializable {

	private String login;

	private String email;
}
