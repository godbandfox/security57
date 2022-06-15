package com.fxy.dhm.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fxy.dhm.config.RedisConfig;
import com.fxy.dhm.constant.Constants;
import com.fxy.dhm.entity.pojo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author fudonghui
 * @version 1.0
 * @date 2022/6/9 14:33
 */
@Slf4j
@Component
public class JwtUtil {
    private static final Long TEN_MINUTE = 20 * 60 * 1000L;
    private static final Long MINUTE = 60 * 1000L;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public String genJwt(LoginUser userDetails)   {
        Map<String, Object> header = new HashMap<>();
        header.put("jwt", "java");
        //  保存用户token到redis , 无需多端同在线
        // 保存验证码信息 简化UUID 无- 斜线
        String uuid = UUID.randomUUID().toString();
        // 生成 captcha_code: + UUID
        String verifyKey = Constants.LOGIN_USER_KEY + uuid;
        // 保存用户信息到redis 超时时间30分钟
        userDetails.setExpireTime(30L);
        try {
            redisTemplate.opsForValue().set(verifyKey, RedisConfig.mapper.writeValueAsString(userDetails),30 , TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
          log.error("用户信息为空或发生了未知异常");
        }

        return JWT.create()
                .withHeader(header)
                .withClaim("name", userDetails.getUsername())
                .withClaim("uuid", uuid)
                .withClaim("create", new Date())
                // 不设置超时时间,由redis控制
//                .withExpiresAt(new Date(System.currentTimeMillis() + 3000))
                .sign(Algorithm.HMAC256("secret"));
    }

    public Map<String, Claim> decodeJwt(String jwt) {
        return JWT.decode(jwt).getClaims();
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request)  {
        // 获取请求携带的令牌
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token)) {
            Map<String, Claim> authorization = null;
            try {
                authorization = decodeJwt(request.getHeader("Authorization"));
            } catch (Exception e) {
                //解不出来就是非法的token
                return null;
            }
            // 解析对应的权限以及用户信息
            Claim uuid1 = authorization.get("uuid");
            // 无效token
            if(uuid1==null){
                return null;
            }
            String uuid = uuid1.asString();
            String obj = (String) redisTemplate.opsForValue().get(Constants.LOGIN_USER_KEY + uuid);
            // redis中没有保存用户信息 已过期
            if (!StringUtils.hasText(obj)) {
                return null;
            }
            LoginUser loginUser = null;
            try {
                loginUser = RedisConfig.mapper.readValue(obj, LoginUser.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return loginUser;
        }
        return null;
    }


    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTimeMillis = System.currentTimeMillis();
        if (expireTime - currentTimeMillis <= TEN_MINUTE) {
            refreshToken(loginUser);
        }
    }

    private void refreshToken(LoginUser loginUser) {
        String token = loginUser.getUuid();
        // 刷新token
    }
}
