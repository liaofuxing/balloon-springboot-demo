package com.balloon.springboot.system.user.web;


import com.balloon.core.exception.BusinessRuntimeException;
import com.balloon.springboot.common.SysExceptionEnums;
import com.balloon.springboot.core.enums.ResultStatusCodeEnums;
import com.balloon.springboot.core.jackson.JacksonObjectMapper;
import com.balloon.springboot.core.rules.DatePageVO;
import com.balloon.springboot.core.rules.ResultVO;
import com.balloon.springboot.core.rules.ResultVOUtils;
import com.balloon.springboot.redis.utils.RedisUtils;
import com.balloon.springboot.system.user.dto.SystemUserDto;
import com.balloon.springboot.system.user.entity.SystemUser;
import com.balloon.springboot.system.user.service.SystemUserService;
import com.balloon.springboot.system.user.vo.SystemUserVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 系统用户控制器
 *
 * @author liaofuxing
 * @date 2019/03/10 4:39
 * @E-mail liaofuxing@outlook.com
 */
@RestController
@RequestMapping("/systemUser")
public class SystemUserController {

    @Autowired
    public SystemUserService systemUserService;

    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("/findSystemUserList")
    public ResultVO<List<SystemUser>> findSystemUserList(@RequestBody SystemUserDto systemUserDto) {
        SystemUser systemUser = new SystemUser();
        BeanUtils.copyProperties(systemUserDto, systemUser);
        List<SystemUser> systemUserList = systemUserService.findSystemUserList(systemUser);

        return ResultVOUtils.success(systemUserList);
    }

    /**
     * 运营平台用户分页查询
     *
     * @param systemUserDto dto
     * @return ResultVO
     */
    @PostMapping("/findSystemUserPage")
    public ResultVO<DatePageVO<SystemUserVO>> findSystemUserPage(@RequestBody SystemUserDto systemUserDto) {
        DatePageVO<SystemUserVO> systemUserPage = systemUserService.findSystemUserPage(systemUserDto);
        return ResultVOUtils.success(systemUserPage);
    }

    @GetMapping("/findSystemUserById")
    public ResultVO<SystemUser> findSystemUserById(@RequestParam Integer id) {
        SystemUser systemUserById = systemUserService.findSystemUserById(id);
        return ResultVOUtils.success(systemUserById);
    }

    @PostMapping("/editSystemUser")
    public ResultVO<Object> editSystemUser(@RequestBody SystemUserDto systemUserDto) {
        systemUserService.editSystemUser(systemUserDto);
        return ResultVOUtils.success(null);
    }

    @PostMapping("/addSystemUser")
    public ResultVO<Object> addSystemUser(@RequestBody SystemUserDto systemUserDto) {
        systemUserService.addSystemUser(systemUserDto);
        return ResultVOUtils.success(null);
    }

    // 校验用户名重复
    @GetMapping("/validateUsernameRepeat")
    public ResultVO<Boolean> validateUsernameRepeat(String username, Integer id) {
        Boolean validate = systemUserService.validateUsernameRepeat(username, id);
        return ResultVOUtils.success(validate);
    }

    // 在线用户查询
    @GetMapping("/getUserOnline")
    public ResultVO<List<SystemUserVO>> getUserOnline() {
        List<SystemUserVO> SystemUserVOs = systemUserService.userOnline();
        return ResultVOUtils.success(SystemUserVOs);
    }

    // 强制离线
    @GetMapping("/userForceOffline")
    public ResultVO<List<SystemUserVO>> userForceOffline(Integer userId) {
        List<SystemUserVO> SystemUserVOs = systemUserService.forceOffline(userId);
        return ResultVOUtils.success(SystemUserVOs);
    }

    @GetMapping("/info")
    public ResultVO<SystemUser> getUserInfoByToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        String userInfoStr = redisUtils.get("SYSTEM_USER_INFO:" + token);
        ResultVO<SystemUser> resultVO;
        try {
            JacksonObjectMapper mapper = new JacksonObjectMapper();
            SystemUser systemUser = mapper.readValue(userInfoStr, SystemUser.class);
            resultVO = ResultVOUtils.success(systemUser);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // redis中没有用户信息
            throw new BusinessRuntimeException(SysExceptionEnums.USER_NOT_FOUND);
        }
        return resultVO;
    }
}
