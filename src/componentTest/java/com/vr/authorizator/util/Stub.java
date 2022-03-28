package com.vr.authorizator.util;

import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

public class Stub {

    public static String getRandomCardNumber(){
        return RandomStringUtils.randomNumeric(16);
    }

    public static String getRandomPassword(){
        return RandomStringUtils.randomNumeric(4);
    }
}
