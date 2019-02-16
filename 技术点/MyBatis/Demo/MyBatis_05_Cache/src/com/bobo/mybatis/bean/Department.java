package com.bobo.mybatis.bean;

import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * @program: MyBatis_02_Mapper
 * @description: ≤ø√≈
 * @author: bobobo
 * @create: 2018-07-27 11:59
 **/
@Alias("dept")
public class Department {

    private String deptID;
    private String deptName;
    private List<Employee> emps;

    public Department() {

    }

    public Department(String deptID) {
        this.deptID = deptID;
    }

    public List<Employee> getEmps() {
        return emps;
    }

    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "deptID='" + deptID + '\'' +
                ", deptName='" + deptName + '\'' +
                ", emps=" + emps +
                '}';
    }
}
