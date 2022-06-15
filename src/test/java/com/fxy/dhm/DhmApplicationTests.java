package com.fxy.dhm;

import com.fxy.dhm.util.JwtUtil;
import com.google.code.kaptcha.Producer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Resource;

@SpringBootTest(classes = DhmApplication.class)
class DhmApplicationTests {
    /**
     * google 验证码生成工具
     */
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    /**
     * google 验证码生成工具
     */
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Resource
    private JwtUtil jwtUtil;

    @Test
    void contextLoads() {
/*        String text = captchaProducer.createText();
        String text1 = captchaProducerMath.createText();
        System.out.println(text);
        System.out.println(text1);*/
//        String s = jwtUtil.genJwt(new LoginUser());
//        System.out.println(s);
//        String s1 = jwtUtil.decodeJwt(s);
//        System.out.println(s1);
    }



    public static void main(String[] args) {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("user")
                .build();
        System.out.println(user.getPassword());
    }

}
