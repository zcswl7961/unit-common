//package com.zcswl.user;
//
//import com.mchz.user.client.support.McUserClient;
//import com.mchz.user.comm.vo.UserVo;
//import com.zcswl.spring.plugin.annotation.LocalReference;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * @author zhoucg
// * @date 2020-03-10 15:26
// */
//@RestController
//@RequestMapping("/admin/v1")
//public class UserController {
//
//    @Autowired
//    private McUserClient mcUserClient;
//
//    @LocalReference
//    private LocalInterface localInterface;
//
//
//    @GetMapping
//    public String userList() {
//        String s = localInterface.sayHello();
//
//        List<UserVo> userVos = mcUserClient.userList();
//        return "hello";
//    }
//}
