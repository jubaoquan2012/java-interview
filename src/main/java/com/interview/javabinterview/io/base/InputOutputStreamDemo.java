package com.interview.javabinterview.io.base;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.*;
import java.util.Properties;
import java.util.Set;

/**
 * Description:
 *
 * @author baoquan.Ju
 * Created at  2020/11/27
 */
public class InputOutputStreamDemo {
    /**
     * IO流的分类
     * |- 字节流
     * 	    |- 字节输入流 InputStream 抽象类
     * 		    |-  FileInputStream 操作文件的字节输入流
     *      |- 字节输出流 OutputStream抽象类
     *          |- FileOutputStream 操作文件的字节输出流
     * |- 字符流
     * 	    |- 字符输入流 Reader抽象类
     * 			|- FileReader 用来操作文件的字符输入流（简便的流）
     * 	    |- 字符输出流 Writer抽象类
     * 			|- FileWriter 用来操作文件的字符输出流（简便的流）
     * |- 转换流
     *      |- Writer 字符输出流
     *          |- OutputStreamWriter   转换流（字符流—>字节流）（属于字符输出流, 可以指定字符编码表,
     *                                  用来写入数据到文件）
     * 			    |--FileWriter 操作文件中字符输出流，采用默认的字符编码表
     *
     *      |- Reader 字符输入流
     *          |- InputStreamReader: 转换流（字节流字符流）（属于字符输入流，可以指定字符编码表，用来从文件中读数据）
     *              |--FileReader操作文件中字符输入流，采用默认的字符编码表
     * |- 序列化流和反序列化流
     *      |- 序列化流
     *          |- 用于向流中写入对象的操作流 ObjectOutputStream
     *
     *      |- 反序列化流
     *          |- 用于从流中读取对象的操作流 ObjectInputStream
     *
     *   父类和子类的功能有什么区别呢？
     *      OutputStreamWriter 和 InputStreamReader：是字符和字节的桥梁：
     *                                              也可以称之为字符转换流。字符转换流原理：字节流+编码表。
     *      FileWriter 和 FileReader：作为子类，仅作为操作字符文件的便捷类存在。
     *                              当操作的字符文件，使用的是默认编码表时可以不用父类，而直接用子类就完成操作了，
     *                              简化了代码。
     *      //方式一：默认字符集。
     *      InputStreamReader isr = new InputStreamReader(new FileInputStream("a.txt"));
     *      //方式二：指定GBK字符集。
     *      InputStreamReader isr = new InputStreamReader(new FileInputStream("a.txt"),"GBK");
     *      //方式三：
     *      FileReader fr = new FileReader("a.txt");
     *      这三句代码的功能是一样的，其中第三句最为便捷。
     *          注意：一旦要指定其他编码时，绝对不能用子类，必须使用字符转换流。什么时候用子类呢？
     *              条件：
     *                  1、操作的是文件。2、使用默认编码。
     *      总结：
     *          字节--->编码表--->字符 ： 看不懂的--->看的懂的。  需要读。输入流。 InputStreamReader
     *          字符--->编码表--->字节 ： 看的懂的--->看不懂的。  需要写。输出流。 OutputStreamWriter
     *  ----------------------------------------------------------------------------------------------------
     * 上面程序在读取含有中文的文件时，我们并没有看到具体的中文，而是看到一些数字，这是什么原因呢？
     * 既然看不到中文，那么我们如何对其中的中文做处理呢？要解决这个问题，我们必须研究下字符的编码过程。
     * 引出字符编码：
     *
     * 字符编码表
     *
     * 我们知道计算机底层数据存储的都是二进制数据，而我们生活中的各种各样的数据，如何才能和计算机中存储的二进制数据对应起来呢？
     * 美国人他们就把每一个字符和一个整数对应起来，就形成了一张编码表，老美他们的编码表就是ASCII表。其中就是各种英文字符对应的编码。
     * 编码表：其实就是生活中字符和计算机二进制的对应关系表。
     * 1、ascii： 一个字节中的7位就可以表示。对应的字节都是正数。0-xxxxxxx
     * 2、iso-8859-1:拉丁码表 latin，用了一个字节用的8位。1-xxxxxxx  负数。
     * 3、GB2312:简体中文码表。包含6000-7000中文和符号。用两个字节表示。两个字节都是开头为1 ，两个字节都是负数。
     *    GBK:目前最常用的中文码表，2万的中文和符号。用两个字节表示，其中的一部分文字，第一个字节开头是1，第二字节开头是0
     *    GB18030：最新的中文码表，目前还没有正式使用。
     * 4、unicode：国际标准码表:无论是什么文字，都用两个字节存储。
     * 	Java中的char类型用的就是这个码表。char c = 'a';占两个字节。
     * 	Java中的字符串是按照系统默认码表来解析的。简体中文版 字符串默认的码表是GBK。
     * 5、UTF-8:基于unicode，一个字节就可以存储数据，不要用两个字节存储，而且这个码表更加的标准化，在每一个字节头加入了编码信息(后期到api中查找)。
     * 能识别中文的码表：GBK、UTF-8；正因为识别中文码表不唯一，涉及到了编码解码问题。
     * 对于我们开发而言；常见的编码 GBK  UTF-8  ISO-8859-1
     * 	文字--->(数字) ：编码： 就是看能看懂内容，转换成看不懂的内容。
     * 	(数字)--->文字  : 解码： 就是把看不懂的内容，转换成看懂的内容。
     */
//--------------------------------------------------------------------------------------------------//

