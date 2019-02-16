package com.bobo.mybatis.dao;

import com.bobo.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * @program: MyBatis_01_HelloWorld
 * @description:
 * @author: bobobo
 * @create: 2018-07-22 14:55
 **/
public interface EmployeeMapper {

    /* 添加 */
    void addEmp(Employee emp);

    /* 删除 */
    void deleteEmpById(Integer id);

    /* 修改 */
    boolean updateEmp(Employee employee);

    /* 查询 */
    Employee getEmpById(Integer id);

    /* 使用$符号获取值 */
    Employee getEmpByMap(Map<String, Object> map);

    /* 返回List集合 */
    List<Employee> getList(String lastName);

    /* 返回Map key就是列名，value就是对应的值 */
    Map<String, Object> getMapSingle(Integer id);

    //多条记录封装一个map：Map<String,Employee>:键是这条记录的主键，值是记录封装后的javaBean
    //@MapKey:告诉mybatis封装这个map的时候使用哪个属性作为map的key
    @MapKey("lastName")
    Map<String, Employee> getMapList(String lastName);

}
