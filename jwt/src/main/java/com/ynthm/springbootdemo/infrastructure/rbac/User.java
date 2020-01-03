package com.ynthm.springbootdemo.infrastructure.rbac;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ynthm.springbootdemo.domain.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户信息
 *
 * Author : Ynthm
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "t_user", indexes = { @Index(name = "user_name_unique", columnList = "name", unique = true) })
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "name is empty.")
	private String name;

	@Column(name = "first_name", length = 50)
	@NotNull
	@Size(min = 4, max = 50)
	private String firstName;

	@Column(name = "last_name", length = 50)
	@NotNull
	@Size(min = 4, max = 50)
	private String lastName;

	@Column(name = "email", length = 50)
	@NotNull
	@Size(min = 4, max = 50)
	private String email;

	/**
	 *  密码
     *  注意任何时候不能返回到前台，必要时候安全相关字段放另外一个表
	 */
	@JsonIgnore
	@Length(min = 2, max = 10, message = "name 长度必须在 {min} - {max} 之间")
	private String password;

	private boolean enabled;

	private Date lastPasswordResetDate;

	/**
	 * 角色
	 */
	@ManyToMany(fetch = FetchType.EAGER )
	@JoinTable(name="t_user_role",
			joinColumns={ @JoinColumn(name="user_id",referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="role_id",referencedColumnName="id")})
	private List<Role> roles;

}
