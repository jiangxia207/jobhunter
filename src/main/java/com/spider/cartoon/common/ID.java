package com.spider.cartoon.common;

import java.util.UUID;

public class ID {
    public static final String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
