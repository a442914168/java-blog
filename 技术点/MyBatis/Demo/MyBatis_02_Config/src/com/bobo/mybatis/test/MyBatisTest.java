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
 * 1���ӿ�ʽ���
 * 	ԭ����		Dao		====>  DaoImpl
 * 	mybatis��	Mapper	====>  xxMapper.xml
 *
 * 2��SqlSession��������ݿ��һ�λỰ���������رգ�
 * 3��SqlSession��connectionһ�������Ƿ��̰߳�ȫ��ÿ��ʹ�ö�Ӧ��ȥ��ȡ�µĶ���
 * 4��mapper�ӿ�û��ʵ���࣬����mybatis��Ϊ����ӿ�����һ���������
 * 		�����ӿں�xml���а󶨣�
 * 		EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
 * 5��������Ҫ�������ļ���
 * 		mybatis��ȫ�������ļ����������ݿ����ӳ���Ϣ�������������Ϣ��...ϵͳ���л�����Ϣ
 * 		sqlӳ���ļ���������ÿһ��sql����ӳ����Ϣ��
 * 					��sql��ȡ������
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
     * 1������xml�����ļ���mybatis-config.xml������һ��SqlSessionFactory���� ������Դ��һЩ���л�����Ϣ��
     * 2��sqlӳ���ļ���EmployeeMapper.xml��������ÿһ��sql���Լ�sql�ķ�װ����ȡ�
     * 3����sqlӳ���ļ�д��ȫ�������ļ���
     *    <mapper resource="com/bobo/mybatis/conf/EmployeeMapper.xml"/>
     * 4�����벽��
     *    1��������ȫ�������ļ��õ�SqlSessionFactory��
     *    2����ʹ��sqlSession��������ȡ��sqlSession����ִ����ɾ�Ĳ飬
     *         һ��sqlSession��������ݿ��һ�λػ���������Ҫ�رգ�
     *    3����ʹ��sql��Ψһ��־������MyBatisִ���ĸ�sql��sql���б�����ӳ���ļ��У�
     *
     *
     * @throws IOException
     */
    public static void test() throws IOException {

        //1��������ȫ�������ļ��õ�SqlSessionFactory��
        SqlSessionFactory sessionFactory = getSqlSessionFactory();

        //ʹ��sqlSession��������ȡ��sqlSession����ִ����ɾ�Ĳ飬
        SqlSession sqlSession = sessionFactory.openSession();

        //ʹ��sql��Ψһ��־������MyBatisִ���ĸ�sql��sql���б�����ӳ���ļ��У�
        //Ψһ��־,��EmployeeMapper.xml������
        //com.atguigu.mybatis.EmployeeMapper.getEmpById �ɲ��Ϊ
        //1�������ռ䣺com.atguigu.mybatis.EmployeeMapper
        //2��ִ�з�����getEmpById
        Employee employee = sqlSession.selectOne("com.atguigu.mybatis.EmployeeMapper.getEmpById", 1);

        System.out.println(employee);

        //һ��sqlSession��������ݿ��һ�λػ���������Ҫ�رգ�
        sqlSession.close();
    }

    public static void testInterface() throws IOException {

        //1��������ȫ�������ļ��õ�SqlSessionFactory��
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //ʹ��sqlSession��������ȡ��sqlSession����ִ����ɾ�Ĳ飬
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //��ȡ�ӿ�ӳ��
        //1�������ռ�����ΪEmployeeMapper��ȫ·��
        //2��������������ΪEmployeeMapper�ķ���
        EmployeeMapperAnnotation employeeMapper = sqlSession.getMapper(EmployeeMapperAnnotation.class);

        //��ѯ
        Employee employee = employeeMapper.getEmpById(1);

        System.out.println(employeeMapper.getClass());
        System.out.println(employee);
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



