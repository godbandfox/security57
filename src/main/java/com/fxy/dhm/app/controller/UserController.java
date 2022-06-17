package com.fxy.dhm.app.controller;

import com.fxy.dhm.entity.pojo.LoginUser;
import com.fxy.dhm.entity.vo.R;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fudon
 * @version 1.0
 * @date 2022-06-17 14:10
 */

@RestController
@RequestMapping("/usr")
public class UserController {

    /**
     * 获取登录人信息
     *
     * @return
     */
    @GetMapping("/info")
    public R getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = (LoginUser) authentication.getPrincipal();
        user.setPassword(null);
        return R.success(user);
    }
}
