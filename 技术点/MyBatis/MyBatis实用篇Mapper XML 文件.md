1.  Mapper XML 文件是什么?
2.  增删改查;
3.  insert;
  3.1  获取自增主键的值;
  3.2  获取非自增主键的值_selectKey;
4.  参数处理;
  4.1  单个参数&多个参数&命名参数&POJO&Map;
  4.2  #与$取值区别;
  4.3  #取值时指定参数相关规则;
5.  select;
  5.1  返回List;
  5.2  记录封装map;
  5.3  resultMap_自定义结果映射规则;
  5.4  resultMap_关联查询_环境搭建;
  5.5  resultMap_关联查询_级联属性封装结果;
  5.6  resultMap_关联查询_association定义关联对象封装规则;
  5.7  resultMap_关联查询_association分步查询;
  5.8  resultMap_关联查询_分步查询&延迟加载;
  5.9  resultMap_关联查询_collection定义关联集合封装规则;
  5.10  resultMap_关联查询_collection分步查询&延迟加载;
  5.11  resultMap_分步查询传递多列值&fetchType;
  5.12  resultMap_discriminator鉴别器;

本文将会从上面几点进行讲述。

#1.  Mapper XML 文件是什么?
MyBatis 的真正强大在于它的映射语句，也是它的魔力所在。由于它的异常强大，映射器的 XML 文件就显得相对简单。如果拿它跟具有相同功能的 JDBC 代码进行对比，你会立即发现省掉了将近 95% 的代码。MyBatis 就是针对 SQL 构建的，并且比普通的方法做的更好。[来自官方文档](http://www.mybatis.org/mybatis-3/zh/sqlmap-xml.html)

#2.  增删改查
沿用[MyBatis实用篇全局Config](https://www.jianshu.com/p/0376686e6339)的Demo，在EmployeeMapper接口中添加几个方法
```java
public interface EmployeeMapper {

    /* 添加 */
    void addEmp(Employee emp);

    /* 删除 */
    void deleteEmpById(Integer id);

    /* 修改 */
    boolean updateEmp(Employee employee);

    /* 查询 */
    Employee getEmpById(Integer id);

}
```
EmployeeMapper.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.bobo.mybatis.dao.EmployeeMapper">

    <!--/* 添加 */-->
    <!--void addEmp(Employee emp);-->
    <insert id="addEmp">
		insert into tbl_employee(last_name,email,gender)
		values(#{lastName},#{email},#{gender})
	</insert>

    <!--/* 删除 */-->
    <!--void deleteEmpById(Integer id);-->
    <delete id="deleteEmpById">
		delete from tbl_employee
		where id=#{id}
	</delete>

    <!--/* 修改 */-->
    <!--boolean updateEmpById(Employee employee);-->
    <update id="updateEmp">
		update tbl_employee
		set last_name=#{lastName},email=#{email},gender=#{gender}
		where id=#{id}
	</update>

    <!--/* 查询 */-->
    <!--Employee getEmpById(Integer id);-->
    <select id="getEmpById" resultType="emp">
		select id,last_name lastName,email,gender
		from tbl_employee
		where id = #{id}
	</select>

</mapper>
```
测试
```java
/**
  * 测试增删改
  *
  * 1、mybatis允许增删改直接定义以下类型返回值
  * 		Integer、Long、Boolean、void
  *
  * 2、我们需要手动提交数据
  * 		sqlSessionFactory.openSession();===》手动提交
  * 		sqlSessionFactory.openSession(true);===》自动提交
  *
  * @throws IOException
  */
public static void testCURD() throws IOException {

  SqlSessionFactory sessionFactory = getSqlSessionFactory();
  SqlSession session = sessionFactory.openSession();
  EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);

  try {
     //add
    Employee employee = new Employee(null, "jerry4", "xjyouxiang@qq.com", "1");
    mapper.addEmp(employee);

    //delete
    mapper.deleteEmpById(1);

    //read
    Employee employee1 = mapper.getEmpById(11);
    System.out.println(employee1);

    //update
    employee1.setEmail("hbyouxiang@qq.com");
    boolean success = mapper.updateEmp(employee1);
    if (success) {
      employee1 = mapper.getEmpById(11);
      System.out.println(employee1);
    }

    session.commit();

  } finally {
     session.close();
  }

}
```
######细节说明：
1.  mybatis允许增删改直接定义以下类型返回值
  **Integer、Long、Boolean、void**
  如修改时，返回是否成功
  boolean updateEmp(Employee employee);
2.  我们需要手动提交数据
  sqlSessionFactory.openSession();->手动提交
  sqlSessionFactory.openSession(true);->自动提交
#3.  insert
####3.1  获取自增主键的值
我们执行了insert语句后，想要获取到插入的id，在原生JDBC是**getGeneratedKeys**，而在MyBatis里也是statement.getGenreatedKeys()；我们只需要设置insert里的**useGeneratedKeys**为true，并且将得到最新的值设置给emp的**id**即可
```xml
<!--void addEmp(Employee emp);-->
<!-- parameterType：参数类型，可以省略，
  获取自增主键的值：
    mysql支持自增主键，自增主键值的获取，mybatis也是利用statement.getGenreatedKeys()；
    useGeneratedKeys="true"；使用自增主键获取主键值策略
    keyProperty；指定对应的主键属性，也就是mybatis获取到主键值以后，将这个值封装给javaBean的哪个属性
-->
<insert id="addEmp" useGeneratedKeys="true" keyProperty="id">
  insert into tbl_employee(last_name,email,gender)
  values(#{lastName},#{email},#{gender})
</insert>
```
测试
```java
//add
Employee employee = new Employee(null, "jerry4", "xjyouxiang@qq.com", "1");
mapper.addEmp(employee);
System.out.println(employee);
```
>Employee{id=13, lastName='jerry4', email='xjyouxiang@qq.com', gender='1'}

在new的时候，并没有设置id值，可是经过addEmp后再打印，就有了id值。

####3.2  获取非自增主键的值_selectKey
对于不支持自增型主键的数据库(例如 Oracle)，则可以使用 **selectKey** 子元素: selectKey 元素将会首先运行，id 会被设置，然 后插入语句会被调用
```xml
<!--
  获取非自增主键的值：
    Oracle不支持自增；Oracle使用序列来模拟自增；
    每次插入的数据的主键是从序列中拿到的值；
-->
<insert id="addEmp" databaseId="oracle">
  <selectKey keyProperty="id" order="BEFORE" resultType="Integer">
    <!-- 编写查询主键的sql语句 -->
    <!-- BEFORE-->
    select EMPLOYEES_SEQ.nextval from dual
    <!-- AFTER：
      select EMPLOYEES_SEQ.currval from dual -->
  </selectKey>

  <!-- 插入时的主键是从序列中拿到的 -->
    <!-- BEFORE:-->
    insert into employees(EMPLOYEE_ID,LAST_NAME,EMAIL)
    values(#{id},#{lastName},#{email})
    <!-- AFTER：
    insert into employees(EMPLOYEE_ID,LAST_NAME,EMAIL)
    values(employees_seq.nextval,#{lastName},#{email}) -->
  </insert>
```
|       key       |                             说明                             |
| :-------------: | :----------------------------------------------------------: |
| **keyProperty** |           **查出的主键值封装给javaBean的哪个属性**           |
|    keyColumn    |                匹配属性的返回结果集中的列名称                |
| **resultType**  | **查出的数据的返回值类型**，Mybatis通常可以推算出来，但为了更加确定写上也不会有什么问题。MyBatis允许任何简单类型作为主键的类型，包括字符串 |
|    **order**    | 可以被设置为**BEFORE/AFTER**。 BEFORE：先选择主键，设置keyProperty再执行插入语句。AFTER：先执行插入语句，再设置主键 |
|  statementType  | 与前面相同，Mybatis支持STATEMENT，PREPARED 和 CALLABLE 语句的映射类型，分别代表PreparedStatement 和 CallableStatement 类型 |

#4.  参数处理
####4.1  单个参数&多个参数&命名参数&POJO&Map
-  **单个参数：**
  可以接受基本类型，对象类型，集合类型的值。这种情况MyBatis可直接使用这个参数，不需要经过任何处理。
  .#{参数名/任意名}：取出参数值。
-  **多个参数：**
  多个参数会被封装成 一个map
  	key：param1...paramN,或者参数的索引也可以
  	value：传入的参数值
-  **命名参数：**
  明确指定封装参数时map的key；@Param("id")
  多个参数会被封装成 一个map
  	key：使用@Param注解指定的值
  	value：参数值
  .#{指定的key}取出对应的参数值
-  **POJO：**
  如果多个参数正好是我们业务逻辑的数据模型，我们就可以直接传入pojo；
  .#{属性名}：取出传入的pojo的属性值	
-  **Map：**
  如果多个参数不是业务模型中的数据，没有对应的pojo，不经常使用，为了方便，我们也可以传入map
  .#{key}：取出map中对应的值

####4.2  #与$取值区别
```xml
<!--/* 使用$与#符号获取值 */-->
<!--Employee getEmpByMap(Map<String, Object> map);-->
<select id="getEmpByMap" resultType="emp">
  select * from ${tableName} where id=${id} and last_name=#{lastName}
</select>
```
查询代码
```java
//使用$获取参数
Map<String, Object> map = new HashMap<>();
map.put("id", 2);
map.put("lastName", "Tom");
map.put("tableName", "tbl_employee");
Employee employee = mapper.getEmpByMap(map);
```
产生的sql
```sql
==>  Preparing: select * from tbl_employee where id=2 and last_name=? 
==>  Parameters: Tom(String)
```
-  **#** 获取参数的值：预编译到SQL中，安全。
-  **💲** 获取参数的值：拼接到SQL中，有SQL注入问题。

####4.3  #取值时指定参数相关规则
```xml
#{}:更丰富的用法：
规定参数的一些规则：
	javaType、 jdbcType、 mode（存储过程）、 numericScale、
	resultMap、 typeHandler、 jdbcTypeName、 expression（未来准备支持的功能）；

	jdbcType通常需要在某种特定的条件下被设置：
		在我们数据为null的时候，有些数据库可能不能识别mybatis对null的默认处理。比如Oracle（报错）；
		
		JdbcType OTHER：无效的类型；因为mybatis对所有的null都映射的是原生Jdbc的OTHER类型，oracle不能正确处理;
		
		由于全局配置中：jdbcTypeForNull=OTHER；oracle不支持；两种办法
		1、#{email,jdbcType=OTHER};
		2、jdbcTypeForNull=NULL
			<setting name="jdbcTypeForNull" value="NULL"/>
```
#5.  select
####5.1  返回List
resultType：如果返回的是一个集合，要写集合中元素的类型
```xml
<!--/* 返回List集合 */-->
<!--List<Employee> getList(String lastName);-->
<!--resultType：如果返回的是一个集合，要写集合中元素的类型  -->
<select id="getList" resultType="emp">
    select *
    from tbl_employee
    where last_name
     like #{lastName}
</select>
```
####5.2  记录封装map
1.   单条记录map
```xml
<!--/* 返回Map key就是列名，value就是对应的值 */-->
<!--Map<String, Object> getMapSingle(Integer id);-->
<select id="getMapSingle" resultType="map">
  select *
  from tbl_employee
  where id=#{id}
</select>
```
2.  多条记录map
```xml
<!--//多条记录封装一个map：Map<String,Employee>:键是这条记录的主键，值是记录封装后的javaBean-->
<!--//@MapKey:告诉mybatis封装这个map的时候使用哪个属性作为map的key-->
<!--@MapKey("lastName")-->
<!--Map<String, Employee> getMapList(String lastName);-->
<select id="getMapList" resultType="emp">
  select *
  from tbl_employee
  where last_name
  like #{lastName}
</select>
```
>{
>bobo2=Employee{id=3, lastName='bobo2', email='xjyouxiang@qq.com', gender='1'}, 
>bobo=Employee{id=1, lastName='bobo', email='bobo@qq.com', gender='1'}
>}

#### 5.3  resultMap_自定义结果映射规则
如果查出数据库中的列名与java bean中的属性名不一致。有三种解决办法。
1.通过别名。2.如果是属性是驼峰命名法，而列名是_形式，则可以开启setting的mapUnderscoreToCamelCase。3.使用resultMap，手动定义每个属性对应的列名。
```xml
<!--自定义某个javaBean的封装规则
	        type：自定义规则的Java类型
	        id:唯一id方便引用
-->
<resultMap id="MySimpleEmp" type="emp">
    <!--指定主键列的封装规则
      id定义主键会底层有优化；
      column：指定哪一列
      property：指定对应的javaBean属性
    -->
  <id column="id" property="id"/>
  <!-- 定义普通列封装规则 -->
  <result column="last_name" property="lastName"/>
  <!-- 其他不指定的列会自动封装：我们只要写resultMap就把全部的映射规则都写上。 -->
  <result column="email" property="email"/>
  <result column="gender" property="gender"/>
</resultMap>

<!--/* 根据id获取，并且返回是ResultMap操作 */-->
<!--Employee getEmpByIdResultMap(Integer id);-->
<select id="getEmpByIdResultMap" resultMap="MySimpleEmp" >
    select *
    from tbl_employee
    where id=#{id}
</select>
```

####5.4  resultMap_关联查询_环境搭建
每个员工Employee都会有他所属的部门Department
```java
@Alias("emp")
public class Employee {

    private Integer id;
    private String lastName;//与last_name字段不一致
    private String email;
    private String gender;
    private Department department;

    //省略getter,setter,构造器,toString
}

@Alias("dept")
public class Department {

    private String deptID;
    private String deptName;

    //省略getter,setter,构造器,toString
}
```
创建对应的数据库表，并添加数据，添加雇员外键
```sql
//创建表
CREATE TABLE tbl_dept(
  id INT(11) PRIMARY KEY AUTO_INCREMENT,
  dept_name VARCHAR(255)
);

//添加数据
INSERT INTO tbl_dept Values(1,'开发部');
INSERT INTO tbl_dept Values(2,'人事部');

//雇员添加部门id
ALTER TABLE tbl_employee ADD COLUMN d_id INT(11);

//设置住外键关系
ALTER TABLE tbl_employee ADD CONSTRAINT fk_emp_dept
FOREIGN KEY(d_id) REFERENCES tbl_dept(id);
```
.sql文件在[本文Demo](https://github.com/a442914168/Mybatis)包含。

###5.5  resultMap_关联查询_级联属性封装结果
```xml
<!--将对应的值一一设置-->
<resultMap id="resultMap4Dept" type="emp">
  <id column="id" property="id"/>
  <result column="last_name" property="lastName"/>
  <result column="gender" property="gender"/>
  <!--直接属性.属性方法设置-->
  <result column="did" property="department.deptID"/>
  <result column="dept_name" property="department.deptName"/>
</resultMap>

<!--/* 获取员工信息，并且包含其的部门信息 */-->
<!--Employee getEmpAndDept(Integer id);-->
<select id="getEmpAndDept" resultMap="resultMap4Dept">
  SELECT e.id id,e.last_name last_name,e.gender gender,
         e.d_id d_id,d.id did,d.dept_name dept_name
  FROM   tbl_employee e,tbl_dept d
  WHERE   e.d_id=d.id
  AND   e.id=#{id}
</select>
```
###5.6  resultMap_关联查询_association定义关联对象封装规则
```xml
<!--使用association定义关联的单个对象的封装规则；-->
<resultMap id="resultMap4DeptAssociation" type="emp">
  <id column="id" property="id"/>
  <result column="last_name" property="lastName"/>
  <result column="email" property="email"/>
  <result column="gender" property="gender"/>
  <!--Department值得封装使用 association-->
  <!--association可以指定联合的javaBean对象
      property="department"：指定哪个属性是联合的对象
       javaType:指定这个属性对象的类型[不能省略]
  -->
  <association property="department" javaType="dept">
    <id column="did" property="deptID"/>
    <result column="dept_name" property="deptName"/>
  </association>
</resultMap>

<!--/* 获取员工信息，并且包含其的部门信息 使用association方式*/-->
<!--Employee getEmpAndDeptAssociation(Integer id);-->
<select id="getEmpAndDeptAssociation" resultMap="resultMap4DeptAssociation">
  SELECT e.id id,e.last_name last_name,e.gender gender,
         e.d_id d_id,d.id did,d.dept_name dept_name
  FROM   tbl_employee e,tbl_dept d
  WHERE   e.d_id=d.id
  AND   e.id=#{id}
</select>
```
###5.7  resultMap_关联查询_association分步查询
在实际项目中，既然有雇员的查询，那也意味着会有部门的查询。
1.  创建DepartmentMapper和DepartmentMapper.xml并且在mybatis-config.xml添加配置。
2.  DepartmentMapper添加根据id查询部门信息的方法。
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.bobo.mybatis.dao.DepartmentMapper">

    <!--/* 根据部门id查询部门信息 */-->
    <!--Department getDeptById(Integer id);-->
    <select id="getDeptById" resultType="dept">
        SELECT *
        FROM tbl_dept
        WHERE id=#{id}
    </select>

</mapper>
```
3.  进行Employee查询
```xml
<!--使用association进行分步查询：
  1、先按照员工id查询员工信息
  2、根据查询员工信息中的d_id值去部门表查出部门信息
  3、部门设置到员工中；
-->
<resultMap id="resultMap4DeptStep" type="emp">
  <id column="id" property="id"/>
  <result column="last_name" property="lastName"/>
  <result column="email" property="email"/>
  <result column="gender" property="gender"/>

  <!--association定义关联对象的封装规则
      select:表明当前属性是调用select指定的方法查出的结果
      column:指定将哪一列的值传给这个方法

      流程：使用select指定的方法（传入column指定的这列参数的值）
           查出对象，并封装给property指定的属性
  -->
  <association property="department" 
    select="com.bobo.mybatis.dao.DepartmentMapper.getDeptById"
    column="d_id">
  </association>

</resultMap>

<!--/* 获取员工信息，并且包含其的部门信息 使用association 分步查询方式*/-->
<!--Employee getEmpAndDeptStep(Integer id);-->
<select id="getEmpAndDeptStep" resultMap="resultMap4DeptStep">
  SELECT *
  FROM tbl_employee
  WHERE id=#{id}
</select>
```
###5.8  resultMap_关联查询_分步查询&延迟加载
我们知道雇员信息肯定会有部门的信息，现在我们一查询某个雇员信息就会把部门信息给查出来了。这样会造成浪费。
基于association分步查询，我们不需要更改任何代码，只需要在mybatis-config.xml添加配置setting即可
```xml
<!--懒加载-->
<setting name="lazyLoadingEnabled" value="true"/>

<!--当设置为‘true’的时候，懒加载的对象可能被任何懒属性全部加载。
    否则，每个属性都按需加载。-->
<setting name="aggressiveLazyLoading" value="false"/>
```
###5.9  resultMap_关联查询_collection定义关联集合封装规则
在现实中，每个部门会有很多员工，这样我们需要使用collection来查询
1. 在Department添加员工的list
```java
@Alias("dept")
public class Department {

    private String deptID;
    private String deptName;
    private List<Employee> emps;
   
    ////省略getter,setter,构造器,toString
}
```
2.  DepartmentMapper.xml添加方法
```xml
<!--嵌套结果集的方式，使用collection标签定义关联的集合类型的属性封装规则  -->
<resultMap id="getDeptAndEmpsByIdMap" type="dept">
  <id column="did" property="deptID"/>
  <result column="dept_name" property="deptName"/>
  <!--
    collection定义关联集合类型的属性的封装规则
    ofType:指定集合里面元素的类型
  -->
  <collection property="emps" ofType="emp">
    <!-- 定义这个集合中元素的封装规则 -->
    <id column="eid" property="id"/>
    <result column="last_name" property="lastName"/>
    <result column="email" property="email"/>
    <result column="gender" property="gender"/>
  </collection>
</resultMap>

<!--/* 根据部门id查询部门信息，包含雇员信息 */-->
<!--Department getDeptAndEmpsById(Integer id);-->
<select id="getDeptAndEmpsById" resultMap="getDeptAndEmpsByIdMap">
  SELECT d.id did,d.dept_name dept_name,
         e.id eid,e.last_name last_name,e.email email,e.gender gender
  FROM tbl_dept d
  LEFT JOIN tbl_employee e
  ON d.id=e.d_id
  WHERE d.id=#{id}
</select>
```
###5.10 resultMap关联查询collection分步查询&延迟加载
与雇员查询，查出部门信息一样。我们大多数业务都是只查部门的基本信息。如果每次查都把雇员信息查出来就会造成浪费。
1.  DepartmentMapper.xml添加延迟查询方法，根据id查询部门信息
```xml
<!-- collection：分段查询 -->
<resultMap id="getDeptAndEmpsByIdStepMap" type="dept">
  <id column="did" property="deptID"/>
  <result column="dept_name" property="deptName"/>
  <collection property="emps"
                    select="com.bobo.mybatis.dao.EmployeeMapperPluse.getEmpsByDeptId"
                    column="{deptId=id}" fetchType="lazy"/>
</resultMap>

<!--/* 根据部门id查询部门信息，包含雇员信息，分步查询*/-->
<!--Department getDeptAndEmpsByIdStep(Integer id);-->
<select id="getDeptAndEmpsByIdStep" resultMap="getDeptAndEmpsByIdStepMap">
  select id,dept_name
  from tbl_dept
  where id=#{id}
</select>
```
2.  Employee映射添加根据deptId查询雇员数组方法
```xml
<!--/* 根据ID查询雇员信息 */-->
<!--List<Employee> getEmpsByDeptId(Integer id);-->
<select id="getEmpsByDeptId" resultType="emp">
  select *
  from tbl_employee
  where d_id=#{deptId}
</select>
```
###5.11  resultMap_分步查询传递多列值&fetchType
到目前为止无论是association还是collection，我们都是直接传单个参数的，如果想传多个参数可以使用以下方法：
 >将多列的值封装map传递；
 >  column="{key1=column1,key2=column2}"
 >  fetchType="lazy"：表示使用延迟加载；
 >    - lazy：延迟
 >    - eager：立即

###5.12 resultMap_discriminator鉴别器
Mybatis可以discriminator鉴别器，来判断某列的值，来改变封装行为。
```xml
<!-- <discriminator javaType=""></discriminator>
  鉴别器：mybatis可以使用discriminator判断某列的值，然后根据某列的值改变封装行为
  封装Employee：
    如果查出的是女生：就把部门信息查询出来，否则不查询；
    如果是男生，把last_name这一列的值赋值给email;
-->
<resultMap id="MyEmpDis" type="emp">
  <id column="id" property="id"/>
  <result column="last_name" property="lastName"/>
  <result column="email" property="email"/>
  <result column="gender" property="gender"/>
  <!--
    column：指定判定的列名
    javaType：列值对应的java类型  -->
    <discriminator javaType="string" column="gender">
      <!--女生  resultType:指定封装的结果类型；不能缺少。/resultMap-->
      <case value="0" resultType="com.atguigu.mybatis.bean.Employee">
        <association property="dept"
                             select="com.bobo.mybatis.dao.DepartmentMapper.getDeptById"
                             column="d_id">
                </association>
            </case>
            <!--男生 ;如果是男生，把last_name这一列的值赋值给email; -->
      <case value="1" resultType="emp">
        <id column="id" property="id"/>
        <result column="last_name" property="lastName"/>
        <result column="last_name" property="email"/>
        <result column="gender" property="gender"/>
      </case>
    </discriminator>
</resultMap>
```