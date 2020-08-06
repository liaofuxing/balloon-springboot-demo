package com.balloon.springboot.system.department.service;


import com.balloon.springboot.system.department.entity.SystemUserDepartment;

import java.util.List;

public interface SystemUserDepartmentService {
    List<SystemUserDepartment> findSystemUserDepartmentAll();

    SystemUserDepartment findSystemUserDepartmentBySystemUserId(Integer systemUserId);

    void addSystemUserDepartment(SystemUserDepartment systemUserDepartment);

}
