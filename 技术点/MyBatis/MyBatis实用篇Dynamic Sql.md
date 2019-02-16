1.  动态sql_简介;
2.  动态sql_if_判断;
3.  动态sql_where_查询条件;
4.  动态sql_trim_自定义字符串截取;
5.  动态sql_choose_分支选择;
6.  动态sql_set_与if结合的动态更新;
7.  动态sql_foreach_遍历集合;
8.  动态sql_foreach_mysql下foreach批量插入的两种方式;
9.  动态sql_foreach_oracle下批量插入的两种方式;
10.  动态sql_内置参数_parameter&_databaseId;
11.  动态sql_bind_绑定;
12.  动态sql_sql_抽取可重用的sql片段;

本文将会从上面几点进行讲述。

###1.  动态sql_简介
MyBatis 的强大特性之一便是它的动态 SQL。如果你有使用 JDBC 或其它类似框架的经验，你就能体会到根据不同条件拼接 SQL 语句的痛苦。例如拼接时要确保不能忘记添加必要的空格，还要注意去掉列表最后一个列名的逗号。利用动态 SQL 这一特性可以彻底摆脱这种痛苦。

虽然在以前使用动态 SQL 并非一件易事，但正是 MyBatis 提供了可以被用在任意 SQL 映射语句中的强大的动态 SQL 语言得以改进这种情形。

动态 SQL 元素和 JSTL 或基于类似 XML 的文本处理器相似。在 MyBatis 之前的版本中，有很多元素需要花时间了解。MyBatis 3 大大精简了元素种类，现在只需学习原来一半的元素便可。MyBatis 采用功能强大的基于**OGNL的表达式**来淘汰其它大部分元素。
 if、choose (when, otherwise)、 trim (where, set)、foreach

