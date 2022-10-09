# 个人项目后端模板

# 项目介绍

<p align="center">我个人使用的后端模板项目，用户鉴权、菜单等基本功能配置已实现，后续会根据自己的需求进行维护更新。</p>

<p align="center">
	<a target="_blank" href="https://github.com/Shinyoki/my_project_back">
      <img src="https://img.shields.io/badge/license-MIT-green"/>
      <img src="https://img.shields.io/badge/JDK-1.8+-green.svg"/>
      <img src="https://img.shields.io/badge/springboot-2.4.13-green"/>
      <img src="https://img.shields.io/badge/MybatisPlus-3.5.0-green"/>
      <img src="https://img.shields.io/badge/jwt-0.9.1-green"/>
      <img src="https://img.shields.io/badge/fastjson-1.2.75-green"/>
      <img src="https://img.shields.io/badge/mysql-8.0.28-green"/>
      <img src="https://img.shields.io/badge/redis-7.0.0-green"/>
    </a>
</p>
<p align="center">
    <a href="#所有项目">所有项目</a>
    <a href="#项目特点">项目特点</a>
    <a href="技术介绍">技术介绍</a>
    <a href="项目截图">项目截图</a>
    <a href="项目总结">项目总结</a>
    <a href="项目结构">项目结构</a>
</p>


# 所有项目

