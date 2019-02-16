package com.bobo.mybatis.dao;

import com.bobo.mybatis.bean.Employee;

/**
 * @program: MyBatis_05_Cache
 * @description:
 * @author: bobobo
 * @create: 2018-07-30 21:31
 **/
public interface EmployeeMapperCache {

    /* ����id��ȡ��Ա��Ϣ */
    Employee getEmpById(Integer id);

    /* ��ӹ�Ա */
    void addEmp(Employee emp);

}
