package com.bobo.proxy.custom;

import java.io.*;

/**
 * @program: Proxy
 * @description: 重写ClassLoader类
 * @author: bobobo
 * @create: 2018-07-11 11:25
 **/
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
