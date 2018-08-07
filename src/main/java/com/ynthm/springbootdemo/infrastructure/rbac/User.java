package com.ynthm.springbootdemo.infrastructure.rbac;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ynthm.springbootdemo.domain.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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

	/**
	 *  加密的盐
	 */
	@JsonIgnore
	private String salt;

	/**
	 *  密码
     *  FIXME 注意任何时候不能返回到前台，必要时候安全相关字段放另外一个表
	 */
	@JsonIgnore
	private String password;

	/**
	 * 角色
	 */
	@ManyToMany(fetch = FetchType.EAGER )
	@JoinTable(name="t_user_role",
			joinColumns={ @JoinColumn(name="user_id",referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="role_id",referencedColumnName="id")})
	private List<Role> roles;

}
