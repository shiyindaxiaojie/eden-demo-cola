package org.ylzl.eden.demo.domain.user.entity;

import lombok.*;
import org.ylzl.eden.cola.domain.Entity;

/**
 * 用户领域（聚合根）
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
@Entity
public class User {

	/**
	 * 用户 ID
	 */
	private Long id;

	/**
	 * 账号
	 */
	private String login;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 密码
	 */
	private String password;
}
