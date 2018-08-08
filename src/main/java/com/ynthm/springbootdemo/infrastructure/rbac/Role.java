package com.ynthm.springbootdemo.infrastructure.rbac;


import com.ynthm.springbootdemo.domain.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色
 * Author : Ynthm
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name="t_role")
public class Role extends BaseEntity {

    /**
     * 角色名称
     */
    @Column(name = "NAME", length = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleName name;

    /**
     * 角色描述
     */
    private String comment;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

}
