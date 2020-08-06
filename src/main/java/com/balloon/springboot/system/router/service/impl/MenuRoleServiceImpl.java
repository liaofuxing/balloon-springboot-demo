package com.balloon.springboot.system.router.service.impl;

import com.balloon.core.exception.BusinessRuntimeException;
import com.balloon.springboot.common.SysExceptionEnums;
import com.balloon.springboot.system.router.dao.MenuRoleDao;
import com.balloon.springboot.system.router.dao.RouterDao;
import com.balloon.springboot.system.router.entity.MenuRole;
import com.balloon.springboot.system.router.entity.Router;
import com.balloon.springboot.system.router.service.MenuRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author liaofuxing
 * @E-mail liaofuxing@outlook.com
 * @date 2019/08/09 15:21
 **/
@Service
public class MenuRoleServiceImpl implements MenuRoleService {

    private static final Logger logger = LoggerFactory.getLogger(MenuRoleServiceImpl.class);

    @Autowired
    private MenuRoleDao menuRoleDao;
    @Autowired
    private RouterDao routerDao;


    /**
     * @return List<Integer>
     */
    public List<Integer> findMenuRoleList(Integer roleId) {
        MenuRole menuRole = menuRoleDao.findByRoleId(roleId);
        List<Integer> menu = new ArrayList<>();
        if (menuRole != null) {
            String[] menuArr = menuRole.getMenu().split(",");
            for (String s : menuArr) {
                Optional<Router> byId = routerDao.findById(Integer.parseInt(s));
                if (!byId.isPresent()) {
                    logger.info("角色id:{}未配置菜单", roleId);
                    throw new BusinessRuntimeException(SysExceptionEnums.ROLE_NOT_MENU);
                }
                if (byId.get().getParent() != 0) {
                    menu.add(Integer.parseInt(s));
                }
            }
        }
        return menu;
    }


    /**
     * 根据 roleId 查询角色和菜单关系
     *
     * @return MenuRole 菜单和角色中间表
     */
    public MenuRole findMenuRole(Integer roleId) {
        return menuRoleDao.findByRoleId(roleId);
    }

    /**
     * 新增角色和菜单关系
     */
    @Transactional
    public void addMenuRole(MenuRole menuRole) {
        menuRoleDao.save(menuRole);
    }

}
