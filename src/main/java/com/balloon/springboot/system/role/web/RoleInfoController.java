package com.balloon.springboot.system.role.web;

import com.balloon.springboot.core.rules.DatePageVO;
import com.balloon.springboot.core.rules.ResultVO;
import com.balloon.springboot.core.rules.ResultVOUtils;
import com.balloon.springboot.core.rules.SelectFormatVO;
import com.balloon.springboot.system.role.dto.RoleInfoDto;
import com.balloon.springboot.system.role.service.RoleInfoService;
import com.balloon.springboot.system.role.vo.RoleInfoVO;
import com.balloon.springboot.system.router.service.MenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author liaofuxing
 * @E-mail liaofuxing@outlook.com
 * @date 2020/02/15 14:18
 **/
@RestController
@RequestMapping("/role")
public class RoleInfoController {

    @Autowired
    private RoleInfoService roleInfoService;

    @Autowired
    private MenuRoleService menuRoleService;



    /**
     * 查询角色列表
     *
     * @return ResultVO<List<SelectFormatVO>>
     */
    @GetMapping("/getRoleSelect")
    public ResultVO<List<SelectFormatVO>> getRoleSelect() {
        List<SelectFormatVO> roleInfoVO = roleInfoService.findRoleAll();
        return ResultVOUtils.success(roleInfoVO);
    }

    /**
     * 查询角色列表
     *
     * @return ResultVO<DatePageVO<RoleInfoVO>>
     */
    @GetMapping("/getRoleList")
    public ResultVO<DatePageVO<RoleInfoVO>> getRoleList(RoleInfoDto roleInfoDto) {
        DatePageVO<RoleInfoVO> roleInfoPage = roleInfoService.findRoleInfoPage(roleInfoDto);
        return ResultVOUtils.success(roleInfoPage);
    }

    /**
     * 编辑角色
     *
     * @return ResultVO<Object>
     */
    @PostMapping("/editRole")
    public ResultVO<Object> editRole(@RequestBody RoleInfoDto roleInfoDto) {
        roleInfoService.editRole(roleInfoDto);
        return ResultVOUtils.success(null);
    }

    /**
     * 新增角色
     *
     * @return ResultVO<Object>
     */
    @PostMapping("/addRole")
    public ResultVO<Object> addRole(@RequestBody RoleInfoDto roleInfoDto) {
        roleInfoService.addRole(roleInfoDto);
        return ResultVOUtils.success(null);
    }

    /**
     * 获取角色对应菜单(页面菜单tree的默认选中状态)
     *
     * @return ResultVO<List<Integer>>
     */
    @GetMapping("/getRoleMenu")
    public ResultVO<List<Integer>> getRoleMenu(RoleInfoDto roleInfoDto) {
        List<Integer> menuRoleList = menuRoleService.findMenuRoleList(roleInfoDto.getId());
        return ResultVOUtils.success(menuRoleList);
    }

    // 校验角色名重复
    @GetMapping("/validateRoleNameRepeat")
    public ResultVO<Boolean> validateRoleNameRepeat(String roleName, Integer id) {
        Boolean validate = roleInfoService.validateRoleNameRepeat(roleName, id);
        return ResultVOUtils.success(validate);
    }
}
