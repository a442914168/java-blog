package com.bobo.mybatis.dao;

import com.bobo.mybatis.bean.Department;

/**
 * @program: MyBatis_02_Mapper
 * @description:
 * @author: bobobo
 * @create: 2018-07-27 23:10
 **/
public interface DepartmentMapper {

    /* ���ݲ���id��ѯ������Ϣ */
    Department getDeptById(Integer id);

    /* ���ݲ���id��ѯ������Ϣ��������Ա��Ϣ */
    Department getDeptAndEmpsById(Integer id);

    /* ���ݲ���id��ѯ������Ϣ��������Ա��Ϣ���ֲ���ѯ*/
    Department getDeptAndEmpsByIdStep(Integer id);

}
