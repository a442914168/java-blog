1.  缓存介绍;
2.  一级缓存体验;
3.  一级缓存失效的四种情况;
4.  二级缓存介绍;
5.  二级缓存使用&细节;
6.  缓存有关的设置以及属性;

本文将会从上面几点进行讲述。

###1.  缓存介绍
MyBatis包含一个非常强大的查询缓存特性,它可以非常方便地配置和定制。缓存可以极大的提升查询效率。
MyBatis系统中默认定义了两级缓存 (**一级缓存**和**二级缓存**)。
-  默认情况下，只有一级缓存(SqlSession级别的缓存，也称为本地缓存)开启。
-  二级缓存需要手动开启和配置，它是基于namespace级别的缓存。
-  为了提高扩展性，MyBatis定义了缓存接口Cache。我们可以通过实现Cache接口来自定义二级缓存。

###2.  一级缓存体验
创建对应的映射文件与接口
```xml
<!--/* 根据id获取雇员信息 */-->
<!--Employee getEmpById(Integer id);-->
<select id="getEmpById" resultType="emp">
  select * from tbl_employee where id = #{id};
</select>
```
测试
```java
EmployeeMapperCache mapperCache = sqlSession.getMapper(EmployeeMapperCache.class);

Employee employee = mapperCache.getEmpById(1);
System.out.println("第一次查询:"+employee);

Employee employee1 = mapperCache.getEmpById(1);
System.out.println("第二次查询:"+employee1);

System.out.println("两次结果是否是同一个:" + (employee == employee1));
```
结果
>==>  Preparing: select * from tbl_employee where id = ?; 
>==> Parameters: 1(Integer)
><==    Columns: id, last_name, gender, email, d_id
><==        Row: 1, jerry4, 1, hbyouxiang@qq.com, 1
><==      Total: 1
>第一次查询:Employee{id=1, lastName='jerry4', email='hbyouxiang@qq.com', gender='1', department=null}
>第二次查询:Employee{id=1, lastName='jerry4', email='hbyouxiang@qq.com', gender='1', department=null}
>两次结果是否是同一个:true

在第二次查询的时候，并没有向数据库发送sql指令，并且第一次查出来的结果与第二次是一模一样的。

###3.  一级缓存失效的四种情况
一级缓存失效情况（没有使用到当前一级缓存的情况，效果就是，还需要再向数据库发出查询）：
1.  sqlSession不同。
```java
SqlSession sqlSession = sessionFactory.openSession();
SqlSession sqlSession1 = sessionFactory.openSession();
try {
  EmployeeMapperCache mapperCache = sqlSession.getMapper(EmployeeMapperCache.class);
  EmployeeMapperCache mapperCache1 = sqlSession1.getMapper(EmployeeMapperCache.class);

  Employee employee = mapperCache.getEmpById(1);
  System.out.println("第一次查询:"+employee);

  Employee employee1 = mapperCache1.getEmpById(1);
  System.out.println("第二次查询:"+employee1);

  System.out.println("两次结果是否是同一个:" + (employee == employee1));

} finally {
  sqlSession.close();
  sqlSession1.close();
}
```
2.  sqlSession相同，查询条件不同(当前一级缓存中还没有这个数据)。
```java
SqlSession sqlSession = sessionFactory.openSession();
EmployeeMapperCache mapperCache = sqlSession.getMapper(EmployeeMapperCache.class);

Employee employee = mapperCache.getEmpById(1);
System.out.println("第一次查询:"+employee);

Employee employee1 = mapperCache.getEmpById(3);
System.out.println("第一次查询:"+employee);
```
3.  sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)。
```java
Employee employee = mapperCache.getEmpById(1);
System.out.println("第一次查询:"+employee);

mapperCache.addEmp(new Employee(null,"bobo","hbyouxiang@qq.com","1"));
System.out.println("执行添加操作");

Employee employee1 = mapperCache.getEmpById(1);
System.out.println("第二次查询:"+employee1);
```
4.  sqlSession相同，手动清除了一级缓存（缓存清空）。
```java
Employee employee = mapperCache.getEmpById(1);
System.out.println("第一次查询:"+employee);

//清除缓存
sqlSession.clearCache();

Employee employee1 = mapperCache.getEmpById(1);
System.out.println("第二次查询:"+employee1);
```