    /**
     * FileOutputStream类写入数据到文件中
     * FileOutputStream(File file, boolean append)的构造函数中，
     * 可以接受一个boolean类型的值，如果值true，就会在文件末位继续添加。
     *
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        //需求：将数据写入到文件中。
        //创建存储数据的文件。
        File file = new File("c:\\file.txt");
        //创建一个用于操作文件的字节输出流对象。一创建就必须明确数据存储目的地。
        //输出流目的是文件，会自动创建。如果文件存在，则覆盖。
        FileOutputStream fos = new FileOutputStream(file);
        //调用父类中的write方法。
        byte[] data = "abcde".getBytes();
        fos.write(data);
        //关闭流资源。
        fos.close();
    }

    /**
     * FileInputStream类读取数据read方法
     */
    @Test
    public void test_2() throws IOException {
        File file = new File("c:\\file.txt");
        //创建一个字节输入流对象,必须明确数据源，其实就是创建字节读取流和数据源相关联。
        FileInputStream fis = new FileInputStream(file);
        //读取数据。使用 read();一次读一个字节。
        int ch = 0;
        while ((ch = fis.read()) != -1) {
            System.out.println("ch=" + (char) ch);
        }
        // 关闭资源。
        fis.close();
    }

    /**
     * FileInputStream类读取数据read方法
     */
    @Test
    public void test_3() throws IOException {
        /*
         * 演示第二个读取方法， read(byte[]);
         */
        File file = new File("c:\\file.txt");
        // 创建一个字节输入流对象,必须明确数据源，其实就是创建字节读取流和数据源相关联。
        FileInputStream fis = new FileInputStream(file);
        //创建一个字节数组。
        byte[] buf = new byte[1024];//长度可以定义成1024的整数倍。
        int len = 0;
        while ((len = fis.read(buf)) != -1) {
            System.out.println(new String(buf, 0, len));
        }
        fis.close();
    }

    /**
     * 复制文件
     */
    @Test
    public void test_4() throws IOException {
        //1,明确源和目的。
        File srcFile = new File("c:\\YesDir\test.JPG");
        File destFile = new File("copyTest.JPG");

        //2,明确字节流 输入流和源相关联，输出流和目的关联。
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(destFile);

        //3, 使用输入流的读取方法读取字节，并将字节写入到目的中。
        int ch = 0;
        while ((ch = fis.read()) != -1) {
            fos.write(ch);
        }
        //4,关闭资源。
        fos.close();
        fis.close();
    }

