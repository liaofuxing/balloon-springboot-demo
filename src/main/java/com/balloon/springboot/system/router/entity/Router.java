package com.balloon.springboot.system.router.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 前端路由实体类
 *
 * @author liaofuxing
 * @E-mail liaofuxing@outlook.com
 * @date: 2019/08/07 11:45
 **/
@Data
@Entity
public class Router {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String path;

    private String component;

    private String title;

    private String icon;

    private String redirect;

    private Integer parent;

    private Integer level;

    private String description;


    public Router() {
    }

    public Router(Integer id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }
}
