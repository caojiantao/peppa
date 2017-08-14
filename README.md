# SSM + velocity 最好的开发框架

从事java web开发工程师一年有余，吸收借鉴并完善了一个略微好用的一套网站开发框架，权当个人学习总结。

### 后台框架

spring + spring mvc + mybatis

### 页面渲染

velocity

### 项目管理

maven

### 目录结构
```
    Java
    |--com.cjt.ssm
    |  |--controller (所有请求的集中处理)
    |  |--dao (数据持久层操作的包)
    |  |--encrypt (一些加密解密的工具类)
    |  |--entity (数据模型层，与表的字段有所对应)
    |  |--exception (自定义的异常集合)
    |  |--interceptor (自定义拦截器)
    |  |--service (业务逻辑处理)
```

```
    resources
    |--mapper (数据库sql编写的xml)
    |--properties (项目的配置资源文件，类似数据库的连接信息，页面定向跳转的相关url)
    |--spring (spring的相关配置，注解，包扫描，数据源的配置，页面渲染，等等)
```

```
    webapp
    |--vm (所有velocity动态页面存放的根目录)
    |--WEB-INF (网站配置文件web.xml)
```

### 特性支持

1. 静态资源与拦截器、restFul接口共存；

2. 登陆拦截器雏形已经编写；

3. 数据库启用注解式事务，支持失败回滚；

4. mybatis批量insert，批量update代码完善；

5. spring mvc 异常统一处理；