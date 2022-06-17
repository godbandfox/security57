package com.fxy.dhm.auth.controller;

import com.fxy.dhm.auth.service.UserService;
import com.fxy.dhm.constant.Constants;
import com.fxy.dhm.entity.dto.LoginDTO;
import com.fxy.dhm.entity.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fudonghui
 * @version 1.0
 * @date 2022/6/10 14:01
 */
@RestController
@RequestMapping("/auth")
@Api("验证鉴权接口")
public class AuthController {
    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @PostMapping("/login")
    @ApiOperation("登录")
    public R login(@RequestBody LoginDTO dto) {
        // 验证验证码是否成功
        if (!dto.getCode().equals(redisTemplate.opsForValue().getAndDelete(Constants.CAPTCHA_CODE_KEY + dto.getUuid()))) {
            return R.fail("验证码错误");
        }
        return userService.login(dto.getUsername(), dto.getPassword());

    }

}
