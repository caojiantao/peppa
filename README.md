# Java Web快速开发框架

仅供学习总结.

### 后台框架

spring + spring mvc + mybatis

### 渲染引擎

velocity

### 项目管理

maven

### 目录结构

根据业务职责划分多module，通过maven进行管理。

```
  ├── admin                      // 管理后台
  ├── api                        // 对外服务接口（纯接口） 
  ├── common                     // 公共模块（工具类）
  ├── core                       // 对外接口服务（Hessian）
  ├── dao                        // 数据持久层
  ├── entity                     // 数据表对应实体类
  ├── service                    // 业务逻辑层
  └── web                        // 网站主体
```

其中admin、core和web是war包形式，可以对外发布，其他则都是jar包形式，提供依赖。

### 特性支持

1. 静态资源与拦截器、restFul接口共存；

2. 登陆拦截器雏形已经编写；

3. 数据库启用注解式事务，支持失败回滚；

4. mybatis批量insert，批量update代码完善；

5. spring mvc 异常统一处理；

6. 采用spring的profiles机制，分环境读取配置文件；

7. 支持图片上传，采用高效率的filechannel机制；

8. Spring结合Redis，完美解决数据缓存；

9. 支持邮件通知，可采用vm模板渲染；

10. 动态定时任务；

11. 递归菜单数；