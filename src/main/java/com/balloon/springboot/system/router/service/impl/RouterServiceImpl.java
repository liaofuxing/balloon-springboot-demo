package com.balloon.springboot.system.router.service.impl;



import com.balloon.core.exception.BusinessRuntimeException;
import com.balloon.springboot.enums.exception.SysExceptionEnums;
import com.balloon.springboot.core.jackson.JacksonObjectMapper;
import com.balloon.springboot.redis.utils.RedisUtils;
import com.balloon.springboot.system.role.entity.SystemUserRole;
import com.balloon.springboot.system.role.service.SystemUserRoleService;
import com.balloon.springboot.system.router.dao.RouterDao;
import com.balloon.springboot.system.router.dto.Menu2RouterDto;
import com.balloon.springboot.system.router.entity.MenuRole;
import com.balloon.springboot.system.router.entity.Router;
import com.balloon.springboot.system.router.service.MenuRoleService;
import com.balloon.springboot.system.router.service.RouterService;
import com.balloon.springboot.system.router.vo.Router2TreeVO;
import com.balloon.springboot.system.router.vo.RouterVO;
import com.balloon.springboot.system.user.entity.SystemUser;
import com.balloon.springboot.system.user.service.SystemUserService;
import com.fasterxml.jackson.core.JsonProcessingException;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author liaofuxing
 * @E-mail liaofuxing@outlook.com
 * @date 2020/03/19 02:37
 *
 **/

@Service
public class RouterServiceImpl implements RouterService {

    @Autowired
    private RouterDao routerDao;

    @Autowired
    private SystemUserRoleService systemUserRoleService;

    @Autowired
    private MenuRoleService menuRoleService;

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 获取所有路由,不按菜单结构排序
     *
     * @return List<Router> 所有路由
     */
    public List<Router> getRouterAll() {
        return routerDao.findAll();
    }

    /**
     * 新增路由
     */
    @Transactional
    public Integer addRouter(Menu2RouterDto menu2RouterDto) {
        Router router = new Router();
        BeanUtils.copyProperties(menu2RouterDto, router);
        routerDao.save(router);
        return router.getId();
    }


    /**
     * 获取前端路由
     *
     * @param token 登录用户token
     *
     * @return List<RouterVo> 所有的一级路由
     *
     */
    public List<RouterVO> getRouters(String token) {
        // 先获取一级路由
        Integer parent = 0;
        List<RouterVO> routerByParent = getRouterByParent(parent);

        //获取登录用户的菜单权限
        List<Integer> showMenu = getMenuRoleByLoginUser(token);

        return formatRouter(routerByParent, showMenu);
    }



    /**
     *
     * 根据Parent获取路由
     * @param parent 父级路由id
     *
     * @return List<RouterVo>
     */
    public List<RouterVO> getRouterByParent(Integer parent) {
        List<Router> byParent = routerDao.findByParent(parent);
        List<RouterVO> RouterEntityVoList = new ArrayList<>();
        for (Router router : byParent) {
            RouterVO routerVo = new RouterVO();
            BeanUtils.copyProperties(router, routerVo);
            RouterEntityVoList.add(routerVo);
        }
        return RouterEntityVoList;
    }


    /**
     * 通过与传入的父级路由 组装子级
     *
     * 递归组装router,目前可以无限递归,但前端最多能渲染2级目录（vue前端有bug）
     *
     * 通过子级和showMenu如果有一个子级在showMenu中，则显示该父级目录
     * 如果该父级不存在子子级，则直接与showMenu对比，是否在showMenu中，在setHidden(0)
     *
     * @param routerList 传入的父级路由
     *
     * @return List<RouterVo> 递归
     */
    public List<RouterVO> formatRouter(List<RouterVO> routerList, List<Integer> showMenu) {
        for (RouterVO routerEntityVo : routerList) {
            List<RouterVO> routerByParent = getRouterByParent(routerEntityVo.getId());
            if (routerByParent != null && routerByParent.size() > 0) {
                for (RouterVO byParent: routerByParent) {
                    if (showMenu.contains(byParent.getId())) {
                        routerEntityVo.setHidden(0);
                    }
                }
                routerEntityVo.setChildren(routerByParent);
                formatRouter(routerByParent, showMenu);
            } else {
                if (showMenu.contains(routerEntityVo.getId())) {
                    routerEntityVo.setHidden(0);
                }
            }
        }
        return routerList;
    }




    /**
     * 获取菜单Tree
     *
     * @return List<Router2TreeVO> TreeVO
     *
     */
    public List<Router2TreeVO> getRouters2Tree() {
        // 先获取一级路由
        Integer parent = 0;
        List<Router2TreeVO> routerByParent = getRouter2TreeByParent(parent);
        return formatRouter2Tree(routerByParent);
    }



    /**
     * 根据Parent获取路由
     * @param parent 父级路由id
     * @return List<Router2TreeVO>
     */
    public List<Router2TreeVO> getRouter2TreeByParent(Integer parent) {
        List<Router> byParent = routerDao.findByParent(parent);
        List<Router2TreeVO> router2TreeVOList = new ArrayList<>();
        for (Router router : byParent) {
            Router2TreeVO router2TreeVO = new Router2TreeVO(router.getId(), router.getTitle(), router.getParent());
            router2TreeVOList.add(router2TreeVO);
        }
        return router2TreeVOList;
    }

    /**
     * 通过与传入的父级Tree 组装子级
     * 递归组装router,目前可以无限递归,但前端最多能渲染2级目录（vue前端有bug）
     *
     * @param router2TreeVOList 传入的父级路由
     *
     * @return router2TreeVOList 递归
     */
    public List<Router2TreeVO> formatRouter2Tree(List<Router2TreeVO> router2TreeVOList) {
        for (Router2TreeVO router2TreeVO : router2TreeVOList) {
            List<Router2TreeVO> router2TreeByParent = getRouter2TreeByParent(router2TreeVO.getId());
            if (router2TreeByParent != null && router2TreeByParent.size() > 0) {
                router2TreeVO.setChildren(router2TreeByParent);
                formatRouter2Tree(router2TreeByParent);
            }
        }
        return router2TreeVOList;
    }

    /**
     *
     * 获取所有显示的路由 showMenu
     * @param token 登录用户token
     * @return showMenu
     */
    public List<Integer> getMenuRoleByLoginUser(String token) {

        List<String> menuList;
        try{
            String userInfoStr = redisUtils.get("SYSTEM_USER_INFO:" + token);
            JacksonObjectMapper mapper = new JacksonObjectMapper();
            SystemUser systemUser = mapper.readValue(userInfoStr, SystemUser.class);
            SystemUser systemUserById = systemUserService.findSystemUserById(systemUser.getId());
            SystemUserRole systemUserRole = systemUserRoleService.findSystemUserRoleBySystemUserId(systemUserById.getId());
            MenuRole menuRole = menuRoleService.findMenuRole(systemUserRole.getRoleId());
            String[] menuSplit = menuRole.getMenu().split(",");
            menuList = Arrays.asList(menuSplit);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BusinessRuntimeException(SysExceptionEnums.USER_NOT_FOUND);
        }
        return menuList.stream().map(Integer::parseInt).distinct().collect(Collectors.toList());
    }

    @Override
    public Boolean validateRouterTitleRepeat(String routerTitle, Integer id) {
        Router byTitle = routerDao.findByTitleAndIdNot(routerTitle, id);
        return byTitle != null;
    }
}
