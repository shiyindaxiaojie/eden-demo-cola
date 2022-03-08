package org.ylzl.eden.demo.client.user.dto.command;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 新增用户指令
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
public class UserAddCmd implements Serializable {

	@NotBlank(message = "账号不能为空")
	private String login;

	@NotBlank(message = "密码不能为空")
	private String password;

	@NotBlank(message = "邮箱不能为空")
	private String email;
}