###4.  二级缓存介绍
基于namespace级别的缓存：一个namespace对应一个二级缓存。
######工作机制：
1.  一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中；
2.  如果会话关闭；一级缓存中的数据会被保存到二级缓存中；新的会话查询信息，就可以参照二级缓存中的内容；
3.  同一个session下每个namespace的缓存独立，不同namespace查出的数据会放在自己对应的缓存中（map）

###5.  二级缓存使用&细节
1.  开启全局二级缓存配置：<setting name="cacheEnabled" value="true"/>
```xml
<!--缓存-->
<setting name="cacheEnabled" value="true"/>
```
2.  去mapper.xml中配置使用二级缓存：<cache></cache>
```xml
<!--
eviction:缓存的回收策略：
  • LRU – 最近最少使用的：移除最长时间不被使用的对象。
  • FIFO – 先进先出：按对象进入缓存的顺序来移除它们。
  • SOFT – 软引用：移除基于垃圾回收器状态和软引用规则的对象。
  • WEAK – 弱引用：更积极地移除基于垃圾收集器状态和弱引用规则的对象。
  • 默认的是 LRU。
flushInterval：缓存刷新间隔
  缓存多长时间清空一次，默认不清空，设置一个毫秒值
readOnly:是否只读：
  true：只读；mybatis认为所有从缓存中获取数据的操作都是只读操作，不会修改数据。
        mybatis为了加快获取速度，直接就会将数据在缓存中的引用交给用户。不安全，速度快
  false：非只读：mybatis觉得获取的数据可能会被修改。
        mybatis会利用序列化&反序列的技术克隆一份新的数据给你。安全，速度慢
size：缓存存放多少元素；
type=""：指定自定义缓存的全类名，实现Cache接口即可；
-->
<cache eviction="FIFO" flushInterval="60000" readOnly="false" size="1024"/>
```
3.  我们的POJO需要实现序列化接口
```java
@Alias("emp")
public class Employee implements Serializable {

    private Integer id;
    private String lastName;//与last_name字段不一致
    private String email;
    private String gender;
    private Department department;

    //省略。。。
}
```
测试
```java
SqlSessionFactory sessionFactory = getSqlSessionFactory();

SqlSession sqlSession = sessionFactory.openSession();
SqlSession sqlSession1 = sessionFactory.openSession();

EmployeeMapperCache mapperCache = sqlSession.getMapper(EmployeeMapperCache.class);
EmployeeMapperCache mapperCache1 = sqlSession1.getMapper(EmployeeMapperCache.class);

Employee employee = mapperCache.getEmpById(1);
System.out.println("第一次查询:"+employee);

//关闭第一个session
sqlSession.close();

Employee employee1 = mapperCache1.getEmpById(1);
System.out.println("第二次查询:"+employee1);

System.out.println("两次结果是否是同一个:" + (employee == employee1));

sqlSession1.close();
```
>==>  Preparing: select * from tbl_employee where id = ?; 
>==> Parameters: 1(Integer)
><==    Columns: id, last_name, gender, email, d_id
><==        Row: 1, jerry4, 1, hbyouxiang@qq.com, 1
><==      Total: 1
>第一次查询:Employee{id=1, lastName='jerry4', email='hbyouxiang@qq.com', gender='1', department=null}
>Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@e45f292]
>Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@e45f292]
>Returned connection 239465106 to pool.
>Cache Hit Ratio [com.bobo.mybatis.dao.EmployeeMapperCache]: 0.5
>第二次查询:Employee{id=1, lastName='jerry4', email='hbyouxiang@qq.com', gender='1', department=null}
>两次结果是否是同一个:false

**查出的数据都会被默认先放在一级缓存中，只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中。**
虽然两次结果不是同一次，但是第二次查询并没有发送sql语句。
###6.  缓存有关的设置以及属性
1.  cacheEnabled = false：关闭缓存（二级缓存关闭）(一级缓存一直可用的)
2.  每个select标签都有useCache="true"。
    false：不使用缓存（一级缓存依然使用，二级缓存不使用）
```xml
<select id="getEmpById" resultType="emp" useCache="true">
  select * from tbl_employee where id = #{id};
</select>
```
3.  每个增删改标签的：flushCache="true"：（一级二级都会清除）
  增删改执行完成后就会清楚缓存；
  测试：flushCache="true"：一级缓存就清空了；二级也会被清除；
  查询标签：flushCache="false"：
  如果flushCache=true;每次查询之后都会清空缓存；缓存是没有被使用的；
4.  sqlSession.clearCache();只是清除当前session的一级缓存；
5.  localCacheScope：本地缓存作用域：（一级缓存SESSION）；当前会话的所有数据保存在会话缓存中；