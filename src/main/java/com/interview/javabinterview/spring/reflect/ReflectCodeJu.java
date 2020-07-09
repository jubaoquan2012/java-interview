package com.interview.javabinterview.spring.reflect;

import com.interview.javabinterview.java_base.map.ArrayList;
import com.interview.javabinterview.spring.reflect.target.Book;
import com.interview.javabinterview.spring.reflect.target.Car;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * Java 反射机制:JAVA反射机制是在运行状态中，对于任意一个类，都能够知道这个类的所有属性和方法；对于任意一个对象，都能够调用它的任意方法和属性；
 * 这种动态获取信息以及动态调用对象方法的功能称为java语言的反射机制。
 *
 * @author Ju Baoquan
 * Created at  2020/5/22
 */
public class ReflectCodeJu {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        method_1();// 反射的创建对象三种方式
        method_2();// 反射私有的构造方法
        method_3();// 反射私有属性
        method_4();// 反射私有方法
        method_5();// 获取接口类
        method_6();// 获取注解
    }

    private static void method_1() throws
            ClassNotFoundException,
            IllegalAccessException,
            InstantiationException,
            NoSuchMethodException,
            InvocationTargetException {

        /**第一种 通过Object获取对象*/
        Book book = new Book();
        Class<?> clazz_1 = book.getClass();
        /**第二种：通过对象实例方法获取对象*/
        Class<?> clazz_2 = Book.class;
        /**第三种：通过Class.forName方式*/
        Class<?> clazz_3 = Class.forName("com.interview.javabinterview.spring.reflect.target.Book");

        /**对于有空构造函数的类,可以直接用字节码获取实例*/
        Book book1 = (Book) clazz_1.newInstance();
        /**对于没有空的构造函数的类则需要先获取到他的构造对象 在通过该构造方法类获取实例*/
        Constructor constructor = clazz_1.getConstructor(String.class, String.class);
        Book book2 = (Book) constructor.newInstance("高并发", "高洪岩");
    }

    /**
     * 反射私有的构造方法
     */
    private static void method_2() {
        try {
            Class<?> bookClazz = getBookClass();

            Constructor<?> constructor = bookClazz.getConstructor(String.class, String.class);
            System.out.println(constructor.isAccessible());//false
            /**
             * 1.当isAccessible()的结果是false时不允许通过反射访问该字段
             * 2.当该字段时private修饰时isAccessible()得到的值是false，必须要改成true才可以访问
             * 3.所以 f.setAccessible(true);得作用就是让我们在用反射时访问私有变量
             */
            Constructor<?> declaredConstructor = bookClazz.getDeclaredConstructor(String.class, String.class);
            System.out.println(declaredConstructor.isAccessible());//false

            declaredConstructor.setAccessible(true);
            Book book = (Book) declaredConstructor.newInstance("高并发", "高洪岩");
            System.out.println("method_6:" + book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射私有属性
     */
    private static void method_3() {
        Class<?> bookClazz = getBookClass();
        try {
            Field fieldTag = bookClazz.getDeclaredField("TAG");
            fieldTag.setAccessible(true);
            Object objectBook = bookClazz.newInstance();
            String tag = (String) fieldTag.get(objectBook);
            System.out.println("method_3:" + tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射私有方法
     */
    private static void method_4() {
        Class<?> bookClazz = getBookClass();
        try {
            Method declaredMethod = bookClazz.getDeclaredMethod("declaredMethod", int.class);
            declaredMethod.setAccessible(true);

            Object objectBook = bookClazz.newInstance();
            String result_1 = (String) declaredMethod.invoke(objectBook, 0);
            String result_2 = (String) declaredMethod.invoke(objectBook, 1);

            System.out.println("method_4:" + result_1);
            System.out.println("method_4:" + result_2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射操作接口
     */
    private static void method_5() {
        List<Car> carImplList = new ArrayList<>();
        try {
            Class<?> carClazz = Car.class;

            if (carClazz.isInterface()) {
                List<Class> classList = getAllClassByPath(carClazz.getPackage().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * https://blog.csdn.net/qq_16438883/article/details/103408709
     * @param packName
     * @return
     */
    private static List<Class> getAllClassByPath(String packName) {
        List<Class> classList = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packName.replace(".", "/");

        try {
            List<File> fileList = new ArrayList<>();
            Enumeration<URL> enumeration = classLoader.getResources(path);
            while (enumeration.hasMoreElements()){
                URL url = enumeration.nextElement();
                fileList.add(new File(url.getFile()));
            }
            for (int i = 0; i < fileList.size(); i++) {
                classList.addAll(findClass(fileList.get(i),packName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Collection<? extends Class> findClass(File file, String packName) {
        ArrayList<Class> list = new ArrayList<>();
        if (!file.exists()){
            return list;
        }
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isDirectory()){

            }
        }
        return null;
    }

    /**
     * 反射操作注解
     */
    private static void method_6() {
        try {
            Class<?> carClazz = Class.forName("com.interview.javabinterview.spring.reflect.target.Car");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Class<?> getBookClass() {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("com.interview.javabinterview.spring.reflect.target.Book");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }
}