**后端Github地址：**[https://github.com/Shinyoki/my_project_back](https://github.com/Shinyoki/my_project_back)

**后台Github地址：**[https://github.com/Shinyoki/my_project_admin](https://github.com/Shinyoki/my_project_admin)



**后端接口文档地址：**[https://localhost:8888/doc.html](https://localhost:8888/doc.html)

默认管理员账号：`admin`，密码：`1234567`

# 项目特点

TODO ....

# 技术介绍

**后台：** 

- vue
- vuex
- vue-router
- axios
- element

**后端：**

- Spring boot
- Spring Security
- Swagger
- Mybatis Plus
- Redis
- MySql

**开发环境：**

| 开发工具                      | 说明              |
| ----------------------------- | ----------------- |
| IDEA                          | Java开发工具      |
| WebStorm                      | 前端开发工具      |
| Another Redis Desktop Manager | Redis远程链接工具 |

| 开发环境 | 版本   |
| -------- | ------ |
| JDK      | 1.8    |
| MySql    | 8.0.28 |
| Redis    | 7.0.0  |

# 项目截图

## 后台

- 登录

![](https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/20221003163326.png)

- 首页

![](https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/20221003163421.png)

- 用户管理

![image-20221003163454567](C:\Users\16418\AppData\Roaming\Typora\typora-user-images\image-20221003163454567.png)

![image-20221003163624830](C:\Users\16418\AppData\Roaming\Typora\typora-user-images\image-20221003163624830.png)

- 角色管理

![](https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/20221003163509.png)

- 菜单管理

![](https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/20221003163527.png)

- 日志管理

![](https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/20221003163554.png)

- 接口权限管理

![](https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/20221003163655.png)

- 个人中心

![](https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/20221003163712.png)

## 前台

TODO ...

# 项目结构

<pre>
├─project.sql   				sql文件
├─system						系统模块：存放数据库操作的mappers & services
|   ├─pom.xml
|   ├─src
|   |  ├─main
|   |  |  ├─resources
|   |  |  |     ├─mapper					mapper xml
|   |  |  ├─java
|   |  |  |  ├─com
|   |  |  |  |  ├─senko
|   |  |  |  |  |   ├─system
|   |  |  |  |  |   |   ├─service		services
|   |  |  |  |  |   |   |    ├─impl
|   |  |  |  |  |   |   ├─mapper		mappers
├─framework
|     ├─pom.xml
|     ├─src
|     |  ├─main
|     |  |  ├─java
|     |  |  |  ├─com
|     |  |  |  |  ├─senko
|     |  |  |  |  |   ├─framework				框架模块：用于存放和Spring框架配置有关的类
|     |  |  |  |  |   |     ├─web													核心webservice
|     |  |  |  |  |   |     |  ├─service	
|     |  |  |  |  |   |     |  ├─exception
|     |  |  |  |  |   |     |  |     └ControllerExceptionHandler.java		web异常处理
|     |  |  |  |  |   |     |  ├─core
|     |  |  |  |  |   |     |  |  ├─service
|     |  |  |  |  |   |     ├─strategy															一些策略，如 上传策略
|     |  |  |  |  |   |     |    ├─AbstractUploadStrategy.java
|     |  |  |  |  |   |     |    ├─context
|     |  |  |  |  |   |     |    |    └FileUploadStrategyContext.java
|     |  |  |  |  |   |     ├─properties
|     |  |  |  |  |   |     ├─cron																	定时任务
|     |  |  |  |  |   |     |  └UserIpLocationChecker.java
|     |  |  |  |  |   |     ├─consumer
|     |  |  |  |  |   |     ├─config																配置类
|     |  |  |  |  |   |     |   ├─SenkoConfig.java
|     |  |  |  |  |   |     |   ├─ThreadPoolConfig.java
|     |  |  |  |  |   |     |   ├─web
|     |  |  |  |  |   |     |   |  ├─WebMvcConfiguration.java
|     |  |  |  |  |   |     |   |  ├─interceptor
|     |  |  |  |  |   |     |   |  |      ├─AccessLimitInterceptor.java
|     |  |  |  |  |   |     |   |  |      └PageInterceptor.java
|     |  |  |  |  |   |     |   ├─swagger
|     |  |  |  |  |   |     |   |    └SwaggerConfig.java
|     |  |  |  |  |   |     |   ├─security
|     |  |  |  |  |   |     |   |    ├─CustomAuthorityDeserializer.java
|     |  |  |  |  |   |     |   |    ├─SecurityUtils.java
|     |  |  |  |  |   |     |   |    ├─WebSecurityConfig.java
|     |  |  |  |  |   |     |   |    ├─manager
|     |  |  |  |  |   |     |   |    |    ├─AccessDecisionManagerImpl.java
|     |  |  |  |  |   |     |   |    |    └FilterInvocationSecurityMetadataSourceImpl.java
|     |  |  |  |  |   |     |   |    ├─handler
|     |  |  |  |  |   |     |   |    |    ├─AccessDeniedHandlerImpl.java
|     |  |  |  |  |   |     |   |    |    ├─LoginFailedHandlerImpl.java
|     |  |  |  |  |   |     |   |    |    ├─LoginSuccessHandlerImpl.java
|     |  |  |  |  |   |     |   |    |    ├─LogoutSuccessHandlerImpl.java
|     |  |  |  |  |   |     |   |    |    └NoEnoughAuthorityHandler.java
|     |  |  |  |  |   |     |   |    ├─filter
|     |  |  |  |  |   |     |   |    |   └JwtAuthenticationTokenFilter.java
|     |  |  |  |  |   |     |   ├─redis
|     |  |  |  |  |   |     |   |   ├─FastJson2JsonRedisSerializer.java
|     |  |  |  |  |   |     |   |   ├─RedisConfiguration.java
|     |  |  |  |  |   |     |   |   └RedisHandler.java
|     |  |  |  |  |   |     |   ├─rabbitmq
|     |  |  |  |  |   |     |   ├─mysql
|     |  |  |  |  |   |     |   |   ├─MybatisMetaObjectConfig.java
|     |  |  |  |  |   |     |   |   └MybatisPlusConfig.java
|     |  |  |  |  |   |     ├─aspetc
|     |  |  |  |  |   |     |   └LogAspect.java
├─common
|   ├─pom.xml
|   ├─src
|   |  ├─main
|   |  |  ├─java
|   |  |  |  ├─com
|   |  |  |  |  ├─senko
|   |  |  |  |  |   ├─common								公共模块，存放默认会用到的类
|   |  |  |  |  |   |   ├─utils									工具类
|   |  |  |  |  |   |   |   ├─StreamUtil.java
|   |  |  |  |  |   |   |   ├─page
|   |  |  |  |  |   |   |   |  └PageUtils.java
|   |  |  |  |  |   |   |   ├─ip
|   |  |  |  |  |   |   |   | ├─AddressUtils.java
|   |  |  |  |  |   |   |   | └IpUtils.java
|   |  |  |  |  |   |   |   ├─http
|   |  |  |  |  |   |   |   |  ├─HttpUtils.java
|   |  |  |  |  |   |   |   |  └ServletUtils.java
|   |  |  |  |  |   |   |   ├─file
|   |  |  |  |  |   |   |   |  └FileUtil.java
|   |  |  |  |  |   |   |   ├─bean
|   |  |  |  |  |   |   |   |  ├─BeanCopyUtils.java
|   |  |  |  |  |   |   |   |  └SpringUtils.java
|   |  |  |  |  |   |   |   ├─async
|   |  |  |  |  |   |   |   |   ├─AsyncManager.java
|   |  |  |  |  |   |   |   |   └ShutDownManager.java
|   |  |  |  |  |   |   ├─exceptions										异常类
|   |  |  |  |  |   |   |     ├─service
|   |  |  |  |  |   |   |     |    ├─PageRequestException.java
|   |  |  |  |  |   |   |     |    └ServiceException.java
|   |  |  |  |  |   |   ├─enums												枚举
|   |  |  |  |  |   |   ├─core													系统vo、po、dto
|   |  |  |  |  |   |   |  ├─vo
|   |  |  |  |  |   |   |  ├─entity
|   |  |  |  |  |   |   |  ├─dto
|   |  |  |  |  |   |   ├─constants											常量
|   |  |  |  |  |   |   |     ├─CommonConstants.java
|   |  |  |  |  |   |   |     ├─HttpStatus.java
|   |  |  |  |  |   |   |     ├─RedisConstants.java
|   |  |  |  |  |   |   |     └UploadConstants.java
|   |  |  |  |  |   |   ├─common											
|   |  |  |  |  |   |   |   ├─vo
|   |  |  |  |  |   |   |   ├─entity
|   |  |  |  |  |   |   |   ├─dto
|   |  |  |  |  |   |   ├─annotations										注解
|   |  |  |  |  |   |   |      ├─AccessLimit.java
|   |  |  |  |  |   |   |      ├─LogOperation.java
|   |  |  |  |  |   |   |      └OptType.java
├─admin
|   ├─pom.xml
|   |   ├─classes
|   |   |    ├─application-dev.yml										开发环境配置
|   |   |    ├─application.yml												默认配置
|   |   |    ├─com
|   |   |    |  ├─senko
|   |   |    |  |   ├─Application.class									程序入口
|   |   |    |  |   ├─controller						
|   |   |    |  |   |     ├─system											 系统Controller
|   |   |    |  |   |     |   ├─SysLogController.class
|   |   |    |  |   |     |   ├─SysMenuController.class
|   |   |    |  |   |     |   ├─SysResourceController.class
|   |   |    |  |   |     |   ├─SysRoleController.class
|   |   |    |  |   |     |   ├─SysUploadController.class
|   |   |    |  |   |     |   └SysUserController.class
</pre>