package com.balloon.springboot.system.user.entity;

import com.balloon.springboot.security.entity.SecurityUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 用户
 *
 * @author liaofuxing
 * @date 2020/03/13 22:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class SystemUser extends SecurityUser implements Serializable {

    private static final long serialVersionUI = 2656653232L;

    /**
     * 系统用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * 账号
     */
    private String account;

    /**
     * 系统用户用户名
     */
    private String username;

    /**
     * 系统用户密码
     */
    private String password;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 创建时间
     */
    private String creationTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 账户是否被禁用 0.否 1.是
     */
    private Integer enabledFlag;

}