    /**
     * 临时数组方式复制文件
     */
    @Test
    public void test_5() throws IOException {
        File srcFile = new File("c:\\YesDir\test.JPG");
        File destFile = new File("copyTest.JPG");
        // 明确字节流 输入流和源相关联，输出流和目的关联。
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        //定义一个缓冲区。
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = fis.read(buf)) != -1) {
            fos.write(buf, 0, len);// 将数组中的指定长度的数据写入到输出流中。
        }
        // 关闭资源。
        fos.close();
        fis.close();
    }

    //----------------------------------------缓冲流--------------------------------------------
    /*
     * 字节缓冲流根据流的方向，共有2个
     * 	写入数据到流中，字节缓冲输出流 BufferedOutputStream
     * 	读取流中的数据，字节缓冲输入流 BufferedInputStream
     */

    /**
     * 字节缓冲输出流BufferedOutputStream
     */
    @Test
    public void test_6() throws IOException {
        //创建基本的字节输出流
        FileOutputStream fileOut = new FileOutputStream("abc.txt");
        //使用高效的流，把基本的流进行封装，实现速度的提升
        BufferedOutputStream out = new BufferedOutputStream(fileOut);
        //2，写数据
        out.write("hello".getBytes());
        //3，关闭流
        out.close();

    }

    /**
     * 字节缓冲输入流 BufferedInputStream
     */
    @Test
    public void test_7() throws IOException {
        //1,创建缓冲流对象
        FileInputStream fileIn = new FileInputStream("abc.txt");
        //把基本的流包装成高效的流
        BufferedInputStream in = new BufferedInputStream(fileIn);
        //2，读数据
        int ch = -1;
        while ((ch = in.read()) != -1) {
            //打印
            System.out.print((char) ch);
        }
        //3，关闭
        in.close();
    }

    //--------------------------------------------字符流-------------------------------------
    @Test
    public void test_8() throws IOException {
        //给文件中写中文
        writeCNText();
        //读取文件中的中文
        readCNText();
    }


    //读取中文
    public static void readCNText() throws IOException {
        FileInputStream fis = new FileInputStream("c:\\cn.txt");
        int ch = 0;
        while ((ch = fis.read()) != -1) {
            System.out.println(ch);
        }
    }

    //写中文
    public static void writeCNText() throws IOException {
        FileOutputStream fos = new FileOutputStream("c:\\cn.txt");
        fos.write("小白开发欢迎你".getBytes());
        fos.close();
    }


//--------------------------------------------转换流-----------------------------------------------------------

    /**
     * OutputStreamWriter 是字符流通向字节流的桥梁：
     * 可使用指定的字符编码表，将要写入流中的字符编码成字节。它的作用的就是，将字符串按照指定的编码表转成字节，在使用字节流将这些字节写出去。
     */
    @Test
    public void test_9() throws IOException {
        //创建与文件关联的字节输出流对象
        FileOutputStream fos = new FileOutputStream("c:\\cn8.txt");
        //创建可以把字符转成字节的转换流对象，并指定编码
        OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
        //调用转换流，把文字写出去，其实是写到转换流的高效区中
        osw.write("你好");//写入高效区。
        osw.close();
    }


    /**
     * InputStreamReader 是字节流通向字符流的桥梁：
     * 它使用指定的字符编码表读取字节并将其解码为字符。它使用的字符集可以由名称指定或显式给定，或者可以接受平台默认的字符集。
     */
    @Test
    public void test_10() throws IOException {
        //创建读取文件的字节流对象
        InputStream in = new FileInputStream("c:\\cn8.txt");
        //创建转换流对象
        //InputStreamReader isr = new InputStreamReader(in);这样创建对象，会用本地默认码表读取，将会发生错误解码的错误
        InputStreamReader isr = new InputStreamReader(in, "utf-8");
        //使用转换流去读字节流中的字节
        int ch = 0;
        while ((ch = isr.read()) != -1) {
            System.out.println((char) ch);
        }
        //关闭流
        isr.close();
    }
