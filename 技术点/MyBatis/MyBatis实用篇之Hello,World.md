1.  什么是MyBatis?
2.  MyBatis和Hibernate的区别?
3.  去哪里找MyBatis?
4.  编写Hello,World;

本文将会从上面四点进行讲述。
###什么是MyBatis?
##### 一、MyBatis简介
 -  MyBatis 是支持定制化 SQL、存储过程以及高级 映射的优秀的持久层框架。
 -  MyBatis 避免了几乎所有的 JDBC 代码和手动设 置参数以及获取结果集。
-  MyBatis可以使用简单的XML或注解用于配置和原 始映射，将接口和Java的POJO(Plain Old Java Objects，普通的Java对象)映射成数据库中的记录。
#####二、 MyBatis历史
-  原是Apache的一个开源项目iBatis, 2010年6月这 个项目由Apache Software Foundation 迁移到了 Google Code，随着开发团队转投Google Code 旗下， iBatis3.x正式更名为MyBatis ，代码于 2013年11月迁移到[Github](https://github.com/mybatis/mybatis-3)。
-  iBatis一词来源于“internet”和“abatis”的组合，是 一个基于Java的持久层框架。 iBatis提供的持久 层框架包括SQL Maps和Data Access Objects (DAO)
###MyBatis和Hibernate的区别?

#####JDBC
在没有框架的时候，我们使用JDBC请求数据库需要经过：
1.  编写sql;   2.  预编译;  3.  设置参数;  4.  执行sql;  5.  封装结果;

这样的步骤如果每次都写的话，那是相当的繁琐。即使我们封装一个JDBC的工具类也会有下列的问题：
-  SQL夹在Java代码块里，耦合度高导致硬编码内伤;
-  维护不易且实际开发需求中sql是有变化，频繁修改的情况多见;

#####Hibernate
我们熟悉的Hibernate是一个ORM(Object Relational Mapping)框架，它将请求数据库的过程全部封装了起来，并且你只需要配置好对应的关系即可内部自动生成SQL。
因为自动与方便，也会产生一些问题：
-  长难复杂SQL，对于Hibernate而言处理也不容易;
-  内部自动生产的SQL，不容易做特殊优化;
-  基于全映射的全自动框架，大量字段的POJO进行部分映射时比较困难。 导致数据库性能下降;
#####MyBatis
MyBatis是一个半自动化的持久化层框架。它与Hibernate一样，封装了请求数据库的过程，但是暴露出了SQL的部分，让程序员可以更灵活的编写SQL语句。
sql和java编码分开，功能边界清晰，一个专注业务、 一个专注数据。
###去哪里找MyBatis?
1.  [MyBatis Github 地址](https://github.com/mybatis/mybatis-3)
2.  [MyBatis 文档地址](http://mybatis.github.io/mybatis-3/)
3.  [MyBatis 版本下载地址](https://github.com/mybatis/mybatis-3/releases)

###编写Hello,World
1.  创建一张测试表，并插入数据
```sql
SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tbl_employee`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_employee`;
CREATE TABLE `tbl_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `tbl_employee`
-- ----------------------------
BEGIN;
INSERT INTO `tbl_employee` VALUES ('1', 'bobo', '1', 'bobo@qq.com');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
```
2.  导入jar包(本Demo不使用Maven)
-  log4j.jar  （日志）
-  mybatis-3.4.1.jar  （MyBatis）
-  mysql-connector-java-5.1.37-bin.jar （mysql驱动）
3.  创建对应的javaBean
```java
public class Employee {

    private Integer id;
    private String lastName;//与last_name字段不一致
    private String email;
    private String gender;

    //省略getter,setter...
}
```
4.  sql映射文件
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.atguigu.mybatis.EmployeeMapper">

    <!--
        namespace:名称空间;
        id：唯一标识
        resultType：返回值类型
        #{id}：从传递过来的参数中取出id值
    -->

    <select id="getEmpById" resultType="com.bobo.mybatis.bean.Employee">
		select id,last_name lastName,email,gender from tbl_employee where id = #{id}
	</select>
</mapper>
```
5.  创建mybatis配置文件
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <!--数据库信息，基础配置-->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value="xiaojia"/>
            </dataSource>
        </environment>
    </environments>
    <!-- 将我们写好的sql映射文件（EmployeeMapper.xml）一定要注册到全局配置文件（mybatis-config.xml）中 -->
    <mappers>
        <mapper resource="com/bobo/mybatis/conf/EmployeeMapper.xml"/>
</configuration>
```
测试
```java
    /**
     * 根据xml配置文件（mybatis-config.xml）创建一个SqlSessionFactory对象 有数据源等一些运行环境信息。
     *
     * @return
     * @throws IOException
     */
    public static SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "com/bobo/mybatis/conf/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 1、根据xml配置文件（mybatis-config.xml）创建一个SqlSessionFactory对象 有数据源等一些运行环境信息。
     * 2、sql映射文件（EmployeeMapper.xml）配置了每一个sql，以及sql的封装规则等。
     * 3、将sql映射文件写在全局配置文件中
     *    <mapper resource="com/bobo/mybatis/conf/EmployeeMapper.xml"/>
     * 4、代码步骤
     *    1）、根据全局配置文件得到SqlSessionFactory；
     *    2）、使用sqlSession工厂，获取到sqlSession对象，执行增删改查，
     *         一个sqlSession代表和数据库的一次回话，用完需要关闭；
     *    3）、使用sql的唯一标志来告诉MyBatis执行哪个sql，sql都市保存在映射文件中；
     *
     *
     * @throws IOException
     */
    public static void test() throws IOException {

        //1）、根据全局配置文件得到SqlSessionFactory；
        SqlSessionFactory sessionFactory = getSqlSessionFactory();

        //使用sqlSession工厂，获取到sqlSession对象，执行增删改查，
        SqlSession sqlSession = sessionFactory.openSession();

        //使用sql的唯一标志来告诉MyBatis执行哪个sql，sql都市保存在映射文件中；
        //唯一标志,在EmployeeMapper.xml中配置
        //com.atguigu.mybatis.EmployeeMapper.getEmpById 可拆分为
        //1、域名空间：com.atguigu.mybatis.EmployeeMapper
        //2、执行方法：getEmpById
        Employee employee = sqlSession.selectOne("com.atguigu.mybatis.EmployeeMapper.getEmpById", 1);

        System.out.println(employee);

        //一个sqlSession代表和数据库的一次回话，用完需要关闭；
        sqlSession.close();
    }
```
结果
>Employee{id=1, lastName='bobo', email='bobo@qq.com', gender='1'}
#####面向接口编程
在sql映射文件，我们定义了一个ID查询数据方法
```xml
<select id="getEmpById" resultType="com.bobo.mybatis.bean.Employee">
        select id,last_name lastName,email,gender from tbl_employee where id = #{id}
</select>
```
在执行查询时
```java
sqlSession.selectOne("com.atguigu.mybatis.EmployeeMapper.getEmpById", 1);
```
既要传入一大串的标志方法字符，还要传入参数。而且参数类型还是Object得，给我们造成了麻烦，并且容易出错。这时我们使用面向接口编程，就能有效的防止这种情况。
创建EmployeeMapper接口
```java
public interface EmployeeMapper {
    public Employee getEmpById(Integer id);
}
```
更改sql映射文件
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.bobo.mybatis.dao.EmployeeMapper">

    <!--
        namespace:名称空间;指定为接口的全类名
        id：唯一标识
        resultType：返回值类型
        #{id}：从传递过来的参数中取出id值

        public Employee getEmpById(Integer id);
    -->

    <!--public Employee getEmpById(Integer id);-->
    <select id="getEmpById" resultType="com.bobo.mybatis.bean.Employee">
		select id,last_name lastName,email,gender from tbl_employee where id = #{id}
	</select>

</mapper>
```
-  namespace 为 EmployeeMapper的全路径
-  查询方法的id，为EmployeeMapper暴露的方法名称getEmpById

测试
```java
public static void testInterface() throws IOException {

        //1）、根据全局配置文件得到SqlSessionFactory；
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //使用sqlSession工厂，获取到sqlSession对象，执行增删改查，
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //获取接口映射
        //1、命名空间设置为EmployeeMapper的全路径
        //2、方法名称设置为EmployeeMapper的方法
        EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);

        //查询
        Employee employee = employeeMapper.getEmpById(1);

        System.out.println(employeeMapper.getClass());
        System.out.println(employee);
    }
```
 结果
>class com.sun.proxy.$Proxy0
>Employee{id=1, lastName='bobo', email='bobo@qq.com', gender='1'}

可以看到employeeMapper.getClass()打印信息，MyBatis通过动态代理实现了接口的方法，关于动态代理可以看[手写JDK动态代理](https://www.jianshu.com/p/a3c1c45d12a6)

面向接口编程，制定了一系列的规矩，参数类型，返回值等等，并且能解耦。不需要关心实现的MyBatis还是Hibernate，也是使用最多的一种方式