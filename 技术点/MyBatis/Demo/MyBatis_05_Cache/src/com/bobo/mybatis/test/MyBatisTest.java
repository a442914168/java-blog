package com.bobo.mybatis.test;

import com.bobo.mybatis.bean.Department;
import com.bobo.mybatis.bean.Employee;
import com.bobo.mybatis.dao.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import sun.plugin.javascript.navig.Array;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @program: MyBatis_01_HelloWorld
 * @description: 1、接口式编程
 * 原生：		Dao		====>  DaoImpl
 * mybatis：	Mapper	====>  xxMapper.xml
 * <p>
 * 2、SqlSession代表和数据库的一次会话；用完必须关闭；
 * 3、SqlSession和connection一样她都是非线程安全。每次使用都应该去获取新的对象。
 * 4、mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
 * （将接口和xml进行绑定）
 * EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
 * 5、两个重要的配置文件：
 * mybatis的全局配置文件：包含数据库连接池信息，事务管理器信息等...系统运行环境信息
 * sql映射文件：保存了每一个sql语句的映射信息：
 * 将sql抽取出来。
 * @author: bobobo
 * @create: 2018-07-22 13:37
 **/

public class MyBatisTest {

    public static void main(String[] args) throws IOException {
//        testCacheFirst();
//        testCacheFirstFail();
        testCacheSecond();
    }

    /**
     * 两级缓存：
     * 一级缓存：（本地缓存）：sqlSession级别的缓存。一级缓存是一直开启的；SqlSession级别的一个Map
     * 		与数据库同一次会话期间查询到的数据会放在本地缓存中。
     * 		以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库；
     *
     * 		一级缓存失效情况（没有使用到当前一级缓存的情况，效果就是，还需要再向数据库发出查询）：
     * 		1、sqlSession不同。
     * 		2、sqlSession相同，查询条件不同.(当前一级缓存中还没有这个数据)
     * 		3、sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
     * 		4、sqlSession相同，手动清除了一级缓存（缓存清空）
     *
     * 二级缓存：（全局缓存）：基于namespace级别的缓存：一个namespace对应一个二级缓存：
     * 		工作机制：
     * 		1、一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中；
     * 		2、如果会话关闭；一级缓存中的数据会被保存到二级缓存中；新的会话查询信息，就可以参照二级缓存中的内容；
     * 		3、sqlSession===EmployeeMapper==>Employee
     * 						DepartmentMapper===>Department
     * 			不同namespace查出的数据会放在自己对应的缓存中（map）
     * 			效果：数据会从二级缓存中获取
     * 				查出的数据都会被默认先放在一级缓存中。
     * 				只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中
     * 		使用：
     * 			1）、开启全局二级缓存配置：<setting name="cacheEnabled" value="true"/>
     * 			2）、去mapper.xml中配置使用二级缓存：
     * 				<cache></cache>
     * 			3）、我们的POJO需要实现序列化接口
     *
     * 和缓存有关的设置/属性：
     * 			1）、cacheEnabled=true：false：关闭缓存（二级缓存关闭）(一级缓存一直可用的)
     * 			2）、每个select标签都有useCache="true"：
     * 					false：不使用缓存（一级缓存依然使用，二级缓存不使用）
     * 			3）、【每个增删改标签的：flushCache="true"：（一级二级都会清除）】
     * 					增删改执行完成后就会清楚缓存；
     * 					测试：flushCache="true"：一级缓存就清空了；二级也会被清除；
     * 					查询标签：flushCache="false"：
     * 						如果flushCache=true;每次查询之后都会清空缓存；缓存是没有被使用的；
     * 			4）、sqlSession.clearCache();只是清楚当前session的一级缓存；
     * 			5）、localCacheScope：本地缓存作用域：（一级缓存SESSION）；当前会话的所有数据保存在会话缓存中；
     * 								STATEMENT：可以禁用一级缓存；
     *
     *第三方缓存整合：
     *		1）、导入第三方缓存包即可；
     *		2）、导入与第三方缓存整合的适配包；官方有；
     *		3）、mapper.xml中使用自定义缓存
     *		<cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache>
     *
     * @throws IOException
     *
     */
    public static void testCacheSecond() throws IOException {
        SqlSessionFactory sessionFactory = getSqlSessionFactory();

        SqlSession sqlSession = sessionFactory.openSession();
        SqlSession sqlSession1 = sessionFactory.openSession();

        EmployeeMapperCache mapperCache = sqlSession.getMapper(EmployeeMapperCache.class);
        EmployeeMapperCache mapperCache1 = sqlSession1.getMapper(EmployeeMapperCache.class);

        Employee employee = mapperCache.getEmpById(1);
        System.out.println("第一次查询:"+employee);

        //关闭第一个session
        sqlSession.close();

        Employee employee1 = mapperCache1.getEmpById(1);
        System.out.println("第二次查询:"+employee1);

        System.out.println("两次结果是否是同一个:" + (employee == employee1));

        sqlSession1.close();
    }

