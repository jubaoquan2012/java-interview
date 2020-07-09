package com.interview.javabinterview.dubbo.codeju;

import com.interview.javabinterview.dubbo.example.anno.DubboAnnoClientConsumerTest;
import com.interview.javabinterview.dubbo.example.anno.DubboAnnoServiceProviderTest;
import com.interview.javabinterview.dubbo.example.api.DubboApiClientConsumerTest;
import com.interview.javabinterview.dubbo.example.xml.DubboXmlClientConsumerTest;
import com.interview.javabinterview.dubbo.example.xml.DubboXmlServiceProviderTest;

/**
 * dubbo开发三种方式
 *
 * @author Ju Baoquan
 * Created at  2020/6/2
 */
public class DubboQuickDemo {

    /** 基于XML的方式 */
    public void xmlStart() {
        DubboXmlClientConsumerTest xmlClientTest = new DubboXmlClientConsumerTest();
        DubboXmlServiceProviderTest xmlServiceTest = new DubboXmlServiceProviderTest();
    }

    /** 基于api的方式 */
    public void apiStart() {
        DubboApiClientConsumerTest apiClientTest = new DubboApiClientConsumerTest();
        DubboAnnoServiceProviderTest apiServiceTest = new DubboAnnoServiceProviderTest();
    }

    /** 基于注解的方式 */
    public void annoStart() {
        DubboAnnoClientConsumerTest annoClientTest = new DubboAnnoClientConsumerTest();
        DubboAnnoServiceProviderTest annoServiceTest = new DubboAnnoServiceProviderTest();
    }
}
