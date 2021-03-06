package com.balloon.springboot.system.role.dao;

import com.balloon.springboot.system.role.entity.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;



/**
 * @author liaofuxing
 * @E-mail liaofuxing@outlook.com
 * @date 2020/03/16 20:20
 **/
@Repository
public interface RoleInfoDao extends JpaRepository<RoleInfo, Integer>, JpaSpecificationExecutor<RoleInfo> {

    RoleInfo findByRoleName(String roleName);

    RoleInfo findByRoleNameAndIdNot(String roleName,Integer id);
}
