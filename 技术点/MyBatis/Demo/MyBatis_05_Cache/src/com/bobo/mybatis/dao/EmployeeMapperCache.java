package com.bobo.mybatis.dao;

import com.bobo.mybatis.bean.Employee;

/**
 * @program: MyBatis_05_Cache
 * @description:
 * @author: bobobo
 * @create: 2018-07-30 21:31
 **/
public interface EmployeeMapperCache {

    /* 根据id获取雇员信息 */
    Employee getEmpById(Integer id);

    /* 添加雇员 */
    void addEmp(Employee emp);

}
