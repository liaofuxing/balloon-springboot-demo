#  balloon-springboot-demo

#### 项目介绍
    balloon-springboot-demo 是 balloon 系列项目，balloon-core 和 balloon-springboot 项目的整合类子，也是一个快速开发脚手架，包含一个后台和 vue 前端


#### 依赖
    - balloon-core 1.0.0
    - balloon-springboot 0.1.0
    - jdk 1.8+
    - maven 3.3+



#### 功能
    1. 用户管理
    2. 部门管理
    3. 角色管理
    4. 根据用户角色动态菜单管理
    5. 用户登录登出，登录超时，强制下线（包含活跃用户token自动续约和短信登录功能）



#### 地址
    前端 vue 项目地址：[balloon-springboot-demo-vue](https://gitee.com/liaofuxing/balloon-springboot-demo-vue)



#### 部署

    1.将项目从 gitee 上检出，因为项目依赖 balloon-core 和 balloon-springboot，并且两个项目都不在mavne的中央仓库，所以请将这两个项目也一起检出.
    
    - balloon-core 地址:[balloon-core](https://gitee.com/liaofuxing/balloon-core)
    - balloon-springboot 地址:[balloon-springboot](https://gitee.com/liaofuxing/balloon-springboot)
    
    2.将 balloon-core， balloon-springboot 通过 maven 命令 install 到本地 maven 仓库
    
    3.以上操作完成后即可在目录下运行 mavne 的打包命令



