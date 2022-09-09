package org.ylzl.eden.demo.client.user.dto.command;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 更新用户指令
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
public class UserModifyCmd implements Serializable {

	@NotNull(message = "用户ID 不能为空")
	private Long id;

	@NotBlank(message = "账号不能为空")
	private String login;

	@NotBlank(message = "密码不能为空")
	private String password;

	@NotBlank(message = "邮箱不能为空")
	private String email;
}
