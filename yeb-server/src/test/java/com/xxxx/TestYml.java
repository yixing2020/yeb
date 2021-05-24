package com.xxxx;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

public class TestYml {
    @Value("${tokenHead}")
    private String tokenHead;

    @Test
    public void testYml(){
        System.out.println(tokenHead);
    }


}
