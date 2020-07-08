package com.zcswl.local.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoucg
 * @date 2020-07-04 21:22
 */
@RestController
@Api("测试接口")
public class CommonController {


    @GetMapping("/hello")
    @ApiOperation("hello")
    public String say() {
        return "zhoucg";
    }
}
