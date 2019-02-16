package com.bobo.mybatis.dao;

import com.bobo.mybatis.bean.Department;

/**
 * @program: MyBatis_02_Mapper
 * @description:
 * @author: bobobo
 * @create: 2018-07-27 23:10
 **/
public interface DepartmentMapper {

    /* 根据部门id查询部门信息 */
    Department getDeptById(Integer id);

    /* 根据部门id查询部门信息，包含雇员信息 */
    Department getDeptAndEmpsById(Integer id);

    /* 根据部门id查询部门信息，包含雇员信息，分步查询*/
    Department getDeptAndEmpsByIdStep(Integer id);

}
