###前言
开篇前，我假设你们已经初步了解了什么是代理模式，什么是动态代理。如有不了解的**请跳转[代理模式](https://www.jianshu.com/p/6233ac4bbc27)**。

本篇主要目的主要有
1.InvocationHandler 与 Proxy 有什么作用;
2.举栗子说明什么是JDK动态代理;
3.动态代理的原理 和 执行流程;
4.什么是字节码重组 和 其执行原理;
5.仿写JDK动态代理;

[本文Demo地址](https://github.com/a442914168/Design-pattern/tree/master/Proxy)

###简要说明
在java的动态代理机制中，有两个重要的类或接口，一个是 InvocationHandler(Interface)、另一个则是 Proxy(Class)。
#####InvocationHandler(Interface)
每一个动态代理类都必须要实现InvocationHandler这个接口，并且每个代理类的实例都关联到了一个handler，当我们通过代理对象调用 一个方法的时候，这个方法的调用就会被转发为由InvocationHandler这个接口的 invoke 方法来进行调用。
我们来看看InvocationHandler这个接口的唯一一个方法 invoke 方法：
```java
/**
 * 处理代理实例上的方法调用并返回
 * @param proxy 方法调用的代理实例
 *              对应本Demo就是Person，只不过是通过字节码重组后的
 * @param method 在代理实例上调用的接口方法
 *              对应本Demo就是findLove()，buy()方法。
 * @param args 在代理实例上的方法调用中传递的参数
 * @return  真实对象方法的返回类型
 * @throws Throwable
 */
Object invoke(Object proxy, Method method, Object[] args) throws Throwable
```
#####Proxy(Class)
Proxy这个类的作用就是用来动态创建一个代理对象。
我们经常使用的是newProxyInstance这个方法：
```java
/**
     * 动态创建一个代理对象
     * @param loader 由哪个ClassLoader对象来对生成的代理对象进行加载
     * @param interfaces 代理对象实现的所有接口数组
     * @param h 在调用方法的时候，会关联到哪一个InvocationHandler对象上
     * @return 一个代理对象的实例
     */
public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces,  InvocationHandler h)  throws IllegalArgumentException
```
###举栗子
还是使用[代理模式](https://www.jianshu.com/p/6233ac4bbc27)中的JDK动态代理栗子

定义Person接口，有findLove()、buy()方法
```java
public interface Person {
    /**
     * 相亲
     */
    public void findLove();

    /**
     * 买东西
     */
    public void buy();
}
```
创建一个类，Zhangsan实现Person接口，并且实现接口的方法
```java
public class Zhangsan implements Person {

    @Override
    public void findLove() {
        System.out.println("我要大长腿");
    }

    @Override
    public void buy() {
        System.out.println("我要iPhone X");
    }
}
```
创建代理类，Meipo负责代理
```java
public class JDKMeipo implements InvocationHandler {

    //被代理的对象，把引用给保存下来
    private Person target;

    public Object getInstance(Person target) throws Exception {

        this.target = target;

        Class<?> clazz = target.getClass();

        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("我是媒婆：我要给你找对象，现在已经拿到你的需求");
        System.out.println("开始物色");

        method.invoke(this.target, args);

        System.out.println("如果合适的话，就准备办事");

        return proxy;
    }
}
```
创建测试类
```java
public class JDKTest {

    public static void main(String[] args) {
        try {
            Person person = (Person) new JDKMeipo().getInstance(new Zhangsan());
            person.findLove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
测试结果：
>我是媒婆：我要给你找对象，现在已经拿到你的需求
>开始物色
>我要大长腿
>如果合适的话，就准备办事

###动态代理的原理
我们在JDKTest测试类中的person.findLove();打个断点，Debug运行Demo。
![new JDKMeipo().getInstance(new Zhangsan()) 返回的Person信息.jpeg](https://upload-images.jianshu.io/upload_images/1307556-5d6a3f32fec0944e.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
可以看到，返回来的person对象并不是Peron接口，而是一个新的类**$Proxy0**
>JDK中有个规范，只要是$开头的一般都是自动生成的

通过反编译工具查看$Proxy0的源代码(方法在Demo中有展示，这里就忽略过程)
```java
import com.bobo.proxy.common.Person;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

public final class $Proxy0 extends Proxy implements Person {
    private static Method m1;
    private static Method m3;
    private static Method m2;
    private static Method m4;
    private static Method m0;

    public $Proxy0(InvocationHandler var1) throws  {
        super(var1);
    }

    public final boolean equals(Object var1) throws  {
        try {
            return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final void findLove() throws  {
        try {
            super.h.invoke(this, m3, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final String toString() throws  {
        try {
            return (String)super.h.invoke(this, m2, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void buy() throws  {
        try {
            super.h.invoke(this, m4, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final int hashCode() throws  {
        try {
            return (Integer)super.h.invoke(this, m0, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    static {
        try {
            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
            m3 = Class.forName("com.bobo.proxy.common.Person").getMethod("findLove");
            m2 = Class.forName("java.lang.Object").getMethod("toString");
            m4 = Class.forName("com.bobo.proxy.common.Person").getMethod("buy");
            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
        } catch (NoSuchMethodException var2) {
            throw new NoSuchMethodError(var2.getMessage());
        } catch (ClassNotFoundException var3) {
            throw new NoClassDefFoundError(var3.getMessage());
        }
    }
}
```
通过$Proxy0源代码，我们可以看到JDKTest中**person.findLove()**的调用流程

1.类加载时通过反射，$Proxy0实现了Zhangsan所有的方法，并且记录了原方法的调用路径。
```java
public final void findLove() throws  {}  //$Proxy0有这个方法
m3 = Class.forName("com.bobo.proxy.common.Person").getMethod("findLove");  //并且记录了原Person的调用路径
```
2.并且持有InvocationHandler属性(对应Demo就是JDKMeipo类)。
3.测试类在调用findLove() 方法时，会调用JDKMeipo所实现的invoke方法，并且将原方法的调用路径和参数传给invoke中。
```java
super.h.invoke(this, m3, (Object[])null);
```
4.JDKMeipo类中的invoke方法，接收到回调，就可以做一些操作来增强，并且JDKMeipo类中已经持有了Zhangsan所对应的Peron属性，就可以准确的调用原方法。
```java
System.out.println("我是媒婆：我要给你找对象，现在已经拿到你的需求");
System.out.println("开始物色");

//调用原方法
method.invoke(this.target, args);

System.out.println("如果合适的话，就准备办事");
```

###字节码重组
在上面的例子中，最神秘的就是Proxy获取代理对象的操作，而这个获取代理对象的操作我们叫做**字节码重组**
```java
Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
```
#####字节码重组原理：
1.拿到被代理对象的引用，并且通过反射获取到它的所有的接口
2.Proxy类重新生成一个新的类、同时新的类要实现被代理类所有实现的接口
3.动态生成Java代码，把新加的业务逻辑方法由一定的逻辑代码去调用
4.编译新生成的Java代码.class
5.再重新加载到JVM中运行

###仿写JDK动态代理

1、创建一个 BBInvocationHandler 接口，和InvocationHandler一样只有一个invoke方法。
```java
public interface BBInvocationHandler {
    /**
     * 处理代理实例上的方法调用并返回
     * @param proxy 方法调用的代理实例
     *              对应本Demo就是CustomMeipo，只不过是通过字节码重组后的
     * @param method 在代理实例上调用的接口方法
     *              对应本Demo就是findLove()，buy()方法。
     * @param args 在代理实例上的方法调用中传递的参数
     * @return  真实对象方法的返回类型
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
```
2、创建一个BBProxy类，对外提供一个newProxyInstance方法。
>1、动态生成源代码.java文件
>2、Java文件输出磁盘
>3、把生成的.java文件编译成.class文件
>4、编译生成的.class文件加载到JVM中来
>5、返回字节码重组以后的新的代理对象
```java
public class BBProxy {

    //定义一个换行符，省的每次都写
    public static final String ln = "\r\n";

    /**
     * 动态创建一个代理对象
     * @param classLoader 由哪个ClassLoader对象来对生成的代理对象进行加载
     * @param interfaces 代理对象实现的所有接口数组
     * @param invocationHandler 在调用方法的时候，会关联到哪一个BBInvocationHandler对象上
     * @return 一个代理对象的实例
     */
    public static Object newProxyInstance(BBClassLoader classLoader, Class<?>[] interfaces, BBInvocationHandler invocationHandler) {

        try {
            //1、动态生成源代码.java文件
            String codeSrc = generateSrc(interfaces);

            //2、Java文件输出磁盘
            String filePath = BBProxy.class.getResource("").getPath() + "$Proxy0.java";
            System.out.println("$Proxy0.java 地址：" + filePath);
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(codeSrc);
            fileWriter.flush();
            fileWriter.close();

            //3、把生成的.java文件编译成.class文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
            Iterable iterable = manager.getJavaFileObjects(file);

            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
            task.call();
            manager.close();

            //4、编译生成的.class文件加载到JVM中来
            Class proxyClass = classLoader.findClass("$Proxy0");
            Constructor constructor = proxyClass.getConstructor(BBInvocationHandler.class);

            //生成类，并且编译后，把文件给删除
            file.delete();

            //5、返回字节码重组以后的新的代理对象
            return constructor.newInstance(invocationHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 重新生成一个新的类、同时新的类要实现被代理类所有实现的接口
     * 这里只模拟单个接口，无参方法
     *
     * @param interfaces 被代理类实现的所有接口
     * @return
     */
    private static String generateSrc(Class<?>[] interfaces) {

        StringBuffer stringBuffer = new StringBuffer();

        //导入包
        stringBuffer.append("package com.bobo.proxy.custom;" + ln);

        stringBuffer.append("import com.bobo.proxy.common.Person;" + ln);
        stringBuffer.append("import java.lang.reflect.Method;" + ln);

        //声明类
        stringBuffer.append("public class $Proxy0 implements " + interfaces[0].getName() + "{" + ln);
        //变量
        stringBuffer.append("BBInvocationHandler handler;" + ln);
        //构造方法
        stringBuffer.append("public $Proxy0(BBInvocationHandler handler) { " + ln);
        stringBuffer.append("this.handler = handler;" + ln);
        stringBuffer.append("}" + ln);

        //for循环，获取接口的方法，并生成
        for (Method method : interfaces[0].getMethods()) {
            stringBuffer.append("public " + method.getReturnType().getName() + " " + method.getName() + "() {" + ln);
            stringBuffer.append("try {" + ln);
            stringBuffer.append("Method m = " + interfaces[0].getName() + ".class.getMethod(\"" + method.getName() + "\",new Class[]{});" + ln);
            stringBuffer.append("this.handler.invoke(this, m, null);" + ln);
            stringBuffer.append("} catch (Throwable e) {" + ln);
            stringBuffer.append("e.printStackTrace();" + ln);
            stringBuffer.append("}" + ln);
            stringBuffer.append("}");
        }

        stringBuffer.append("}" + ln);

        return stringBuffer.toString();
    }
}
```
3、自定义类加载器
```java
public class BBClassLoader extends ClassLoader {

    //当前文件所在的文件夹
    private File classPathFile;

    public BBClassLoader() {
        String classPath = BBClassLoader.class.getResource("").getPath();
//        System.out.println("BBClassLoader所在文件夹 :" + classPath);
        this.classPathFile = new File(classPath);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        //获取同个包下的目标类
        String className = BBClassLoader.class.getPackage().getName() + "." + name;

        if (classPathFile != null) {
            //根据包名，类名组合找出目标类具体的位置
            File classFile = new File(classPathFile, name.replace("\\.", "/") + ".class");
            //如果目标类文件存在
            if (classFile.exists()) {
                //创建文件读取流读取
                FileInputStream inputStream = null;
                ByteArrayOutputStream outputStream = null;

                try {
                    inputStream = new FileInputStream(classFile);
                    outputStream = new ByteArrayOutputStream();
                    byte[] buff = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buff)) != -1) {
                        outputStream.write(buff, 0, len);
                    }
                    return defineClass(className, outputStream.toByteArray(), 0, outputStream.size());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != inputStream) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (null != outputStream) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

}
```
4、CustomMeipo创建一个代理类，实现BBInvocationHandler方法，并且使用BBProxy.newProxyInstance来完成代理
```java
public class CustomMeipo implements BBInvocationHandler {

    private Person target;

    public Object getInstance(Person target) throws Exception {
        this.target = target;

        Class<?> clazz = target.getClass();

        //字节码重组原理：
        //1、拿到被代理对象的引用，并且通过反射获取到它的所有的接口
        //2、BBProxy类重新生成一个新的类、同时新的类要实现被代理类所有实现的接口
        //3、动态生成Java代码，把新加的业务逻辑方法由一定的逻辑代码去调用
        //4、编译新生成的Java代码.class
        //5、再重新加载到JVM中运行
        return BBProxy.newProxyInstance(new BBClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("你好，我是BOBO派来的媒婆");
        System.out.println("请问你需要啥对象");

        method.invoke(this.target, args);

        System.out.println("ok，收到！");

        return proxy;
    }
}
```
5、测试
```java
public class CustomTest {
    public static void main(String[] args) {
        try {
            Person person = (Person)new CustomMeipo().getInstance(new Zhangsan());
            person.findLove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
测试结果：
>你好，我是BOBO派来的媒婆
>请问你需要啥对象
>我要大长腿
>ok，收到！

###总结
#####字节码重组
1.拿到被代理对象的引用，并且通过反射获取到它的所有的接口
2.Proxy类重新生成一个新的类、同时新的类要实现被代理类所有实现的接口
3.动态生成Java代码，把新加的业务逻辑方法由一定的逻辑代码去调用
4.编译新生成的Java代码.class
5.再重新加载到JVM中运行
#####自定义Proxy类
1、动态生成源代码.java文件
2、Java文件输出磁盘
3、把生成的.java文件编译成.class文件
4、编译生成的.class文件加载到JVM中来
5、返回字节码重组以后的新的代理对象

在手写动态代理中，主要为了理解JAVA动态代理的核心思想。所以还是有一些没兼顾到，也因为我对JVM不熟悉，所以类加载那块还是有点蒙圈，请多包涵~~