package org.ylzl.eden.demo.infrastructure.user.database.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户信息表数据库对象
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder
@ToString
@TableName("demo_user")
public class UserDO implements Serializable {

	/**
	 * 用户 ID
	 */
	@TableId("id")
	private Long id;

	/**
	 * 登录账户
	 */
	@TableField("login")
	private String login;

	/**
	 * 密码哈希
	 */
	@TableField("password_hash")
	private String passwordHash;

	/**
	 * 邮箱
	 */
	@TableField("email")
	private String email;

	/**
	 * 账户是否已激活
	 */
	@TableField("activated")
	private Boolean activated;

	/**
	 * 账户是否被锁定
	 */
	@TableField("locked")
	private Boolean locked;

	/**
	 * 语言
	 */
	@TableField("lang_key")
	private String langKey;

	/**
	 * 激活账户的代码
	 */
	@TableField("activation_key")
	private String activationKey;

	/**
	 * 重置密码的代码
	 */
	@TableField("reset_key")
	private String resetKey;

	/**
	 * 重置密码的时间
	 */
	@TableField("reset_date")
	private LocalDateTime resetDate;

	/**
	 * 创建的账户
	 */
	@TableField("created_by")
	private String createdBy;

	/**
	 * 创建的时间
	 */
	@TableField("created_date")
	private LocalDateTime createdDate;

	/**
	 * 最后修改的账户
	 */
	@TableField("last_modified_by")
	private String lastModifiedBy;

	/**
	 * 最后修改的时间
	 */
	@TableField("last_modified_date")
	private LocalDateTime lastModifiedDate;
}
