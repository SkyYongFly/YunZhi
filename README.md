## 1. 项目简介
**`云知问答`** 是一个类似于知乎、百度知道之类的在线问答网站。系统实现人员注册、登录、权限管理、问题提问、回答、点赞、合法校验、不同场景下的排序处理、统计分析、详情展示、内容搜索等功能模块；为实现核心业务系统的高可用与异步解耦，剥离出短信验证、日志处理、索引处理子系统；管理员角色可通过后台管理模块进行系统管理。

当然啦，我们并不是想去做一个颠覆它们的网站，我们只是借鉴一下他们简洁优雅的界面以及问答风格，本质上还是学习实践成果。当然要做到优雅，并不仅仅是页面的优美，还要有着强大的性能，能承受高并发场景，能快速响应，容错性强，在这里，我们将不懈地追求系统质量的完美。

我们将通过这个项目来检验所学的JavaWeb开发相关的知识，也是一个总结提高的过程，避免眼高手低。其实在实际的开发过程中，
会发现很多光学习发现不到的问题。废话说多了，还是开始正文内容吧~~~

不如先放个系统实际截图压压惊~~~
![首页](https://raw.githubusercontent.com/SkyYongFly/YunZhi/master/doc/img/%E7%B3%BB%E7%BB%9F%E9%A6%96%E9%A1%B5.png "首页")
## 2. 开发环境
* JDK  : 8.0
+ Server : Tomcat8.0
* IDE  ： IntelliJ IDEA  
- 构建  ：  Maven  
* 版本协作 ：Git
- 前端UI  ：Layui 
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
* MySQL数据库表设计：这里我们先设计出系统需要的核心的表

![数据库](https://raw.githubusercontent.com/SkyYongFly/YunZhi/master/doc/sql/database.png "数据库")

+ Redis表设计：

<table>
	<tr>
		<th style="width:200px">表名</th>
		<th style="width:100px">类型</th>
		<th style="width:200px">作用</th>
		<th style="width:250px">键/值/权重</th>
	</tr> 
	<tr>
		<td>REDIS_HASH_PHONEVERCODE_TIME</td>
		<td>Hash</td> 
		<td>已申请验证码手机号</td>
		<td>手机号&nbsp;&nbsp;|&nbsp;&nbsp;时间戳</td>
	</tr> 
	<tr>
		<td>REDIS_HASH_PHONEVERCODES</td>
	     	<td>Hash</td> 
	     	<td>手机号以及发送的验证码</td>
	     	<td>手机号&nbsp;&nbsp;|&nbsp;&nbsp;验证码</td>
	</tr>
	<tr>
		<td>REDIS_HASH_USER_HEAD_IMGS</td>
	     	<td>Hash</td> 
	     	<td>用户头像路径信息</td> 
		<td>用户ID&nbsp;&nbsp;|&nbsp;&nbsp;头像相对路径</td> 
	</tr> 
        <tr>
		<td>REDIS_SET_HASREGISTERPHONE</td>
	     	<td>Set</td> 
	     	<td>已注册用户手机号</td> 
		<td>用户手机号</td> 
	</tr> 
     <tr>
		<td>REDIS_SET_USER_QUESTIONS_人员ID</td>
	    	 <td>Set</td> 
	     	<td>用户已提问问题</td> 
		<td>问题ID</td> 
	</tr> 
     <tr>
		<td>REDIS_SET_STAR_ANSWERS_人员ID</td>
	     	<td>Set</td> 
	     	<td>用户已点赞回答</td> 
		<td>回答ID</td> 
	</tr> 
     <tr>
		<td>REDIS_ZSET_QUESTIONS_TIME</td>
	     	<td>Sorted Set</td> 
	     	<td>新增问题信息</td> 
		<td>问题ID&nbsp;&nbsp;|&nbsp;&nbsp;时间戳</td>  
	</tr> 
     <tr>
		<td>REDIS_ZSET_QUESTIONS_HOT</td>
	     	<td>Sorted Set</td> 
	     	<td>热门问题</td> 
		<td>问题ID&nbsp;&nbsp;|&nbsp;&nbsp;热门指数</td> 
	</tr> 
     <tr>
		<td>REDIS_ZSET_QUESTIONS_HOT_用户ID</td>
	     	<td>Sorted Set</td> 
	     	<td>热门问题临时查询缓存</td> 
		<td>问题ID&nbsp;&nbsp;|&nbsp;&nbsp;热门指数</td> 
	</tr> 
     <tr>
		<td>REDIS_ZSET_QUESTION_ANSWERS_问题ID</td>
	     	<td>Sorted Set</td> 
	     	<td>问题回答信息</td> 
		<td>问题ID&nbsp;&nbsp;|&nbsp;&nbsp;点赞数</td> 
	</tr> 
</table>

## 6. 环境设置
#### 1.Tomcat  
Tomcat版本8.0以上即可

#### 2.MySQL  
创建好数据库，执行SQL脚本创建所需表，数据库连接配置文件 database.properties 对应修改即可

#### 3.Redis  
安装好Redis，建议设置密码，否则Redis连接会有权限等问题，默认连接密码123456，windows中redis可修改redis.windows.conf文件，具体可搜索设置方法。Redis连接配置：springredis.xml。

这里推荐开发中使用可视化工具，方便我们追踪redis缓存情况：[https://redisdesktop.com/download](https://redisdesktop.com/download)

#### 4.RabbitMQ   
安装好RabbitMQ，新建用户，授予权限，修改对应连接配置：springrabbit.xml

#### 5.图片预览
系统没有采用分布式文件系统，只是简单的集成文件模块，用户头像预览等需要配置Tomcat磁盘访问权限，例如用户头像文件默认放在D:\\YunZhi目录，那么需要配置Tomcat的server.xml文件如下内容：
![图片预览](https://github.com/SkyYongFly/YunZhi/blob/master/doc/img/path.png "图片预览")

#### 6.IDEA设置
* Code部署
下载代码 ；编译输出 ；配置Tomcat;
+ 上下文设置
IDEA部署Tomcat访问需要设置应用上下文
![上下文](https://github.com/SkyYongFly/YunZhi/blob/master/doc/img/contextpath.png "上下文")
