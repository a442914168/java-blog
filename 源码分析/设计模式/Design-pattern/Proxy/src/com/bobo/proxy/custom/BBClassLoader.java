package com.bobo.proxy.custom;

import java.io.*;

/**
 * @program: Proxy
 * @description: ��дClassLoader��
 * @author: bobobo
 * @create: 2018-07-11 11:25
 **/
public class BBClassLoader extends ClassLoader {

    //��ǰ�ļ����ڵ��ļ���
    private File classPathFile;

    public BBClassLoader() {
        String classPath = BBClassLoader.class.getResource("").getPath();
//        System.out.println("BBClassLoader�����ļ��� :" + classPath);
        this.classPathFile = new File(classPath);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        //��ȡͬ�����µ�Ŀ����
        String className = BBClassLoader.class.getPackage().getName() + "." + name;

        if (classPathFile != null) {
            //���ݰ�������������ҳ�Ŀ��������λ��
            File classFile = new File(classPathFile, name.replace("\\.", "/") + ".class");
            //���Ŀ�����ļ�����
            if (classFile.exists()) {
                //�����ļ���ȡ����ȡ
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
