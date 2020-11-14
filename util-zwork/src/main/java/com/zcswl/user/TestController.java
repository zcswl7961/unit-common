package com.zcswl.user;

import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhoucg
 * @date 2020-10-30 17:39
 */
@RestController
public class TestController {


    @GetMapping("/test")
    public String test() {
        List<SpringApplicationRunListener> springApplicationRunListeners = SpringFactoriesLoader.loadFactories(SpringApplicationRunListener.class, null);
        return "1";
    }
}
