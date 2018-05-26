## 1. 项目简介
实践是检验真理的唯一标准！
   
我们将通过这个项目来检验所学的JavaWeb开发相关的知识，也是一个总结提高的过程，避免眼高手低。其实在实际的开发过程中，
会发现很多光学习发现不到的问题。废话说多了，还是开始正文内容吧~~~

**`云知问答`** 是一个在线问答网站，类似于知乎、百度知道之类的网站。当然啦，我们并不是想去做一个颠覆它们的网站，我们只是借鉴
一下他们简洁优雅的界面以及问答风格，本质上还是学习实践成果。
## 2. 开发环境
* IDE  ： IntelliJ IDEA  
- 构建  ：  Maven  
* 版本协作 ：GIT  
- 前端UI  ：LayUI  
* 前端框架 ：SpringMVC
+ 逻辑框架 ：Spring
- ORM  :  MyBatis
* 数据库 ：MySQL
+ 缓存 ：Ehcache
- NoSql：Redis
+ 搜索引擎 ：Lucene
* 权限 ： Shiro
- 队列 ：RabbitMQ 
     
## 3. 项目架构
![云知架构](https://raw.githubusercontent.com/SkyYongFly/YunZhi/master/doc/img/%E4%BA%91%E7%9F%A5%E6%9E%B6%E6%9E%84.png "云知架构")
## 4. 原型设计
工欲善其事必先利其器，在正式开发之前，我们先对项目做个简单的设计，把我们对于网站的界面的想法用原型设计出来，方便我们后期开发
有一定的参考，不然写一下，就要想想要做个啥东西，肯定心累~~~~

我们使用Mockplus设计一下我们的网站界面，来，走起！

* 首页
![首页](https://raw.githubusercontent.com/SkyYongFly/YunZhi/master/doc/img/index.jpg "首页")
- 注册
![注册](https://raw.githubusercontent.com/SkyYongFly/YunZhi/master/doc/img/register.jpg "注册")
+ 登录
![登录](https://raw.githubusercontent.com/SkyYongFly/YunZhi/master/doc/img/login.jpg "登录")
* 个人中心
![个人中心](https://raw.githubusercontent.com/SkyYongFly/YunZhi/master/doc/img/user.jpg "个人中心")
- 提问问题
![提问问题](https://raw.githubusercontent.com/SkyYongFly/YunZhi/master/doc/img/question.jpg "提问问题")
+ 回答
![回答](https://raw.githubusercontent.com/SkyYongFly/YunZhi/master/doc/img/answer.jpg "回答")
- 问题详情
![问题详情](https://raw.githubusercontent.com/SkyYongFly/YunZhi/master/doc/img/question_detail.jpg "问题详情")

## 5. 数据库设计
## 6. 环境设置
#### 1.Tomcat  
Tomcat版本8.0以上即可

#### 2.mysql  
创建好数据库，执行SQL脚本创建所需表，数据库连接配置文件 database.properties 对应修改即可

#### 3.redis  
安装好redis，建议设置密码，修改redis连接配置：springredis.xml。

这里推荐开发中使用可视化工具，方便我们追踪redis缓存情况：[https://redisdesktop.com/download](https://redisdesktop.com/download)

#### 4.rabbitmq   
安装好rabbitmq，新建用户，授予权限，修改对应连接配置：springrabbit.xml
