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

            //���ý�Ż��������ɫ��������(����Ҫ����֪����ʲô����)
            //ֻ�����ý��̫��ִ�����Ҫרҵ����רҵ�����Ļ�������Ҫȥ������'������'
            //����Ͳ��ظ���ʾ��
            Person person = (Person) new JDKMeipo().getInstance(new Zhangsan());
            person.findLove();
            System.out.println();
            person.buy();

            //JDK���и��淶��ֻҪ��$��ͷ��һ�㶼���Զ����ɵ�
            //ͨ�������빤�߲鿴$Proxy0��Դ���루����ļ���ֱ���Ͻ�idea���ɲ鿴��
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
