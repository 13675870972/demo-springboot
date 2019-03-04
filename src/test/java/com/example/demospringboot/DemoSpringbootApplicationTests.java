package com.example.demospringboot;

import com.example.demospringboot.action.DemoSpringbootApplication;
import com.example.demospringboot.config.RsaComponent;
import com.example.demospringboot.mapper.UserInfoMapper;
import com.example.demospringboot.vo.UserInfo;
import com.example.demospringboot.vo.UserInfoExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.PrivateKey;
import java.security.PublicKey;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoSpringbootApplication.class)
public class DemoSpringbootApplicationTests {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Test
    public void contextLoads() {
    }

    @Autowired
    RsaComponent rsaComponent;

    @Test
    public void testRSA() throws Exception {
        System.err.println("-----------------------------");
        String encrypt = rsaComponent.encrypt("123");
        System.err.println(encrypt);
        System.err.println("-----------------------------");
        String decrypt = rsaComponent.decrypt(encrypt);
        System.err.println(decrypt);


    }

    @Test
    public void testDB() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("cyc");
        userInfo.setPassword("cay");
        userInfo.setSalt("123");
        userInfo.setStatus(true);
        int insert = userInfoMapper.insert(userInfo);

        System.err.println(insert);


        UserInfo userInfo1 = userInfoMapper.selectByPrimaryKey(1);
        System.err.println(userInfo1);
    }
}
