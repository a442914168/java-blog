package com.bobo.proxy.custom;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @program: Proxy
 * @description: 模仿Proxy类
 * @author: bobobo
 * @create: 2018-07-11 11:25
 **/
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
