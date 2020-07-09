package com.interview.javabinterview.a_mvc.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/4
 */
@Configuration
@ConfigurationProperties(prefix = "zhaopin.pulsar")
public class CustomConfig {

    private String topic;

    private long consumerId;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(long consumerId) {
        this.consumerId = consumerId;
    }
}
