package com.balloon.springboot.system.role.service.impl;

import com.balloon.springboot.core.rules.DatePageVO;
import com.balloon.springboot.core.rules.SelectFormatVO;
import com.balloon.springboot.core.uilts.BeanCopyUtil;
import com.balloon.springboot.system.role.dao.RoleInfoDao;
import com.balloon.springboot.system.role.dto.RoleInfoDto;
import com.balloon.springboot.system.role.entity.RoleInfo;
import com.balloon.springboot.system.role.entity.SystemUserRole;
import com.balloon.springboot.system.role.service.RoleInfoService;
import com.balloon.springboot.system.role.service.SystemUserRoleService;
import com.balloon.springboot.system.role.vo.RoleInfoVO;
import com.balloon.springboot.system.router.entity.MenuRole;
import com.balloon.springboot.system.router.service.MenuRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 角色 Service
 *
 * @author liaofuxing
 * @E-mail liaofuxing@outlook.com
 * @date 2020/03/16 20:25
 **/
@Service
public class RoleInfoServiceImpl implements RoleInfoService {

    @Autowired
    private SystemUserRoleService systemUserRoleService;

    @Autowired
    private RoleInfoDao roleInfoRepository;

    @Autowired
    private MenuRoleService menuRoleService;

    /**
     * 角色分页查询
     *
     * @param roleInfoDto roleInfoDto
     * @return DatePageVO<RoleInfoVO>
     *
     */
    @Override
    public DatePageVO<RoleInfoVO> findRoleInfoPage(RoleInfoDto roleInfoDto) {
        Pageable pageable = PageRequest.of(roleInfoDto.getPage() - 1, roleInfoDto.getPageSize(), Sort.Direction.ASC, "id");

        Specification<RoleInfo> specification = (Specification<RoleInfo>) (root, criteriaQuery, criteriaBuilder) -> {
            //分页条件组装
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(roleInfoDto.getRoleName())) {
                list.add(criteriaBuilder.like(root.get("roleName").as(String.class), "%" + roleInfoDto.getRoleName() + "%"));
            }

            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        Page<RoleInfo> roleInfoPage = roleInfoRepository.findAll(specification, pageable);
        List<RoleInfoVO> roleInfoVOList = BeanCopyUtil.copyListProperties(roleInfoPage.getContent(), RoleInfoVO::new);

        return new DatePageVO<>(roleInfoPage.getTotalElements(), roleInfoVOList);
    }

    /**
     * 查询所有角色
     *
     * @return List<SelectFormatVO>
     */
    @Override
    public List<SelectFormatVO> findRoleAll() {
        List<SelectFormatVO> roleInfoVOList = new ArrayList<>();
        List<RoleInfo> roleInfoList = roleInfoRepository.findAll();
        for (RoleInfo roleInfo : roleInfoList) {
            SelectFormatVO roleInfoVO = new SelectFormatVO(roleInfo.getId(), roleInfo.getRoleName());
            roleInfoVOList.add(roleInfoVO);
        }
        return roleInfoVOList;
    }

    @Override
    public RoleInfo findByRoleName(String roleName) {
        return roleInfoRepository.findByRoleName(roleName);
    }

    /**
     *
     * 根据userId 查询角色
     *
     * @param userId userId
     * @return RoleInfo
     */
    @Override
    public RoleInfo findRoleInfoByUserId(Integer userId) {
        SystemUserRole systemUserRoleBySystemUserId = systemUserRoleService.findSystemUserRoleBySystemUserId(userId);
        Optional<RoleInfo> byId = roleInfoRepository.findById(systemUserRoleBySystemUserId.getRoleId());
        return byId.orElse(null);
    }

    /**
     * 新增角色
     *
     * @param roleInfoDto roleInfoDto
     */
    @Transactional
    @Override
    public void addRole(RoleInfoDto roleInfoDto) {
        RoleInfo roleInfo = new RoleInfo();
        BeanUtils.copyProperties(roleInfoDto, roleInfo);
        roleInfoRepository.save(roleInfo);

        // 保存角色和菜单的关系
        MenuRole menuRole = new MenuRole(roleInfo.getId(), roleInfoDto.getTreeChecked());
        menuRoleService.addMenuRole(menuRole);
    }

    /**
     * 编辑角色
     *
     * @param roleInfoDto roleInfoDto
     */
    @Transactional
    @Override
    public void editRole(RoleInfoDto roleInfoDto) {
        Optional<RoleInfo> byId = roleInfoRepository.findById(roleInfoDto.getId());
        RoleInfo roleInfoDB = byId.orElse(new RoleInfo());
        BeanUtils.copyProperties(roleInfoDto, roleInfoDB);
        roleInfoRepository.save(roleInfoDB);

        // 保存角色和菜单的关系
        MenuRole menuRole = menuRoleService.findMenuRole(roleInfoDto.getId());
        if(menuRole == null) {
            menuRole = new MenuRole();
            menuRole.setRoleId(roleInfoDto.getId());
        }
        menuRole.setMenu(roleInfoDto.getTreeChecked());
        menuRoleService.addMenuRole(menuRole);
    }

    @Override
    public Boolean validateRoleNameRepeat(String roleName, Integer id) {
        RoleInfo byRoleName = roleInfoRepository.findByRoleNameAndIdNot(roleName, id);
        return byRoleName != null;
    }

}
