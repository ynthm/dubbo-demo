package com.ynthm.springbootdemo.infrastructure.dao;


import com.ynthm.springbootdemo.infrastructure.rbac.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


/**
 * 用户DAO
 * 
 * @author Ynthm
 *
 */
@Repository
public interface UserDao extends PagingAndSortingRepository<User, Long> {

	User findByName(String name);

    @Query(value = "select t from User t where t.name like ?1", nativeQuery = false)
    Page<User> findAllByKeyword(String keyword, Pageable pageable);
}