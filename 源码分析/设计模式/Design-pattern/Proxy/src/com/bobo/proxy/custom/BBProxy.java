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
 * @description: ģ��Proxy��
 * @author: bobobo
 * @create: 2018-07-11 11:25
 **/
public class BBProxy {

    //����һ�����з���ʡ��ÿ�ζ�д
    public static final String ln = "\r\n";

    /**
     * ��̬����һ���������
     * @param classLoader ���ĸ�ClassLoader�����������ɵĴ��������м���
     * @param interfaces �������ʵ�ֵ����нӿ�����
     * @param invocationHandler �ڵ��÷�����ʱ�򣬻��������һ��BBInvocationHandler������
     * @return һ����������ʵ��
     */
    public static Object newProxyInstance(BBClassLoader classLoader, Class<?>[] interfaces, BBInvocationHandler invocationHandler) {

        try {
            //1����̬����Դ����.java�ļ�
            String codeSrc = generateSrc(interfaces);

            //2��Java�ļ��������
            String filePath = BBProxy.class.getResource("").getPath() + "$Proxy0.java";
            System.out.println("$Proxy0.java ��ַ��" + filePath);
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(codeSrc);
            fileWriter.flush();
            fileWriter.close();

            //3�������ɵ�.java�ļ������.class�ļ�
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
            Iterable iterable = manager.getJavaFileObjects(file);

            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
            task.call();
            manager.close();

            //4���������ɵ�.class�ļ����ص�JVM����
            Class proxyClass = classLoader.findClass("$Proxy0");
            Constructor constructor = proxyClass.getConstructor(BBInvocationHandler.class);

            //�����࣬���ұ���󣬰��ļ���ɾ��
            file.delete();

            //5�������ֽ��������Ժ���µĴ������
            return constructor.newInstance(invocationHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * ��������һ���µ��ࡢͬʱ�µ���Ҫʵ�ֱ�����������ʵ�ֵĽӿ�
     * ����ֻģ�ⵥ���ӿڣ��޲η���
     *
     * @param interfaces ��������ʵ�ֵ����нӿ�
     * @return
     */
    private static String generateSrc(Class<?>[] interfaces) {

        StringBuffer stringBuffer = new StringBuffer();

        //�����
        stringBuffer.append("package com.bobo.proxy.custom;" + ln);

        stringBuffer.append("import com.bobo.proxy.common.Person;" + ln);
        stringBuffer.append("import java.lang.reflect.Method;" + ln);

        //������
        stringBuffer.append("public class $Proxy0 implements " + interfaces[0].getName() + "{" + ln);
        //����
        stringBuffer.append("BBInvocationHandler handler;" + ln);
        //���췽��
        stringBuffer.append("public $Proxy0(BBInvocationHandler handler) { " + ln);
        stringBuffer.append("this.handler = handler;" + ln);
        stringBuffer.append("}" + ln);

        //forѭ������ȡ�ӿڵķ�����������
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
