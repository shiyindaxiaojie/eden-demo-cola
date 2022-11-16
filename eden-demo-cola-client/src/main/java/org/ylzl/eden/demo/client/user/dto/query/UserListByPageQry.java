package org.ylzl.eden.demo.client.user.dto.query;

import lombok.*;
import org.ylzl.eden.cola.dto.PageQuery;

import java.io.Serializable;

/**
 * 根据分页查询获取用户列表指令
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
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
