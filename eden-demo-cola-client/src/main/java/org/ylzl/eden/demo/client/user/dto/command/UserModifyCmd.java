package org.ylzl.eden.demo.client.user.dto.command;

import lombok.*;
import org.ylzl.eden.demo.client.user.dto.UserDTO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 更新用户指令
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
public class UserModifyCmd implements Serializable {

	@NotNull(message = "用户ID 不能为空")
	private Long id;

	private UserDTO userDTO;
}
