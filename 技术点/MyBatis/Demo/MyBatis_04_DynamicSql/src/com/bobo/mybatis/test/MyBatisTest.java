package com.bobo.mybatis.test;

import com.bobo.mybatis.bean.Department;
import com.bobo.mybatis.bean.Employee;
import com.bobo.mybatis.dao.DepartmentMapper;
import com.bobo.mybatis.dao.EmployeeMapper;
import com.bobo.mybatis.dao.EmployeeMapperDynamicSQL;
import com.bobo.mybatis.dao.EmployeeMapperPluse;
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
        testDynamicSql();
    }

    public static void testDynamicSql() throws IOException {
        SqlSessionFactory sessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sessionFactory.openSession();
        try {
            EmployeeMapperDynamicSQL mapperDynamicSQL = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);

//            测试 IF
//            mapperDynamicSQL.getEmpsByConditionIf(new Employee(null,null,"hbyouxiang@qq.com",null));

//            测试Trim
//            mapperDynamicSQL.getEmpsByConditionTrim(new Employee(null,null,"hbyouxiang@qq.com",null));

//            测试Choose
//            mapperDynamicSQL.getEmpsByConditionChoose(new Employee(1,"bobo","hbyouxiang@qq.com",null));

//            测试Set
//            mapperDynamicSQL.updateEmp(new Employee(1,"bobo22222","hbyouxiang@qq.com",null));
//            sqlSession.commit();

//            测试foreach
//            List<Employee> employees = mapperDynamicSQL.getEmpsByConditionForeach(Arrays.asList(1,3));
//            System.out.println(employees);

//            测试保存
//            List<Employee> emps = new ArrayList<>();
//            emps.add(new Employee(null, "smith0x1", "hbyouxiang@qq.com", "1",new Department("1")));
//            emps.add(new Employee(null, "allen0x1", "hbyouxiang@qq.com", "0",new Department("1")));
//            mapperDynamicSQL.addEmps(emps);
//            sqlSession.commit();

//            测试内置参数
            mapperDynamicSQL.getEmpsTestInnerParameter(new Employee(1,"bobo","hbyouxiang@qq.com",null));

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



