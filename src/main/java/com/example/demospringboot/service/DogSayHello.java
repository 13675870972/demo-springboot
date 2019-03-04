package com.example.demospringboot.service;

import org.springframework.stereotype.Service;

/**
 * @Author: caoyc
 * @Date: 2018-12-27 10:54
 * @Description:
 */
@Service("dogSayHello")
public class DogSayHello implements SayHello {
    @Override
    public String say() {
        return "wowo";
    }
}
