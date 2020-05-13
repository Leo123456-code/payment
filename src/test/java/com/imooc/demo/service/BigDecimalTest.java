package com.imooc.demo.service;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * ClassName: BigDecimalTest
 * Description: TODO解决精度丢失的问题
 * Author: Leo
 * Date: 2020/3/15-22:07
 * email 1437665365@qq.com
 */
public class BigDecimalTest {
    @Test
    public void mush(){
        new BigDecimal(0.01);
    }
}
