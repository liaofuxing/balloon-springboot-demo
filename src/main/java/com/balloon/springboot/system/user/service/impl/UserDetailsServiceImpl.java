package com.balloon.springboot.system.user.service.impl;

import com.balloon.springboot.security.entity.SecurityUser;
import com.balloon.springboot.security.service.UserDetailsExtService;
import com.balloon.springboot.system.user.dao.SystemUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsExtService {

    @Autowired
    private SystemUserDao systemUserDao;

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return systemUserDao.findByUsername(username);
    }
}
