package com.balloon.springboot.system.role.service;

import com.balloon.springboot.core.rules.DatePageVO;
import com.balloon.springboot.core.rules.SelectFormatVO;
import com.balloon.springboot.system.role.dto.RoleInfoDto;
import com.balloon.springboot.system.role.entity.RoleInfo;
import com.balloon.springboot.system.role.vo.RoleInfoVO;

import java.util.List;

public interface RoleInfoService {

    List<SelectFormatVO> findRoleAll();

    RoleInfo findByRoleName(String roleName);

    RoleInfo findRoleInfoByUserId(Integer userId);

    void editRole(RoleInfoDto roleInfoDto);

    void addRole(RoleInfoDto roleInfoDto);

    DatePageVO<RoleInfoVO> findRoleInfoPage(RoleInfoDto roleInfoDto);

    Boolean validateRoleNameRepeat(String roleName, Integer id);
}
