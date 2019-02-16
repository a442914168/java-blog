package com.bobo.mybatis.dao;

import com.bobo.mybatis.bean.Employee;

import java.util.List;

/**
 * @program: MyBatis_02_Mapper
 * @description:
 * @author: bobobo
 * @create: 2018-07-27 11:36
 **/
public interface EmployeeMapperPluse {

    /* 根据id获取，并且返回是ResultMap操作 */
    Employee getEmpByIdResultMap(Integer id);

    /* 获取员工信息，并且包含其的部门信息 */
    Employee getEmpAndDept(Integer id);

    /* 获取员工信息，并且包含其的部门信息 使用association方式*/
    Employee getEmpAndDeptAssociation(Integer id);

    /* 获取员工信息，并且包含其的部门信息 使用association 分步查询方式*/
    Employee getEmpAndDeptStep(Integer id);

    /* 根据ID查询雇员信息 */
    List<Employee> getEmpsByDeptId(Integer id);

}
