package com.fxy.dhm.auth.service.impl;

import com.fxy.dhm.auth.service.UserService;
import com.fxy.dhm.entity.pojo.LoginUser;
import com.fxy.dhm.entity.vo.R;
import com.fxy.dhm.security.service.UserDetailsServiceImpl;
import com.fxy.dhm.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fudonghui
 * @version 1.0
 * @date 2022/6/10 14:47
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource(type= UserDetailsServiceImpl.class)
    UserDetailsService userDetailsService;
    @Resource
    PasswordEncoder passwordEncoder;
    @Resource
    JwtUtil jwtUtil;

    @Override
    public R login(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (StringUtils.isBlank(userDetails.getUsername()) || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return R.fail("用户名或密码不正确");
        }
        // 把登录的信息保存到全局
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        String token = jwtUtil.genJwt( (LoginUser)userDetails );
        return R.success(token);
    }

/*    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("xxx");
        System.out.println(encode   );
    }*/
}

