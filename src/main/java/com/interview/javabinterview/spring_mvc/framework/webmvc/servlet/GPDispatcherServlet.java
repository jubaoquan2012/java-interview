package com.interview.javabinterview.spring_mvc.framework.webmvc.servlet;



import com.interview.javabinterview.spring_mvc.framework.annotation.GPAutowired;
import com.interview.javabinterview.spring_mvc.framework.annotation.GPController;
import com.interview.javabinterview.spring_mvc.framework.annotation.GPRequestMapping;
import com.interview.javabinterview.spring_mvc.framework.annotation.GPService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/8/26
 */
public class GPDispatcherServlet extends HttpServlet {

    private Properties contextConfig = new Properties();

    private List<String> classNames = new ArrayList<String>();

    private Map<String, Object> IOC = new HashMap<>();

    private Map<String, Method> handlerMapping = new HashMap<>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //6.等待请求
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception:\r\n" + Arrays.toString(e.getStackTrace())
                    .replaceAll("\\[|\\]", "")
                    .replaceAll(",\\s", "\r\n"));
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (this.handlerMapping.isEmpty()) {
            return;
        }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url.replace(contextPath, "").replaceAll("/+", "");
        if (!this.handlerMapping.containsKey(url)) {
            resp.getWriter().write("404 not Found!");
            return;
        }
        Map<String, String[]> parameterMap = req.getParameterMap();
        Method method = this.handlerMapping.get(url);
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] paramValues = new Object[parameterTypes.length];
        for (int i = 0; i < paramValues.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if (parameterType == HttpServletRequest.class) {
                paramValues[i] = req;
                continue;
            } else if (parameterType == HttpServletResponse.class) {
                paramValues[i] = resp;
                continue;
            } else if (parameterType == String.class) {
                for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {
                    String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", "");
                    paramValues[i] = value;
                }
            }
        }
        String beanName = lowerFirstCase(method.getDeclaringClass().getSimpleName());
        try {
            Object o = this.IOC.get(beanName);
            System.out.println(o);
            method.invoke(this.IOC.get(beanName), paramValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        //1.加载配置文件
        doLocationConfig(config.getInitParameter("contextConfigLocation"));

        //2.扫描所有相关的类
        doScanner(contextConfig.getProperty("scanPackage"));

        //3.初始化所有相关的类
        doInstance();

        //4.自动注入
        doAutowired();

        //==========================spring的核心初始化完成======================

        //5.初始化HandlerMapping
        initHandleMapping();

    }

    private void doLocationConfig(String contextConfigLocation) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass()
                .getClassLoader()
                .getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classDir = new File(url.getFile());
        for (File file : classDir.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else {
                String className = scanPackage + "." + file.getName().replace(".class", "").trim();
                classNames.add(className);
            }
        }
    }

    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }
        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);
                //不是所有的类都要实例化的 只有加了注解的类需要实例化
                if (clazz.isAnnotationPresent(GPController.class)) {
                    //key 默认是类名首字母小写
                    String beanName = lowerFirstCase(clazz.getSimpleName());
                    IOC.put(beanName, clazz.newInstance());
                } else if (clazz.isAnnotationPresent(GPService.class)) {
                    //2.自定义了名字,优先使用自定义的名字
                    GPService service = clazz.getAnnotation(GPService.class);
                    String beanName = service.value();

                    //1.默认采用首字母小写
                    if ("".equals(beanName.trim())) {
                        beanName = lowerFirstCase(clazz.getSimpleName());
                    }
                    Object instance = clazz.newInstance();
                    IOC.put(beanName, instance);

                    //3.根据接口类型赋值(接口不能实例化,接口的实现可以实例化)
                    for (Class<?> i : clazz.getInterfaces()) {
                        IOC.put(lowerFirstCase(i.getSimpleName()), instance);
                    }
                } else {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void doAutowired() {
        if (IOC.isEmpty()) {
            return;
        }
        //循环ioc容器中所有的类,需要自动赋值的属性进行赋值
        for (Map.Entry<String, Object> entry : IOC.entrySet()) {
            //依赖注入,不管是不是私有的都要进行赋值
            //@GPAutowired
            //private DemoService demoService;
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(GPAutowired.class)) {
                    continue;
                }
                GPAutowired autowired = field.getAnnotation(GPAutowired.class);
                String beanName = autowired.value().trim();
                if ("".equals(beanName)) {
                    beanName = field.getType().getSimpleName();
                }
                //暴力访问
                field.setAccessible(true);
                try {
                    field.set(entry.getValue(), IOC.get(lowerFirstCase(beanName)));
                    System.out.println("");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    private void initHandleMapping() {
        if (IOC.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : IOC.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(GPController.class)) {
                continue;
            }
            String baseUrl = "";
            //获取controller的url配置
            if (clazz.isAnnotationPresent(GPRequestMapping.class)) {
                GPRequestMapping annotation = clazz.getAnnotation(GPRequestMapping.class);
                baseUrl = annotation.value();
            }
            //获取method的url配置
            for (Method method : clazz.getMethods()) {
                if (!method.isAnnotationPresent(GPRequestMapping.class)) {
                    continue;
                }
                GPRequestMapping requestMapping = method.getAnnotation(GPRequestMapping.class);
                String url = ("/" + baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
                handlerMapping.put(url, method);
            }
        }
    }


    private String lowerFirstCase(String beanName) {
        char[] chars = beanName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
