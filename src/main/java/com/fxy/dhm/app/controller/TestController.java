package com.fxy.dhm.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fudonghui
 * @version 1.0
 * @date 2022/6/14 11:46
 */
@Api("测试接口")
@RestController
@RequestMapping("/test")
public class TestController {

    @ApiOperation("测试")
    @GetMapping("/t1")
    public String test() {
        return "test";
    }
}
