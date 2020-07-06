package com.interview.javabinterview.java_base.io;

import java.io.File;
import java.io.IOException;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/8
 */
public class JavaIO {

    public static void main(String[] args) throws IOException {
        JavaIO javaIO = new JavaIO();
        javaIO.method_1();
    }

    private void method_1() throws IOException {
        String pathName = "D:\\temp\\operationlog.jstack.txt";

        File file1 = new File(pathName);

        File file2 = new File("D:\\temp", "operationlog.jstack.txt");

        File dir = new File("D:\\temp");
        File file3 = new File(dir, "operationlog.jstack.txt");

        String absPtah = file3.getAbsolutePath();
        String path = file3.getPath();
        String fileName = file3.getName();
        long size = file3.length();

        // 对文件或文件夹进行操作
        File file = new File("D:\\temp\\hello.txt");
        /**
         * 创建文件:
         *  如果文件不存在,创建 返回 true ;
         *  如果文件存在,则不创建 返回false;
         *  如果路径错误,IOException
         */
        boolean newFile = file.createNewFile();

        /**删除文件操作*/
        boolean delete = file.delete();

        /**判断文件是否存在*/
        boolean exists = file.exists();

        /**创建单个目录mkdir,创建多级目录*/
        File fileDir = new File("D:\\abc");
        boolean mkdir = fileDir.mkdir();

        /**判断文件,目录*/
        boolean isFile = fileDir.isFile();
        boolean isDirectory = fileDir.isDirectory();

        File efgDir = new File("D:\\efg");
        /**获取的是目录下的当前文件以及文件夹的名称*/
        String[] names = efgDir.list();

        File[] files = efgDir.listFiles();
    }
}
