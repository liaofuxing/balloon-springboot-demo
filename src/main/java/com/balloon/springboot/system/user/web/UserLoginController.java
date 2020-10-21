package com.balloon.springboot.system.user.web;


import com.balloon.core.random.RandomUtils;
import com.balloon.springboot.core.rules.ResultVO;
import com.balloon.springboot.core.rules.ResultVOUtils;
import com.balloon.springboot.redis.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 用户登录控制器
 *
 * @author liaofuxing
 * @date 2020/10/21 15:17
 * @E-mail liaofuxing@outlook.com
 */
@RestController
@RequestMapping("/user")
public class UserLoginController {

    @Autowired
    private RedisUtils redisUtils;

    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @GetMapping("/sendSmsCode")
    public ResultVO<String> findSystemUserList(@RequestParam String phone) {
        String smsCode = RandomUtils.randomLetterUpperCaseOrNumber(6);
        logger.info("发送给{}的验证码{}",phone,smsCode);
        redisUtils.set("SMS_CODE:"+ phone, smsCode);
        return ResultVOUtils.success(smsCode);
    }
}
