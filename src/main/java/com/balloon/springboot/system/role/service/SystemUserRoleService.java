package com.balloon.springboot.system.role.service;


import com.balloon.springboot.system.role.entity.SystemUserRole;

import java.util.List;

public interface SystemUserRoleService {
    List<SystemUserRole> findSystemUserRoleAll();

    SystemUserRole findSystemUserRoleBySystemUserId(Integer systemUserId);

    void addSystemUserRole(SystemUserRole systemUserRole);

}
