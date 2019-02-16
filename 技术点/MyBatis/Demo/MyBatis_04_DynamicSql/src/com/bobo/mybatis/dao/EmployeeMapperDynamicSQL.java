package com.bobo.mybatis.dao;

import com.bobo.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: MyBatis_04_DynamicSql
 * @description:
 * @author: bobobo
 * @create: 2018-07-29 21:58
 **/
public interface EmployeeMapperDynamicSQL {

    //Я�����ĸ��ֶβ�ѯ�����ʹ�������ֶε�ֵ - if
    List<Employee> getEmpsByConditionIf(Employee employee);

    //Я�����ĸ��ֶβ�ѯ�����ʹ�������ֶε�ֵ - Trim
    List<Employee> getEmpsByConditionTrim(Employee employee);

    //Я�����ĸ��ֶβ�ѯ�����ʹ�������ֶε�ֵ - Choose
    List<Employee> getEmpsByConditionChoose(Employee employee);

    //���£��ж�ĳ��������ֵ�Ÿ���
    void updateEmp(Employee employee);

    //��ѯԱ��id'�ڸ��������е�
    List<Employee> getEmpsByConditionForeach(@Param("ids")List<Integer> ids);

    //��������
    void addEmps(@Param("emps")List<Employee> emps);

    //���ò���
    List<Employee> getEmpsTestInnerParameter(Employee employee);

}
