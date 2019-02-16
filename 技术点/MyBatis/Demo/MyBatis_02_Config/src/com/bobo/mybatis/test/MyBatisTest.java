package com.bobo.mybatis.test;

import com.bobo.mybatis.bean.Employee;
import com.bobo.mybatis.dao.EmployeeMapper;
import com.bobo.mybatis.dao.EmployeeMapperAnnotation;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @program: MyBatis_01_HelloWorld
 * @description:
 *
 * 1、接口式编程
 * 	原生：		Dao		====>  DaoImpl
 * 	mybatis：	Mapper	====>  xxMapper.xml
 *
 * 2、SqlSession代表和数据库的一次会话；用完必须关闭；
 * 3、SqlSession和connection一样她都是非线程安全。每次使用都应该去获取新的对象。
 * 4、mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
 * 		（将接口和xml进行绑定）
 * 		EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
 * 5、两个重要的配置文件：
 * 		mybatis的全局配置文件：包含数据库连接池信息，事务管理器信息等...系统运行环境信息
 * 		sql映射文件：保存了每一个sql语句的映射信息：
 * 					将sql抽取出来。
 *
 *
 * @author: bobobo
 * @create: 2018-07-22 13:37
 **/

public class MyBatisTest {

    public static void main(String[] args) throws IOException {
//        test();
        testInterface();
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

    public static void testInterface() throws IOException {

        //1）、根据全局配置文件得到SqlSessionFactory；
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //使用sqlSession工厂，获取到sqlSession对象，执行增删改查，
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //获取接口映射
        //1、命名空间设置为EmployeeMapper的全路径
        //2、方法名称设置为EmployeeMapper的方法
        EmployeeMapperAnnotation employeeMapper = sqlSession.getMapper(EmployeeMapperAnnotation.class);

        //查询
        Employee employee = employeeMapper.getEmpById(1);

        System.out.println(employeeMapper.getClass());
        System.out.println(employee);
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



