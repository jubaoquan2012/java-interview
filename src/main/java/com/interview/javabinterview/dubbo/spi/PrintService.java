package com.interview.javabinterview.dubbo.spi;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * spi接口
 *
 * @author Ju Baoquan
 * Created at  2020/6/3
 */


// 设置默认实现 impl = com.interview.javabinterview.dubbo.spi.PrintServiceImpl
@SPI("impl")
public interface PrintService {

    void printInfo();

}
