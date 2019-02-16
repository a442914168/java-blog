package com.bobo.mybatis.test;

import com.bobo.mybatis.bean.Department;
import com.bobo.mybatis.bean.Employee;
import com.bobo.mybatis.dao.DepartmentMapper;
import com.bobo.mybatis.dao.EmployeeMapper;
import com.bobo.mybatis.dao.EmployeeMapperPluse;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        testCURD();
//        testParamType();
//        testResuType();
        testResultMap();
    }

    public static void testResultMap() throws IOException {
        SqlSessionFactory sessionFactory = getSqlSessionFactory();
        SqlSession session = sessionFactory.openSession();

        try {

            EmployeeMapperPluse mapper = session.getMapper(EmployeeMapperPluse.class);
            DepartmentMapper departmentMapper = session.getMapper(DepartmentMapper.class);

//            简单的ResultMap
//            Employee employee = mapper.getEmpByIdResultMap(1);
//            System.out.println(employee);

//            级联查询
//            Employee employee = mapper.getEmpAndDept(1);
//            System.out.println(employee);

//            级联查询使用association
//            Employee employee = mapper.getEmpAndDeptAssociation(1);
//            System.out.println(employee);

//            级联查询使用association，分步查询
//            Employee employee = mapper.getEmpAndDeptStep(1);
//            System.out.println(employee);

//            级联查询使用collection
//            Department department = departmentMapper.getDeptAndEmpsById(1);
//            System.out.println(department);

//            级联查询使用collection，分步查询
            Department department = departmentMapper.getDeptAndEmpsByIdStep(1);
            System.out.println(department);

        } finally {
            session.close();
        }
    }

    /**
     * 测试返回类型
     *
     * @throws IOException
     */
    public static void testResuType() throws IOException {
        SqlSessionFactory sessionFactory = getSqlSessionFactory();
        SqlSession session = sessionFactory.openSession();

        try {

            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);

//            list
//            List<Employee> list = mapper.getList("%bobo%");
//            System.out.println(list);

//            单条记录map
//            Map<String, Object> map = mapper.getMapSingle(1);
//            System.out.println(map);

//            多条记录map
            Map<String, Employee> map = mapper.getMapList("%bobo%");
            System.out.println(map);

        } finally {
            session.close();
        }

    }

    /**
     * mapper映射的参数测试
     *
     * @throws IOException
     */
    public static void testParamType() throws IOException {
        SqlSessionFactory sessionFactory = getSqlSessionFactory();
        SqlSession session = sessionFactory.openSession();

        try {

            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);

            //使用$获取参数
            Map<String, Object> map = new HashMap<>();
            map.put("id", 2);
            map.put("lastName", "Tom");
            map.put("tableName", "tbl_employee");
            Employee employee = mapper.getEmpByMap(map);

            System.out.println(employee);

        } finally {
            session.close();
        }
    }

    /**
     * 测试增删改
     * <p>
     * 1、mybatis允许增删改直接定义以下类型返回值
     * Integer、Long、Boolean、void
     * <p>
     * 2、我们需要手动提交数据
     * sqlSessionFactory.openSession();===》手动提交
     * sqlSessionFactory.openSession(true);===》自动提交
     *
     * @throws IOException
     */
    public static void testCURD() throws IOException {

        SqlSessionFactory sessionFactory = getSqlSessionFactory();
        SqlSession session = sessionFactory.openSession();

        try {

            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            //add
            Employee employee = new Employee(null, "bobo", "xjyouxiang@qq.com", "1");
            mapper.addEmp(employee);
            System.out.println(employee);

//            //delete
//            mapper.deleteEmpById(1);
//
//            //read
//            Employee employee1 = mapper.getEmpById(1);
//            System.out.println(employee1);
//
//            //update
//            employee1.setEmail("hbyouxiang@qq.com");
//            boolean success = mapper.updateEmp(employee1);
//            if (success) {
//                employee1 = mapper.getEmpById(11);
//                System.out.println(employee1);
//            }

            session.commit();

        } finally {
            session.close();
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



