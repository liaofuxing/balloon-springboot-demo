package com.balloon.springboot.system.role.vo;

import com.balloon.springboot.core.rules.PageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表
 *
 * @author liaofuxing
 * @E-mail liaofuxing@outlook.com
 * @date 2020/03/17 15:24
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleInfoVO extends PageInfo {

    private Integer id;

    private String roleName;

    private String description;

    private String menu;

}
