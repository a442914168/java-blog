package com.bobo.proxy.jdk;

import com.bobo.proxy.common.Person;
import com.bobo.proxy.common.Zhangsan;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * @program: Proxy
 * @description:
 * @author: bobobo
 * @create: 2018-07-10 21:34
 **/
public class JDKTest {

    public static void main(String[] args) {
        try {

            //这个媒婆会帮我们物色对象、买东西(不需要事先知道有什么方法)
            //只是这个媒婆太固执，如果要专业事情专业人做的话，则需要去创建个'代购人'
            //这里就不重复演示了
            Person person = (Person) new JDKMeipo().getInstance(new Zhangsan());
            person.findLove();
            System.out.println();
            person.buy();

            //JDK中有个规范，只要是$开头的一般都是自动生成的
            //通过反编译工具查看$Proxy0的源代码（输出文件后，直接拖进idea即可查看）
            byte [] bytes = ProxyGenerator.generateProxyClass("$Proxy0",new Class[]{Person.class});
            FileOutputStream os = new FileOutputStream("/Users/bohuang/Desktop/$Proxy0.class");
            os.write(bytes);
            os.flush();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
