package com.fxy.dhm.captcha;


import com.fxy.dhm.constant.Constants;
import com.fxy.dhm.entity.vo.R;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Base64Utils;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @author jz
 */
@Api("验证码操作处理")
@Slf4j
@RestController
public class CaptchaController {
    public static final String CAP_MATH = "math";
    public static final String CAP_CHAR = "char";
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
    private RedisTemplate<String, Object> redisTemplate;

    // 验证码类型
    @Value("${yzm.captchaType}")
    private String captchaType;

    /**
     * 生成验证码
     */
    @ApiOperation("生成验证码")
    @GetMapping("/captchaImage")
    public R getCode(HttpServletResponse response) throws IOException {
        // 保存验证码信息 简化UUID 无- 斜线
        String uuid = UUID.randomUUID().toString();
        // 生成 captcha_code: + UUID
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        if (CAP_MATH.equals(captchaType)) {
            // 生成算数
            String capText = captchaProducerMath.createText();
            // 算数的验证码字段  1+1=?/@2
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            // 结果字段 @/2
            code = capText.substring(capText.lastIndexOf("@") + 1);
            // 生成验证码图片
            image = captchaProducerMath.createImage(capStr);
        } else if (CAP_CHAR.equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }
        // 生成前缀+uuid 缓存到redis
        redisTemplate.opsForValue().set(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return R.fail(e.getMessage());
        }

        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("uuid", uuid);
        log.info("uuid=>{}", uuid);
        log.info("code=>{}", code);
        // js需先解码
        stringObjectHashMap.put("img", Base64Utils.encode(os.toByteArray()));
        return R.success(stringObjectHashMap);
    }
}
