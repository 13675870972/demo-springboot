package com.example.demospringboot.controller;

import com.example.demospringboot.vo.TestBean;
import lombok.Cleanup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
public class TestController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/testStr")
    public String testStr(TestBean testBean, HttpServletRequest request) {
        System.err.println(testBean);
        StringBuffer stringBuffer = new StringBuffer();
        try {
            @Cleanup BufferedReader bufferedReader = null;
            @Cleanup InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                @Cleanup InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);
                char[] charBuffer = new char[128];
                int readBytes = -1;
                while ((readBytes = bufferedReader.read(charBuffer)) > 0) {
                    stringBuffer.append(charBuffer, 0, readBytes);
                }
            } else {
                stringBuffer.append("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String body = stringBuffer.toString();
        System.err.println(body);
        return body;
    }
}
