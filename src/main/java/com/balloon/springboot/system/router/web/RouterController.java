package com.balloon.springboot.system.router.web;

import com.balloon.springboot.core.rules.ResultVO;
import com.balloon.springboot.core.rules.ResultVOUtils;
import com.balloon.springboot.system.router.dto.Menu2RouterDto;
import com.balloon.springboot.system.router.service.RouterService;
import com.balloon.springboot.system.router.vo.Router2TreeVO;
import com.balloon.springboot.system.router.vo.RouterVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author liaofuxing
 * @E-mail liaofuxing@outlook.com
 * @date 2020/03/19 02:40
 *
 **/
@RestController
@RequestMapping("/router")
public class RouterController {

    private static final Logger logger = LoggerFactory.getLogger(RouterController.class);


    @Autowired
    private RouterService routerService;


    @GetMapping("/getRouters")
    public ResultVO<List<RouterVO>> getRouters(HttpServletRequest request) {
        logger.info("收到请求...");
        String token = request.getHeader("token");
        List<RouterVO> routerVos = routerService.getRouters(token);
        if (!routerVos.isEmpty()) {
            return ResultVOUtils.success(routerVos);
        } else {
            return ResultVOUtils.error(routerVos);
        }
    }

    @GetMapping("/getMenuTree")
    public ResultVO<List<Router2TreeVO>> getRouterTree() {
        logger.info("收到请求...");

        List<Router2TreeVO> routers2TreeVOs = routerService.getRouters2Tree();
        if (!routers2TreeVOs.isEmpty()) {
            return ResultVOUtils.success(routers2TreeVOs);
        } else {
            return ResultVOUtils.error(routers2TreeVOs);
        }
    }

    @PostMapping("/addMenuTree2Router")
    public ResultVO<Integer> addMenuTree2Router(@RequestBody Menu2RouterDto menu2RouterDto) {
        logger.info("收到请求...");
        Integer id = routerService.addRouter(menu2RouterDto);
        return ResultVOUtils.success(id);
    }

    // 校验路由名重复
    @GetMapping("/validateRouterTitleRepeat")
    public ResultVO<Boolean> validateRouterTitleRepeat(String routerTitle, Integer id) {
        Boolean validate = routerService.validateRouterTitleRepeat(routerTitle, id);
        return ResultVOUtils.success(validate);
    }
}
