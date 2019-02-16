package com.bobo.mybatis.bean;

import org.apache.ibatis.type.Alias;

/**
 * @program: MyBatis_01_HelloWorld
 * @description:
 * @author: bobobo
 * @create: 2018-07-22 13:33
 **/
@Alias("emp")
public class Employee {

    private Integer id;
    private String lastName;//Óëlast_name×Ö¶Î²»Ò»ÖÂ
    private String email;
    private String gender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
