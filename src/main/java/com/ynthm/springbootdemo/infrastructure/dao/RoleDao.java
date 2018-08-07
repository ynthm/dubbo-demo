package com.ynthm.springbootdemo.infrastructure.dao;

import com.ynthm.springbootdemo.infrastructure.rbac.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Author : Ynthm
 */
@Repository
public interface RoleDao extends PagingAndSortingRepository<Role, Long> {
    Role findByName(String name);
}
