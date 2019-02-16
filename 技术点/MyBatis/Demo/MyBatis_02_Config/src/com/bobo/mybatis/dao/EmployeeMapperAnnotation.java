package com.bobo.mybatis.dao;

import com.bobo.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Select;

/**
 * @program: MyBatis_02_Config
 * @description:
 * @author: bobobo
 * @create: 2018-07-23 23:00
 **/
public interface EmployeeMapperAnnotation {

    @Select("select * from tbl_employee where id=#{id}")
    public Employee getEmpById(Integer id);

}
