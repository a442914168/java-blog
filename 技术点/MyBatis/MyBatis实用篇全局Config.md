1.  引入dtd约束;
2.  properties_引入外部配置文件;
3.  settings_运行时行为设置;
4.  typeAliases_别名;
5.  typeHandlers_类型处理器简介;
6.  plugins_插件简介;
7.  enviroments_运行环境;
8.  databaseIdProvider_多数据库支持;
9.  mappers_sql映射注册;

本文将会从上面几点进行讲述，大家可以看[官方的文档](http://www.mybatis.org/mybatis-3/zh/configuration.html#properties)。

###引入dtd约束
```xml
<!DOCTYPE configuration
 PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
 "http://mybatis.org/dtd/mybatis-3-config.dtd">
```
在xml的顶部输入以上代码即可引入（联网情况下）
![提示列表图](https://upload-images.jianshu.io/upload_images/1307556-1f9f0697bf6f3e70.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
等待一下...当我们在xml中输入 < 能够自动弹出属性列表即为成功！

###properties_引入外部配置文件
mybatis可以使用properties来引入外部properties配置文件的内容;
1.  我们创建一个dbconfig.properties
```java
#数据库驱动
jdbc.driver=com.mysql.jdbc.Driver
#数据库地址
jdbc.url=jdbc:mysql://localhost:3306/mybatis
#数据库账号
jdbc.username=root
#数据库密码
jdbc.password=xiaojia
```
2.  在mybatis-config.xml中引入
```xml
<!--
    resource：引入类路径下的资源
    url：引入网络路径或者磁盘路径下的资源
-->
<properties resource="com/bobo/mybatis/conf/dbconfig.properties"></properties>
```
3.  更改dataSource配置
```xml
<property name="driver" value="${jdbc.driver}"/>
    <property name="url" value="${jdbc.url}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
</dataSource>
```
就可以将我们写死的配置 改为. properties来设置。不过MyBatis交给Spring管理的话，这样作用也不大.

###settings_运行时行为设置
settings这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的**运行时行为**。

|           参数           |                             描述                             |    有效值    |     默认值     |
| :----------------------: | :----------------------------------------------------------: | :----------: | :------------: |
|       cacheEnabled       |         该配置影响所有的映射器配置的**缓存**全局开关         | TRUE / FALSE |      TRUE      |
|    lazyLoadingEnabled    | 延迟加载的全局开关，**当开启时，所有关联对象都会延迟加载**。特定关联关系中可通过设置fetchType属性来覆盖该项的开关状态。 | TRUE / FALSE |      TRUE      |
|      useColumnLabel      | **使用列标签代替列名**。不同的驱动在这方面会有不同的表现，具体可参考相关驱动文档或通过测试这两种不同的模式来观察所用驱动的结果。 | TRUE / FALSE |      TRUE      |
| defaultStatementTimeout  |        设置超时时间，它决定驱动等待数据库响应的秒数。        |  任意正整数  | Not Set (null) |
| mapUnderscoreToCamelCase | **是否开启自动驼峰命名规则（camel case）映射**，即从经典数据库列名 A_COLUMN 到经典 Java 属性名 aColumn 的类似映射。 | TRUE / FALSE |     False      |
以上列出了常用的几个setting参数，完整的请看[官方文档settings](http://www.mybatis.org/mybatis-3/zh/configuration.html#settings)。

我们拿mapUnderscoreToCamelCase来举例，在[Hello，World的例子](https://www.jianshu.com/p/6caa414c0cda)中我们创建的tbl_employee表中的姓名为->**last_name**，而在Employee中我们的命名是驼峰形式->**lastName**。
```sql
select * from tbl_employee where id = #{id}
```
如果我们直接使用select * 是映射不到Employee的lastName值的
>Employee{id=1, lastName='null', email='bobo@qq.com', gender='1'}

这时我们在mybatis-config.xml中添加setting
```xml
<!--
  2、settings包含很多重要的设置项
    setting:用来设置每一个设置项
    name：设置项名
    value：设置项取值
-->
<settings>
  <setting name="mapUnderscoreToCamelCase" value="true"/>
</settings>
```
>Employee{id=1, lastName='bobo', email='bobo@qq.com', gender='1'}

即可映射到相对应的值

###typeAliases_别名
typeAliases 类型别名是为 Java 类型设置一个短的名字，可以方便我们引用某个类。
1.  在config文件中添加typeAlias
```xml
<typeAliases>
<!--
   type:指定要起别名的类型全类名;默认别名就是类名小写；employee
   alias:指定新的别名
 -->
  <typeAlias type="com.bobo.mybatis.bean.Employee" alias="emp"/>
<typeAliases>
```
再更改sql映射文件中的resultType改为我们起的别名**emp**
```xml
<select id="getEmpById" resultType="emp">
```
>Employee{id=1, lastName='bobo', email='bobo@qq.com', gender='1'}

运行测试类，发现依然可以映射的到。

2.  批量标注包

类很多的情况下，可以批量设置别名这个包下的每一个类 创建一个默认的别名，就是简单类名小写。
```xml
<!-- 
  package:为某个包下的所有类批量起别名
  name：指定包名，为当前包以及下面所有的后代包的每一个类都起一个默认别名（类名小写）
 -->
<package name="com.bobo.mybatis.bean"/>
```
更改sql映射的resultType为**employee**
```xml
<select id="getEmpById" resultType="employee">
```
>Employee{id=1, lastName='bobo', email='bobo@qq.com', gender='1'}

3.  注解形式

当我们使用批量标注包的时候，Employee也在包下，发现**emp**无效了。
我们可以使用注解形式来声明别名**@Alias**
```java
@Alias("emp")
public class Employee {
  ....
}
```
更改sql映射的resultType为**emp**
```xml
<select id="getEmpById" resultType="emp">
```
>Employee{id=1, lastName='bobo', email='bobo@qq.com', gender='1'}

使用了别名方便了我们的书写，可是也给调试带来了不少的麻烦，我们没办法通过开发工具直接进入返回的类中。一般的话我们还是会只用全路径名作为resultType的值。
在[官方文档typeAliases](http://www.mybatis.org/mybatis-3/zh/configuration.html#typeAliases)中写明了一些内置的别名，我们在自定义的别名时，不要与其重复。

###typeHandlers_类型处理器简介
typeHandlers的主要工作是，**用类型处理器将获取的值以合适的方式转换成 Java 类型**。如我们的java String类型转换为数据库的varchar类型等等。。
|     类型处理器     |         Java 类型          |          JDBC 类型           |
| :----------------: | :------------------------: | :--------------------------: |
| BooleanTypeHandler | java.lang.Boolean, boolean |     数据库兼容的 BOOLEAN     |
|  ByteTypeHandler   |    java.lang.Byte, byte    | 数据库兼容的 NUMERIC 或 BYTE |
|        ...         |            ...             |             ...              |
一些列举了一些[官方文档typeHandlers](http://www.mybatis.org/mybatis-3/zh/configuration.html#typeHandlers)的例子。
>-  **日期和时间的处理**，JDK1.8以前一直是个头疼的问题。我们通常使用**JSR310**规范领导者Stephen Colebourne创建的**Joda-Time**来操作。1.8已经实现全部的JSR310规范了。
>-  日期时间处理上，我们可以使用MyBatis基于 JSR310(Date and Time API)编写的各种日期**时间类型处理器**。
>-  MyBatis3.4以前的版本需要我们手动注册这些处 理器，以后的版本都是自动注册的。

而对于typeHandlers详细节深入，将会在MyBatis的运行原理中进行详解。

###plugins_插件简介
MyBatis 允许你在已映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：
-  Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
-  ParameterHandler (getParameterObject, setParameters)
-  ResultSetHandler (handleResultSets, handleOutputParameters)
-  StatementHandler (prepare, parameterize, batch, update, query)

总体概括为：
-  拦截参数的处理
-  拦截结果集的处理
-  拦截Sql语法构建的处理

plugins是MyBatis提供的一个非常强大的机制，我们可以通过插件来修改MyBatis的一些核心行为。plugins通过**动态代理机制**，可以介入四大对象的任何一个方法的执行。我们必须要懂得MyBatis的运行流程，我们才能学的懂plugins的工作流程。

###enviroments_运行环境
1.  MyBatis可以配置多种环境，比如开发、测试和生 产环境需要有不同的配置。
```xml
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <!--数据库信息，基础配置-->
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>

        <environment id="release">
            <transactionManager type="JDBC"/>
            <!--数据库信息，基础配置-->
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver2}"/>
                <property name="url" value="${jdbc.url2}"/>
                <property name="username" value="${jdbc.username2}"/>
                <property name="password" value="${jdbc.password2}"/>
            </dataSource>
        </environment>
    </environments>
```
2.  每种环境使用一个environment标签进行配置并指 定唯一标识符
```xml
<environment id="development">
<environment id="release">
```
3.  可以通过environments标签中的default属性指定 一个环境的标识符来快速的切换环境
```xml
<environments default="development">
or
<environments default="release">
```
在environment标签中我们必须声明transactionManager与dataSource
>**transactionManager**:
>type: JDBC | MANAGED | 自定义
>– JDBC:使用了 JDBC 的提交和回滚设置，依赖于从数 据源得到的连接来管理事务范围。 JdbcTransactionFactory
>– MANAGED:不提交或回滚一个连接、让容器来管理 事务的整个生命周期(比如 JEE 应用服务器的上下 文)。 ManagedTransactionFactory
>– 自定义:实现TransactionFactory接口，type=全类名/ 别名

>**dataSource**:
>type: UNPOOLED | POOLED | JNDI | 自定义
>– UNPOOLED:不使用连接池， UnpooledDataSourceFactory
>– POOLED:使用连接池， PooledDataSourceFactory – JNDI: 在EJB 或应用服务器这类容器中查找指定的数
>据源
>– 自定义:实现DataSourceFactory接口，定义数据源的 获取方式。

实际开发中我们使用Spring管理数据源，并进行事务控制的配置来覆盖上述配置

###databaseIdProvider_多数据库支持
MyBatis 可以根据不同的数据库厂商执行不同的语句。
```xml
<!-- 5、
databaseIdProvider：支持多数据库厂商的；
  type: DB_VENDOR(使用MyBatis提供的VendorDatabaseIdProvider解析数据库
                  厂商标识。也可以实现DatabaseIdProvider接口来自定义。)
  Property-name:数据库厂商标识
  Property-value:为标识起一个别名，方便SQL语句使用 
                 databaseId属性引用databaseId属性引用
-->
<databaseIdProvider type="DB_VENDOR">
  <!-- 为不同的数据库厂商起别名 -->
  <property name="MySQL" value="mysql"/>
  <property name="Oracle" value="oracle"/>
  <property name="SQL Server" value="sqlserver"/>
</databaseIdProvider>
```
```xml
<!--databaseId databaseIdProvider写的别名-->
<select id="getEmpById" resultType="com.atguigu.mybatis.bean.Employee"
		databaseId="mysql">
		select * from tbl_employee where id = #{id}
</select>
```
>MyBatis匹配规则如下:
>1.  如果没有配置databaseIdProvider标签，那么databaseId=null
>2.  如果配置了databaseIdProvider标签，使用标签配置的name去匹 配数据库信息，匹配上设置databaseId=配置指定的值，否则依旧为 null
>3.  如果databaseId不为null，他只会找到配置databaseId的sql语句
>4.  MyBatis 会加载不带 databaseId 属性和带有匹配当前数据库 databaseId 属性的所有语句。如果同时找到带有 databaseId 和不带 databaseId 的相同语句，则后者会被舍弃。

###mappers_sql映射注册
将写好的sql mapper映射文件注册到全局配置文件中，这也是必不可少的一步。
1.  mapper逐个注册SQL映射文件
```xml
<!-- 
  mapper：注册一个sql映射 ,注册配置文件
  resource：引用类路径下的sql映射文件 mybatis/mapper/EmployeeMapper.xml
  url：引用网路路径或者磁盘路径下的sql映射文件
  file：///var/mappers/AuthorMapper.xml
					
  class：引用（注册）接口，
  1、有sql映射文件，映射文件名必须和接口同名，并且放在与接口同一目录下；
  2、没有sql映射文件，所有的sql都是利用注解写在接口上;

  推荐：
      比较重要的，复杂的Dao接口我们来写sql映射文件
      不重要，简单的Dao接口为了开发快速可以使用注解；
-->
<mappers>
  <mapper resource="mybatis/mapper/EmployeeMapper.xml"/>
  <mapper class="com.atguigu.mybatis.dao.EmployeeMapperAnnotation"/>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
</mappers>
```
2.  使用批量注册
```xml
<mappers>
  <!-- 批量注册：这种方式要求SQL映射文件名必须和接口名相同并且在同一目录下 -->
  <package name="com.atguigu.mybatis.dao"/>
</mappers>
```