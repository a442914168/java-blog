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
//        testCacheFirst();
//        testCacheFirstFail();
        testCacheSecond();
    }

    /**
     * �������棺
     * һ�����棺�����ػ��棩��sqlSession����Ļ��档һ��������һֱ�����ģ�SqlSession�����һ��Map
     * 		�����ݿ�ͬһ�λỰ�ڼ��ѯ�������ݻ���ڱ��ػ����С�
     * 		�Ժ������Ҫ��ȡ��ͬ�����ݣ�ֱ�Ӵӻ������ã�û��Ҫ��ȥ��ѯ���ݿ⣻
     *
     * 		һ������ʧЧ�����û��ʹ�õ���ǰһ������������Ч�����ǣ�����Ҫ�������ݿⷢ����ѯ����
     * 		1��sqlSession��ͬ��
     * 		2��sqlSession��ͬ����ѯ������ͬ.(��ǰһ�������л�û���������)
     * 		3��sqlSession��ͬ�����β�ѯ֮��ִ������ɾ�Ĳ���(�����ɾ�Ŀ��ܶԵ�ǰ������Ӱ��)
     * 		4��sqlSession��ͬ���ֶ������һ�����棨������գ�
     *
     * �������棺��ȫ�ֻ��棩������namespace����Ļ��棺һ��namespace��Ӧһ���������棺
     * 		�������ƣ�
     * 		1��һ���Ự����ѯһ�����ݣ�������ݾͻᱻ���ڵ�ǰ�Ự��һ�������У�
     * 		2������Ự�رգ�һ�������е����ݻᱻ���浽���������У��µĻỰ��ѯ��Ϣ���Ϳ��Բ��ն��������е����ݣ�
     * 		3��sqlSession===EmployeeMapper==>Employee
     * 						DepartmentMapper===>Department
     * 			��ͬnamespace��������ݻ�����Լ���Ӧ�Ļ����У�map��
     * 			Ч�������ݻ�Ӷ��������л�ȡ
     * 				��������ݶ��ᱻĬ���ȷ���һ�������С�
     * 				ֻ�лỰ�ύ���߹ر��Ժ�һ�������е����ݲŻ�ת�Ƶ�����������
     * 		ʹ�ã�
     * 			1��������ȫ�ֶ����������ã�<setting name="cacheEnabled" value="true"/>
     * 			2����ȥmapper.xml������ʹ�ö������棺
     * 				<cache></cache>
     * 			3�������ǵ�POJO��Ҫʵ�����л��ӿ�
     *
     * �ͻ����йص�����/���ԣ�
     * 			1����cacheEnabled=true��false���رջ��棨��������رգ�(һ������һֱ���õ�)
     * 			2����ÿ��select��ǩ����useCache="true"��
     * 					false����ʹ�û��棨һ��������Ȼʹ�ã��������治ʹ�ã�
     * 			3������ÿ����ɾ�ı�ǩ�ģ�flushCache="true"����һ�����������������
     * 					��ɾ��ִ����ɺ�ͻ�������棻
     * 					���ԣ�flushCache="true"��һ�����������ˣ�����Ҳ�ᱻ�����
     * 					��ѯ��ǩ��flushCache="false"��
     * 						���flushCache=true;ÿ�β�ѯ֮�󶼻���ջ��棻������û�б�ʹ�õģ�
     * 			4����sqlSession.clearCache();ֻ�������ǰsession��һ�����棻
     * 			5����localCacheScope�����ػ��������򣺣�һ������SESSION������ǰ�Ự���������ݱ����ڻỰ�����У�
     * 								STATEMENT�����Խ���һ�����棻
     *
     *�������������ϣ�
     *		1���������������������ɣ�
     *		2����������������������ϵ���������ٷ��У�
     *		3����mapper.xml��ʹ���Զ��建��
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
        System.out.println("��һ�β�ѯ:"+employee);

        //�رյ�һ��session
        sqlSession.close();

        Employee employee1 = mapperCache1.getEmpById(1);
        System.out.println("�ڶ��β�ѯ:"+employee1);

        System.out.println("���ν���Ƿ���ͬһ��:" + (employee == employee1));

        sqlSession1.close();
    }

    public static void testCacheFirstFail() throws IOException {
        SqlSessionFactory sessionFactory = getSqlSessionFactory();

        SqlSession sqlSession = sessionFactory.openSession();

//            sqlSession��ͬ
//            SqlSession sqlSession1 = sessionFactory.openSession();
//
//            EmployeeMapperCache mapperCache = sqlSession.getMapper(EmployeeMapperCache.class);
//            EmployeeMapperCache mapperCache1 = sqlSession1.getMapper(EmployeeMapperCache.class);
//
//            Employee employee = mapperCache.getEmpById(1);
//            System.out.println("��һ�β�ѯ:"+employee);
//
//            Employee employee1 = mapperCache1.getEmpById(1);
//            System.out.println("�ڶ��β�ѯ:"+employee1);


//            sqlSession��ͬ����ѯ������ͬ(��ǰһ�������л�û���������)��
//            EmployeeMapperCache mapperCache = sqlSession.getMapper(EmployeeMapperCache.class);
//
//            Employee employee = mapperCache.getEmpById(1);
//            System.out.println("��һ�β�ѯ:"+employee);
//
//            Employee employee1 = mapperCache.getEmpById(3);
//            System.out.println("��һ�β�ѯ:"+employee);

//          sqlSession��ͬ�����β�ѯ֮��ִ������ɾ�Ĳ���(�����ɾ�Ŀ��ܶԵ�ǰ������Ӱ��)��
//            EmployeeMapperCache mapperCache = sqlSession.getMapper(EmployeeMapperCache.class);
//
//            Employee employee = mapperCache.getEmpById(1);
//            System.out.println("��һ�β�ѯ:"+employee);
//
//            mapperCache.addEmp(new Employee(null,"bobo","hbyouxiang@qq.com","1"));
//            System.out.println("ִ����Ӳ���");
//
//            Employee employee1 = mapperCache.getEmpById(1);
//            System.out.println("�ڶ��β�ѯ:"+employee1);

//          sqlSession��ͬ���ֶ������һ�����棨������գ�
            EmployeeMapperCache mapperCache = sqlSession.getMapper(EmployeeMapperCache.class);

            Employee employee = mapperCache.getEmpById(1);
            System.out.println("��һ�β�ѯ:"+employee);

            //�������
            sqlSession.clearCache();

            Employee employee1 = mapperCache.getEmpById(1);
            System.out.println("�ڶ��β�ѯ:"+employee1);

            System.out.println("���ν���Ƿ���ͬһ��:" + (employee == employee1));

            sqlSession.close();

    }

     public static void testCacheFirst() throws IOException {
        SqlSessionFactory sessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sessionFactory.openSession();
        try {

            EmployeeMapperCache mapperCache = sqlSession.getMapper(EmployeeMapperCache.class);

            Employee employee = mapperCache.getEmpById(1);
            System.out.println("��һ�β�ѯ:"+employee);

            Employee employee1 = mapperCache.getEmpById(1);
            System.out.println("�ڶ��β�ѯ:"+employee1);

            System.out.println("���ν���Ƿ���ͬһ��:" + (employee == employee1));

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