    public static void testCacheFirstFail() throws IOException {
        SqlSessionFactory sessionFactory = getSqlSessionFactory();

        SqlSession sqlSession = sessionFactory.openSession();

//            sqlSession不同
//            SqlSession sqlSession1 = sessionFactory.openSession();
//
//            EmployeeMapperCache mapperCache = sqlSession.getMapper(EmployeeMapperCache.class);
//            EmployeeMapperCache mapperCache1 = sqlSession1.getMapper(EmployeeMapperCache.class);
//
//            Employee employee = mapperCache.getEmpById(1);
//            System.out.println("第一次查询:"+employee);
//
//            Employee employee1 = mapperCache1.getEmpById(1);
//            System.out.println("第二次查询:"+employee1);


//            sqlSession相同，查询条件不同(当前一级缓存中还没有这个数据)。
//            EmployeeMapperCache mapperCache = sqlSession.getMapper(EmployeeMapperCache.class);
//
//            Employee employee = mapperCache.getEmpById(1);
//            System.out.println("第一次查询:"+employee);
//
//            Employee employee1 = mapperCache.getEmpById(3);
//            System.out.println("第一次查询:"+employee);

//          sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)。
//            EmployeeMapperCache mapperCache = sqlSession.getMapper(EmployeeMapperCache.class);
//
//            Employee employee = mapperCache.getEmpById(1);
//            System.out.println("第一次查询:"+employee);
//
//            mapperCache.addEmp(new Employee(null,"bobo","hbyouxiang@qq.com","1"));
//            System.out.println("执行添加操作");
//
//            Employee employee1 = mapperCache.getEmpById(1);
//            System.out.println("第二次查询:"+employee1);

//          sqlSession相同，手动清除了一级缓存（缓存清空）
            EmployeeMapperCache mapperCache = sqlSession.getMapper(EmployeeMapperCache.class);

            Employee employee = mapperCache.getEmpById(1);
            System.out.println("第一次查询:"+employee);

            //清除缓存
            sqlSession.clearCache();

            Employee employee1 = mapperCache.getEmpById(1);
            System.out.println("第二次查询:"+employee1);

            System.out.println("两次结果是否是同一个:" + (employee == employee1));

            sqlSession.close();

    }

     public static void testCacheFirst() throws IOException {
        SqlSessionFactory sessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sessionFactory.openSession();
        try {

            EmployeeMapperCache mapperCache = sqlSession.getMapper(EmployeeMapperCache.class);

            Employee employee = mapperCache.getEmpById(1);
            System.out.println("第一次查询:"+employee);

            Employee employee1 = mapperCache.getEmpById(1);
            System.out.println("第二次查询:"+employee1);

            System.out.println("两次结果是否是同一个:" + (employee == employee1));

        } finally {
            sqlSession.close();
        }
     }

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

}



