package com.balloon.springboot.system.department.vo;

import com.balloon.springboot.core.rules.PageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 部门 vo
 *
 * @author liaofuxing
 * @E-mail liaofuxing@outlook.com
 * @date 2020/03/19 13:33
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentVO extends PageInfo {

    private Integer id;

    private String departmentName;

    private String description;

}
