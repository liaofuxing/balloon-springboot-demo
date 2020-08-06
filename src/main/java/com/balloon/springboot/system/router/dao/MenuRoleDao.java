package com.balloon.springboot.system.router.dao;

import com.balloon.springboot.system.router.entity.MenuRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRoleDao extends JpaRepository<MenuRole, Integer> {

    MenuRole findByRoleId(Integer roleId);
}
