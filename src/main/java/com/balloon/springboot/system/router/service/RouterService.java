package com.balloon.springboot.system.router.service;

import com.balloon.springboot.system.router.dto.Menu2RouterDto;
import com.balloon.springboot.system.router.entity.Router;
import com.balloon.springboot.system.router.vo.Router2TreeVO;
import com.balloon.springboot.system.router.vo.RouterVO;

import java.util.List;

/**
 *
 * @author liaofuxing
 * @E-mail liaofuxing@outlook.com
 * @date 2020/03/19 02:37
 *
 **/

public interface RouterService {



    /**
     * 获取所有路由,不按菜单结构排序
     *
     * @return List<Router> 所有路由
     */
    List<Router> getRouterAll();

    /**
     * 新增路由
     */
     Integer addRouter(Menu2RouterDto menu2RouterDto);


    /**
     * 获取前端路由
     *
     * @param token 登录用户token
     *
     * @return List<RouterVo> 所有的一级路由
     *
     */
    List<RouterVO> getRouters(String token);


    /**
     * 获取菜单Tree
     *
     * @return List<Router2TreeVO> TreeVO
     *
     */
     List<Router2TreeVO> getRouters2Tree();



    /**
     *
     * 获取所有显示的路由 showMenu
     *
     * @param token 登录用户token
     *
     * @return showMenu
     */
    List<Integer> getMenuRoleByLoginUser(String token);


    Boolean validateRouterTitleRepeat(String routerTitle, Integer id);
}
