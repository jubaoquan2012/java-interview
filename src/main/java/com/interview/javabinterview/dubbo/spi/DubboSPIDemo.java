package com.interview.javabinterview.dubbo.spi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;

/**
 * @author Ju Baoquan
 * Created at  2020/6/3
 */
public class DubboSPIDemo {

    public static void main(String[] args) {
        DubboSPIDemo demo = new DubboSPIDemo();
        demo.method_1();//Dubbo SPI实现
        demo.method_2();//Dubbo SPI 注解机械
        demo.method_3();//Dubbo SPI ExtensionLoader的工作原理
    }

    private void method_1() {
        PrintService printService = ExtensionLoader
                .getExtensionLoader(PrintService.class)
                .getDefaultExtension();
        printService.printInfo();
    }

    private void method_2() {
        /**
         * 1. @SPI  扩展点注解
         *       value:
         *          String value() default ""; 只有一个值
         *     使用范围:
         *          类、接口、枚举类上
         *     作   用:
         *          在Dubbo中标记这个接口是一个Dubbo SPI接口,即时一个扩展点
         *
         * 2. @Adaptive 扩展点 自适应注解
         *      value:
         *          String[] value() default ""; 可以有多个值
         *     使用范围:
         *          类、接口、枚举类上
         *     作   用:
         *          (1).方法级注解: 通过参数动态获取实现类.
         *              在第一次getExtension时,会自动生成和编译一个动态的Adaptive类,从而达到动态实现类的效果
         *          (2).类级别注解: 整个实现类会直接作为默认实现. 如果有多个实现类有该注解会抛出异常
         *
         * 3. @Activate 扩展点 自动激活注解
         *      value:
         *          String[] group() default {};  URL中的分组如果匹配则激活,则可以设置多个
         *          String[] value() default {};  查找URL中如果含有该key值,则会激活
         *          String[] before() default {}; 填写扩展点列表,表示那些扩展点要在本扩展点之前
         *          String[] after() default {};  同上
         *          int order() default 0;        直接的排序信息.
         *      使用范围:
         *          类、接口、枚举类上
         *      作   用:
         *          主要是使用在有多个扩展点实现、需要根据不同条件被激活的场景中,
         *          如Filter需要多个同时激活,因为每个Filter实现的是不同的功能
         * 4.
         */
    }

    /**
     * 工作流程:
     *  ExtensionLoader 的逻辑入口有三个:
     *      1.getExtension          获取普通扩展类
     *      2.getAdaptiveExtension  获取自适应扩展类
     *      3.getActivateExtension    获取自动激活扩展类
     */
    private void method_3() {
//        ExtensionLoader<PrintService> extensionLoader = ExtensionLoader.getExtensionLoader(PrintService.class);
//        PrintService extension = extensionLoader.getExtension("");
//        PrintService adaptiveExtension = extensionLoader.getAdaptiveExtension();
//        List<PrintService> activateExtension = extensionLoader.getActivateExtension(null, null);
    }
}