###2.  动态sql_if_判断
我们根据传入的Employee的属性是否有值，来写sql的查询条件
```xml
<!--//携带了哪个字段查询条件就带上这个字段的值-->
<!--List<Employee> getEmpsByConditionIf(Employee employee);-->
<select id="getEmpsByConditionIf" resultType="emp">
  select *
  from tbl_employee
  where
  <!-- test：判断表达式（OGNL）
    OGNL参照PPT或者官方文档。
      c:if  test
    从参数中取值进行判断

    遇见特殊符号应该去写转义字符：
      &&：&amp;&amp;
  -->
  <if test="lastName!=null &amp;&amp; lastName!=&quot;&quot;">
     last_name like #{lastName}
  </if>
   <if test="email!=null and email.trim()!=&quot;&quot;">
    and email=#{email}
  </if>
  <!-- ognl会进行字符串与数字的转换判断  "0"==0 -->
  <if test="gender==0 or gender==1">
    and gender=#{gender}
  </if>
</select>
```
测试，创建一个Employee对象，只有lastName的值
```java
EmployeeMapperDynamicSQL mapperDynamicSQL = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
mapperDynamicSQL.getEmpsByConditionIf(new Employee(null,"bobo",null,null));
```
执行的sql结果，可以看到只有last_name的sql条件有效
```sql
Preparing: select * from tbl_employee where last_name like ? 
```
[转义字符-百度百科链接](https://baike.baidu.com/item/%E8%BD%AC%E4%B9%89%E5%AD%97%E7%AC%A6)，[OGNL表达式](http://www.voidcn.com/article/p-eigybvck-bdv.html)

###3.  动态sql_where_查询条件
延用if的例子，如果lastName为空的话，那么sql语句将会报错。
```java
EmployeeMapperDynamicSQL mapperDynamicSQL = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
mapperDynamicSQL.getEmpsByConditionIf(new Employee(null,null,"hbyouxiang@qq.com",null));
```
>SQL: select * from tbl_employee  where and email=?

因为lastName为空，然而email前面强行添加了and 关键字，这不符合sql的语法。有两种解决办法
-  给where后面加上1=1，以后的条件都and xxx.
-  mybatis使用where标签来将所有的查询条件包括在内。mybatis就会将where标签中拼装的sql，多出来的and或者or去掉，**where只会去掉第一个多出来的and或者or**。
```xml
<!--//携带了哪个字段查询条件就带上这个字段的值-->
<!--List<Employee> getEmpsByConditionIf(Employee employee);-->
<select id="getEmpsByConditionIf" resultType="emp">
  select *
  from tbl_employee
  <where>
  <!-- test：判断表达式（OGNL）
    OGNL参照PPT或者官方文档。
    c:if  test
    从参数中取值进行判断

    遇见特殊符号应该去写转义字符：
    &&：&amp;&amp;
   -->
    <if test="lastName!=null &amp;&amp; lastName!=&quot;&quot;">
      last_name like #{lastName}
    </if>
    <if test="email!=null and email.trim()!=&quot;&quot;">
      and email=#{email}
    </if>
    <!-- ognl会进行字符串与数字的转换判断  "0"==0 -->
    <if test="gender==0 or gender==1">
      and gender=#{gender}
    </if>
  </where>
</select>
```
###4.  动态sql_trim_自定义字符串截取
where标签只能帮助我们去掉sql拼接的开头，而想要自定义的话则需要使用trim标签。
```xml
<!--//携带了哪个字段查询条件就带上这个字段的值 - Trim-->
<!--List<Employee> getEmpsByConditionTrim(Employee employee);-->
<select id="getEmpsByConditionTrim" resultType="emp">
  select * from tbl_employee
  <!-- 后面多出的and或者or where标签不能解决
    prefix="":前缀：trim标签体中是整个字符串拼串 后的结果，prefix给拼串后的整个字符串加一个前缀
    prefixOverrides="":  前缀覆盖： 去掉整个字符串前面多余的字符
    suffix="":后缀 suffix给拼串后的整个字符串加一个后缀
    suffixOverrides=""  后缀覆盖：去掉整个字符串后面多余的字符
  -->
  <!-- 自定义字符串的截取规则 -->
  <trim prefix="where" suffixOverrides="and">
    <if test="id!=null">
      id=#{id} and
    </if>
    <if test="lastName!=null &amp;&amp; lastName!=&quot;&quot;">
      last_name like #{lastName} and
    </if>
    <if test="email!=null and email.trim()!=&quot;&quot;">
      email=#{email} and
    </if>
    <!-- ognl会进行字符串与数字的转换判断  "0"==0 -->
    <if test="gender==0 or gender==1">
      gender=#{gender}
    </if>
  </trim>
</select>
```
###5.  动态sql_choose_分支选择
类似于代码中switch-case，如果查询的Employee中id属性有值，那么就用id查，如果lastName属性有值，那么就用lastName查，**只会使用一个**。
```xml
<!--//携带了哪个字段查询条件就带上这个字段的值 - Choose-->
<!--List<Employee> getEmpsByConditionChoose(Employee employee);-->
<select id="getEmpsByConditionChoose" resultType="emp">
  select * from tbl_employee
<where>
  <!-- 如果带了id就用id查，如果带了lastName就用lastName查;只会进入其中一个 -->
  <choose>
    <when test="id!=null">
      id=#{id}
    </when>
    <when test="lastName!=null">
      last_name like #{lastName}
    </when>
    <when test="email!=null">
      email = #{email}
    </when>
    <!-- 都不满足的情况下 -->
    <otherwise>
      gender = 0
    </otherwise>
    </choose>
  </where>
</select>
```
###6.  动态sql_set_与if结合的动态更新
在[MyBatis实用篇Mapper XML 文件](https://www.jianshu.com/p/ff6ae25f81fa)，我们有一个更新数据的方法
```xml
<!--/* 修改 */-->
<!--boolean updateEmpById(Employee employee);-->
<update id="updateEmp">
  update tbl_employee
  set last_name=#{lastName},email=#{email},gender=#{gender}
  where id=#{id}
</update>
```
这个方法是将Employee所有的属性都更新一遍，无论这些属性是否有值。
######Trim：更新拼串
```xml
<trim prefix="set" suffixOverrides=",">
  <if test="lastName!=null">
    last_name=#{lastName},
  </if>
  <if test="email!=null">
    email=#{email},
  </if>
  <if test="gender!=null">
     gender=#{gender}
    </if>
</trim>
```
使用Trim标签，sql语句插入**set**，并且去掉每条拼接sql后面的**,**
######Set标签的使用
```xml
<set>
  <if test="lastName!=null">
    last_name=#{lastName},
  </if>
  <if test="email!=null">
    email=#{email},
  </if>
  <if test="gender!=null">
    gender=#{gender}
  </if>
</set>
```
Set标签，会默认去掉每条拼接sql后面的**,**

###7.  动态sql_foreach_遍历集合
在实际业务中，我们经常会查询一组id的信息，这时候我们可以使用循环foreach
```xml
<!--//查询员工id'在给定集合中的-->
<!--List<Employee> getEmpsByConditionForeach(@Param("ids")List<Integer> ids);-->
<select id="getEmpsByConditionForeach" resultType="emp">
  select * from tbl_employee
  <!--
    collection：指定要遍历的集合：
          list类型的参数会特殊处理封装在map中，map的key就叫list
    item：将当前遍历出的元素赋值给指定的变量
    separator:每个元素之间的分隔符
    open：遍历出所有结果拼接一个开始的字符
    close:遍历出所有结果拼接一个结束的字符
    index:索引。遍历list的时候是index就是索引，item就是当前值
        遍历map的时候index表示的就是map的key，item就是map的值

    #{变量名}就能取出变量的值也就是当前遍历出的元素
  -->
  <foreach collection="ids" item="item_id" separator=","
    open="where id in("
    close=")">
    #{item_id}
  </foreach>
</select>
```
###8.  动态sql_foreach_mysql下foreach批量插入的两种方式

######方式一、在mysql一条sql插入多条数据的语法:
```sql
INSERT INTO  [表名]([列名],[列名])  VALUES  ([列值],[列值])), ([列值],[列值]))
```
对应的xml
```xml
<!--//批量保存-->
<!--void addEmps(@Param("emps")List<Employee> emps);-->
<!--MySQL下批量保存：可以foreach遍历   mysql支持values(),(),()语法-->
<insert id="addEmps">
  insert into tbl_employee(last_name,email,gender)
  values
  <foreach collection="emps" item="emp" separator=",">
    (#{emp.lastName},#{emp.email},#{emp.gender})
  </foreach>
</insert>
```
######方式二、循环多条insert into方法，以**;**分隔sql
```xml
<!-- 这种方式需要数据库连接属性allowMultiQueries=true；
    这种分号分隔多个sql可以用于其他的批量操作（删除，修改） -->
<insert id="addEmps">
  <foreach collection="emps" item="emp" separator=";">
    insert into tbl_employee(last_name,email,gender)
    values(#{emp.lastName},#{emp.email},#{emp.gender})
  </foreach>
</insert>
```
>需要在JDBC连接时候设置，**allowMultiQueries=true**
>jdbc.url=jdbc:mysql://localhost:3306/mybatis?allowMultiQueries=true

[Jdbc Url 设置allowMultiQueries为true和false时底层处理机制研究](https://my.oschina.net/zhuguowei/blog/411853)

###9.  动态sql_foreach_oracle下批量插入的两种方式
在oracle中是不支持VALUES  ([列值],[列值])), ([列值],[列值]))这种格式的
Oracle支持的批量方式：
-  **多个insert放在begin - end里面**：
  begin
  insert into employees(employee_id,last_name,email)
  values(employees_seq.nextval,'test_001','test_001@atguigu.com');
  insert into employees(employee_id,last_name,email)
  values(employees_seq.nextval,'test_002','test_002@atguigu.com');
  end;
-  **利用中间表**：
    insert into employees(employee_id,last_name,email)
    select employees_seq.nextval,lastName,email from (
    select 'test_a_01' lastName,'test_a_e01' email from dual
    union
     select 'test_a_02' lastName,'test_a_e02' email from dual
    union
    select 'test_a_03' lastName,'test_a_e03' email from dual
    )
```xml
<insert id="addEmps" databaseId="oracle">
  <!-- oracle第一种批量方式 -->
  <!-- <foreach collection="emps" item="emp" open="begin" close="end;">
    insert into employees(employee_id,last_name,email)
      values(employees_seq.nextval,#{emp.lastName},#{emp.email});
  </foreach> -->
  
  <!-- oracle第二种批量方式  -->
    insert into employees(
    <!-- 引用外部定义的sql -->
    <include refid="insertColumn">
      <property name="testColomn" value="abc"/>
    </include>
    )
    <foreach collection="emps" item="emp" separator="union"
      open="select employees_seq.nextval,lastName,email from("
      close=")">
        select #{emp.lastName} lastName,#{emp.email} email from dual
    </foreach>
</insert>
```
###10.  动态sql_内置参数_parameter&_databaseId
MyBatis不止存在方法传递过来的参数可以用来被判断，取值等等。还有两个默认的内置参数_parameter&_databaseId
-  _parameter:代表整个参数
  单个参数：_parameter就是这个参数
  多个参数：参数会被封装为一个map；_parameter就是代表这个map
-  _databaseId:如果配置了databaseIdProvider标签。
```xml
<!--//内置参数-->
<!--List<Employee> getEmpsTestInnerParameter(Employee employee);-->
<select id="getEmpsTestInnerParameter" resultType="emp">
  <if test="_databaseId=='mysql'">
    select * from tbl_employee
    <if test="_parameter!=null">
        where last_name like #{lastName}
    </if>
  </if>
  <if test="_databaseId=='oracle'">
     select * from employees
     <if test="_parameter!=null">
        where last_name like #{lastName}
    </if>
  </if>
</select>
```
###11.  动态sql_bind_绑定
bind：可以将OGNL表达式的值绑定到一个变量中，方便后来引用这个变量的值。
```xml
<select id="getEmpsTestInnerParameter" resultType="emp">
  <!-- bind：可以将OGNL表达式的值绑定到一个变量中，方便后来引用这个变量的值 -->
  <bind name="_lastName" value="'%'+lastName+'%'"/>
  <if test="_databaseId=='mysql'">
    select * from tbl_employee
    <if test="_parameter!=null">
      where last_name like #{lastName}
    </if>
  </if>
  <if test="_databaseId=='oracle'">
    select * from employees
      <if test="_parameter!=null">
        where last_name like #{_parameter.lastName}
      </if>
  </if>
</select>
```
###12.  动态sql_sql_抽取可重用的sql片段
抽取可重用的sql片段。方便后面引用 
-  sql抽取：经常将要查询的列名，或者插入用的列名抽取出来方便引用
-  include来引用已经抽取的sql：
-  include还可以自定义一些property，sql标签内部就能使用自定义的属性
  include-property：取值的正确方式${prop}，#{不能使用这种方式}
```xml
定义
<sql id="insertColumn">
  <if test="_databaseId=='oracle'">
    employee_id,last_name,email
  </if>
  <if test="_databaseId=='mysql'">
    last_name,email,gender,d_id
  </if>
</sql>

使用
<insert id="addEmps">
  insert into tbl_employee(
    <include refid="insertColumn">
      <property name="testColomn" value="abc"/>
    </include>
  ) 
  values
  <foreach collection="emps" item="emp" separator=",">
    (#{emp.lastName},#{emp.email},#{emp.gender},#{emp.dept.id})
  </foreach>
</insert>
```