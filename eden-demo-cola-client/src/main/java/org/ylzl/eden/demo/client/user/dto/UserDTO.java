package org.ylzl.eden.demo.client.user.dto;

import lombok.*;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * 用户创建（数据传输对象）
 *
 * @author gyl
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
	 * 账号
	 */
	@NotBlank(message = "账号不能为空")
	private String login;

	/**
	 * 密码
	 */
	@NotBlank(message = "密码不能为空")
	private String password;

	/**
	 * 用户邮箱
	 */
	@NotBlank(message = "邮箱不能为空")
	private String email;
}
