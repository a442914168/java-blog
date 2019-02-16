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

    //携带了哪个字段查询条件就带上这个字段的值 - if
    List<Employee> getEmpsByConditionIf(Employee employee);

    //携带了哪个字段查询条件就带上这个字段的值 - Trim
    List<Employee> getEmpsByConditionTrim(Employee employee);

    //携带了哪个字段查询条件就带上这个字段的值 - Choose
    List<Employee> getEmpsByConditionChoose(Employee employee);

    //更新，判断某个属性有值才更新
    void updateEmp(Employee employee);

    //查询员工id'在给定集合中的
    List<Employee> getEmpsByConditionForeach(@Param("ids")List<Integer> ids);

    //批量保存
    void addEmps(@Param("emps")List<Employee> emps);

    //内置参数
    List<Employee> getEmpsTestInnerParameter(Employee employee);

}
