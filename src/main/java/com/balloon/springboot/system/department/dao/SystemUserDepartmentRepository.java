package com.balloon.springboot.system.department.dao;

import com.balloon.springboot.system.department.entity.SystemUserDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author liaofuxing
 * @E-mail liaofuxing@outlook.com
 * @date 2020/03/17 15:41
 **/
@Repository
public interface SystemUserDepartmentRepository extends JpaRepository<SystemUserDepartment, Integer> {

    SystemUserDepartment findBySystemUserId(Integer systemUserId);
}
