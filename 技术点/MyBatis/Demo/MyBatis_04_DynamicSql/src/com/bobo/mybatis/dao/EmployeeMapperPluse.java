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

    /* ����id��ȡ�����ҷ�����ResultMap���� */
    Employee getEmpByIdResultMap(Integer id);

    /* ��ȡԱ����Ϣ�����Ұ�����Ĳ�����Ϣ */
    Employee getEmpAndDept(Integer id);

    /* ��ȡԱ����Ϣ�����Ұ�����Ĳ�����Ϣ ʹ��association��ʽ*/
    Employee getEmpAndDeptAssociation(Integer id);

    /* ��ȡԱ����Ϣ�����Ұ�����Ĳ�����Ϣ ʹ��association �ֲ���ѯ��ʽ*/
    Employee getEmpAndDeptStep(Integer id);

    /* ����ID��ѯ��Ա��Ϣ */
    List<Employee> getEmpsByDeptId(Integer id);

}
