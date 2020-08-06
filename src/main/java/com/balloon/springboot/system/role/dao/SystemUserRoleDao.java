package com.balloon.springboot.system.role.dao;

import com.balloon.springboot.system.role.entity.SystemUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author liaofuxing
 * @E-mail liaofuxing@outlook.com
 * @date 2020/03/16 20:25
 **/
@Repository
public interface SystemUserRoleDao extends JpaRepository<SystemUserRole, Integer> {

    SystemUserRole findBySystemUserId(Integer systemUserId);

}
