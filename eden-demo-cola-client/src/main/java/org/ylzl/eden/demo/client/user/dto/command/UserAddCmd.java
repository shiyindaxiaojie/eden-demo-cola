package org.ylzl.eden.demo.client.user.dto.command;

import lombok.*;
import org.ylzl.eden.demo.client.user.dto.UserDTO;

import java.io.Serializable;

/**
 * 新增用户指令
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
public class UserAddCmd implements Serializable {

	private UserDTO userDTO;
}
