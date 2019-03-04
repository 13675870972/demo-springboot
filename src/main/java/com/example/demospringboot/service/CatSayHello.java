package com.example.demospringboot.service;

import org.springframework.stereotype.Service;

/**
 * @Author: caoyc
 * @Date: 2018-12-27 10:54
 * @Description:
 */
@Service("catSayHello")
public class CatSayHello implements SayHello {
    @Override
    public String say() {
        return "miao";
    }
}
