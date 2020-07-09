package com.interview.javabinterview.spring.aop.support;

import com.interview.javabinterview.spring.aop.aspect.ZPAfterReturningAdviceInterceptor;
import com.interview.javabinterview.spring.aop.aspect.ZPAfterThrowingAdvice;
import com.interview.javabinterview.spring.aop.aspect.ZPMethodBeforeAdviceInterceptor;
import com.interview.javabinterview.spring.aop.config.ZPAopConfig;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/25
 */
public class ZPAdvisedSupport {

    private Class<?> targetClass;

    private Object target;

    private Pattern pointCutClassPattern;

    private ZPAopConfig config;

    private transient Map<Method, List<Object>> methodCache;

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        parse();
    }

    private void parse() {
        String pointCut = config.getPointCut()
                .replaceAll("\\.", "\\\\.")
                .replaceAll("\\\\.\\*", ".*")
                .replaceAll("\\(", "\\\\(")
                .replaceAll("\\)", "\\\\)");
        String pointCutForClassRegex = pointCut
                .substring(0, pointCut.lastIndexOf("\\(") - 4)
                .substring(pointCut.lastIndexOf(" ") + 1);

        pointCutClassPattern = Pattern.compile("calss " + pointCutForClassRegex);

        methodCache = new HashMap<>();
        Pattern pattern = Pattern.compile(pointCut);
        try {
            Class aspectClass = Class.forName(this.config.getAspectClass());
            Map<String, Method> aspectMethods = new HashMap<>();
            for (Method m : aspectClass.getMethods()) {
                aspectMethods.put(m.getName(), m);
            }
            for (Method m : this.targetClass.getMethods()) {
                String methodString = m.toString();
                //Object get() throws Exception{}; 处理为 Object get() {}
                if (methodString.contains("throws")) {
                    methodString = methodString.substring(0, methodString.lastIndexOf("throw")).trim();
                }
                Matcher matcher = pattern.matcher(methodString);
                if (matcher.matches()) {
                    List<Object> advices = new LinkedList<>();
                    //把每一个方法包装成 MethodIntercept
                    //before
                    if (!(null == config.getAspectBefore() || "".equals(config.getAspectBefore()))) {
                        //创建一个advice对象;
                        advices.add(new ZPMethodBeforeAdviceInterceptor(aspectMethods.get(config.getAspectBefore()), aspectClass.newInstance()));
                    }
                    //after
                    if (!(null == config.getAspectAfter() || "".equals(config.getAspectAfter()))) {
                        //创建一个advice对象;
                        advices.add(new ZPAfterReturningAdviceInterceptor(aspectMethods.get(config.getAspectAfter()), aspectClass.newInstance()));
                    }
                    //throw
                    if (!(null == config.getAspectAfterThrow() || "".equals(config.getAspectAfterThrow()))) {
                        //创建一个advice对象;
                        ZPAfterThrowingAdvice zpAfterThrowingAdvice =
                                new ZPAfterThrowingAdvice(
                                        aspectMethods.get(config.getAspectAfterThrow()),
                                        aspectClass.newInstance()
                                );
                        zpAfterThrowingAdvice.setThrowName(config.getAspectAfterTrowingName());
                        advices.add(zpAfterThrowingAdvice);
                    }
                    methodCache.put(m, advices);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object getTarget() {
        return null;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws Exception {
        List<Object> cached = methodCache.get(method);
        if (cached == null){
            Method m = targetClass.getMethod(method.getName(),method.getParameterTypes());
            this.methodCache.put(m,cached);
        }
        return cached;
    }

    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }
}
