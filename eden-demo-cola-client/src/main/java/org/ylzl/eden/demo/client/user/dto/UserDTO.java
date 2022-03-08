package org.ylzl.eden.demo.client.user.dto;

import lombok.*;

import java.io.Serializable;

/**
 * 用户（数据传输对象）
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class UserDTO implements Serializable {

	/**
	 * 用户ID
	 */
	private Long id;

	/**
	 * 用户账号
	 */
	private String login;

	/**
	 * 用户邮箱
	 */
	private String email;
}
