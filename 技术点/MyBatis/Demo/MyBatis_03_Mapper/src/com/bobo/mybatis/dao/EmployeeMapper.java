package com.bobo.mybatis.dao;

import com.bobo.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * @program: MyBatis_01_HelloWorld
 * @description:
 * @author: bobobo
 * @create: 2018-07-22 14:55
 **/
public interface EmployeeMapper {

    /* ��� */
    void addEmp(Employee emp);

    /* ɾ�� */
    void deleteEmpById(Integer id);

    /* �޸� */
    boolean updateEmp(Employee employee);

    /* ��ѯ */
    Employee getEmpById(Integer id);

    /* ʹ��$���Ż�ȡֵ */
    Employee getEmpByMap(Map<String, Object> map);

    /* ����List���� */
    List<Employee> getList(String lastName);

    /* ����Map key����������value���Ƕ�Ӧ��ֵ */
    Map<String, Object> getMapSingle(Integer id);

    //������¼��װһ��map��Map<String,Employee>:����������¼��������ֵ�Ǽ�¼��װ���javaBean
    //@MapKey:����mybatis��װ���map��ʱ��ʹ���ĸ�������Ϊmap��key
    @MapKey("lastName")
    Map<String, Employee> getMapList(String lastName);

}
