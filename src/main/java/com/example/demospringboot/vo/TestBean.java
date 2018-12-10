package com.example.demospringboot.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: caoyc
 * @Date: 2018/10/29 下午1:55
 * @Description:
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestBean {
    private String name;
    private String age;
}
