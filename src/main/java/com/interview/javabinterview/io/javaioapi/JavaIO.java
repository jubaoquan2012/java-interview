package com.interview.javabinterview.io.javaioapi;

import java.io.*;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/8
 */
public class JavaIO {

    public static void main(String[] args) throws Exception {
        JavaIO javaIO = new JavaIO();
        javaIO.method_1();// file 操作
        javaIO.method_2();// 字节流
        javaIO.method_3();// 缓冲流
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

    /**
     * 字节输入流: InputStream 抽象类
     * FileInputStream 操作文件的字节输入流
     * 字节输出流: OutputStream 抽象类
     * FileOutputStream 操作文件的字节输出流
     * <p>
     * 按照传输方式: 分为字符流和字节流
     */
    private void method_2() throws Exception {
        File file = new File("D:\\temp\\hello.text");

        /**将数据写入到文件中*/
        FileOutputStream fos1 = new FileOutputStream(file);
        byte[] data1 = "abcd".getBytes();
        fos1.write(data1);
        fos1.close();

        /**文件续写或换行*/
        FileOutputStream fos2 = new FileOutputStream(file, true);
        String data2 = "\r\n" + "jubaoquan";
        fos2.write(data2.getBytes());
        fos2.close();

        /**从文件中读取数据*/
        FileInputStream fis1 = new FileInputStream(file);
        int ch = 0;
        //read读取数据(一次读取一个字节)
        while ((ch = fis1.read()) != -1) {
            System.out.println("ch=" + (char) ch);
        }

        // read(byte) 一次读取定义的byte的长度
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = fis1.read(buf)) != -1) {
            System.out.println(new String(buf, 0, len));
        }
        fis1.close();
    }

    /**
     * 字节缓冲流
     * 写入缓冲流 BufferedOutputStream
     * 读取缓冲流 BufferedInputStream
     *
     * @throws Exception
     */
    private void method_3() throws Exception {
        /**写数据到文件中*/
        FileOutputStream fos = new FileOutputStream("D:\\temp\\hello.text");    //创建基本的字节输出流
        BufferedOutputStream out = new BufferedOutputStream(fos);                  //使用高效的流,把基本的流进行封装,实现速度的提升
        out.write("hello jubaoquan".getBytes());
        out.close();

        /**从文件中读取数据*/
        FileInputStream fis = new FileInputStream("D:\\temp\\hello.text");
        BufferedInputStream in = new BufferedInputStream(fis);
        int len = 0;
        while ((len = in.read()) != -1) {
            System.out.println((char) len);
        }
        in.close();
        fis.close();

        /***/
        /***/

    }
}
