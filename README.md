项目：论坛应用系统  

描述：该论坛系统是一个简单的web应用，使用SSH集成框架实现了网络论坛的基本功能：包括用户注册/登录，搜索/浏览帖子，发表/回复/删除帖子、置精华帖等一系列操作；

主要功能模块：
	1、用户管理：用户登录/注册、账号注销、用户个人信息维护、用户锁定/解锁等；
	2、论坛管理：创建/删除论坛版块、显示论坛版块列表、指定论坛版块管理员等；
	3、版块管理：显示论坛版块主题帖列表、搜索/浏览帖子、发表主题帖/回复贴、删除主题帖/回复贴、设置精华帖等；

技术点：
1、	使用Eclipse作为开发工具，Java作为开发语言，使用Maven进行项目构建；
2、	服务器：Tomcat
3、	数据库：MySQL
4、	Dao层：使用Hibernate JPA注解配置数据库表与PO类的映射关系；使用Spring的hibernateTemplate模板操作类执行hibernate的各项操作；
5、	Service层：使用Spring的AOP、IOC编写业务逻辑代码，使用Spring声明式事务管理；
6、	Controller层：使用SpringMVC进行请求的处理和响应，映射使用RestFul风格的URL；
7、	前端视图：使用JSP + JSTL技术；
8、	单元测试：TestNG
