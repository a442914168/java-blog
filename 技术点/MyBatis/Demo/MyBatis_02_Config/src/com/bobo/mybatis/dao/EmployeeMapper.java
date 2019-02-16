package com.bobo.mybatis.dao;

import com.bobo.mybatis.bean.Employee;

/**
 * @program: MyBatis_01_HelloWorld
 * @description:
 * @author: bobobo
 * @create: 2018-07-22 14:55
 **/
public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

}
