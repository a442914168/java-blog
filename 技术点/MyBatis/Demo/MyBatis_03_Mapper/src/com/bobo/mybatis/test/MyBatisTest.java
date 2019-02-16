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
 * @description: 1���ӿ�ʽ���
 * ԭ����		Dao		====>  DaoImpl
 * mybatis��	Mapper	====>  xxMapper.xml
 * <p>
 * 2��SqlSession��������ݿ��һ�λỰ���������رգ�
 * 3��SqlSession��connectionһ�������Ƿ��̰߳�ȫ��ÿ��ʹ�ö�Ӧ��ȥ��ȡ�µĶ���
 * 4��mapper�ӿ�û��ʵ���࣬����mybatis��Ϊ����ӿ�����һ���������
 * �����ӿں�xml���а󶨣�
 * EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
 * 5��������Ҫ�������ļ���
 * mybatis��ȫ�������ļ����������ݿ����ӳ���Ϣ�������������Ϣ��...ϵͳ���л�����Ϣ
 * sqlӳ���ļ���������ÿһ��sql����ӳ����Ϣ��
 * ��sql��ȡ������
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

//            �򵥵�ResultMap
//            Employee employee = mapper.getEmpByIdResultMap(1);
//            System.out.println(employee);

//            ������ѯ
//            Employee employee = mapper.getEmpAndDept(1);
//            System.out.println(employee);

//            ������ѯʹ��association
//            Employee employee = mapper.getEmpAndDeptAssociation(1);
//            System.out.println(employee);

//            ������ѯʹ��association���ֲ���ѯ
//            Employee employee = mapper.getEmpAndDeptStep(1);
//            System.out.println(employee);

//            ������ѯʹ��collection
//            Department department = departmentMapper.getDeptAndEmpsById(1);
//            System.out.println(department);

//            ������ѯʹ��collection���ֲ���ѯ
            Department department = departmentMapper.getDeptAndEmpsByIdStep(1);
            System.out.println(department);

        } finally {
            session.close();
        }
    }

    /**
     * ���Է�������
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

//            ������¼map
//            Map<String, Object> map = mapper.getMapSingle(1);
//            System.out.println(map);

//            ������¼map
            Map<String, Employee> map = mapper.getMapList("%bobo%");
            System.out.println(map);

        } finally {
            session.close();
        }

    }

    /**
     * mapperӳ��Ĳ�������
     *
     * @throws IOException
     */
    public static void testParamType() throws IOException {
        SqlSessionFactory sessionFactory = getSqlSessionFactory();
        SqlSession session = sessionFactory.openSession();

        try {

            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);

            //ʹ��$��ȡ����
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
     * ������ɾ��
     * <p>
     * 1��mybatis������ɾ��ֱ�Ӷ����������ͷ���ֵ
     * Integer��Long��Boolean��void
     * <p>
     * 2��������Ҫ�ֶ��ύ����
     * sqlSessionFactory.openSession();===���ֶ��ύ
     * sqlSessionFactory.openSession(true);===���Զ��ύ
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
     * ����xml�����ļ���mybatis-config.xml������һ��SqlSessionFactory���� ������Դ��һЩ���л�����Ϣ��
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