//--------------------------------------------序列化和反序列化流----------------------------------------------------------

    /**
     * ObjectInputStream 读取（重构）对象。
     * 通过在流中使用文件可以实现对象的持久存储。
     * 注意：只能将支持 java.io.Serializable 接口的对象写入流中
     */
    @Test
    public void test_11() throws IOException {
        //1,明确存储对象的文件。
        FileOutputStream fos = new FileOutputStream("tempfile\\obj.object");
        //2，给操作文件对象加入写入对象功能。
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        //3，调用了写入对象的方法。
        oos.writeObject(new IOPerson("wangcai", 20));
        //关闭资源。
        oos.close();
    }

    /**
     * ObjectOutputStream 写入的基本数据和对象进行反序列化。支持 java.io.Serializable接口的对象才能从流读取
     */
    @Test
    public void test_12() throws IOException, ClassNotFoundException {
        //1,定义流对象关联存储了对象文件。
        FileInputStream fis = new FileInputStream("tempfile\\obj.object");

        //2,建立用于读取对象的功能对象。
        ObjectInputStream ois = new ObjectInputStream(fis);

        IOPerson obj = (IOPerson) ois.readObject();

        System.out.println(obj.toString());
    }
//------------------------------------------------------序列化接口----------------------------------------------
    /**
     *
     *  当一个对象要能被序列化，这个对象所属的类必须实现Serializable接口。否则会发生异常NotSerializableException异常。
     * 同时当反序列化对象时，如果对象所属的class文件在序列化之后进行的修改，那么进行反序列化也会发生异常InvalidClassException。
     * 发生这个异常的原因如下：
     *       该类的序列版本号与从流中读取的类描述符的版本号不匹配
     *       该类包含未知数据类型
     *       该类没有可访问的无参数构造方法
     * Serializable标记接口。该接口给需要序列化的类，提供了一个序列版本号。serialVersionUID.
     * 该版本号的目的在于验证序列化的对象和对应类是否版本匹配。
     *
     *  瞬态关键字transient:瞬态和静态都不能被序列化,序列化的都是对象
     *       当一个类的对象需要被序列化时，某些属性不需要被序列化，这时不需要序列化的属性可以使用关键字transient修饰。
     *          只要被transient修饰了，序列化时这个属性就不会被序列化了。
     *       同时静态修饰也不会被序列化，因为序列化是把对象数据进行持久化存储，而静态的属于类加载时的数据，不会被序列化。
     */
    //------------------------------------------------------打印流----------------------------------------------

    /**
     * 打印流添加输出数据的功能，使它们能够方便地打印各种数据值表示形式.
     * 打印流根据流的分类：
     * 	字节打印流	PrintStream
     * 	字符打印流	PrintWriter
     * 	方法：
     * void print(String str): 输出任意类型的数据，
     * void println(String str): 输出任意类型的数据，自动写入换行操作
     */

    @Test
    public void test_13() throws IOException {
        //创建流
        //PrintWriter out = new PrintWriter(new FileWriter("printFile.txt"));
        PrintWriter out = new PrintWriter("printFile.txt");
        //2，写数据
        for (int i = 0; i < 5; i++) {
            out.println("helloWorld");
        }
        //3,关闭流
        out.close();
    }
    //-----------------------------------------------------commons-IO----------------------------------------------

    /**
     *提供文件操作（移动文件，读取文件，检查文件是否存在等等）的方法。
     *  常用方法：
     *     readFileToString(File file)：读取文件内容，并返回一个String；
     *     writeStringToFile(File file，String content)：将内容content写入到file中；
     *     copyDirectoryToDirectory(File srcDir,File destDir);文件夹复制
     *     copyFile(File srcFile, File destFile): 文件复制
     */

    /**
     * FileUtils
     */
    @Test
    public void test_14() throws IOException {
        //通过Commons-IO完成了文件复制的功能
        FileUtils.copyFile(new File("D:\\test.avi"), new File("D:\\copy.avi"));

        //通过Commons-IO完成了文件夹复制的功能
        //D:\soft 复制到 C:\\abc文件夹下
        FileUtils.copyDirectoryToDirectory(new File("D:\\soft"), new File("C:\\abc"));
    }

    //-----------------------------------------------------Properties类----------------------------------------------

    /**
     * Properties 类表示了一个持久的属性集。Properties 可保存在流中或从流中加载。属性列表中每个键及其对应值都是一个字符串。
     * 特点：
     * 1、Hashtable的子类，map集合中的方法都可以用。
     * 2、该集合没有泛型。键值都是字符串。
     * 3、它是一个可以持久化的属性集。键值可以存储到集合中，也可以存储到持久化的设备(硬盘、U盘、光盘)上。键值的来源也可以是持久化的设备。
     * <p>
     * 常用API：
     * 	load(InputStream)  把指定流所对应的文件中的数据，读取出来，保存到Propertie集合中
     * 	load(Reader)
     * 	store(OutputStream,commonts)把集合中的数据，保存到指定的流所对应的文件中，参数commonts代表对描述信息
     * 	stroe(Writer,comments);
     */

    /**
     * Properties集合，它是唯一一个能与IO流交互的集合
     * <p>
     * 需求：向Properties集合中添加元素，并遍历
     * <p>
     * 方法：
     * public Object setProperty(String key, String value)调用 Hashtable 的方法 put。
     * public Set<String> stringPropertyNames()返回此属性列表中的键集，
     * public String getProperty(String key)用指定的键在此属性列表中搜索属性
     */
    @Test
    public void test_15() {
        //创建集合对象
        Properties prop = new Properties();
        //添加元素到集合
        //prop.put(key, value);
        prop.setProperty("周迅", "张学友");
        prop.setProperty("李小璐", "贾乃亮");
        prop.setProperty("杨幂", "刘恺威");

        //System.out.println(prop);//测试的使用
        //遍历集合
        Set<String> keys = prop.stringPropertyNames();
        for (String key : keys) {
            //通过键 找值
            //prop.get(key)
            String value = prop.getProperty(key);

            System.out.println(key + "==" + value);
        }
    }

    /**
     * 将集合中内容存储到文件:
     * 使用Properties集合，完成把集合内容存储到IO流所对应文件中的操作
     * 分析：
     * 1，创建Properties集合
     * 2，添加元素到集合
     * 3，创建流
     * 4，把集合中的数据存储到流所对应的文件中
     * stroe(Writer,comments)
     * store(OutputStream,commonts)
     * 把集合中的数据，保存到指定的流所对应的文件中，参数commonts代表对描述信息
     * 5，关闭流c
     */
    @Test
    public void test_16() throws IOException {
        //1，创建Properties集合
        Properties prop = new Properties();
        //2，添加元素到集合
        prop.setProperty("周迅", "张学友");
        prop.setProperty("李小璐", "贾乃亮");
        prop.setProperty("杨幂", "刘恺威");

        //3，创建流
        FileWriter out = new FileWriter("prop.properties");
        //4，把集合中的数据存储到流所对应的文件中
        prop.store(out, "save data");
        //5，关闭流
        out.close();
    }

    /**
     * 读取文件中的数据，并保存到集合
     * 从属性集文件prop.properties 中取出数据，保存到集合中
     * 分析：
     * 1，创建集合
     * 2，创建流对象
     * 3,把流所对应文件中的数据 读取到集合中
     * load(InputStream)  把指定流所对应的文件中的数据，读取出来，保存到Propertie集合中
     * load(Reader)
     * 4,关闭流
     * 5,显示集合中的数据
     */
    @Test
    public void test_17() throws IOException {
        //1，创建集合
        Properties prop = new Properties();
        //2，创建流对象
        FileInputStream in = new FileInputStream("prop.properties");
        //FileReader in = new FileReader("prop.properties");
        //3,把流所对应文件中的数据 读取到集合中
        prop.load(in);
        //4,关闭流
        in.close();
        //5,显示集合中的数据
        System.out.println(prop);
    }
}
