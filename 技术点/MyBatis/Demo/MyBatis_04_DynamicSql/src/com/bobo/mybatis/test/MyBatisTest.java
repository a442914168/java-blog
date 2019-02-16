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
        testDynamicSql();
    }

    public static void testDynamicSql() throws IOException {
        SqlSessionFactory sessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sessionFactory.openSession();
        try {
            EmployeeMapperDynamicSQL mapperDynamicSQL = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);

//            ���� IF
//            mapperDynamicSQL.getEmpsByConditionIf(new Employee(null,null,"hbyouxiang@qq.com",null));

//            ����Trim
//            mapperDynamicSQL.getEmpsByConditionTrim(new Employee(null,null,"hbyouxiang@qq.com",null));

//            ����Choose
//            mapperDynamicSQL.getEmpsByConditionChoose(new Employee(1,"bobo","hbyouxiang@qq.com",null));

//            ����Set
//            mapperDynamicSQL.updateEmp(new Employee(1,"bobo22222","hbyouxiang@qq.com",null));
//            sqlSession.commit();

//            ����foreach
//            List<Employee> employees = mapperDynamicSQL.getEmpsByConditionForeach(Arrays.asList(1,3));
//            System.out.println(employees);

//            ���Ա���
//            List<Employee> emps = new ArrayList<>();
//            emps.add(new Employee(null, "smith0x1", "hbyouxiang@qq.com", "1",new Department("1")));
//            emps.add(new Employee(null, "allen0x1", "hbyouxiang@qq.com", "0",new Department("1")));
//            mapperDynamicSQL.addEmps(emps);
//            sqlSession.commit();

//            �������ò���
            mapperDynamicSQL.getEmpsTestInnerParameter(new Employee(1,"bobo","hbyouxiang@qq.com",null));

        } finally {
            sqlSession.close();
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



